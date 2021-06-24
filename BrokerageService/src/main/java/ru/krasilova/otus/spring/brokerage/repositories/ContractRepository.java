package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Contract;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    @EntityGraph(value = "clientJoin", type = EntityGraph.EntityGraphType.FETCH)
    List<Contract> findAll();

    List<Contract> findAllByClientId(Long id);

}
