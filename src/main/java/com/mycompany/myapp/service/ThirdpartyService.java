package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Thirdparty;
import com.mycompany.myapp.repository.ThirdpartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Thirdparty.
 */
@Service
@Transactional
public class ThirdpartyService {

    private final Logger log = LoggerFactory.getLogger(ThirdpartyService.class);

    private final ThirdpartyRepository thirdpartyRepository;

    public ThirdpartyService(ThirdpartyRepository thirdpartyRepository) {
        this.thirdpartyRepository = thirdpartyRepository;
    }

    /**
     * Save a thirdparty.
     *
     * @param thirdparty the entity to save
     * @return the persisted entity
     */
    public Thirdparty save(Thirdparty thirdparty) {
        log.debug("Request to save Thirdparty : {}", thirdparty);
        return thirdpartyRepository.save(thirdparty);
    }

    /**
     * Get all the thirdparties.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Thirdparty> findAll() {
        log.debug("Request to get all Thirdparties");
        return thirdpartyRepository.findAll();
    }


    /**
     * Get one thirdparty by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Thirdparty> findOne(Long id) {
        log.debug("Request to get Thirdparty : {}", id);
        return thirdpartyRepository.findById(id);
    }

    /**
     * Delete the thirdparty by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Thirdparty : {}", id);
        thirdpartyRepository.deleteById(id);
    }
}
