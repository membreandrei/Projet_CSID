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

import com.mycompany.myapp.domain.UserThirdpartyMembership;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserThirdpartyMembershipRepository;
import com.mycompany.myapp.service.dto.UserThirdpartyMembershipCriteria;

/**
 * Service for executing complex queries for UserThirdpartyMembership entities in the database.
 * The main input is a {@link UserThirdpartyMembershipCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserThirdpartyMembership} or a {@link Page} of {@link UserThirdpartyMembership} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserThirdpartyMembershipQueryService extends QueryService<UserThirdpartyMembership> {

    private final Logger log = LoggerFactory.getLogger(UserThirdpartyMembershipQueryService.class);

    private final UserThirdpartyMembershipRepository userThirdpartyMembershipRepository;

    public UserThirdpartyMembershipQueryService(UserThirdpartyMembershipRepository userThirdpartyMembershipRepository) {
        this.userThirdpartyMembershipRepository = userThirdpartyMembershipRepository;
    }

    /**
     * Return a {@link List} of {@link UserThirdpartyMembership} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserThirdpartyMembership> findByCriteria(UserThirdpartyMembershipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserThirdpartyMembership> specification = createSpecification(criteria);
        return userThirdpartyMembershipRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserThirdpartyMembership} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserThirdpartyMembership> findByCriteria(UserThirdpartyMembershipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserThirdpartyMembership> specification = createSpecification(criteria);
        return userThirdpartyMembershipRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserThirdpartyMembershipCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserThirdpartyMembership> specification = createSpecification(criteria);
        return userThirdpartyMembershipRepository.count(specification);
    }

    /**
     * Function to convert UserThirdpartyMembershipCriteria to a {@link Specification}
     */
    private Specification<UserThirdpartyMembership> createSpecification(UserThirdpartyMembershipCriteria criteria) {
        Specification<UserThirdpartyMembership> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), UserThirdpartyMembership_.id));
            }
            if (criteria.getFonction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFonction(), UserThirdpartyMembership_.fonction));
            }
            if (criteria.getSpecialite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSpecialite(), UserThirdpartyMembership_.specialite));
            }
            if (criteria.getUserMemberShipId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserMemberShipId(),
                    root -> root.join(UserThirdpartyMembership_.userMemberShips, JoinType.LEFT).get(UserApp_.id)));
            }
            if (criteria.getThirdpartyId() != null) {
                specification = specification.and(buildSpecification(criteria.getThirdpartyId(),
                    root -> root.join(UserThirdpartyMembership_.thirdparty, JoinType.LEFT).get(Thirdparty_.id)));
            }
        }
        return specification;
    }
}
