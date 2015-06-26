package com.spring.app.web.rest;

import com.spring.app.Application;
import com.spring.app.domain.Crdetail;
import com.spring.app.repository.CrdetailRepository;
import com.spring.app.repository.search.CrdetailSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CrdetailResource REST controller.
 *
 * @see CrdetailResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CrdetailResourceTest {

    private static final String DEFAULT_CHANGE_ID = "SAMPLE_TEXT";
    private static final String UPDATED_CHANGE_ID = "UPDATED_TEXT";
    private static final String DEFAULT_CHANGE_SUMMARY = "SAMPLE_TEXT";
    private static final String UPDATED_CHANGE_SUMMARY = "UPDATED_TEXT";
    private static final String DEFAULT_CH_SCHD_FISCAL_WEEK = "SAMPLE_TEXT";
    private static final String UPDATED_CH_SCHD_FISCAL_WEEK = "UPDATED_TEXT";
    private static final String DEFAULT_CH_SCHD_FISCAL_MTH = "SAMPLE_TEXT";
    private static final String UPDATED_CH_SCHD_FISCAL_MTH = "UPDATED_TEXT";
    private static final String DEFAULT_CI_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_CI_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CI_PRIORITY = "SAMPLE_TEXT";
    private static final String UPDATED_CI_PRIORITY = "UPDATED_TEXT";
    private static final String DEFAULT_DEPENDENT_CI_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_DEPENDENT_CI_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_PARENT_CI_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_PARENT_CI_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_CI_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_CI_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_CHANGE_STATUS = "SAMPLE_TEXT";
    private static final String UPDATED_CHANGE_STATUS = "UPDATED_TEXT";

    @Inject
    private CrdetailRepository crdetailRepository;

    @Inject
    private CrdetailSearchRepository crdetailSearchRepository;

    private MockMvc restCrdetailMockMvc;

    private Crdetail crdetail;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CrdetailResource crdetailResource = new CrdetailResource();
        ReflectionTestUtils.setField(crdetailResource, "crdetailRepository", crdetailRepository);
        ReflectionTestUtils.setField(crdetailResource, "crdetailSearchRepository", crdetailSearchRepository);
        this.restCrdetailMockMvc = MockMvcBuilders.standaloneSetup(crdetailResource).build();
    }

    @Before
    public void initTest() {
        crdetail = new Crdetail();
        crdetail.setChangeId(DEFAULT_CHANGE_ID);
        crdetail.setChangeSummary(DEFAULT_CHANGE_SUMMARY);
        crdetail.setChSchdFiscalWeek(DEFAULT_CH_SCHD_FISCAL_WEEK);
        crdetail.setChSchdFiscalMth(DEFAULT_CH_SCHD_FISCAL_MTH);
        crdetail.setCiName(DEFAULT_CI_NAME);
        crdetail.setCiPriority(DEFAULT_CI_PRIORITY);
        crdetail.setDependentCiName(DEFAULT_DEPENDENT_CI_NAME);
        crdetail.setParentCiName(DEFAULT_PARENT_CI_NAME);
        crdetail.setCiType(DEFAULT_CI_TYPE);
        crdetail.setChangeStatus(DEFAULT_CHANGE_STATUS);
    }

    @Test
    @Transactional
    public void createCrdetail() throws Exception {
        int databaseSizeBeforeCreate = crdetailRepository.findAll().size();

        // Create the Crdetail
        restCrdetailMockMvc.perform(post("/api/crdetails")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(crdetail)))
                .andExpect(status().isCreated());

        // Validate the Crdetail in the database
        List<Crdetail> crdetails = crdetailRepository.findAll();
        assertThat(crdetails).hasSize(databaseSizeBeforeCreate + 1);
        Crdetail testCrdetail = crdetails.get(crdetails.size() - 1);
        assertThat(testCrdetail.getChangeId()).isEqualTo(DEFAULT_CHANGE_ID);
        assertThat(testCrdetail.getChangeSummary()).isEqualTo(DEFAULT_CHANGE_SUMMARY);
        assertThat(testCrdetail.getChSchdFiscalWeek()).isEqualTo(DEFAULT_CH_SCHD_FISCAL_WEEK);
        assertThat(testCrdetail.getChSchdFiscalMth()).isEqualTo(DEFAULT_CH_SCHD_FISCAL_MTH);
        assertThat(testCrdetail.getCiName()).isEqualTo(DEFAULT_CI_NAME);
        assertThat(testCrdetail.getCiPriority()).isEqualTo(DEFAULT_CI_PRIORITY);
        assertThat(testCrdetail.getDependentCiName()).isEqualTo(DEFAULT_DEPENDENT_CI_NAME);
        assertThat(testCrdetail.getParentCiName()).isEqualTo(DEFAULT_PARENT_CI_NAME);
        assertThat(testCrdetail.getCiType()).isEqualTo(DEFAULT_CI_TYPE);
        assertThat(testCrdetail.getChangeStatus()).isEqualTo(DEFAULT_CHANGE_STATUS);
    }

    @Test
    @Transactional
    public void getAllCrdetails() throws Exception {
        // Initialize the database
        crdetailRepository.saveAndFlush(crdetail);

        // Get all the crdetails
        restCrdetailMockMvc.perform(get("/api/crdetails"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(crdetail.getId().intValue())))
                .andExpect(jsonPath("$.[*].changeId").value(hasItem(DEFAULT_CHANGE_ID.toString())))
                .andExpect(jsonPath("$.[*].changeSummary").value(hasItem(DEFAULT_CHANGE_SUMMARY.toString())))
                .andExpect(jsonPath("$.[*].chSchdFiscalWeek").value(hasItem(DEFAULT_CH_SCHD_FISCAL_WEEK.toString())))
                .andExpect(jsonPath("$.[*].chSchdFiscalMth").value(hasItem(DEFAULT_CH_SCHD_FISCAL_MTH.toString())))
                .andExpect(jsonPath("$.[*].ciName").value(hasItem(DEFAULT_CI_NAME.toString())))
                .andExpect(jsonPath("$.[*].ciPriority").value(hasItem(DEFAULT_CI_PRIORITY.toString())))
                .andExpect(jsonPath("$.[*].dependentCiName").value(hasItem(DEFAULT_DEPENDENT_CI_NAME.toString())))
                .andExpect(jsonPath("$.[*].parentCiName").value(hasItem(DEFAULT_PARENT_CI_NAME.toString())))
                .andExpect(jsonPath("$.[*].ciType").value(hasItem(DEFAULT_CI_TYPE.toString())))
                .andExpect(jsonPath("$.[*].changeStatus").value(hasItem(DEFAULT_CHANGE_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCrdetail() throws Exception {
        // Initialize the database
        crdetailRepository.saveAndFlush(crdetail);

        // Get the crdetail
        restCrdetailMockMvc.perform(get("/api/crdetails/{id}", crdetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(crdetail.getId().intValue()))
            .andExpect(jsonPath("$.changeId").value(DEFAULT_CHANGE_ID.toString()))
            .andExpect(jsonPath("$.changeSummary").value(DEFAULT_CHANGE_SUMMARY.toString()))
            .andExpect(jsonPath("$.chSchdFiscalWeek").value(DEFAULT_CH_SCHD_FISCAL_WEEK.toString()))
            .andExpect(jsonPath("$.chSchdFiscalMth").value(DEFAULT_CH_SCHD_FISCAL_MTH.toString()))
            .andExpect(jsonPath("$.ciName").value(DEFAULT_CI_NAME.toString()))
            .andExpect(jsonPath("$.ciPriority").value(DEFAULT_CI_PRIORITY.toString()))
            .andExpect(jsonPath("$.dependentCiName").value(DEFAULT_DEPENDENT_CI_NAME.toString()))
            .andExpect(jsonPath("$.parentCiName").value(DEFAULT_PARENT_CI_NAME.toString()))
            .andExpect(jsonPath("$.ciType").value(DEFAULT_CI_TYPE.toString()))
            .andExpect(jsonPath("$.changeStatus").value(DEFAULT_CHANGE_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCrdetail() throws Exception {
        // Get the crdetail
        restCrdetailMockMvc.perform(get("/api/crdetails/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCrdetail() throws Exception {
        // Initialize the database
        crdetailRepository.saveAndFlush(crdetail);

		int databaseSizeBeforeUpdate = crdetailRepository.findAll().size();

        // Update the crdetail
        crdetail.setChangeId(UPDATED_CHANGE_ID);
        crdetail.setChangeSummary(UPDATED_CHANGE_SUMMARY);
        crdetail.setChSchdFiscalWeek(UPDATED_CH_SCHD_FISCAL_WEEK);
        crdetail.setChSchdFiscalMth(UPDATED_CH_SCHD_FISCAL_MTH);
        crdetail.setCiName(UPDATED_CI_NAME);
        crdetail.setCiPriority(UPDATED_CI_PRIORITY);
        crdetail.setDependentCiName(UPDATED_DEPENDENT_CI_NAME);
        crdetail.setParentCiName(UPDATED_PARENT_CI_NAME);
        crdetail.setCiType(UPDATED_CI_TYPE);
        crdetail.setChangeStatus(UPDATED_CHANGE_STATUS);
        restCrdetailMockMvc.perform(put("/api/crdetails")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(crdetail)))
                .andExpect(status().isOk());

        // Validate the Crdetail in the database
        List<Crdetail> crdetails = crdetailRepository.findAll();
        assertThat(crdetails).hasSize(databaseSizeBeforeUpdate);
        Crdetail testCrdetail = crdetails.get(crdetails.size() - 1);
        assertThat(testCrdetail.getChangeId()).isEqualTo(UPDATED_CHANGE_ID);
        assertThat(testCrdetail.getChangeSummary()).isEqualTo(UPDATED_CHANGE_SUMMARY);
        assertThat(testCrdetail.getChSchdFiscalWeek()).isEqualTo(UPDATED_CH_SCHD_FISCAL_WEEK);
        assertThat(testCrdetail.getChSchdFiscalMth()).isEqualTo(UPDATED_CH_SCHD_FISCAL_MTH);
        assertThat(testCrdetail.getCiName()).isEqualTo(UPDATED_CI_NAME);
        assertThat(testCrdetail.getCiPriority()).isEqualTo(UPDATED_CI_PRIORITY);
        assertThat(testCrdetail.getDependentCiName()).isEqualTo(UPDATED_DEPENDENT_CI_NAME);
        assertThat(testCrdetail.getParentCiName()).isEqualTo(UPDATED_PARENT_CI_NAME);
        assertThat(testCrdetail.getCiType()).isEqualTo(UPDATED_CI_TYPE);
        assertThat(testCrdetail.getChangeStatus()).isEqualTo(UPDATED_CHANGE_STATUS);
    }

    @Test
    @Transactional
    public void deleteCrdetail() throws Exception {
        // Initialize the database
        crdetailRepository.saveAndFlush(crdetail);

		int databaseSizeBeforeDelete = crdetailRepository.findAll().size();

        // Get the crdetail
        restCrdetailMockMvc.perform(delete("/api/crdetails/{id}", crdetail.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Crdetail> crdetails = crdetailRepository.findAll();
        assertThat(crdetails).hasSize(databaseSizeBeforeDelete - 1);
    }
}
