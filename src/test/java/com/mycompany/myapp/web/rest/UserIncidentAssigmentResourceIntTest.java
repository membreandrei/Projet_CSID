package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.UserIncidentAssigment;
import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.repository.UserIncidentAssigmentRepository;
import com.mycompany.myapp.service.UserIncidentAssigmentService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.UserIncidentAssigmentCriteria;
import com.mycompany.myapp.service.UserIncidentAssigmentQueryService;

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
 * Test class for the UserIncidentAssigmentResource REST controller.
 *
 * @see UserIncidentAssigmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class UserIncidentAssigmentResourceIntTest {

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private UserIncidentAssigmentRepository userIncidentAssigmentRepository;

    @Autowired
    private UserIncidentAssigmentService userIncidentAssigmentService;

    @Autowired
    private UserIncidentAssigmentQueryService userIncidentAssigmentQueryService;

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

    private MockMvc restUserIncidentAssigmentMockMvc;

    private UserIncidentAssigment userIncidentAssigment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserIncidentAssigmentResource userIncidentAssigmentResource = new UserIncidentAssigmentResource(userIncidentAssigmentService, userIncidentAssigmentQueryService);
        this.restUserIncidentAssigmentMockMvc = MockMvcBuilders.standaloneSetup(userIncidentAssigmentResource)
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
    public static UserIncidentAssigment createEntity(EntityManager em) {
        UserIncidentAssigment userIncidentAssigment = new UserIncidentAssigment()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .commentaire(DEFAULT_COMMENTAIRE)
            .status(DEFAULT_STATUS);
        return userIncidentAssigment;
    }

    @Before
    public void initTest() {
        userIncidentAssigment = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserIncidentAssigment() throws Exception {
        int databaseSizeBeforeCreate = userIncidentAssigmentRepository.findAll().size();

        // Create the UserIncidentAssigment
        restUserIncidentAssigmentMockMvc.perform(post("/api/user-incident-assigments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIncidentAssigment)))
            .andExpect(status().isCreated());

        // Validate the UserIncidentAssigment in the database
        List<UserIncidentAssigment> userIncidentAssigmentList = userIncidentAssigmentRepository.findAll();
        assertThat(userIncidentAssigmentList).hasSize(databaseSizeBeforeCreate + 1);
        UserIncidentAssigment testUserIncidentAssigment = userIncidentAssigmentList.get(userIncidentAssigmentList.size() - 1);
        assertThat(testUserIncidentAssigment.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testUserIncidentAssigment.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testUserIncidentAssigment.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testUserIncidentAssigment.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUserIncidentAssigmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userIncidentAssigmentRepository.findAll().size();

        // Create the UserIncidentAssigment with an existing ID
        userIncidentAssigment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserIncidentAssigmentMockMvc.perform(post("/api/user-incident-assigments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIncidentAssigment)))
            .andExpect(status().isBadRequest());

        // Validate the UserIncidentAssigment in the database
        List<UserIncidentAssigment> userIncidentAssigmentList = userIncidentAssigmentRepository.findAll();
        assertThat(userIncidentAssigmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigments() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIncidentAssigment.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getUserIncidentAssigment() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get the userIncidentAssigment
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments/{id}", userIncidentAssigment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userIncidentAssigment.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultUserIncidentAssigmentShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the userIncidentAssigmentList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultUserIncidentAssigmentShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultUserIncidentAssigmentShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the userIncidentAssigmentList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultUserIncidentAssigmentShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateDebut is not null
        defaultUserIncidentAssigmentShouldBeFound("dateDebut.specified=true");

        // Get all the userIncidentAssigmentList where dateDebut is null
        defaultUserIncidentAssigmentShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateFin equals to DEFAULT_DATE_FIN
        defaultUserIncidentAssigmentShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the userIncidentAssigmentList where dateFin equals to UPDATED_DATE_FIN
        defaultUserIncidentAssigmentShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultUserIncidentAssigmentShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the userIncidentAssigmentList where dateFin equals to UPDATED_DATE_FIN
        defaultUserIncidentAssigmentShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where dateFin is not null
        defaultUserIncidentAssigmentShouldBeFound("dateFin.specified=true");

        // Get all the userIncidentAssigmentList where dateFin is null
        defaultUserIncidentAssigmentShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByCommentaireIsEqualToSomething() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where commentaire equals to DEFAULT_COMMENTAIRE
        defaultUserIncidentAssigmentShouldBeFound("commentaire.equals=" + DEFAULT_COMMENTAIRE);

        // Get all the userIncidentAssigmentList where commentaire equals to UPDATED_COMMENTAIRE
        defaultUserIncidentAssigmentShouldNotBeFound("commentaire.equals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByCommentaireIsInShouldWork() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where commentaire in DEFAULT_COMMENTAIRE or UPDATED_COMMENTAIRE
        defaultUserIncidentAssigmentShouldBeFound("commentaire.in=" + DEFAULT_COMMENTAIRE + "," + UPDATED_COMMENTAIRE);

        // Get all the userIncidentAssigmentList where commentaire equals to UPDATED_COMMENTAIRE
        defaultUserIncidentAssigmentShouldNotBeFound("commentaire.in=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByCommentaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where commentaire is not null
        defaultUserIncidentAssigmentShouldBeFound("commentaire.specified=true");

        // Get all the userIncidentAssigmentList where commentaire is null
        defaultUserIncidentAssigmentShouldNotBeFound("commentaire.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where status equals to DEFAULT_STATUS
        defaultUserIncidentAssigmentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the userIncidentAssigmentList where status equals to UPDATED_STATUS
        defaultUserIncidentAssigmentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUserIncidentAssigmentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the userIncidentAssigmentList where status equals to UPDATED_STATUS
        defaultUserIncidentAssigmentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);

        // Get all the userIncidentAssigmentList where status is not null
        defaultUserIncidentAssigmentShouldBeFound("status.specified=true");

        // Get all the userIncidentAssigmentList where status is null
        defaultUserIncidentAssigmentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByUserAppIsEqualToSomething() throws Exception {
        // Initialize the database
        UserApp userApp = UserAppResourceIntTest.createEntity(em);
        em.persist(userApp);
        em.flush();
        userIncidentAssigment.setUserApp(userApp);
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);
        Long userAppId = userApp.getId();

        // Get all the userIncidentAssigmentList where userApp equals to userAppId
        defaultUserIncidentAssigmentShouldBeFound("userAppId.equals=" + userAppId);

        // Get all the userIncidentAssigmentList where userApp equals to userAppId + 1
        defaultUserIncidentAssigmentShouldNotBeFound("userAppId.equals=" + (userAppId + 1));
    }


    @Test
    @Transactional
    public void getAllUserIncidentAssigmentsByIncidentIsEqualToSomething() throws Exception {
        // Initialize the database
        Incident incident = IncidentResourceIntTest.createEntity(em);
        em.persist(incident);
        em.flush();
        userIncidentAssigment.setIncident(incident);
        userIncidentAssigmentRepository.saveAndFlush(userIncidentAssigment);
        Long incidentId = incident.getId();

        // Get all the userIncidentAssigmentList where incident equals to incidentId
        defaultUserIncidentAssigmentShouldBeFound("incidentId.equals=" + incidentId);

        // Get all the userIncidentAssigmentList where incident equals to incidentId + 1
        defaultUserIncidentAssigmentShouldNotBeFound("incidentId.equals=" + (incidentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultUserIncidentAssigmentShouldBeFound(String filter) throws Exception {
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userIncidentAssigment.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultUserIncidentAssigmentShouldNotBeFound(String filter) throws Exception {
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserIncidentAssigment() throws Exception {
        // Get the userIncidentAssigment
        restUserIncidentAssigmentMockMvc.perform(get("/api/user-incident-assigments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserIncidentAssigment() throws Exception {
        // Initialize the database
        userIncidentAssigmentService.save(userIncidentAssigment);

        int databaseSizeBeforeUpdate = userIncidentAssigmentRepository.findAll().size();

        // Update the userIncidentAssigment
        UserIncidentAssigment updatedUserIncidentAssigment = userIncidentAssigmentRepository.findById(userIncidentAssigment.getId()).get();
        // Disconnect from session so that the updates on updatedUserIncidentAssigment are not directly saved in db
        em.detach(updatedUserIncidentAssigment);
        updatedUserIncidentAssigment
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .commentaire(UPDATED_COMMENTAIRE)
            .status(UPDATED_STATUS);

        restUserIncidentAssigmentMockMvc.perform(put("/api/user-incident-assigments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserIncidentAssigment)))
            .andExpect(status().isOk());

        // Validate the UserIncidentAssigment in the database
        List<UserIncidentAssigment> userIncidentAssigmentList = userIncidentAssigmentRepository.findAll();
        assertThat(userIncidentAssigmentList).hasSize(databaseSizeBeforeUpdate);
        UserIncidentAssigment testUserIncidentAssigment = userIncidentAssigmentList.get(userIncidentAssigmentList.size() - 1);
        assertThat(testUserIncidentAssigment.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testUserIncidentAssigment.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testUserIncidentAssigment.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testUserIncidentAssigment.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserIncidentAssigment() throws Exception {
        int databaseSizeBeforeUpdate = userIncidentAssigmentRepository.findAll().size();

        // Create the UserIncidentAssigment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserIncidentAssigmentMockMvc.perform(put("/api/user-incident-assigments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userIncidentAssigment)))
            .andExpect(status().isBadRequest());

        // Validate the UserIncidentAssigment in the database
        List<UserIncidentAssigment> userIncidentAssigmentList = userIncidentAssigmentRepository.findAll();
        assertThat(userIncidentAssigmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserIncidentAssigment() throws Exception {
        // Initialize the database
        userIncidentAssigmentService.save(userIncidentAssigment);

        int databaseSizeBeforeDelete = userIncidentAssigmentRepository.findAll().size();

        // Delete the userIncidentAssigment
        restUserIncidentAssigmentMockMvc.perform(delete("/api/user-incident-assigments/{id}", userIncidentAssigment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserIncidentAssigment> userIncidentAssigmentList = userIncidentAssigmentRepository.findAll();
        assertThat(userIncidentAssigmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserIncidentAssigment.class);
        UserIncidentAssigment userIncidentAssigment1 = new UserIncidentAssigment();
        userIncidentAssigment1.setId(1L);
        UserIncidentAssigment userIncidentAssigment2 = new UserIncidentAssigment();
        userIncidentAssigment2.setId(userIncidentAssigment1.getId());
        assertThat(userIncidentAssigment1).isEqualTo(userIncidentAssigment2);
        userIncidentAssigment2.setId(2L);
        assertThat(userIncidentAssigment1).isNotEqualTo(userIncidentAssigment2);
        userIncidentAssigment1.setId(null);
        assertThat(userIncidentAssigment1).isNotEqualTo(userIncidentAssigment2);
    }
}
