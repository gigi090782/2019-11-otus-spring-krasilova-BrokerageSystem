package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Client;


import org.springframework.data.jpa.repository.*;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findAll();

}
