package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Thirdparty;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Thirdparty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThirdpartyRepository extends JpaRepository<Thirdparty, Long> {

}
