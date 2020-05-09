package ru.krasilova.otus.spring.brokerage.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.krasilova.otus.spring.brokerage.repositories.ClientRepository;
import ru.krasilova.otus.spring.brokerage.services.ClientService;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ComponentScan({"ru.krasilova.otus.spring.brokerage.services"})
@WebMvcTest(ClientResource.class)
@DisplayName("Тест ClientResourceApi")
public class ClientResourceApiTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ClientService clientService;



    @Test
    @DisplayName("Получая информацию о пользователе, должен возвращать статус ОК")
    public void find_login_shouldSucceedWith200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors")).andExpect(status().isOk());
    }


    @Test
    @DisplayName("Не получая информацию о пользователе, должен делать редирект на форму логина")
    public void find_nologin_redirect_to_loginform() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().is3xxRedirection());
    }

}