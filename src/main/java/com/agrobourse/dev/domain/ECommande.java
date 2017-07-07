package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ECommande.
 */
@Entity
@Table(name = "e_commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ecommande")
public class ECommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "quantite")
    private Long quantite;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "numcommande")
    private Long numcommande;

    @ManyToOne
    private Annonce annonce;

    @ManyToOne
    private Profil commandBy;

    @ManyToOne
    private Transaction transaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtat() {
        return etat;
    }

    public ECommande etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Double getPrix() {
        return prix;
    }

    public ECommande prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Long getQuantite() {
        return quantite;
    }

    public ECommande quantite(Long quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ECommande date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getNumcommande() {
        return numcommande;
    }

    public ECommande numcommande(Long numcommande) {
        this.numcommande = numcommande;
        return this;
    }

    public void setNumcommande(Long numcommande) {
        this.numcommande = numcommande;
    }

    public Annonce getAnnonce() {
        return annonce;
    }

    public ECommande annonce(Annonce annonce) {
        this.annonce = annonce;
        return this;
    }

    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public Profil getCommandBy() {
        return commandBy;
    }

    public ECommande commandBy(Profil profil) {
        this.commandBy = profil;
        return this;
    }

    public void setCommandBy(Profil profil) {
        this.commandBy = profil;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public ECommande transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ECommande eCommande = (ECommande) o;
        if (eCommande.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eCommande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ECommande{" +
            "id=" + id +
            ", etat='" + etat + "'" +
            ", prix='" + prix + "'" +
            ", quantite='" + quantite + "'" +
            ", date='" + date + "'" +
            ", numcommande='" + numcommande + "'" +
            '}';
    }
}
