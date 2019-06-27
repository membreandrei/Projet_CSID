package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.UserIncidentAssigment;
import com.mycompany.myapp.service.UserIncidentAssigmentService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.UserIncidentAssigmentCriteria;
import com.mycompany.myapp.service.UserIncidentAssigmentQueryService;
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
 * REST controller for managing UserIncidentAssigment.
 */
@RestController
@RequestMapping("/api")
public class UserIncidentAssigmentResource {

    private final Logger log = LoggerFactory.getLogger(UserIncidentAssigmentResource.class);

    private static final String ENTITY_NAME = "userIncidentAssigment";

    private final UserIncidentAssigmentService userIncidentAssigmentService;

    private final UserIncidentAssigmentQueryService userIncidentAssigmentQueryService;

    public UserIncidentAssigmentResource(UserIncidentAssigmentService userIncidentAssigmentService, UserIncidentAssigmentQueryService userIncidentAssigmentQueryService) {
        this.userIncidentAssigmentService = userIncidentAssigmentService;
        this.userIncidentAssigmentQueryService = userIncidentAssigmentQueryService;
    }

    /**
     * POST  /user-incident-assigments : Create a new userIncidentAssigment.
     *
     * @param userIncidentAssigment the userIncidentAssigment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userIncidentAssigment, or with status 400 (Bad Request) if the userIncidentAssigment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-incident-assigments")
    public ResponseEntity<UserIncidentAssigment> createUserIncidentAssigment(@RequestBody UserIncidentAssigment userIncidentAssigment) throws URISyntaxException {
        log.debug("REST request to save UserIncidentAssigment : {}", userIncidentAssigment);
        if (userIncidentAssigment.getId() != null) {
            throw new BadRequestAlertException("A new userIncidentAssigment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserIncidentAssigment result = userIncidentAssigmentService.save(userIncidentAssigment);
        return ResponseEntity.created(new URI("/api/user-incident-assigments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-incident-assigments : Updates an existing userIncidentAssigment.
     *
     * @param userIncidentAssigment the userIncidentAssigment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userIncidentAssigment,
     * or with status 400 (Bad Request) if the userIncidentAssigment is not valid,
     * or with status 500 (Internal Server Error) if the userIncidentAssigment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-incident-assigments")
    public ResponseEntity<UserIncidentAssigment> updateUserIncidentAssigment(@RequestBody UserIncidentAssigment userIncidentAssigment) throws URISyntaxException {
        log.debug("REST request to update UserIncidentAssigment : {}", userIncidentAssigment);
        if (userIncidentAssigment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserIncidentAssigment result = userIncidentAssigmentService.save(userIncidentAssigment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userIncidentAssigment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-incident-assigments : get all the userIncidentAssigments.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userIncidentAssigments in body
     */
    @GetMapping("/user-incident-assigments")
    public ResponseEntity<List<UserIncidentAssigment>> getAllUserIncidentAssigments(UserIncidentAssigmentCriteria criteria) {
        log.debug("REST request to get UserIncidentAssigments by criteria: {}", criteria);
        List<UserIncidentAssigment> entityList = userIncidentAssigmentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /user-incident-assigments/count : count all the userIncidentAssigments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/user-incident-assigments/count")
    public ResponseEntity<Long> countUserIncidentAssigments(UserIncidentAssigmentCriteria criteria) {
        log.debug("REST request to count UserIncidentAssigments by criteria: {}", criteria);
        return ResponseEntity.ok().body(userIncidentAssigmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /user-incident-assigments/:id : get the "id" userIncidentAssigment.
     *
     * @param id the id of the userIncidentAssigment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userIncidentAssigment, or with status 404 (Not Found)
     */
    @GetMapping("/user-incident-assigments/{id}")
    public ResponseEntity<UserIncidentAssigment> getUserIncidentAssigment(@PathVariable Long id) {
        log.debug("REST request to get UserIncidentAssigment : {}", id);
        Optional<UserIncidentAssigment> userIncidentAssigment = userIncidentAssigmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userIncidentAssigment);
    }

    /**
     * DELETE  /user-incident-assigments/:id : delete the "id" userIncidentAssigment.
     *
     * @param id the id of the userIncidentAssigment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-incident-assigments/{id}")
    public ResponseEntity<Void> deleteUserIncidentAssigment(@PathVariable Long id) {
        log.debug("REST request to delete UserIncidentAssigment : {}", id);
        userIncidentAssigmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
