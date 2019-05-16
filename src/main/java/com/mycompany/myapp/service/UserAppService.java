package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.repository.UserAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing UserApp.
 */
@Service
@Transactional
public class UserAppService {

    private final Logger log = LoggerFactory.getLogger(UserAppService.class);

    private final UserAppRepository userAppRepository;

    public UserAppService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    /**
     * Save a userApp.
     *
     * @param userApp the entity to save
     * @return the persisted entity
     */
    public UserApp save(UserApp userApp) {
        log.debug("Request to save UserApp : {}", userApp);
        return userAppRepository.save(userApp);
    }

    /**
     * Get all the userApps.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<UserApp> findAll() {
        log.debug("Request to get all UserApps");
        return userAppRepository.findAll();
    }


    /**
     * Get one userApp by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<UserApp> findOne(Long id) {
        log.debug("Request to get UserApp : {}", id);
        return userAppRepository.findById(id);
    }

    /**
     * Delete the userApp by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserApp : {}", id);
        userAppRepository.deleteById(id);
    }
}
