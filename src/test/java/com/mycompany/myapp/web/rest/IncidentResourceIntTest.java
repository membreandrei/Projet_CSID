package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.Incident;
import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.domain.UserIncidentAssigment;
import com.mycompany.myapp.repository.IncidentRepository;
import com.mycompany.myapp.service.IncidentService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;
import com.mycompany.myapp.service.dto.IncidentCriteria;
import com.mycompany.myapp.service.IncidentQueryService;

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
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class IncidentResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_PRIORITE = "AAAAAAAAAA";
    private static final String UPDATED_PRIORITE = "BBBBBBBBBB";

    private static final String DEFAULT_SUJET = "AAAAAAAAAA";
    private static final String UPDATED_SUJET = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DEBUT = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DEBUT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_FIN = "AAAAAAAAAA";
    private static final String UPDATED_DATE_FIN = "BBBBBBBBBB";

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private IncidentQueryService incidentQueryService;

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

    private MockMvc restIncidentMockMvc;

    private Incident incident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentResource incidentResource = new IncidentResource(incidentService, incidentQueryService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
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
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .titre(DEFAULT_TITRE)
            .statut(DEFAULT_STATUT)
            .priorite(DEFAULT_PRIORITE)
            .sujet(DEFAULT_SUJET)
            .categorie(DEFAULT_CATEGORIE)
            .description(DEFAULT_DESCRIPTION)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN);
        return incident;
    }

    @Before
    public void initTest() {
        incident = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testIncident.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testIncident.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testIncident.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testIncident.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testIncident.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIncident.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testIncident.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
    }

    @Test
    @Transactional
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT.toString())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE.toString())))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())));
    }
    
    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT.toString()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()));
    }

    @Test
    @Transactional
    public void getAllIncidentsByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where titre equals to DEFAULT_TITRE
        defaultIncidentShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the incidentList where titre equals to UPDATED_TITRE
        defaultIncidentShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultIncidentShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the incidentList where titre equals to UPDATED_TITRE
        defaultIncidentShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where titre is not null
        defaultIncidentShouldBeFound("titre.specified=true");

        // Get all the incidentList where titre is null
        defaultIncidentShouldNotBeFound("titre.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByStatutIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where statut equals to DEFAULT_STATUT
        defaultIncidentShouldBeFound("statut.equals=" + DEFAULT_STATUT);

        // Get all the incidentList where statut equals to UPDATED_STATUT
        defaultIncidentShouldNotBeFound("statut.equals=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllIncidentsByStatutIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where statut in DEFAULT_STATUT or UPDATED_STATUT
        defaultIncidentShouldBeFound("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT);

        // Get all the incidentList where statut equals to UPDATED_STATUT
        defaultIncidentShouldNotBeFound("statut.in=" + UPDATED_STATUT);
    }

    @Test
    @Transactional
    public void getAllIncidentsByStatutIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where statut is not null
        defaultIncidentShouldBeFound("statut.specified=true");

        // Get all the incidentList where statut is null
        defaultIncidentShouldNotBeFound("statut.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByPrioriteIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where priorite equals to DEFAULT_PRIORITE
        defaultIncidentShouldBeFound("priorite.equals=" + DEFAULT_PRIORITE);

        // Get all the incidentList where priorite equals to UPDATED_PRIORITE
        defaultIncidentShouldNotBeFound("priorite.equals=" + UPDATED_PRIORITE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPrioriteIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where priorite in DEFAULT_PRIORITE or UPDATED_PRIORITE
        defaultIncidentShouldBeFound("priorite.in=" + DEFAULT_PRIORITE + "," + UPDATED_PRIORITE);

        // Get all the incidentList where priorite equals to UPDATED_PRIORITE
        defaultIncidentShouldNotBeFound("priorite.in=" + UPDATED_PRIORITE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPrioriteIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where priorite is not null
        defaultIncidentShouldBeFound("priorite.specified=true");

        // Get all the incidentList where priorite is null
        defaultIncidentShouldNotBeFound("priorite.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsBySujetIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where sujet equals to DEFAULT_SUJET
        defaultIncidentShouldBeFound("sujet.equals=" + DEFAULT_SUJET);

        // Get all the incidentList where sujet equals to UPDATED_SUJET
        defaultIncidentShouldNotBeFound("sujet.equals=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllIncidentsBySujetIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where sujet in DEFAULT_SUJET or UPDATED_SUJET
        defaultIncidentShouldBeFound("sujet.in=" + DEFAULT_SUJET + "," + UPDATED_SUJET);

        // Get all the incidentList where sujet equals to UPDATED_SUJET
        defaultIncidentShouldNotBeFound("sujet.in=" + UPDATED_SUJET);
    }

    @Test
    @Transactional
    public void getAllIncidentsBySujetIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where sujet is not null
        defaultIncidentShouldBeFound("sujet.specified=true");

        // Get all the incidentList where sujet is null
        defaultIncidentShouldNotBeFound("sujet.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where categorie equals to DEFAULT_CATEGORIE
        defaultIncidentShouldBeFound("categorie.equals=" + DEFAULT_CATEGORIE);

        // Get all the incidentList where categorie equals to UPDATED_CATEGORIE
        defaultIncidentShouldNotBeFound("categorie.equals=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByCategorieIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where categorie in DEFAULT_CATEGORIE or UPDATED_CATEGORIE
        defaultIncidentShouldBeFound("categorie.in=" + DEFAULT_CATEGORIE + "," + UPDATED_CATEGORIE);

        // Get all the incidentList where categorie equals to UPDATED_CATEGORIE
        defaultIncidentShouldNotBeFound("categorie.in=" + UPDATED_CATEGORIE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByCategorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where categorie is not null
        defaultIncidentShouldBeFound("categorie.specified=true");

        // Get all the incidentList where categorie is null
        defaultIncidentShouldNotBeFound("categorie.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description equals to DEFAULT_DESCRIPTION
        defaultIncidentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the incidentList where description equals to UPDATED_DESCRIPTION
        defaultIncidentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultIncidentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the incidentList where description equals to UPDATED_DESCRIPTION
        defaultIncidentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description is not null
        defaultIncidentShouldBeFound("description.specified=true");

        // Get all the incidentList where description is null
        defaultIncidentShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultIncidentShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the incidentList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultIncidentShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultIncidentShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the incidentList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultIncidentShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateDebut is not null
        defaultIncidentShouldBeFound("dateDebut.specified=true");

        // Get all the incidentList where dateDebut is null
        defaultIncidentShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateFin equals to DEFAULT_DATE_FIN
        defaultIncidentShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the incidentList where dateFin equals to UPDATED_DATE_FIN
        defaultIncidentShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultIncidentShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the incidentList where dateFin equals to UPDATED_DATE_FIN
        defaultIncidentShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where dateFin is not null
        defaultIncidentShouldBeFound("dateFin.specified=true");

        // Get all the incidentList where dateFin is null
        defaultIncidentShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByUserAppIsEqualToSomething() throws Exception {
        // Initialize the database
        UserApp userApp = UserAppResourceIntTest.createEntity(em);
        em.persist(userApp);
        em.flush();
        incident.setUserApp(userApp);
        incidentRepository.saveAndFlush(incident);
        Long userAppId = userApp.getId();

        // Get all the incidentList where userApp equals to userAppId
        defaultIncidentShouldBeFound("userAppId.equals=" + userAppId);

        // Get all the incidentList where userApp equals to userAppId + 1
        defaultIncidentShouldNotBeFound("userAppId.equals=" + (userAppId + 1));
    }


    @Test
    @Transactional
    public void getAllIncidentsByAssigmentIncidentIsEqualToSomething() throws Exception {
        // Initialize the database
        UserIncidentAssigment assigmentIncident = UserIncidentAssigmentResourceIntTest.createEntity(em);
        em.persist(assigmentIncident);
        em.flush();
        incident.addAssigmentIncident(assigmentIncident);
        incidentRepository.saveAndFlush(incident);
        Long assigmentIncidentId = assigmentIncident.getId();

        // Get all the incidentList where assigmentIncident equals to assigmentIncidentId
        defaultIncidentShouldBeFound("assigmentIncidentId.equals=" + assigmentIncidentId);

        // Get all the incidentList where assigmentIncident equals to assigmentIncidentId + 1
        defaultIncidentShouldNotBeFound("assigmentIncidentId.equals=" + (assigmentIncidentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIncidentShouldBeFound(String filter) throws Exception {
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET)))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT)))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN)));

        // Check, that the count call also returns 1
        restIncidentMockMvc.perform(get("/api/incidents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIncidentShouldNotBeFound(String filter) throws Exception {
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIncidentMockMvc.perform(get("/api/incidents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentService.save(incident);

        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findById(incident.getId()).get();
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident
            .titre(UPDATED_TITRE)
            .statut(UPDATED_STATUT)
            .priorite(UPDATED_PRIORITE)
            .sujet(UPDATED_SUJET)
            .categorie(UPDATED_CATEGORIE)
            .description(UPDATED_DESCRIPTION)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncident)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testIncident.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testIncident.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testIncident.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testIncident.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testIncident.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIncident.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testIncident.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentService.save(incident);

        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Delete the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId(1L);
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId(2L);
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }
}
