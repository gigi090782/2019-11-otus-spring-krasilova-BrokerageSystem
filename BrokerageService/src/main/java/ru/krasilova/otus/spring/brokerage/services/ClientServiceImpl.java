package ru.krasilova.otus.spring.brokerage.services;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import ru.krasilova.otus.spring.brokerage.utils.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.BadBirthDate;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.NotFoundException;

import ru.krasilova.otus.spring.brokerage.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
@DefaultProperties(groupKey = "ClientGroupKey", defaultFallback = "getWaitResponse")
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;


    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    @Override
    public Client save(Client client) throws ParseException, BadBirthDate {
        log.debug("Request to save Client : {}", client);
        checkClient(client);
        return clientRepository.save(client);
    }

    public void checkClient(Client client) throws BadBirthDate, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date datetoday = new Date();
        Date birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(client.getBirthDate());
        if (birthdate.compareTo(datetoday) > 0)
            throw new BadBirthDate("Дата рождения не может быть больше текущей даты!");
    }

    @Override
    public Client saveAndFlush(Client client) throws BadBirthDate, ParseException {
        checkClient(client);
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "ClientGroup", commandKey = "getAllClientsCommand",
            fallbackMethod = "getReserveListClients")
    public List<Client> findAll() {
        log.debug("Request to get all Clients");

        return clientRepository.findAll();
    }

    public List<Client> getReserveListClients() {
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }

    public String getWaitResponse() {
        return "Сервер не отвечает! Попробуйте еще раз попозже!";
    }
}
