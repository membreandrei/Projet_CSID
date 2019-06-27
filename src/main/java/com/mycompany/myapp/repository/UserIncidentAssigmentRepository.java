package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserIncidentAssigment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserIncidentAssigment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserIncidentAssigmentRepository extends JpaRepository<UserIncidentAssigment, Long>, JpaSpecificationExecutor<UserIncidentAssigment> {

}
