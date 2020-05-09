package ru.krasilova.otus.spring.brokerage.models;

import javax.persistence.*;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.krasilova.otus.spring.brokerage.models.enumeration.MarketPlaceType;


@Entity
@Data
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract_market_place")
public class ContractMarketPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "market_place_type")
    private MarketPlaceType marketPlaceType;

    @Column(name = "date_add")
    private String dateAdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private MarketPlaceType getMarketPlaceType() {
        return marketPlaceType;
    }

    public ContractMarketPlace marketPlaceType(MarketPlaceType marketPlaceType) {
        this.marketPlaceType = marketPlaceType;
        return this;
    }

    public void setMarketPlaceType(MarketPlaceType marketPlaceType) {
        this.marketPlaceType = marketPlaceType;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public ContractMarketPlace dateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
        return this;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Contract getContract() {
        return contract;
    }

    public ContractMarketPlace contract(Contract contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractMarketPlace)) {
            return false;
        }
        return id != null && id.equals(((ContractMarketPlace) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "'" + getMarketPlaceType() + "'";
    }
}
