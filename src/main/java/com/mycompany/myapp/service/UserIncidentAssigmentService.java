package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserIncidentAssigment;
import com.mycompany.myapp.repository.UserIncidentAssigmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing UserIncidentAssigment.
 */
@Service
@Transactional
public class UserIncidentAssigmentService {

    private final Logger log = LoggerFactory.getLogger(UserIncidentAssigmentService.class);

    private final UserIncidentAssigmentRepository userIncidentAssigmentRepository;

    public UserIncidentAssigmentService(UserIncidentAssigmentRepository userIncidentAssigmentRepository) {
        this.userIncidentAssigmentRepository = userIncidentAssigmentRepository;
    }

    /**
     * Save a userIncidentAssigment.
     *
     * @param userIncidentAssigment the entity to save
     * @return the persisted entity
     */
    public UserIncidentAssigment save(UserIncidentAssigment userIncidentAssigment) {
        log.debug("Request to save UserIncidentAssigment : {}", userIncidentAssigment);
        return userIncidentAssigmentRepository.save(userIncidentAssigment);
    }

    /**
     * Get all the userIncidentAssigments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserIncidentAssigment> findAll() {
        log.debug("Request to get all UserIncidentAssigments");
        return userIncidentAssigmentRepository.findAll();
    }


    /**
     * Get one userIncidentAssigment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserIncidentAssigment> findOne(Long id) {
        log.debug("Request to get UserIncidentAssigment : {}", id);
        return userIncidentAssigmentRepository.findById(id);
    }

    /**
     * Delete the userIncidentAssigment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserIncidentAssigment : {}", id);
        userIncidentAssigmentRepository.deleteById(id);
    }
}
