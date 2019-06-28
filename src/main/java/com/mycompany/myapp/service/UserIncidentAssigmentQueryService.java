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

import com.mycompany.myapp.domain.UserIncidentAssigment;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserIncidentAssigmentRepository;
import com.mycompany.myapp.service.dto.UserIncidentAssigmentCriteria;

/**
 * Service for executing complex queries for UserIncidentAssigment entities in the database.
 * The main input is a {@link UserIncidentAssigmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserIncidentAssigment} or a {@link Page} of {@link UserIncidentAssigment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserIncidentAssigmentQueryService extends QueryService<UserIncidentAssigment> {

    private final Logger log = LoggerFactory.getLogger(UserIncidentAssigmentQueryService.class);

    private final UserIncidentAssigmentRepository userIncidentAssigmentRepository;

    public UserIncidentAssigmentQueryService(UserIncidentAssigmentRepository userIncidentAssigmentRepository) {
        this.userIncidentAssigmentRepository = userIncidentAssigmentRepository;
    }

    /**
     * Return a {@link List} of {@link UserIncidentAssigment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserIncidentAssigment> findByCriteria(UserIncidentAssigmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserIncidentAssigment> specification = createSpecification(criteria);
        return userIncidentAssigmentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserIncidentAssigment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserIncidentAssigment> findByCriteria(UserIncidentAssigmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserIncidentAssigment> specification = createSpecification(criteria);
        return userIncidentAssigmentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserIncidentAssigmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserIncidentAssigment> specification = createSpecification(criteria);
        return userIncidentAssigmentRepository.count(specification);
    }

    /**
     * Function to convert UserIncidentAssigmentCriteria to a {@link Specification}
     */
    private Specification<UserIncidentAssigment> createSpecification(UserIncidentAssigmentCriteria criteria) {
        Specification<UserIncidentAssigment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserIncidentAssigment_.id));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateDebut(), UserIncidentAssigment_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateFin(), UserIncidentAssigment_.dateFin));
            }
            if (criteria.getCommentaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentaire(), UserIncidentAssigment_.commentaire));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), UserIncidentAssigment_.status));
            }
            if (criteria.getUserAppId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserAppId(),
                    root -> root.join(UserIncidentAssigment_.userApp, JoinType.LEFT).get(UserApp_.id)));
            }
            if (criteria.getIncidentId() != null) {
                specification = specification.and(buildSpecification(criteria.getIncidentId(),
                    root -> root.join(UserIncidentAssigment_.incident, JoinType.LEFT).get(Incident_.id)));
            }
        }
        return specification;
    }
}
