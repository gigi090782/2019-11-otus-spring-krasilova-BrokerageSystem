package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;

import java.util.List;


@Repository
public interface ContractMarketPlaceRepository extends JpaRepository<ContractMarketPlace, Long> {
    List<ContractMarketPlace> findAll();

    List<ContractMarketPlace> findAllByContractId(Long id);
}
