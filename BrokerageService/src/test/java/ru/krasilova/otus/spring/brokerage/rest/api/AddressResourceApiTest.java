package ru.krasilova.otus.spring.brokerage.rest.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.krasilova.otus.spring.brokerage.repositories.*;
import ru.krasilova.otus.spring.brokerage.services.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ComponentScan({"ru.krasilova.otus.spring.brokerage.services"})
@WebMvcTest(AddressResourceApi.class)
@DisplayName("Тест AddressResourceApi")
@WithMockUser(
        username = "admin",
        password = "1"
)
public class AddressResourceApiTest {

    @Autowired
    private MockMvc mvc;


    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AddressService addressService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ClientService clientService;


    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private ContactService contactService;


    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ContractService contractService;

    @MockBean
    private ContractMarketPlaceRepository contractMarketPlaceRepository;

    @MockBean
    private ContractMarketPlaceService contractMarketPlaceService;

    @Test

    @DisplayName("Получая информацию об адресе, должен возвращать статус ОК")
    public void find_shouldSucceedWith200() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/addresses")).andExpect(status().isOk());
    }


}