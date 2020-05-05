package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.Address;


import java.util.List;
import java.util.Optional;

public interface AddressService {

    Address save(Address address);

    List<Address> findAll();

    Optional<Address> findOne(Long id);

    void delete(Long id);

    List<Address> findAllByClientId(Long id);
}
