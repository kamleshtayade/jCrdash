package com.spring.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.spring.app.domain.Crdetail;
import com.spring.app.repository.CrdetailRepository;
import com.spring.app.repository.search.CrdetailSearchRepository;
import com.spring.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Crdetail.
 */
@RestController
@RequestMapping("/api")
public class CrdetailResource {

    private final Logger log = LoggerFactory.getLogger(CrdetailResource.class);

    @Inject
    private CrdetailRepository crdetailRepository;

    @Inject
    private CrdetailSearchRepository crdetailSearchRepository;

    /**
     * POST  /crdetails -> Create a new crdetail.
     */
    @RequestMapping(value = "/crdetails",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody Crdetail crdetail) throws URISyntaxException {
        log.debug("REST request to save Crdetail : {}", crdetail);
        if (crdetail.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new crdetail cannot already have an ID").build();
        }
        crdetailRepository.save(crdetail);
        crdetailSearchRepository.save(crdetail);
        return ResponseEntity.created(new URI("/api/crdetails/" + crdetail.getId())).build();
    }

    /**
     * PUT  /crdetails -> Updates an existing crdetail.
     */
    @RequestMapping(value = "/crdetails",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody Crdetail crdetail) throws URISyntaxException {
        log.debug("REST request to update Crdetail : {}", crdetail);
        if (crdetail.getId() == null) {
            return create(crdetail);
        }
        crdetailRepository.save(crdetail);
        crdetailSearchRepository.save(crdetail);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /crdetails -> get all the crdetails.
     */
    @RequestMapping(value = "/crdetails",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Crdetail>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<Crdetail> page = crdetailRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/crdetails", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /crdetails/:id -> get the "id" crdetail.
     */
    @RequestMapping(value = "/crdetails/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Crdetail> get(@PathVariable Long id) {
        log.debug("REST request to get Crdetail : {}", id);
        return Optional.ofNullable(crdetailRepository.findOne(id))
            .map(crdetail -> new ResponseEntity<>(
                crdetail,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /crdetails/:id -> delete the "id" crdetail.
     */
    @RequestMapping(value = "/crdetails/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Crdetail : {}", id);
        crdetailRepository.delete(id);
        crdetailSearchRepository.delete(id);
    }

    /**
     * SEARCH  /_search/crdetails/:query -> search for the crdetail corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/crdetails/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Crdetail> search(@PathVariable String query) {
        return StreamSupport
            .stream(crdetailSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
