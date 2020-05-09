package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.krasilova.otus.spring.brokerage.models.Contact;


import org.springframework.data.jpa.repository.*;

import java.util.List;

@SuppressWarnings("unused")
@RepositoryRestResource(path = "contact")
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAll();
    List<Contact> findAllByClientId(Long id);

}
