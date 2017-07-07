package com.agrobourse.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_of_order")
    private LocalDate dateOfOrder;

    @Column(name = "date_of_delivery")
    private LocalDate dateOfDelivery;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Integer status;

    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeDetails> commandeDetails = new HashSet<>();

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private TraderAGB traderAGB;

    @ManyToOne
    private Agriculteur agriculteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public Commande dateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
        return this;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public Commande dateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
        return this;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Commande quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Commande price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public Commande status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<CommandeDetails> getCommandeDetails() {
        return commandeDetails;
    }

    public Commande commandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
        return this;
    }

    public Commande addCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.add(commandeDetails);
        commandeDetails.setCommande(this);
        return this;
    }

    public Commande removeCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.remove(commandeDetails);
        commandeDetails.setCommande(null);
        return this;
    }

    public void setCommandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Commande currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TraderAGB getTraderAGB() {
        return traderAGB;
    }

    public Commande traderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
        return this;
    }

    public void setTraderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public Commande agriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
        return this;
    }

    public void setAgriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        if (commande.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + id +
            ", dateOfOrder='" + dateOfOrder + "'" +
            ", dateOfDelivery='" + dateOfDelivery + "'" +
            ", quantity='" + quantity + "'" +
            ", price='" + price + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
