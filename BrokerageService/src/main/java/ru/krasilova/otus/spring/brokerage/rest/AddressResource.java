package ru.krasilova.otus.spring.brokerage.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krasilova.otus.spring.brokerage.models.Address;
import ru.krasilova.otus.spring.brokerage.rest.errors.BadRequestAlertException;
import ru.krasilova.otus.spring.brokerage.services.AddressService;
import ru.krasilova.otus.spring.brokerage.utils.HeaderUtil;
import ru.krasilova.otus.spring.brokerage.utils.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AddressResource {

    private final Logger log = LoggerFactory.getLogger(AddressResource.class);

    private static final String ENTITY_NAME = "address";

    @Value("${spring.application.name}")
    private String applicationName;

    private final AddressService addressService;

    public AddressResource(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/addresses")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) throws URISyntaxException {
        log.debug("REST request to save Address : {}", address);
        if (address.getId() != null) {
            throw new BadRequestAlertException("A new address cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Address result = addressService.save(address);
        return ResponseEntity.created(new URI("/api/addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/addresses")
    public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
        log.debug("REST request to update Address : {}", address);
        if (address.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Address result = addressService.save(address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, address.getId().toString()))
            .body(result);
    }


    @GetMapping("/addresses")
    public List<Address> getAllAddresses() {
        log.debug("REST request to get all Addresses");
        return addressService.findAll();
    }





    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        log.debug("REST request to get Address : {}", id);
        Optional<Address> address = addressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(address);
    }


    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        log.debug("REST request to delete Address : {}", id);
        addressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
