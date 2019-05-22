package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.Thirdparty;
import com.mycompany.myapp.domain.UserThirdpartyMembership;
import com.mycompany.myapp.domain.Licence;
import com.mycompany.myapp.repository.ThirdpartyRepository;
import com.mycompany.myapp.service.ThirdpartyService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.ThirdpartyCriteria;
import com.mycompany.myapp.service.ThirdpartyQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ThirdpartyResource REST controller.
 *
 * @see ThirdpartyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class ThirdpartyResourceIntTest {

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SIRET = 1;
    private static final Integer UPDATED_SIRET = 2;

    @Autowired
    private ThirdpartyRepository thirdpartyRepository;

    @Autowired
    private ThirdpartyService thirdpartyService;

    @Autowired
    private ThirdpartyQueryService thirdpartyQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restThirdpartyMockMvc;

    private Thirdparty thirdparty;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ThirdpartyResource thirdpartyResource = new ThirdpartyResource(thirdpartyService, thirdpartyQueryService);
        this.restThirdpartyMockMvc = MockMvcBuilders.standaloneSetup(thirdpartyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thirdparty createEntity(EntityManager em) {
        Thirdparty thirdparty = new Thirdparty()
            .denomination(DEFAULT_DENOMINATION)
            .siret(DEFAULT_SIRET);
        return thirdparty;
    }

    @Before
    public void initTest() {
        thirdparty = createEntity(em);
    }

    @Test
    @Transactional
    public void createThirdparty() throws Exception {
        int databaseSizeBeforeCreate = thirdpartyRepository.findAll().size();

        // Create the Thirdparty
        restThirdpartyMockMvc.perform(post("/api/thirdparties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdparty)))
            .andExpect(status().isCreated());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartyList = thirdpartyRepository.findAll();
        assertThat(thirdpartyList).hasSize(databaseSizeBeforeCreate + 1);
        Thirdparty testThirdparty = thirdpartyList.get(thirdpartyList.size() - 1);
        assertThat(testThirdparty.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testThirdparty.getSiret()).isEqualTo(DEFAULT_SIRET);
    }

    @Test
    @Transactional
    public void createThirdpartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = thirdpartyRepository.findAll().size();

        // Create the Thirdparty with an existing ID
        thirdparty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restThirdpartyMockMvc.perform(post("/api/thirdparties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdparty)))
            .andExpect(status().isBadRequest());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartyList = thirdpartyRepository.findAll();
        assertThat(thirdpartyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllThirdparties() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList
        restThirdpartyMockMvc.perform(get("/api/thirdparties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdparty.getId().intValue())))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION.toString())))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET)));
    }
    
    @Test
    @Transactional
    public void getThirdparty() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get the thirdparty
        restThirdpartyMockMvc.perform(get("/api/thirdparties/{id}", thirdparty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(thirdparty.getId().intValue()))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION.toString()))
            .andExpect(jsonPath("$.siret").value(DEFAULT_SIRET));
    }

    @Test
    @Transactional
    public void getAllThirdpartiesByDenominationIsEqualToSomething() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where denomination equals to DEFAULT_DENOMINATION
        defaultThirdpartyShouldBeFound("denomination.equals=" + DEFAULT_DENOMINATION);

        // Get all the thirdpartyList where denomination equals to UPDATED_DENOMINATION
        defaultThirdpartyShouldNotBeFound("denomination.equals=" + UPDATED_DENOMINATION);
    }

    @Test
    @Transactional
    public void getAllThirdpartiesByDenominationIsInShouldWork() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where denomination in DEFAULT_DENOMINATION or UPDATED_DENOMINATION
        defaultThirdpartyShouldBeFound("denomination.in=" + DEFAULT_DENOMINATION + "," + UPDATED_DENOMINATION);

        // Get all the thirdpartyList where denomination equals to UPDATED_DENOMINATION
        defaultThirdpartyShouldNotBeFound("denomination.in=" + UPDATED_DENOMINATION);
    }

    @Test
    @Transactional
    public void getAllThirdpartiesByDenominationIsNullOrNotNull() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where denomination is not null
        defaultThirdpartyShouldBeFound("denomination.specified=true");

        // Get all the thirdpartyList where denomination is null
        defaultThirdpartyShouldNotBeFound("denomination.specified=false");
    }

    @Test
    @Transactional
    public void getAllThirdpartiesBySiretIsEqualToSomething() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where siret equals to DEFAULT_SIRET
        defaultThirdpartyShouldBeFound("siret.equals=" + DEFAULT_SIRET);

        // Get all the thirdpartyList where siret equals to UPDATED_SIRET
        defaultThirdpartyShouldNotBeFound("siret.equals=" + UPDATED_SIRET);
    }

    @Test
    @Transactional
    public void getAllThirdpartiesBySiretIsInShouldWork() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where siret in DEFAULT_SIRET or UPDATED_SIRET
        defaultThirdpartyShouldBeFound("siret.in=" + DEFAULT_SIRET + "," + UPDATED_SIRET);

        // Get all the thirdpartyList where siret equals to UPDATED_SIRET
        defaultThirdpartyShouldNotBeFound("siret.in=" + UPDATED_SIRET);
    }

    @Test
    @Transactional
    public void getAllThirdpartiesBySiretIsNullOrNotNull() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where siret is not null
        defaultThirdpartyShouldBeFound("siret.specified=true");

        // Get all the thirdpartyList where siret is null
        defaultThirdpartyShouldNotBeFound("siret.specified=false");
    }

    @Test
    @Transactional
    public void getAllThirdpartiesBySiretIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where siret greater than or equals to DEFAULT_SIRET
        defaultThirdpartyShouldBeFound("siret.greaterOrEqualThan=" + DEFAULT_SIRET);

        // Get all the thirdpartyList where siret greater than or equals to UPDATED_SIRET
        defaultThirdpartyShouldNotBeFound("siret.greaterOrEqualThan=" + UPDATED_SIRET);
    }

    @Test
    @Transactional
    public void getAllThirdpartiesBySiretIsLessThanSomething() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

        // Get all the thirdpartyList where siret less than or equals to DEFAULT_SIRET
        defaultThirdpartyShouldNotBeFound("siret.lessThan=" + DEFAULT_SIRET);

        // Get all the thirdpartyList where siret less than or equals to UPDATED_SIRET
        defaultThirdpartyShouldBeFound("siret.lessThan=" + UPDATED_SIRET);
    }


    @Test
    @Transactional
    public void getAllThirdpartiesByThirdpartyMemberShipIsEqualToSomething() throws Exception {
        // Initialize the database
        UserThirdpartyMembership thirdpartyMemberShip = UserThirdpartyMembershipResourceIntTest.createEntity(em);
        em.persist(thirdpartyMemberShip);
        em.flush();
        thirdparty.addThirdpartyMemberShip(thirdpartyMemberShip);
        thirdpartyRepository.saveAndFlush(thirdparty);
        Long thirdpartyMemberShipId = thirdpartyMemberShip.getId();

        // Get all the thirdpartyList where thirdpartyMemberShip equals to thirdpartyMemberShipId
        defaultThirdpartyShouldBeFound("thirdpartyMemberShipId.equals=" + thirdpartyMemberShipId);

        // Get all the thirdpartyList where thirdpartyMemberShip equals to thirdpartyMemberShipId + 1
        defaultThirdpartyShouldNotBeFound("thirdpartyMemberShipId.equals=" + (thirdpartyMemberShipId + 1));
    }


    @Test
    @Transactional
    public void getAllThirdpartiesByThirdpartyLicenceIsEqualToSomething() throws Exception {
        // Initialize the database
        Licence thirdpartyLicence = LicenceResourceIntTest.createEntity(em);
        em.persist(thirdpartyLicence);
        em.flush();
        thirdparty.addThirdpartyLicence(thirdpartyLicence);
        thirdpartyRepository.saveAndFlush(thirdparty);
        Long thirdpartyLicenceId = thirdpartyLicence.getId();

        // Get all the thirdpartyList where thirdpartyLicence equals to thirdpartyLicenceId
        defaultThirdpartyShouldBeFound("thirdpartyLicenceId.equals=" + thirdpartyLicenceId);

        // Get all the thirdpartyList where thirdpartyLicence equals to thirdpartyLicenceId + 1
        defaultThirdpartyShouldNotBeFound("thirdpartyLicenceId.equals=" + (thirdpartyLicenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultThirdpartyShouldBeFound(String filter) throws Exception {
        restThirdpartyMockMvc.perform(get("/api/thirdparties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thirdparty.getId().intValue())))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].siret").value(hasItem(DEFAULT_SIRET)));

        // Check, that the count call also returns 1
        restThirdpartyMockMvc.perform(get("/api/thirdparties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultThirdpartyShouldNotBeFound(String filter) throws Exception {
        restThirdpartyMockMvc.perform(get("/api/thirdparties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restThirdpartyMockMvc.perform(get("/api/thirdparties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingThirdparty() throws Exception {
        // Get the thirdparty
        restThirdpartyMockMvc.perform(get("/api/thirdparties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdparty() throws Exception {
        // Initialize the database
        thirdpartyService.save(thirdparty);

        int databaseSizeBeforeUpdate = thirdpartyRepository.findAll().size();

        // Update the thirdparty
        Thirdparty updatedThirdparty = thirdpartyRepository.findById(thirdparty.getId()).get();
        // Disconnect from session so that the updates on updatedThirdparty are not directly saved in db
        em.detach(updatedThirdparty);
        updatedThirdparty
            .denomination(UPDATED_DENOMINATION)
            .siret(UPDATED_SIRET);

        restThirdpartyMockMvc.perform(put("/api/thirdparties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedThirdparty)))
            .andExpect(status().isOk());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartyList = thirdpartyRepository.findAll();
        assertThat(thirdpartyList).hasSize(databaseSizeBeforeUpdate);
        Thirdparty testThirdparty = thirdpartyList.get(thirdpartyList.size() - 1);
        assertThat(testThirdparty.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testThirdparty.getSiret()).isEqualTo(UPDATED_SIRET);
    }

    @Test
    @Transactional
    public void updateNonExistingThirdparty() throws Exception {
        int databaseSizeBeforeUpdate = thirdpartyRepository.findAll().size();

        // Create the Thirdparty

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThirdpartyMockMvc.perform(put("/api/thirdparties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(thirdparty)))
            .andExpect(status().isBadRequest());

        // Validate the Thirdparty in the database
        List<Thirdparty> thirdpartyList = thirdpartyRepository.findAll();
        assertThat(thirdpartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteThirdparty() throws Exception {
        // Initialize the database
        thirdpartyService.save(thirdparty);

        int databaseSizeBeforeDelete = thirdpartyRepository.findAll().size();

        // Delete the thirdparty
        restThirdpartyMockMvc.perform(delete("/api/thirdparties/{id}", thirdparty.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Thirdparty> thirdpartyList = thirdpartyRepository.findAll();
        assertThat(thirdpartyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thirdparty.class);
        Thirdparty thirdparty1 = new Thirdparty();
        thirdparty1.setId(1L);
        Thirdparty thirdparty2 = new Thirdparty();
        thirdparty2.setId(thirdparty1.getId());
        assertThat(thirdparty1).isEqualTo(thirdparty2);
        thirdparty2.setId(2L);
        assertThat(thirdparty1).isNotEqualTo(thirdparty2);
        thirdparty1.setId(null);
        assertThat(thirdparty1).isNotEqualTo(thirdparty2);
    }
}
