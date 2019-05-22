package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserThirdpartyMembership;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UserThirdpartyMembership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserThirdpartyMembershipRepository extends JpaRepository<UserThirdpartyMembership, Long>, JpaSpecificationExecutor<UserThirdpartyMembership> {

}
