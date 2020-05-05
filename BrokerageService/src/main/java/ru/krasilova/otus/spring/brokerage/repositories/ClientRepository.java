package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.krasilova.otus.spring.brokerage.models.Client;


import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@SuppressWarnings("unused")
@RepositoryRestResource(path = "client")
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();
}
