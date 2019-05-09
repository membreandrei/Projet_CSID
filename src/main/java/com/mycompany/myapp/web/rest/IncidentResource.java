package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.service.IncidentService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.IncidentCriteria;
import com.mycompany.myapp.service.IncidentQueryService;
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
 * REST controller for managing Incident.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    private final IncidentService incidentService;

    private final IncidentQueryService incidentQueryService;

    public IncidentResource(IncidentService incidentService, IncidentQueryService incidentQueryService) {
        this.incidentService = incidentService;
        this.incidentQueryService = incidentQueryService;
    }

    /**
     * POST  /incidents : Create a new incident.
     *
     * @param incident the incident to create
     * @return the ResponseEntity with status 201 (Created) and with body the new incident, or with status 400 (Bad Request) if the incident has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incidents")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) throws URISyntaxException {
        log.debug("REST request to save Incident : {}", incident);
        if (incident.getId() != null) {
            throw new BadRequestAlertException("A new incident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Incident result = incidentService.save(incident);
        return ResponseEntity.created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incidents : Updates an existing incident.
     *
     * @param incident the incident to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated incident,
     * or with status 400 (Bad Request) if the incident is not valid,
     * or with status 500 (Internal Server Error) if the incident couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incidents")
    public ResponseEntity<Incident> updateIncident(@RequestBody Incident incident) throws URISyntaxException {
        log.debug("REST request to update Incident : {}", incident);
        if (incident.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Incident result = incidentService.save(incident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidents : get all the incidents.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/incidents")
    public ResponseEntity<List<Incident>> getAllIncidents(IncidentCriteria criteria) {
        log.debug("REST request to get Incidents by criteria: {}", criteria);
        List<Incident> entityList = incidentQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /incidents/count : count all the incidents.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/incidents/count")
    public ResponseEntity<Long> countIncidents(IncidentCriteria criteria) {
        log.debug("REST request to count Incidents by criteria: {}", criteria);
        return ResponseEntity.ok().body(incidentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /incidents/:id : get the "id" incident.
     *
     * @param id the id of the incident to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the incident, or with status 404 (Not Found)
     */
    @GetMapping("/incidents/{id}")
    public ResponseEntity<Incident> getIncident(@PathVariable Long id) {
        log.debug("REST request to get Incident : {}", id);
        Optional<Incident> incident = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incident);
    }

    /**
     * DELETE  /incidents/:id : delete the "id" incident.
     *
     * @param id the id of the incident to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incidents/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
