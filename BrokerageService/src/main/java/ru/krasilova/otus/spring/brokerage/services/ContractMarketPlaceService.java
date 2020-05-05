package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;
import java.util.List;
import java.util.Optional;

public interface ContractMarketPlaceService {


    ContractMarketPlace save(ContractMarketPlace contractMarketPlace);

    List<ContractMarketPlace> findAll();

    Optional<ContractMarketPlace> findOne(Long id);

    void delete(Long id);

    List<ContractMarketPlace> findAllByContractId(Long id);
}
