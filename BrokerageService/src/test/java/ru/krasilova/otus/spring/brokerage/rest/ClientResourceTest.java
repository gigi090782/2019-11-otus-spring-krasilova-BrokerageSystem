package ru.krasilova.otus.spring.brokerage.rest;


import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.krasilova.otus.spring.brokerage.BrokerageApplication;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.models.TestUtil;
import ru.krasilova.otus.spring.brokerage.repositories.ClientRepository;
import ru.krasilova.otus.spring.brokerage.services.ClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = BrokerageApplication.class)

@AutoConfigureMockMvc
//@WithMockUser
class ClientResourceTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "1981-01-01";
    private static final String UPDATED_BIRTH_DATE = "1981-02-02";

    private static final String DEFAULT_DATE_ADD = "2020-01-01";
    private static final String UPDATED_DATE_ADD = "2020-02-02";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;


    static Client createEntity(EntityManager em) {
        Client client = new Client()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .birthDate(DEFAULT_BIRTH_DATE)
                .dateAdd(DEFAULT_DATE_ADD);
        return client;
    }

    static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .birthDate(UPDATED_BIRTH_DATE)
                .dateAdd(UPDATED_DATE_ADD);
        return client;
    }

    @BeforeEach
    void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();


        restClientMockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(client)))
                .andExpect(status().isCreated());


        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testClient.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testClient.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testClient.getDateAdd()).isEqualTo(DEFAULT_DATE_ADD);
    }

    @Test
    void testListClients() throws Exception {

        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/"));

        result.andExpect(MockMvcResultMatchers.view().name("listСlients"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("clients"));

    }


    @Test
    @Transactional
    void getClient() throws Exception {

        clientRepository.saveAndFlush(client);
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(client.getId().intValue()))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
                .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
                .andExpect(jsonPath("$.dateAdd").value(DEFAULT_DATE_ADD));
    }


    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/clients/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void updateClient() throws Exception {

        clientService.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();


        Client updatedClient = clientRepository.findById(client.getId()).get();

        em.detach(updatedClient);
        updatedClient
                .firstName(UPDATED_FIRST_NAME)
                .lastName(UPDATED_LAST_NAME)
                .birthDate(UPDATED_BIRTH_DATE)
                .dateAdd(UPDATED_DATE_ADD);

        restClientMockMvc.perform(put("/api/clients")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
                .andExpect(status().isOk());


        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testClient.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testClient.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testClient.getDateAdd()).isEqualTo(UPDATED_DATE_ADD);
    }


    @Test
    @Transactional
    void deleteClient() throws Exception {
        clientService.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();


        restClientMockMvc.perform(delete("/clients/{id}", client.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }


}
