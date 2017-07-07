package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ECommandeHistorique.
 */
@Entity
@Table(name = "e_commande_historique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "ecommandehistorique")
public class ECommandeHistorique implements Serializable {

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
    private AnnonceHistorique annonceHistorique;

    @ManyToOne
    private Profil userCommandHistorique;

    @ManyToOne
    private TransactionHistorique transactionHistorique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtat() {
        return etat;
    }

    public ECommandeHistorique etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Double getPrix() {
        return prix;
    }

    public ECommandeHistorique prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Long getQuantite() {
        return quantite;
    }

    public ECommandeHistorique quantite(Long quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ECommandeHistorique date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getNumcommande() {
        return numcommande;
    }

    public ECommandeHistorique numcommande(Long numcommande) {
        this.numcommande = numcommande;
        return this;
    }

    public void setNumcommande(Long numcommande) {
        this.numcommande = numcommande;
    }

    public AnnonceHistorique getAnnonceHistorique() {
        return annonceHistorique;
    }

    public ECommandeHistorique annonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistorique = annonceHistorique;
        return this;
    }

    public void setAnnonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistorique = annonceHistorique;
    }

    public Profil getUserCommandHistorique() {
        return userCommandHistorique;
    }

    public ECommandeHistorique userCommandHistorique(Profil profil) {
        this.userCommandHistorique = profil;
        return this;
    }

    public void setUserCommandHistorique(Profil profil) {
        this.userCommandHistorique = profil;
    }

    public TransactionHistorique getTransactionHistorique() {
        return transactionHistorique;
    }

    public ECommandeHistorique transactionHistorique(TransactionHistorique transactionHistorique) {
        this.transactionHistorique = transactionHistorique;
        return this;
    }

    public void setTransactionHistorique(TransactionHistorique transactionHistorique) {
        this.transactionHistorique = transactionHistorique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ECommandeHistorique eCommandeHistorique = (ECommandeHistorique) o;
        if (eCommandeHistorique.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, eCommandeHistorique.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ECommandeHistorique{" +
            "id=" + id +
            ", etat='" + etat + "'" +
            ", prix='" + prix + "'" +
            ", quantite='" + quantite + "'" +
            ", date='" + date + "'" +
            ", numcommande='" + numcommande + "'" +
            '}';
    }
}
