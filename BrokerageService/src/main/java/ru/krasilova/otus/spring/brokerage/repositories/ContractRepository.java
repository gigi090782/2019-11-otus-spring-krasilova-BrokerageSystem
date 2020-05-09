package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.krasilova.otus.spring.brokerage.models.Contract;


import org.springframework.data.jpa.repository.*;


import java.util.List;


@SuppressWarnings("unused")
@RepositoryRestResource(path = "contract")

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAll();
    List<Contract> findAllByClientId(Long id);

}
