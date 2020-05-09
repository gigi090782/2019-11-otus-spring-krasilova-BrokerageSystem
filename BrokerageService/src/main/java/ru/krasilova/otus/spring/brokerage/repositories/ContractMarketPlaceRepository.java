package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;


import org.springframework.data.jpa.repository.*;

import java.util.List;


@SuppressWarnings("unused")
@RepositoryRestResource(path = "contractmarketplace")
public interface ContractMarketPlaceRepository extends JpaRepository<ContractMarketPlace, Long> {
    List<ContractMarketPlace> findAll();
    List<ContractMarketPlace> findAllByContractId(Long id);
}
