package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.UserThirdpartyMembership;
import com.mycompany.myapp.service.UserThirdpartyMembershipService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.UserThirdpartyMembershipCriteria;
import com.mycompany.myapp.service.UserThirdpartyMembershipQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserThirdpartyMembership.
 */
@RestController
@RequestMapping("/api")
public class UserThirdpartyMembershipResource {

    private final Logger log = LoggerFactory.getLogger(UserThirdpartyMembershipResource.class);

    private static final String ENTITY_NAME = "userThirdpartyMembership";

    private final UserThirdpartyMembershipService userThirdpartyMembershipService;

    private final UserThirdpartyMembershipQueryService userThirdpartyMembershipQueryService;

    public UserThirdpartyMembershipResource(UserThirdpartyMembershipService userThirdpartyMembershipService, UserThirdpartyMembershipQueryService userThirdpartyMembershipQueryService) {
        this.userThirdpartyMembershipService = userThirdpartyMembershipService;
        this.userThirdpartyMembershipQueryService = userThirdpartyMembershipQueryService;
    }

    /**
     * POST  /user-thirdparty-memberships : Create a new userThirdpartyMembership.
     *
     * @param userThirdpartyMembership the userThirdpartyMembership to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userThirdpartyMembership, or with status 400 (Bad Request) if the userThirdpartyMembership has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-thirdparty-memberships")
    public ResponseEntity<UserThirdpartyMembership> createUserThirdpartyMembership(@RequestBody UserThirdpartyMembership userThirdpartyMembership) throws URISyntaxException {
        log.debug("REST request to save UserThirdpartyMembership : {}", userThirdpartyMembership);
        if (userThirdpartyMembership.getId() != null) {
            throw new BadRequestAlertException("A new userThirdpartyMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserThirdpartyMembership result = userThirdpartyMembershipService.save(userThirdpartyMembership);
        return ResponseEntity.created(new URI("/api/user-thirdparty-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-thirdparty-memberships : Updates an existing userThirdpartyMembership.
     *
     * @param userThirdpartyMembership the userThirdpartyMembership to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userThirdpartyMembership,
     * or with status 400 (Bad Request) if the userThirdpartyMembership is not valid,
     * or with status 500 (Internal Server Error) if the userThirdpartyMembership couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-thirdparty-memberships")
    public ResponseEntity<UserThirdpartyMembership> updateUserThirdpartyMembership(@RequestBody UserThirdpartyMembership userThirdpartyMembership) throws URISyntaxException {
        log.debug("REST request to update UserThirdpartyMembership : {}", userThirdpartyMembership);
        if (userThirdpartyMembership.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserThirdpartyMembership result = userThirdpartyMembershipService.save(userThirdpartyMembership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userThirdpartyMembership.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-thirdparty-memberships : get all the userThirdpartyMemberships.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userThirdpartyMemberships in body
     */
    @GetMapping("/user-thirdparty-memberships")
    public ResponseEntity<List<UserThirdpartyMembership>> getAllUserThirdpartyMemberships(UserThirdpartyMembershipCriteria criteria) {
        log.debug("REST request to get UserThirdpartyMemberships by criteria: {}", criteria);
        List<UserThirdpartyMembership> entityList = userThirdpartyMembershipQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /user-thirdparty-memberships/count : count all the userThirdpartyMemberships.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/user-thirdparty-memberships/count")
    public ResponseEntity<Long> countUserThirdpartyMemberships(UserThirdpartyMembershipCriteria criteria) {
        log.debug("REST request to count UserThirdpartyMemberships by criteria: {}", criteria);
        return ResponseEntity.ok().body(userThirdpartyMembershipQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /user-thirdparty-memberships/:id : get the "id" userThirdpartyMembership.
     *
     * @param id the id of the userThirdpartyMembership to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userThirdpartyMembership, or with status 404 (Not Found)
     */
    @GetMapping("/user-thirdparty-memberships/{id}")
    public ResponseEntity<UserThirdpartyMembership> getUserThirdpartyMembership(@PathVariable Long id) {
        log.debug("REST request to get UserThirdpartyMembership : {}", id);
        Optional<UserThirdpartyMembership> userThirdpartyMembership = userThirdpartyMembershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userThirdpartyMembership);
    }

    /**
     * DELETE  /user-thirdparty-memberships/:id : delete the "id" userThirdpartyMembership.
     *
     * @param id the id of the userThirdpartyMembership to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-thirdparty-memberships/{id}")
    public ResponseEntity<Void> deleteUserThirdpartyMembership(@PathVariable Long id) {
        log.debug("REST request to delete UserThirdpartyMembership : {}", id);
        userThirdpartyMembershipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
