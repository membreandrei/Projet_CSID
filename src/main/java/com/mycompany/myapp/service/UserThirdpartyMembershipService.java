package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserThirdpartyMembership;
import com.mycompany.myapp.repository.UserThirdpartyMembershipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing UserThirdpartyMembership.
 */
@Service
@Transactional
public class UserThirdpartyMembershipService {

    private final Logger log = LoggerFactory.getLogger(UserThirdpartyMembershipService.class);

    private final UserThirdpartyMembershipRepository userThirdpartyMembershipRepository;

    public UserThirdpartyMembershipService(UserThirdpartyMembershipRepository userThirdpartyMembershipRepository) {
        this.userThirdpartyMembershipRepository = userThirdpartyMembershipRepository;
    }

    /**
     * Save a userThirdpartyMembership.
     *
     * @param userThirdpartyMembership the entity to save
     * @return the persisted entity
     */
    public UserThirdpartyMembership save(UserThirdpartyMembership userThirdpartyMembership) {
        log.debug("Request to save UserThirdpartyMembership : {}", userThirdpartyMembership);
        return userThirdpartyMembershipRepository.save(userThirdpartyMembership);
    }

    /**
     * Get all the userThirdpartyMemberships.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserThirdpartyMembership> findAll() {
        log.debug("Request to get all UserThirdpartyMemberships");
        return userThirdpartyMembershipRepository.findAll();
    }


    /**
     * Get one userThirdpartyMembership by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserThirdpartyMembership> findOne(Long id) {
        log.debug("Request to get UserThirdpartyMembership : {}", id);
        return userThirdpartyMembershipRepository.findById(id);
    }

    /**
     * Delete the userThirdpartyMembership by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserThirdpartyMembership : {}", id);
        userThirdpartyMembershipRepository.deleteById(id);
    }
}
