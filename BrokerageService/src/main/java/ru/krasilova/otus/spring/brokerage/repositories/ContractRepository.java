package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAll();

    List<Contract> findAllByClientId(Long id);

}
