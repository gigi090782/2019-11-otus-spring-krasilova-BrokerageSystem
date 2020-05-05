package ru.krasilova.otus.spring.brokerage.services;

import ru.krasilova.otus.spring.brokerage.models.ContractMarketPlace;
import ru.krasilova.otus.spring.brokerage.services.ContractMarketPlaceService;
import ru.krasilova.otus.spring.brokerage.repositories.ContractMarketPlaceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional

public class ContractMarketPlaceServiceImpl implements ContractMarketPlaceService {

    private final Logger log = LoggerFactory.getLogger(ContractMarketPlaceServiceImpl.class);

    private final ContractMarketPlaceRepository contractMarketPlaceRepository;

    public ContractMarketPlaceServiceImpl(ContractMarketPlaceRepository contractMarketPlaceRepository) {
        this.contractMarketPlaceRepository = contractMarketPlaceRepository;
    }


    @Override
    public ContractMarketPlace save(ContractMarketPlace contractMarketPlace) {
        log.debug("Request to save ContractMarketPlace : {}", contractMarketPlace);
        return contractMarketPlaceRepository.save(contractMarketPlace);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ContractMarketPlace> findAll() {
        log.debug("Request to get all ContractMarketPlaces");
        return contractMarketPlaceRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ContractMarketPlace> findOne(Long id) {
        log.debug("Request to get ContractMarketPlace : {}", id);
        return contractMarketPlaceRepository.findById(id);
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContractMarketPlace : {}", id);
        contractMarketPlaceRepository.deleteById(id);
    }

    @Override
    public List<ContractMarketPlace> findAllByContractId(Long id) {
        return contractMarketPlaceRepository.findAllByContractId(id);
    }
}
