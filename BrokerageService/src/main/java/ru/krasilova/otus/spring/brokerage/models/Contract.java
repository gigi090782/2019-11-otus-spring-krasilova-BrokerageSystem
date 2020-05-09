package ru.krasilova.otus.spring.brokerage.models;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import ru.krasilova.otus.spring.brokerage.models.enumeration.ChannelType;

@Entity
@Data
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type")
    private ChannelType channelType;

    @Column(name = "date_add")
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    private String dateAdd;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "contract", orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ContractMarketPlace> contractMarketPlaces = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private ChannelType getChannelType() {
        return channelType;
    }

    public Contract channelType(ChannelType channelType) {
        this.channelType = channelType;
        return this;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    private String getDateAdd() {
        return dateAdd;
    }

    public Contract dateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
        return this;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public List<ContractMarketPlace> getContractMarketPlaces() {
        return contractMarketPlaces;
    }

    public Contract contractMarketPlaces(List<ContractMarketPlace> contractMarketPlaces) {
        this.contractMarketPlaces = contractMarketPlaces;
        return this;
    }

    public Contract addContractMarketPlace(ContractMarketPlace contractMarketPlace) {
        this.contractMarketPlaces.add(contractMarketPlace);
        contractMarketPlace.setContract(this);
        return this;
    }

    public Contract removeContractMarketPlace(Long id) {
        this.contractMarketPlaces = this.contractMarketPlaces.stream().filter(a -> a.getId() != id).collect(Collectors.toList());
        return this;
    }

    public void setContractMarketPlaces(List<ContractMarketPlace> contractMarketPlaces) {
        this.contractMarketPlaces = contractMarketPlaces;
    }

    public Client getClient() {
        return client;
    }

    public Contract client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contract)) {
            return false;
        }
        return id != null && id.equals(((Contract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + getId() +
                ", channelType='" + getChannelType() + "'" +
                ", dateAdd='" + getDateAdd() + "'" +
                "}";
    }
}
