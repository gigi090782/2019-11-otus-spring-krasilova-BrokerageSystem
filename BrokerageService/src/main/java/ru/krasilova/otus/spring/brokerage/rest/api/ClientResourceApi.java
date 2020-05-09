package ru.krasilova.otus.spring.brokerage.rest.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.rest.errors.BadRequestAlertException;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.BadBirthDate;
import ru.krasilova.otus.spring.brokerage.services.ClientService;
import ru.krasilova.otus.spring.brokerage.services.ContractService;
import ru.krasilova.otus.spring.brokerage.utils.HeaderUtil;
import ru.krasilova.otus.spring.brokerage.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientResourceApi {

    private final Logger log = LoggerFactory.getLogger(ru.krasilova.otus.spring.brokerage.rest.api.ClientResourceApi.class);

    private static final String ENTITY_NAME = "client";

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";


    @Value("${spring.application.name}")
    private String applicationName;

    private final ClientService clientService;

    @Autowired
    public ClientResourceApi(ClientService clientService, ContractService contractService) {
        this.clientService = clientService;
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
    public ResponseEntity<Client> updateClient(@RequestBody Client client) throws ParseException, BadBirthDate {
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



}
