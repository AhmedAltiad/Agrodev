package com.agrobourse.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TransactionHistorique.
 */
@Entity
@Table(name = "transaction_historique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transactionhistorique")
public class TransactionHistorique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @Column(name = "numtransaction")
    private Long numtransaction;

    @Column(name = "cmdspayees")
    private Integer cmdspayees;

    @Column(name = "image")
    private String image;

    @Column(name = "date_validation")
    private ZonedDateTime dateValidation;

    @Column(name = "date_validation_paiment")
    private ZonedDateTime dateValidationPaiment;

    @Column(name = "date_refuse")
    private ZonedDateTime dateRefuse;

    @Column(name = "date_refuspaiment")
    private ZonedDateTime dateRefuspaiment;

    @OneToMany(mappedBy = "transactionHistorique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommandeHistorique> eCommandeHistoriques = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtat() {
        return etat;
    }

    public TransactionHistorique etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Double getPrix() {
        return prix;
    }

    public TransactionHistorique prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public TransactionHistorique date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getNumtransaction() {
        return numtransaction;
    }

    public TransactionHistorique numtransaction(Long numtransaction) {
        this.numtransaction = numtransaction;
        return this;
    }

    public void setNumtransaction(Long numtransaction) {
        this.numtransaction = numtransaction;
    }

    public Integer getCmdspayees() {
        return cmdspayees;
    }

    public TransactionHistorique cmdspayees(Integer cmdspayees) {
        this.cmdspayees = cmdspayees;
        return this;
    }

    public void setCmdspayees(Integer cmdspayees) {
        this.cmdspayees = cmdspayees;
    }

    public String getImage() {
        return image;
    }

    public TransactionHistorique image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public TransactionHistorique dateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public ZonedDateTime getDateValidationPaiment() {
        return dateValidationPaiment;
    }

    public TransactionHistorique dateValidationPaiment(ZonedDateTime dateValidationPaiment) {
        this.dateValidationPaiment = dateValidationPaiment;
        return this;
    }

    public void setDateValidationPaiment(ZonedDateTime dateValidationPaiment) {
        this.dateValidationPaiment = dateValidationPaiment;
    }

    public ZonedDateTime getDateRefuse() {
        return dateRefuse;
    }

    public TransactionHistorique dateRefuse(ZonedDateTime dateRefuse) {
        this.dateRefuse = dateRefuse;
        return this;
    }

    public void setDateRefuse(ZonedDateTime dateRefuse) {
        this.dateRefuse = dateRefuse;
    }

    public ZonedDateTime getDateRefuspaiment() {
        return dateRefuspaiment;
    }

    public TransactionHistorique dateRefuspaiment(ZonedDateTime dateRefuspaiment) {
        this.dateRefuspaiment = dateRefuspaiment;
        return this;
    }

    public void setDateRefuspaiment(ZonedDateTime dateRefuspaiment) {
        this.dateRefuspaiment = dateRefuspaiment;
    }

    public Set<ECommandeHistorique> getECommandeHistoriques() {
        return eCommandeHistoriques;
    }

    public TransactionHistorique eCommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
        return this;
    }

    public TransactionHistorique addECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.add(eCommandeHistorique);
        eCommandeHistorique.setTransactionHistorique(this);
        return this;
    }

    public TransactionHistorique removeECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.remove(eCommandeHistorique);
        eCommandeHistorique.setTransactionHistorique(null);
        return this;
    }

    public void setECommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransactionHistorique transactionHistorique = (TransactionHistorique) o;
        if (transactionHistorique.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transactionHistorique.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransactionHistorique{" +
            "id=" + id +
            ", etat='" + etat + "'" +
            ", prix='" + prix + "'" +
            ", date='" + date + "'" +
            ", numtransaction='" + numtransaction + "'" +
            ", cmdspayees='" + cmdspayees + "'" +
            ", image='" + image + "'" +
            ", dateValidation='" + dateValidation + "'" +
            ", dateValidationPaiment='" + dateValidationPaiment + "'" +
            ", dateRefuse='" + dateRefuse + "'" +
            ", dateRefuspaiment='" + dateRefuspaiment + "'" +
            '}';
    }
}
