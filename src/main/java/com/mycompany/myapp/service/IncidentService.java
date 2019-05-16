package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.repository.IncidentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Incident.
 */
@Service
@Transactional
public class IncidentService {

    private final Logger log = LoggerFactory.getLogger(IncidentService.class);

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Save a incident.
     *
     * @param incident the entity to save
     * @return the persisted entity
     */
    public Incident save(Incident incident) {
        log.debug("Request to save Incident : {}", incident);
        return incidentRepository.save(incident);
    }

    /**
     * Get all the incidents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Incident> findAll() {
        log.debug("Request to get all Incidents");
        return incidentRepository.findAll();
    }


    /**
     * Get one incident by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Incident> findOne(Long id) {
        log.debug("Request to get Incident : {}", id);
        return incidentRepository.findById(id);
    }

    /**
     * Delete the incident by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Incident : {}", id);
        incidentRepository.deleteById(id);
    }
}
