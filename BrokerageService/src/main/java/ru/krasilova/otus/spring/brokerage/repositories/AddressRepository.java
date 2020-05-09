package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.brokerage.models.Address;
import org.springframework.data.jpa.repository.*;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAll();

    List<Address> findAllByClientId(Long id);
}
