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

import com.mycompany.myapp.domain.Thirdparty;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ThirdpartyRepository;
import com.mycompany.myapp.service.dto.ThirdpartyCriteria;

/**
 * Service for executing complex queries for Thirdparty entities in the database.
 * The main input is a {@link ThirdpartyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Thirdparty} or a {@link Page} of {@link Thirdparty} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ThirdpartyQueryService extends QueryService<Thirdparty> {

    private final Logger log = LoggerFactory.getLogger(ThirdpartyQueryService.class);

    private final ThirdpartyRepository thirdpartyRepository;

    public ThirdpartyQueryService(ThirdpartyRepository thirdpartyRepository) {
        this.thirdpartyRepository = thirdpartyRepository;
    }

    /**
     * Return a {@link List} of {@link Thirdparty} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Thirdparty> findByCriteria(ThirdpartyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Thirdparty> specification = createSpecification(criteria);
        return thirdpartyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Thirdparty} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Thirdparty> findByCriteria(ThirdpartyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Thirdparty> specification = createSpecification(criteria);
        return thirdpartyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ThirdpartyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Thirdparty> specification = createSpecification(criteria);
        return thirdpartyRepository.count(specification);
    }

    /**
     * Function to convert ThirdpartyCriteria to a {@link Specification}
     */
    private Specification<Thirdparty> createSpecification(ThirdpartyCriteria criteria) {
        Specification<Thirdparty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Thirdparty_.id));
            }
            if (criteria.getDenomination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDenomination(), Thirdparty_.denomination));
            }
            if (criteria.getSiret() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSiret(), Thirdparty_.siret));
            }
            if (criteria.getThirdpartyMemberShipId() != null) {
                specification = specification.and(buildSpecification(criteria.getThirdpartyMemberShipId(),
                    root -> root.join(Thirdparty_.thirdpartyMemberShips, JoinType.LEFT).get(UserThirdpartyMembership_.id)));
            }
            if (criteria.getThirdpartyLicenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getThirdpartyLicenceId(),
                    root -> root.join(Thirdparty_.thirdpartyLicences, JoinType.LEFT).get(Licence_.id)));
            }
        }
        return specification;
    }
}
