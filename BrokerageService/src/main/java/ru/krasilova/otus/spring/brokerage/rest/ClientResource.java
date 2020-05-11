package ru.krasilova.otus.spring.brokerage.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.krasilova.otus.spring.brokerage.models.Address;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.models.Contact;
import ru.krasilova.otus.spring.brokerage.models.Contract;
import ru.krasilova.otus.spring.brokerage.models.enumeration.AddressType;
import ru.krasilova.otus.spring.brokerage.models.enumeration.ContactType;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.BadBirthDate;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.NotFoundException;
import ru.krasilova.otus.spring.brokerage.services.ClientService;
import ru.krasilova.otus.spring.brokerage.services.ContractService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";


    @Value("${spring.application.name}")
    private String applicationName;

    private final ClientService clientService;
    private final ContractService contractService;

    @Autowired
    public ClientResource(ClientService clientService, ContractService contractService) {
        this.clientService = clientService;
        this.contractService = contractService;
    }




    @GetMapping("/")
    public String getListClient(Model model, HttpServletResponse response) {
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Pragma","no-cache");
        response.addHeader("Expires","0");
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "listСlients";
    }

    @GetMapping("/addclient")
    public String getAddClient(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        List<Contract> contracts = new ArrayList<>();
        model.addAttribute("contracts", contracts);
        return addModelsForClient(model);
    }

    @GetMapping("/editclient")
    public String getEditClient(@RequestParam("id") long id, Model model) {
        Client client = clientService.findClientById(id);
        model.addAttribute("client", client);
        List<Contract> contracts = contractService.findAllByClientId(id);
        model.addAttribute("contracts", contracts);
        return addModelsForClient(model);
    }

    private String addModelsForClient(Model model) {
        List<ContactType> contactTypes = Arrays.asList(ContactType.values());
        model.addAttribute("contacttypes", contactTypes);
        List<AddressType> addressTypes = Arrays.asList(AddressType.values());
        model.addAttribute("addresstypes", addressTypes);
        return "editClient";
    }





    @PostMapping("/client/removecontract")
    public String removeClientContract(Client client, Model model, Long removeContractId, HttpServletRequest request) {
        client.removeContract(contractService.findOne(removeContractId).orElseThrow(NotFoundException::new));
        return returnClientContracts(client, model, request);
    }

    private String returnClientContracts(Client client, Model model, HttpServletRequest request) {
        List<Contract> contracts = contractService.findAllByClientId(client.getId());
        model.addAttribute("contracts", contracts);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editClient::#contractstable";
        } else {
            return "editClient";
        }
    }

    @PostMapping("/client/addaddress")
    public String addClientAddress(Client client, Model model, HttpServletRequest request) {
        client.addAddress(new Address());
        return returnClientAddresses(client, model, request);
    }


    @PostMapping("/client/removeaddress")
    public String removeClientAddress(Client client, Model model, int removeAddressIndex, HttpServletRequest request) {
        client.removeAddress(client.getAddresses().get(removeAddressIndex).getId());
        return returnClientAddresses(client, model, request);
    }


    private String returnClientAddresses(Client client, Model model, HttpServletRequest request) {
        List<AddressType> addressTypes = Arrays.asList(AddressType.values());
        model.addAttribute("addresstypes", addressTypes);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editClient::#addresses";
        } else {
            return "editClient";
        }
    }


    @PostMapping("/client/addcontact")
    public String addClientContact(Client client, Model model, HttpServletRequest request) {
        client.addContact(new Contact());
        return returnClientContacts(client, model, request);
    }


    @PostMapping("/client/removecontact")
    public String removeClientContact(Client client, Model model, int removeContactIndex, HttpServletRequest request) {
        client.removeContact(client.getContacts().get(removeContactIndex).getId());
        return returnClientContacts(client, model, request);
    }


    private String returnClientContacts(Client client, Model model, HttpServletRequest request) {
        List<ContactType> contactTypes = Arrays.asList(ContactType.values());
        model.addAttribute("contacttypes", contactTypes);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editClient::#contacts";
        } else {
            return "editClient";
        }
    }


    @PostMapping("/removeclient")
    public String removeClient(@RequestParam("id") long id, Model model, HttpServletRequest request) {
        clientService.delete(id);
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "listСlients::#clientstable";
        } else {
            return "listСlients";
        }

    }


    @PostMapping("/saveClient")
    public String postSaveClient(
            Client client,
            Model model
    ) throws ParseException, BadBirthDate {
        client.getAddresses().forEach(a -> a.setClient(client));
        client.getContacts().forEach(c -> c.setClient(client));
        if (client.getId() == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            client.setDateAdd(dateFormat.format(date));
        }
        clientService.saveAndFlush(client);
        return "redirect:/";
    }


}
