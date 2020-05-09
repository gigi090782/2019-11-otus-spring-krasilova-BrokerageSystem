package ru.krasilova.otus.spring.brokerage.models;


import javax.persistence.*;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.krasilova.otus.spring.brokerage.models.enumeration.AddressType;

@Entity
@Data
@Cacheable(false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    @Column(name = "value")
    private String value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;


    public Address(AddressType addressType, String value, Client client) {
        this.addressType = addressType;
        this.value = value;
        this.client = client;
    }

    public Address(AddressType addressType, String value) {
        this.addressType = addressType;
        this.value = value;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public Address addressType(AddressType addressType) {
        this.addressType = addressType;
        return this;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getValue() {
        return value;
    }

    public Address value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Client getClient() {
        return client;
    }

    public Address client(Client client) {
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
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "id=" + getId() +
                ", '" + getAddressType().toString() + "'" +
                ", " + getValue() + "; ";
    }
}
