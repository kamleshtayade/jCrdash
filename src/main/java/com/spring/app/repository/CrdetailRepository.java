package com.spring.app.repository;

import com.spring.app.domain.Crdetail;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Crdetail entity.
 */
public interface CrdetailRepository extends JpaRepository<Crdetail,Long> {

}
