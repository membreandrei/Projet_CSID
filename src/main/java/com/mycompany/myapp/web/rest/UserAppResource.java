package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.service.UserAppService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.UserAppCriteria;
import com.mycompany.myapp.service.UserAppQueryService;
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
 * REST controller for managing UserApp.
 */
@RestController
@RequestMapping("/api")
public class UserAppResource {

    private final Logger log = LoggerFactory.getLogger(UserAppResource.class);

    private static final String ENTITY_NAME = "userApp";

    private final UserAppService userAppService;

    private final UserAppQueryService userAppQueryService;

    public UserAppResource(UserAppService userAppService, UserAppQueryService userAppQueryService) {
        this.userAppService = userAppService;
        this.userAppQueryService = userAppQueryService;
    }

    /**
     * POST  /user-apps : Create a new userApp.
     *
     * @param userApp the userApp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userApp, or with status 400 (Bad Request) if the userApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-apps")
    public ResponseEntity<UserApp> createUserApp(@RequestBody UserApp userApp) throws URISyntaxException {
        log.debug("REST request to save UserApp : {}", userApp);
        if (userApp.getId() != null) {
            throw new BadRequestAlertException("A new userApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApp result = userAppService.save(userApp);
        return ResponseEntity.created(new URI("/api/user-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-apps : Updates an existing userApp.
     *
     * @param userApp the userApp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userApp,
     * or with status 400 (Bad Request) if the userApp is not valid,
     * or with status 500 (Internal Server Error) if the userApp couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-apps")
    public ResponseEntity<UserApp> updateUserApp(@RequestBody UserApp userApp) throws URISyntaxException {
        log.debug("REST request to update UserApp : {}", userApp);
        if (userApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApp result = userAppService.save(userApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-apps : get all the userApps.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of userApps in body
     */
    @GetMapping("/user-apps")
    public ResponseEntity<List<UserApp>> getAllUserApps(UserAppCriteria criteria) {
        log.debug("REST request to get UserApps by criteria: {}", criteria);
        List<UserApp> entityList = userAppQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /user-apps/count : count all the userApps.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/user-apps/count")
    public ResponseEntity<Long> countUserApps(UserAppCriteria criteria) {
        log.debug("REST request to count UserApps by criteria: {}", criteria);
        return ResponseEntity.ok().body(userAppQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /user-apps/:id : get the "id" userApp.
     *
     * @param id the id of the userApp to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userApp, or with status 404 (Not Found)
     */
    @GetMapping("/user-apps/{id}")
    public ResponseEntity<UserApp> getUserApp(@PathVariable Long id) {
        log.debug("REST request to get UserApp : {}", id);
        Optional<UserApp> userApp = userAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApp);
    }

    /**
     * DELETE  /user-apps/:id : delete the "id" userApp.
     *
     * @param id the id of the userApp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-apps/{id}")
    public ResponseEntity<Void> deleteUserApp(@PathVariable Long id) {
        log.debug("REST request to delete UserApp : {}", id);
        userAppService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
