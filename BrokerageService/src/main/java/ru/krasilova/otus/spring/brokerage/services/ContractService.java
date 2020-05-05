package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.Contract;
import java.util.List;
import java.util.Optional;

public interface ContractService {

    Contract save(Contract contract);

    Contract saveAndFlush(Contract client);

    List<Contract> findAll();

    Optional<Contract> findOne(Long id);

    void delete(Long id);

    void delete(Contract contract);

    List<Contract> findAllByClientId(Long id);
}
