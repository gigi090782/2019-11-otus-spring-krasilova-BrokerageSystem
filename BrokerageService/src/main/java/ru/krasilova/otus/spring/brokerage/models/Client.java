package ru.krasilova.otus.spring.brokerage.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Cacheable(false)

@Table(name = "client")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "allJoin", attributeNodes = {
                @NamedAttributeNode("addresses"),
                @NamedAttributeNode("contacts"),
                @NamedAttributeNode("contracts")
        })})

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    private String birthDate;

    @Column(name = "date_add")
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    private String dateAdd;


    @OneToMany(targetEntity = Address.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Address> addresses = new ArrayList<Address>();

   @OneToMany(targetEntity = Contact.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "client_id")
   @Fetch(value = FetchMode.SUBSELECT)
    private List<Contact> contacts = new ArrayList<Contact>();


    @OneToMany(targetEntity = Contract.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Contract> contracts = new ArrayList<Contract>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Client lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Client birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public Client dateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
        return this;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Client addresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Client addAddress(Address address) {
        this.addresses.add(address);
        address.setClient(this);
        return this;
    }

    public Client removeAddress(Long id) {
        this.addresses = this.addresses.stream().filter(a -> a.getId() != id).collect(Collectors.toList());
        return this;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Client contacts(List<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Client addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setClient(this);
        return this;
    }

    public Client removeContact(Long id) {
        this.contacts = this.contacts.stream().filter(a -> a.getId() != id).collect(Collectors.toList());
        return this;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public Client contracts(List<Contract> contracts) {
        this.contracts = contracts;
        return this;
    }

    public Client addContract(Contract contract) {
        this.contracts.add(contract);
        contract.setClient(this);
        return this;
    }

    public Client removeContract(Contract contract) {
        this.contracts.remove(contract);
        contract.setClient(null);
        return this;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return getLastName() + " " +
                getFirstName() + " " +
                getSecondName();
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
