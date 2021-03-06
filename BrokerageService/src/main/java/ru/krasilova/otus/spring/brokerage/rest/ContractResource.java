package ru.krasilova.otus.spring.brokerage.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.models.Contract;
import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;
import ru.krasilova.otus.spring.brokerage.models.enumeration.ChannelType;
import ru.krasilova.otus.spring.brokerage.models.enumeration.MarketPlaceType;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.NotFoundException;
import ru.krasilova.otus.spring.brokerage.services.ClientService;
import ru.krasilova.otus.spring.brokerage.services.ContractMarketPlaceService;
import ru.krasilova.otus.spring.brokerage.services.ContractService;
import ru.krasilova.otus.spring.brokerage.rest.errors.BadRequestAlertException;
import ru.krasilova.otus.spring.brokerage.utils.HeaderUtil;
import ru.krasilova.otus.spring.brokerage.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class ContractResource {

    private final Logger log = LoggerFactory.getLogger(ContractResource.class);

    private static final String ENTITY_NAME = "contract";

    @Value("${spring.application.name}")
    private String applicationName;


    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";


    private final ClientService clientService;
    private final ContractService contractService;
    private final ContractMarketPlaceService marketPlaceService;


    @Autowired
    public ContractResource(ClientService clientService, ContractService contractService,
                            ContractMarketPlaceService marketPlaceService) {
        this.clientService = clientService;
        this.marketPlaceService = marketPlaceService;
        this.contractService = contractService;
    }


    @PostMapping("/contracts")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) throws URISyntaxException {
        log.debug("REST request to save Contract : {}", contract);
        if (contract.getId() != null) {
            throw new BadRequestAlertException("A new contract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contract result = contractService.save(contract);
        return ResponseEntity.created(new URI("/api/contracts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }


    @PutMapping("/contracts")
    public ResponseEntity<Contract> updateContract(@RequestBody Contract contract) throws URISyntaxException {
        log.debug("REST request to update Contract : {}", contract);
        if (contract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contract result = contractService.save(contract);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contract.getId().toString()))
                .body(result);
    }


    @GetMapping("/contracts")
    public String getListContracts(Model model) throws InterruptedException {
        List<Contract> contracts = contractService.findAll();
        model.addAttribute("contracts", contracts);
        return "listContracts";
    }


    @GetMapping("/clientcontracts")
    public String getListClientContracts(@RequestParam("clientid") long clientid, Model model) throws InterruptedException {
        List<Contract> contracts = contractService.findAllByClientId(clientid);
        model.addAttribute("contracts", contracts);
        Client client = clientService.findOne(clientid).orElseThrow(NotFoundException::new);
        model.addAttribute("client", client);
        return "listClientContracts";
    }


    @GetMapping("/contracts/{id}")
    public ResponseEntity<Contract> getContract(@PathVariable Long id) {
        log.debug("REST request to get Contract : {}", id);
        Optional<Contract> contract = contractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contract);
    }


    @DeleteMapping("/contracts/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        log.debug("REST request to delete Contract : {}", id);
        contractService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/addcontract")
    public String getAddContract(Model model) throws ParseException {
        Contract contract = new Contract();
        model.addAttribute("contract", contract);
        addModelsForContract(model);
        return "addContract";
    }


    @GetMapping("/addclientcontract")
    public String getAddClientContract(@RequestParam("clientid") long clientid, Model model) throws ParseException {
        Client client = clientService.findOne(clientid).get();
        Contract contract = new Contract();
        contract.setClient(client);
        model.addAttribute("contract", contract);
        addModelsForContract(model);
        model.addAttribute("client", client);
        addModelsForContract(model);
        return "addClientContract";
    }


    @GetMapping("/editcontract")
    public String getEditContract(@RequestParam("id") long id, Model model) {
        Contract contract = contractService.findOne(id).orElseThrow(NotFoundException::new);
        model.addAttribute("contract", contract);
        addModelsForContract(model);
        return "editContract";
    }



    @GetMapping("/editclientcontract")
    public String getEditClientContract(@RequestParam("id") long id, Model model) {
        Contract contract = contractService.findOne(id).orElseThrow(NotFoundException::new);
        model.addAttribute("contract", contract);
        addModelsForContract(model);
        return "editClientContract";
    }



    public void addModelsForContract(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        List<ChannelType> channelTypes = Arrays.asList(ChannelType.values());
        model.addAttribute("allchannelTypes", channelTypes);
        List<MarketPlaceType> marketPlaceTypes = Arrays.asList(MarketPlaceType.values());
        model.addAttribute("allmarketPlaceTypes", marketPlaceTypes);

    }

    @PostMapping("/contract/addplace")
    public String addContractMarketPlace(Contract contract, Model model, HttpServletRequest request) {
        contract.addContractMarketPlace(new ContractMarketPlace());
        return returnContract(contract, model, request);
    }


    @PostMapping("/contract/removeplace")
    public String removeContractMarketPlace(Contract contract, Model model, int removePlaceId, HttpServletRequest request) {
        contract.removeContractMarketPlace(contract.getContractMarketPlaces().get(removePlaceId).getId());
        return returnContract(contract, model, request);
    }


    public String returnContract(Contract contract, Model model, HttpServletRequest request) {
        List<MarketPlaceType> marketPlaceTypes = Arrays.asList(MarketPlaceType.values());
        model.addAttribute("allmarketPlaceTypes", marketPlaceTypes);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "editContract::#marketplaces";
        } else {
            return "editContract";
        }
    }


    @PostMapping("/saveContract")
    public String postSaveContract(
            Contract contract,
            Model model
    ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datestr = dateFormat.format(date);
        contract.getContractMarketPlaces().stream().forEach(a -> a.setContract(contract));
        contract.getContractMarketPlaces().stream().filter(p -> p.getId() == null).forEach(a -> a.setDateAdd(datestr));

        if (contract.getId() == null) {
            contract.setDateAdd(datestr);
        }
        contractService.saveAndFlush(contract);
        return "redirect:/contracts";
    }

    @PostMapping("/saveClientContract")
    public String postSaveClientContract(
            @RequestParam("clientid") long clientid,
            Contract contract,
            Model model
    ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datestr = dateFormat.format(date);
        contract.getContractMarketPlaces().stream().forEach(a -> a.setContract(contract));
        contract.getContractMarketPlaces().stream().filter(p -> p.getId() == null).forEach(a -> a.setDateAdd(datestr));
        Client client = clientService.findOne(clientid).get();
        contract.setClient(client);
        if (contract.getId() == null) {
            contract.setDateAdd(datestr);
        }
        contractService.saveAndFlush(contract);

        return "redirect:/clientcontracts/?clientid=" + String.valueOf(clientid);

    }

    @PostMapping("/contract/removecontract")
    public String removeContract(@RequestParam("id") long id, Model model, HttpServletRequest request) {
        contractService.delete(id);
        List<Contract> contracts = contractService.findAll();
        model.addAttribute("contracts", contracts);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "listContracts::#contractstable";
        } else {
            return "listContracts";
        }

    }

    @PostMapping("/contract/removeclientcontract")
    public String removeClientContract(@RequestParam("id") long id, @RequestParam("clientid") long clientid, Model model, HttpServletRequest request) {
        contractService.delete(id);
        List<Contract> contracts = contractService.findAllByClientId(clientid);
        model.addAttribute("contracts", contracts);
        Client client = clientService.findOne(clientid).orElseThrow(NotFoundException::new);
        model.addAttribute("client", client);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "listClientContracts::#contractstable";
        } else {
            return "listClientContracts";
        }

    }

    @PostMapping("/contract/removeClientContract")
    public String removeClientContract(@RequestParam("id") long id, Model model, HttpServletRequest request) {
        contractService.delete(id);
        List<Contract> contracts = contractService.findAll();
        model.addAttribute("contracts", contracts);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "listContracts::#contractstable";
        } else {
            return "listContracts";
        }

    }

}
