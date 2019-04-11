package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.Licence;
import com.mycompany.myapp.repository.LicenceRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
 * Test class for the LicenceResource REST controller.
 *
 * @see LicenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class LicenceResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIX = 1;
    private static final Integer UPDATED_PRIX = 2;

    @Autowired
    private LicenceRepository licenceRepository;

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

    private MockMvc restLicenceMockMvc;

    private Licence licence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicenceResource licenceResource = new LicenceResource(licenceRepository);
        this.restLicenceMockMvc = MockMvcBuilders.standaloneSetup(licenceResource)
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
    public static Licence createEntity(EntityManager em) {
        Licence licence = new Licence()
            .titre(DEFAULT_TITRE)
            .prix(DEFAULT_PRIX);
        return licence;
    }

    @Before
    public void initTest() {
        licence = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicence() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();

        // Create the Licence
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isCreated());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate + 1);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testLicence.getPrix()).isEqualTo(DEFAULT_PRIX);
    }

    @Test
    @Transactional
    public void createLicenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();

        // Create the Licence with an existing ID
        licence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLicences() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList
        restLicenceMockMvc.perform(get("/api/licences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licence.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(DEFAULT_PRIX)));
    }
    
    @Test
    @Transactional
    public void getLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", licence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licence.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.prix").value(DEFAULT_PRIX));
    }

    @Test
    @Transactional
    public void getNonExistingLicence() throws Exception {
        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Update the licence
        Licence updatedLicence = licenceRepository.findById(licence.getId()).get();
        // Disconnect from session so that the updates on updatedLicence are not directly saved in db
        em.detach(updatedLicence);
        updatedLicence
            .titre(UPDATED_TITRE)
            .prix(UPDATED_PRIX);

        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLicence)))
            .andExpect(status().isOk());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testLicence.getPrix()).isEqualTo(UPDATED_PRIX);
    }

    @Test
    @Transactional
    public void updateNonExistingLicence() throws Exception {
        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Create the Licence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        int databaseSizeBeforeDelete = licenceRepository.findAll().size();

        // Delete the licence
        restLicenceMockMvc.perform(delete("/api/licences/{id}", licence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licence.class);
        Licence licence1 = new Licence();
        licence1.setId(1L);
        Licence licence2 = new Licence();
        licence2.setId(licence1.getId());
        assertThat(licence1).isEqualTo(licence2);
        licence2.setId(2L);
        assertThat(licence1).isNotEqualTo(licence2);
        licence1.setId(null);
        assertThat(licence1).isNotEqualTo(licence2);
    }
}
