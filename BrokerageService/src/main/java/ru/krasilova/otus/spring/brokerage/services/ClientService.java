package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.Client;
import ru.krasilova.otus.spring.brokerage.rest.exceptions.BadBirthDate;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client save(Client client) throws ParseException, BadBirthDate;

    Client saveAndFlush(Client client) throws BadBirthDate, ParseException;

    Client findClientById(Long id);

    List<Client> findAll();

    Optional<Client> findOne(Long id);

    void delete(Long id);
}
