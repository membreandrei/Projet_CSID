package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.IncidentRepository;
import com.mycompany.myapp.service.dto.IncidentCriteria;

/**
 * Service for executing complex queries for Incident entities in the database.
 * The main input is a {@link IncidentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Incident} or a {@link Page} of {@link Incident} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IncidentQueryService extends QueryService<Incident> {

    private final Logger log = LoggerFactory.getLogger(IncidentQueryService.class);

    private final IncidentRepository incidentRepository;

    public IncidentQueryService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Return a {@link List} of {@link Incident} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Incident> findByCriteria(IncidentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Incident> specification = createSpecification(criteria);
        return incidentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Incident} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Incident> findByCriteria(IncidentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Incident> specification = createSpecification(criteria);
        return incidentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IncidentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Incident> specification = createSpecification(criteria);
        return incidentRepository.count(specification);
    }

    /**
     * Function to convert IncidentCriteria to a {@link Specification}
     */
    private Specification<Incident> createSpecification(IncidentCriteria criteria) {
        Specification<Incident> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Incident_.id));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), Incident_.titre));
            }
            if (criteria.getStatut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatut(), Incident_.statut));
            }
            if (criteria.getPriorite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPriorite(), Incident_.priorite));
            }
            if (criteria.getSujet() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSujet(), Incident_.sujet));
            }
            if (criteria.getCategorie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategorie(), Incident_.categorie));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Incident_.description));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateDebut(), Incident_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateFin(), Incident_.dateFin));
            }
            if (criteria.getUserAppId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserAppId(),
                    root -> root.join(Incident_.userApp, JoinType.LEFT).get(UserApp_.id)));
            }
            if (criteria.getAssigmentIncidentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssigmentIncidentId(),
                    root -> root.join(Incident_.assigmentIncidents, JoinType.LEFT).get(UserIncidentAssigment_.id)));
            }
        }
        return specification;
    }
}
