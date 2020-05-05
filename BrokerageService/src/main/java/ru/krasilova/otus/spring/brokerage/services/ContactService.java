package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.Contact;
import java.util.List;
import java.util.Optional;


public interface ContactService {


    Contact save(Contact contact);

    List<Contact> findAll();

    List<Contact> findAllByClientId(Long id);

    Optional<Contact> findOne(Long id);

    void delete(Long id);
}
