package com.mycompany.myapp.web.rest;
import com.mycompany.myapp.domain.Thirdparty;
import com.mycompany.myapp.repository.ThirdpartyRepository;
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
 * REST controller for managing Thirdparty.
 */
@RestController
@RequestMapping("/api")
public class ThirdpartyResource {

    private final Logger log = LoggerFactory.getLogger(ThirdpartyResource.class);

    private static final String ENTITY_NAME = "thirdparty";

    private final ThirdpartyRepository thirdpartyRepository;

    public ThirdpartyResource(ThirdpartyRepository thirdpartyRepository) {
        this.thirdpartyRepository = thirdpartyRepository;
    }

    /**
     * POST  /thirdparties : Create a new thirdparty.
     *
     * @param thirdparty the thirdparty to create
     * @return the ResponseEntity with status 201 (Created) and with body the new thirdparty, or with status 400 (Bad Request) if the thirdparty has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/thirdparties")
    public ResponseEntity<Thirdparty> createThirdparty(@RequestBody Thirdparty thirdparty) throws URISyntaxException {
        log.debug("REST request to save Thirdparty : {}", thirdparty);
        if (thirdparty.getId() != null) {
            throw new BadRequestAlertException("A new thirdparty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thirdparty result = thirdpartyRepository.save(thirdparty);
        return ResponseEntity.created(new URI("/api/thirdparties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /thirdparties : Updates an existing thirdparty.
     *
     * @param thirdparty the thirdparty to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated thirdparty,
     * or with status 400 (Bad Request) if the thirdparty is not valid,
     * or with status 500 (Internal Server Error) if the thirdparty couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/thirdparties")
    public ResponseEntity<Thirdparty> updateThirdparty(@RequestBody Thirdparty thirdparty) throws URISyntaxException {
        log.debug("REST request to update Thirdparty : {}", thirdparty);
        if (thirdparty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Thirdparty result = thirdpartyRepository.save(thirdparty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, thirdparty.getId().toString()))
            .body(result);
    }

    /**
     * GET  /thirdparties : get all the thirdparties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of thirdparties in body
     */
    @GetMapping("/thirdparties")
    public List<Thirdparty> getAllThirdparties() {
        log.debug("REST request to get all Thirdparties");
        return thirdpartyRepository.findAll();
    }

    /**
     * GET  /thirdparties/:id : get the "id" thirdparty.
     *
     * @param id the id of the thirdparty to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the thirdparty, or with status 404 (Not Found)
     */
    @GetMapping("/thirdparties/{id}")
    public ResponseEntity<Thirdparty> getThirdparty(@PathVariable Long id) {
        log.debug("REST request to get Thirdparty : {}", id);
        Optional<Thirdparty> thirdparty = thirdpartyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thirdparty);
    }

    /**
     * DELETE  /thirdparties/:id : delete the "id" thirdparty.
     *
     * @param id the id of the thirdparty to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/thirdparties/{id}")
    public ResponseEntity<Void> deleteThirdparty(@PathVariable Long id) {
        log.debug("REST request to delete Thirdparty : {}", id);
        thirdpartyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
