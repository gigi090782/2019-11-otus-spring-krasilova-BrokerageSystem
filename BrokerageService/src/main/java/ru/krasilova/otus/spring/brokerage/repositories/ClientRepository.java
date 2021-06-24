package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Client;

import java.util.List;

@Repository

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

}
