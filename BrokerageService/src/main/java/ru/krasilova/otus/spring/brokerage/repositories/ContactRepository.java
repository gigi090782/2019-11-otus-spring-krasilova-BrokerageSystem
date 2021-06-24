package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAll();
    List<Contact> findAllByClientId(Long id);

}
