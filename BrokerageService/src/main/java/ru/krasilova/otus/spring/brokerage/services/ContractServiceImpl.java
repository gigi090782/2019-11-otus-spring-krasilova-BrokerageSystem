package ru.krasilova.otus.spring.brokerage.services;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ru.krasilova.otus.spring.brokerage.models.Contract;
import ru.krasilova.otus.spring.brokerage.services.ContractService;
import ru.krasilova.otus.spring.brokerage.repositories.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static ru.krasilova.otus.spring.brokerage.utils.UtilRandomSleep.getRandomSleep;

@Service
@Transactional
@DefaultProperties(groupKey = "ContractGroupKey", defaultFallback = "getWaitResponse")
public class ContractServiceImpl implements ContractService {

    private final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }


    @Override
    public Contract save(Contract contract) {
        log.debug("Request to save Contract : {}", contract);
        return contractRepository.save(contract);
    }

    @Override
    public Contract saveAndFlush(Contract contract) {
        return contractRepository.saveAndFlush(contract);
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(groupKey = "ContractGroup", commandKey = "getAllContractsCommand",
            fallbackMethod = "getReserveListContracts")
    public List<Contract> findAll() {
        log.debug("Request to get all Contracts");
        getRandomSleep();
        return contractRepository.findAll();
    }

    public List<Contract> getReserveListContracts( ) {
        return Collections.emptyList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contract> findOne(Long id) {
        log.debug("Request to get Contract : {}", id);
        return contractRepository.findById(id);
    }


    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contract : {}", id);
        contractRepository.deleteById(id);
    }

    @Override
    public void delete(Contract contract) {
        contractRepository.delete(contract);
    }


    @Override
    public List<Contract> findAllByClientId(Long id) {
        return contractRepository.findAllByClientId(id);
    }

    public String getWaitResponse()
    {
        return "Сервер не отвечает! Попробуйте еще раз попозже!";
    }

}
