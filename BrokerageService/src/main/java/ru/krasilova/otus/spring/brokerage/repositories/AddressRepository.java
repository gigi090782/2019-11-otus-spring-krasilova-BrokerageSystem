package ru.krasilova.otus.spring.brokerage.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.krasilova.otus.spring.brokerage.models.Address;

import org.springframework.data.jpa.repository.*;


import javax.transaction.Transactional;
import java.util.List;


@SuppressWarnings("unused")

@RepositoryRestResource(path = "addresses")
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAll();
    List<Address> findAllByClientId(Long id);
}
