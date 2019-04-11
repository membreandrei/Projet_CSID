package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.Thirdparty;
import com.mycompany.myapp.repository.ThirdpartyRepository;
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
        final ThirdpartyResource thirdpartyResource = new ThirdpartyResource(thirdpartyRepository);
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
    public void getNonExistingThirdparty() throws Exception {
        // Get the thirdparty
        restThirdpartyMockMvc.perform(get("/api/thirdparties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateThirdparty() throws Exception {
        // Initialize the database
        thirdpartyRepository.saveAndFlush(thirdparty);

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
        thirdpartyRepository.saveAndFlush(thirdparty);

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
