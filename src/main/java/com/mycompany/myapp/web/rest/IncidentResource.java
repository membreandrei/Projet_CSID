package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.repository.IncidentRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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

    private final IncidentRepository incidentRepository;

    public IncidentResource(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
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
        Incident result = incidentRepository.save(incident);
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
        Incident result = incidentRepository.save(incident);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, incident.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incidents : get all the incidents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incidents in body
     */
    @GetMapping("/incidents")
    public List<Incident> getAllIncidents() {
        log.debug("REST request to get all Incidents");
        return incidentRepository.findAll();
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
        Optional<Incident> incident = incidentRepository.findById(id);
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
        incidentRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
