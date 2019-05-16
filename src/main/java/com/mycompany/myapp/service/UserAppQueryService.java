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

import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserAppRepository;
import com.mycompany.myapp.service.dto.UserAppCriteria;

/**
 * Service for executing complex queries for UserApp entities in the database.
 * The main input is a {@link UserAppCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserApp} or a {@link Page} of {@link UserApp} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserAppQueryService extends QueryService<UserApp> {

    private final Logger log = LoggerFactory.getLogger(UserAppQueryService.class);

    private final UserAppRepository userAppRepository;

    public UserAppQueryService(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    /**
     * Return a {@link List} of {@link UserApp} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserApp> findByCriteria(UserAppCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserApp> specification = createSpecification(criteria);
        return userAppRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserApp} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserApp> findByCriteria(UserAppCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserApp> specification = createSpecification(criteria);
        return userAppRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserAppCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserApp> specification = createSpecification(criteria);
        return userAppRepository.count(specification);
    }

    /**
     * Function to convert UserAppCriteria to a {@link Specification}
     */
    private Specification<UserApp> createSpecification(UserAppCriteria criteria) {
        Specification<UserApp> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserApp_.id));
            }
            if (criteria.getAssigmentUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getAssigmentUserId(),
                    root -> root.join(UserApp_.assigmentUsers, JoinType.LEFT).get(UserIncidentAssigment_.id)));
            }
            if (criteria.getIncidentClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getIncidentClientId(),
                    root -> root.join(UserApp_.incidentClients, JoinType.LEFT).get(Incident_.id)));
            }
            if (criteria.getUserThirdpartyMembershipId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserThirdpartyMembershipId(),
                    root -> root.join(UserApp_.userThirdpartyMembership, JoinType.LEFT).get(UserThirdpartyMembership_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserApp_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
