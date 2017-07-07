package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CommandeDetails.
 */
@Entity
@Table(name = "commande_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commandedetails")
public class CommandeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Variete variete;

    @ManyToOne
    private Agriculteur agriculteur;

    @ManyToOne
    private Commande commande;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public CommandeDetails price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Variete getVariete() {
        return variete;
    }

    public CommandeDetails variete(Variete variete) {
        this.variete = variete;
        return this;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public CommandeDetails agriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
        return this;
    }

    public void setAgriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
    }

    public Commande getCommande() {
        return commande;
    }

    public CommandeDetails commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandeDetails commandeDetails = (CommandeDetails) o;
        if (commandeDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commandeDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommandeDetails{" +
            "id=" + id +
            ", price='" + price + "'" +
            '}';
    }
}
