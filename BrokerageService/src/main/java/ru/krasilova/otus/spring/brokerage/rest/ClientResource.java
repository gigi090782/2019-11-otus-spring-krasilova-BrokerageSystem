package ru.krasilova.otus.spring.brokerage.rest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.krasilova.otus.spring.brokerage.models.Address;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.models.Contact;
import ru.krasilova.otus.spring.brokerage.models.Contract;
import ru.krasilova.otus.spring.brokerage.models.enumeration.AddressType;
import ru.krasilova.otus.spring.brokerage.models.enumeration.ContactType;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.BadBirthDate;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.NotFoundException;
import ru.krasilova.otus.spring.brokerage.services.AddressService;
import ru.krasilova.otus.spring.brokerage.services.ClientService;
import ru.krasilova.otus.spring.brokerage.rest.errors.BadRequestAlertException;
import ru.krasilova.otus.spring.brokerage.services.ContactService;
import ru.krasilova.otus.spring.brokerage.services.ContractService;
import ru.krasilova.otus.spring.brokerage.utils.HeaderUtil;
import ru.krasilova.otus.spring.brokerage.utils.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private final AddressService addressService;
    private final ContactService contactService;

    @Autowired
    public ClientResource(ClientService clientService, ContractService contractService,
                          AddressService addressService, ContactService contactService) {
        this.clientService = clientService;
        this.addressService = addressService;
        this.contactService = contactService;
        this.contractService = contractService;
    }


    @PostMapping("/clients")
    public ResponseEntity<Client> createClient(@RequestBody Client client) throws URISyntaxException, ParseException, BadBirthDate {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            throw new BadRequestAlertException("A new client cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Client result = clientService.save(client);
        return ResponseEntity.created(new URI("/clients/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    @PutMapping("/clients")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws URISyntaxException, ParseException, BadBirthDate {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Client result = clientService.save(client);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, client.getId().toString()))
                .body(result);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        log.debug("REST request to get a page of Clients");
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/")
    public String getListClient(Model model) throws InterruptedException {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "listСlients";
    }

    @GetMapping("/addclient")
    public String getAddClient(Model model) throws ParseException {
        Client client = new Client();
        model.addAttribute("client", client);
        List<Contract> contracts = new ArrayList<Contract>();
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

    public String addModelsForClient(Model model) {
        List<ContactType> contactTypes = Arrays.asList(ContactType.values());
        model.addAttribute("contacttypes", contactTypes);
        List<AddressType> addressTypes = Arrays.asList(AddressType.values());
        model.addAttribute("addresstypes", addressTypes);
        return "editclient";
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Optional<Client> client = clientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(client);
    }


    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @PostMapping("/client/removecontract")
    public String removeClientContract(Client client, Model model, Long removeContractId, HttpServletRequest request) {
        client.removeContract(contractService.findOne(removeContractId).orElseThrow(NotFoundException::new));
        return returnClientContracts(client, model, request);
    }

    public String returnClientContracts(Client client, Model model, HttpServletRequest request) {
        List<Contract> contracts = contractService.findAllByClientId(client.getId());
        model.addAttribute("contracts", contracts);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editclient::#contractstable";
        } else {
            return "editclient";
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


    public String returnClientAddresses(Client client, Model model, HttpServletRequest request) {
        List<AddressType> addressTypes = Arrays.asList(AddressType.values());
        model.addAttribute("addresstypes", addressTypes);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editclient::#addresses";
        } else {
            return "editclient";
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


    public String returnClientContacts(Client client, Model model, HttpServletRequest request) {
        List<ContactType> contactTypes = Arrays.asList(ContactType.values());
        model.addAttribute("contacttypes", contactTypes);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editclient::#contacts";
        } else {
            return "editclient";
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


    @PostMapping(params = "save", path = {"/client", "/editclient/{id}"})
    public String saveClient(Client client) throws ParseException, BadBirthDate {
        clientService.save(client);
        return "client";
    }


    @PostMapping("/saveClient")
    public String postSaveClient(
            Client client,
            Model model
    ) throws ParseException, BadBirthDate {
        client.getAddresses().stream().forEach(a -> a.setClient(client));
        client.getContacts().stream().forEach(c -> c.setClient(client));
        if (client.getId() == null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            client.setDateAdd(dateFormat.format(date));
        }
        clientService.saveAndFlush(client);
        return "redirect:/";
    }


}
