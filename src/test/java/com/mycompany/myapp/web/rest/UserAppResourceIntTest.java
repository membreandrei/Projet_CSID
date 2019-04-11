package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetCsidApp;

import com.mycompany.myapp.domain.UserApp;
import com.mycompany.myapp.repository.UserAppRepository;
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
 * Test class for the UserAppResource REST controller.
 *
 * @see UserAppResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetCsidApp.class)
public class UserAppResourceIntTest {

    @Autowired
    private UserAppRepository userAppRepository;

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

    private MockMvc restUserAppMockMvc;

    private UserApp userApp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAppResource userAppResource = new UserAppResource(userAppRepository);
        this.restUserAppMockMvc = MockMvcBuilders.standaloneSetup(userAppResource)
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
    public static UserApp createEntity(EntityManager em) {
        UserApp userApp = new UserApp();
        return userApp;
    }

    @Before
    public void initTest() {
        userApp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApp() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();

        // Create the UserApp
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isCreated());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate + 1);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAppRepository.findAll().size();

        // Create the UserApp with an existing ID
        userApp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAppMockMvc.perform(post("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserApps() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get all the userAppList
        restUserAppMockMvc.perform(get("/api/user-apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApp.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", userApp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userApp.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserApp() throws Exception {
        // Get the userApp
        restUserAppMockMvc.perform(get("/api/user-apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Update the userApp
        UserApp updatedUserApp = userAppRepository.findById(userApp.getId()).get();
        // Disconnect from session so that the updates on updatedUserApp are not directly saved in db
        em.detach(updatedUserApp);

        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserApp)))
            .andExpect(status().isOk());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
        UserApp testUserApp = userAppList.get(userAppList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApp() throws Exception {
        int databaseSizeBeforeUpdate = userAppRepository.findAll().size();

        // Create the UserApp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAppMockMvc.perform(put("/api/user-apps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userApp)))
            .andExpect(status().isBadRequest());

        // Validate the UserApp in the database
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApp() throws Exception {
        // Initialize the database
        userAppRepository.saveAndFlush(userApp);

        int databaseSizeBeforeDelete = userAppRepository.findAll().size();

        // Delete the userApp
        restUserAppMockMvc.perform(delete("/api/user-apps/{id}", userApp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserApp> userAppList = userAppRepository.findAll();
        assertThat(userAppList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApp.class);
        UserApp userApp1 = new UserApp();
        userApp1.setId(1L);
        UserApp userApp2 = new UserApp();
        userApp2.setId(userApp1.getId());
        assertThat(userApp1).isEqualTo(userApp2);
        userApp2.setId(2L);
        assertThat(userApp1).isNotEqualTo(userApp2);
        userApp1.setId(null);
        assertThat(userApp1).isNotEqualTo(userApp2);
    }
}
