package ru.krasilova.otus.spring.brokerage.rest.api;

import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;
import ru.krasilova.otus.spring.brokerage.services.ContractMarketPlaceService;
import ru.krasilova.otus.spring.brokerage.rest.errors.BadRequestAlertException;

import ru.krasilova.otus.spring.brokerage.utils.HeaderUtil;
import ru.krasilova.otus.spring.brokerage.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ContractMarketPlaceResourceApi {

    private final Logger log = LoggerFactory.getLogger(ContractMarketPlaceResourceApi.class);

    private static final String ENTITY_NAME = "contractMarketPlace";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ContractMarketPlaceService contractMarketPlaceService;

    public ContractMarketPlaceResourceApi(ContractMarketPlaceService contractMarketPlaceService) {
        this.contractMarketPlaceService = contractMarketPlaceService;
    }


    @PostMapping("/contract-market-places")
    public ResponseEntity<ContractMarketPlace> createContractMarketPlace(@RequestBody ContractMarketPlace contractMarketPlace) throws URISyntaxException {
        log.debug("REST request to save ContractMarketPlace : {}", contractMarketPlace);
        if (contractMarketPlace.getId() != null) {
            throw new BadRequestAlertException("A new contractMarketPlace cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractMarketPlace result = contractMarketPlaceService.save(contractMarketPlace);
        return ResponseEntity.created(new URI("/api/contract-market-places/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    @PutMapping("/contract-market-places")
    public ResponseEntity<ContractMarketPlace> updateContractMarketPlace(@RequestBody ContractMarketPlace contractMarketPlace) {
        log.debug("REST request to update ContractMarketPlace : {}", contractMarketPlace);
        if (contractMarketPlace.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractMarketPlace result = contractMarketPlaceService.save(contractMarketPlace);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractMarketPlace.getId().toString()))
                .body(result);
    }


    @GetMapping("/contract-market-places")
    public List<ContractMarketPlace> getAllContractMarketPlaces() {
        log.debug("REST request to get all ContractMarketPlaces");
        return contractMarketPlaceService.findAll();
    }


    @GetMapping("/contract-market-places/{id}")
    public ResponseEntity<ContractMarketPlace> getContractMarketPlace(@PathVariable Long id) {
        log.debug("REST request to get ContractMarketPlace : {}", id);
        Optional<ContractMarketPlace> contractMarketPlace = contractMarketPlaceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractMarketPlace);
    }


    @DeleteMapping("/contract-market-places/{id}")
    public ResponseEntity<Void> deleteContractMarketPlace(@PathVariable Long id) {
        log.debug("REST request to delete ContractMarketPlace : {}", id);
        contractMarketPlaceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
