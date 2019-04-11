package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.UserThirdpartyMembership;
import com.mycompany.myapp.repository.UserThirdpartyMembershipRepository;
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
 * Test class for the UserThirdpartyMembershipResource REST controller.
 *
 * @see UserThirdpartyMembershipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class UserThirdpartyMembershipResourceIntTest {

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALITE = "BBBBBBBBBB";

    @Autowired
    private UserThirdpartyMembershipRepository userThirdpartyMembershipRepository;

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

    private MockMvc restUserThirdpartyMembershipMockMvc;

    private UserThirdpartyMembership userThirdpartyMembership;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserThirdpartyMembershipResource userThirdpartyMembershipResource = new UserThirdpartyMembershipResource(userThirdpartyMembershipRepository);
        this.restUserThirdpartyMembershipMockMvc = MockMvcBuilders.standaloneSetup(userThirdpartyMembershipResource)
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
    public static UserThirdpartyMembership createEntity(EntityManager em) {
        UserThirdpartyMembership userThirdpartyMembership = new UserThirdpartyMembership()
            .fonction(DEFAULT_FONCTION)
            .specialite(DEFAULT_SPECIALITE);
        return userThirdpartyMembership;
    }

    @Before
    public void initTest() {
        userThirdpartyMembership = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserThirdpartyMembership() throws Exception {
        int databaseSizeBeforeCreate = userThirdpartyMembershipRepository.findAll().size();

        // Create the UserThirdpartyMembership
        restUserThirdpartyMembershipMockMvc.perform(post("/api/user-thirdparty-memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userThirdpartyMembership)))
            .andExpect(status().isCreated());

        // Validate the UserThirdpartyMembership in the database
        List<UserThirdpartyMembership> userThirdpartyMembershipList = userThirdpartyMembershipRepository.findAll();
        assertThat(userThirdpartyMembershipList).hasSize(databaseSizeBeforeCreate + 1);
        UserThirdpartyMembership testUserThirdpartyMembership = userThirdpartyMembershipList.get(userThirdpartyMembershipList.size() - 1);
        assertThat(testUserThirdpartyMembership.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testUserThirdpartyMembership.getSpecialite()).isEqualTo(DEFAULT_SPECIALITE);
    }

    @Test
    @Transactional
    public void createUserThirdpartyMembershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userThirdpartyMembershipRepository.findAll().size();

        // Create the UserThirdpartyMembership with an existing ID
        userThirdpartyMembership.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserThirdpartyMembershipMockMvc.perform(post("/api/user-thirdparty-memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userThirdpartyMembership)))
            .andExpect(status().isBadRequest());

        // Validate the UserThirdpartyMembership in the database
        List<UserThirdpartyMembership> userThirdpartyMembershipList = userThirdpartyMembershipRepository.findAll();
        assertThat(userThirdpartyMembershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserThirdpartyMemberships() throws Exception {
        // Initialize the database
        userThirdpartyMembershipRepository.saveAndFlush(userThirdpartyMembership);

        // Get all the userThirdpartyMembershipList
        restUserThirdpartyMembershipMockMvc.perform(get("/api/user-thirdparty-memberships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userThirdpartyMembership.getId().intValue())))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION.toString())))
            .andExpect(jsonPath("$.[*].specialite").value(hasItem(DEFAULT_SPECIALITE.toString())));
    }
    
    @Test
    @Transactional
    public void getUserThirdpartyMembership() throws Exception {
        // Initialize the database
        userThirdpartyMembershipRepository.saveAndFlush(userThirdpartyMembership);

        // Get the userThirdpartyMembership
        restUserThirdpartyMembershipMockMvc.perform(get("/api/user-thirdparty-memberships/{id}", userThirdpartyMembership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userThirdpartyMembership.getId().intValue()))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION.toString()))
            .andExpect(jsonPath("$.specialite").value(DEFAULT_SPECIALITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserThirdpartyMembership() throws Exception {
        // Get the userThirdpartyMembership
        restUserThirdpartyMembershipMockMvc.perform(get("/api/user-thirdparty-memberships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserThirdpartyMembership() throws Exception {
        // Initialize the database
        userThirdpartyMembershipRepository.saveAndFlush(userThirdpartyMembership);

        int databaseSizeBeforeUpdate = userThirdpartyMembershipRepository.findAll().size();

        // Update the userThirdpartyMembership
        UserThirdpartyMembership updatedUserThirdpartyMembership = userThirdpartyMembershipRepository.findById(userThirdpartyMembership.getId()).get();
        // Disconnect from session so that the updates on updatedUserThirdpartyMembership are not directly saved in db
        em.detach(updatedUserThirdpartyMembership);
        updatedUserThirdpartyMembership
            .fonction(UPDATED_FONCTION)
            .specialite(UPDATED_SPECIALITE);

        restUserThirdpartyMembershipMockMvc.perform(put("/api/user-thirdparty-memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserThirdpartyMembership)))
            .andExpect(status().isOk());

        // Validate the UserThirdpartyMembership in the database
        List<UserThirdpartyMembership> userThirdpartyMembershipList = userThirdpartyMembershipRepository.findAll();
        assertThat(userThirdpartyMembershipList).hasSize(databaseSizeBeforeUpdate);
        UserThirdpartyMembership testUserThirdpartyMembership = userThirdpartyMembershipList.get(userThirdpartyMembershipList.size() - 1);
        assertThat(testUserThirdpartyMembership.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testUserThirdpartyMembership.getSpecialite()).isEqualTo(UPDATED_SPECIALITE);
    }

    @Test
    @Transactional
    public void updateNonExistingUserThirdpartyMembership() throws Exception {
        int databaseSizeBeforeUpdate = userThirdpartyMembershipRepository.findAll().size();

        // Create the UserThirdpartyMembership

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserThirdpartyMembershipMockMvc.perform(put("/api/user-thirdparty-memberships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userThirdpartyMembership)))
            .andExpect(status().isBadRequest());

        // Validate the UserThirdpartyMembership in the database
        List<UserThirdpartyMembership> userThirdpartyMembershipList = userThirdpartyMembershipRepository.findAll();
        assertThat(userThirdpartyMembershipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserThirdpartyMembership() throws Exception {
        // Initialize the database
        userThirdpartyMembershipRepository.saveAndFlush(userThirdpartyMembership);

        int databaseSizeBeforeDelete = userThirdpartyMembershipRepository.findAll().size();

        // Delete the userThirdpartyMembership
        restUserThirdpartyMembershipMockMvc.perform(delete("/api/user-thirdparty-memberships/{id}", userThirdpartyMembership.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserThirdpartyMembership> userThirdpartyMembershipList = userThirdpartyMembershipRepository.findAll();
        assertThat(userThirdpartyMembershipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserThirdpartyMembership.class);
        UserThirdpartyMembership userThirdpartyMembership1 = new UserThirdpartyMembership();
        userThirdpartyMembership1.setId(1L);
        UserThirdpartyMembership userThirdpartyMembership2 = new UserThirdpartyMembership();
        userThirdpartyMembership2.setId(userThirdpartyMembership1.getId());
        assertThat(userThirdpartyMembership1).isEqualTo(userThirdpartyMembership2);
        userThirdpartyMembership2.setId(2L);
        assertThat(userThirdpartyMembership1).isNotEqualTo(userThirdpartyMembership2);
        userThirdpartyMembership1.setId(null);
        assertThat(userThirdpartyMembership1).isNotEqualTo(userThirdpartyMembership2);
    }
}
