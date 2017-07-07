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
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "transaction")
public class Transaction implements Serializable {

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

    @ManyToOne
    private Profil userTransaction;

    @ManyToOne
    private Profil validePar;

    @ManyToOne
    private Profil validePaiment;

    @ManyToOne
    private Profil refuserPar;

    @ManyToOne
    private Profil refusePaiment;

    @OneToMany(mappedBy = "transaction")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommande> eCommandes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEtat() {
        return etat;
    }

    public Transaction etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public Double getPrix() {
        return prix;
    }

    public Transaction prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Transaction date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getNumtransaction() {
        return numtransaction;
    }

    public Transaction numtransaction(Long numtransaction) {
        this.numtransaction = numtransaction;
        return this;
    }

    public void setNumtransaction(Long numtransaction) {
        this.numtransaction = numtransaction;
    }

    public Integer getCmdspayees() {
        return cmdspayees;
    }

    public Transaction cmdspayees(Integer cmdspayees) {
        this.cmdspayees = cmdspayees;
        return this;
    }

    public void setCmdspayees(Integer cmdspayees) {
        this.cmdspayees = cmdspayees;
    }

    public String getImage() {
        return image;
    }

    public Transaction image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ZonedDateTime getDateValidation() {
        return dateValidation;
    }

    public Transaction dateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
        return this;
    }

    public void setDateValidation(ZonedDateTime dateValidation) {
        this.dateValidation = dateValidation;
    }

    public ZonedDateTime getDateValidationPaiment() {
        return dateValidationPaiment;
    }

    public Transaction dateValidationPaiment(ZonedDateTime dateValidationPaiment) {
        this.dateValidationPaiment = dateValidationPaiment;
        return this;
    }

    public void setDateValidationPaiment(ZonedDateTime dateValidationPaiment) {
        this.dateValidationPaiment = dateValidationPaiment;
    }

    public ZonedDateTime getDateRefuse() {
        return dateRefuse;
    }

    public Transaction dateRefuse(ZonedDateTime dateRefuse) {
        this.dateRefuse = dateRefuse;
        return this;
    }

    public void setDateRefuse(ZonedDateTime dateRefuse) {
        this.dateRefuse = dateRefuse;
    }

    public ZonedDateTime getDateRefuspaiment() {
        return dateRefuspaiment;
    }

    public Transaction dateRefuspaiment(ZonedDateTime dateRefuspaiment) {
        this.dateRefuspaiment = dateRefuspaiment;
        return this;
    }

    public void setDateRefuspaiment(ZonedDateTime dateRefuspaiment) {
        this.dateRefuspaiment = dateRefuspaiment;
    }

    public Profil getUserTransaction() {
        return userTransaction;
    }

    public Transaction userTransaction(Profil profil) {
        this.userTransaction = profil;
        return this;
    }

    public void setUserTransaction(Profil profil) {
        this.userTransaction = profil;
    }

    public Profil getValidePar() {
        return validePar;
    }

    public Transaction validePar(Profil profil) {
        this.validePar = profil;
        return this;
    }

    public void setValidePar(Profil profil) {
        this.validePar = profil;
    }

    public Profil getValidePaiment() {
        return validePaiment;
    }

    public Transaction validePaiment(Profil profil) {
        this.validePaiment = profil;
        return this;
    }

    public void setValidePaiment(Profil profil) {
        this.validePaiment = profil;
    }

    public Profil getRefuserPar() {
        return refuserPar;
    }

    public Transaction refuserPar(Profil profil) {
        this.refuserPar = profil;
        return this;
    }

    public void setRefuserPar(Profil profil) {
        this.refuserPar = profil;
    }

    public Profil getRefusePaiment() {
        return refusePaiment;
    }

    public Transaction refusePaiment(Profil profil) {
        this.refusePaiment = profil;
        return this;
    }

    public void setRefusePaiment(Profil profil) {
        this.refusePaiment = profil;
    }

    public Set<ECommande> getECommandes() {
        return eCommandes;
    }

    public Transaction eCommandes(Set<ECommande> eCommandes) {
        this.eCommandes = eCommandes;
        return this;
    }

    public Transaction addECommande(ECommande eCommande) {
        this.eCommandes.add(eCommande);
        eCommande.setTransaction(this);
        return this;
    }

    public Transaction removeECommande(ECommande eCommande) {
        this.eCommandes.remove(eCommande);
        eCommande.setTransaction(null);
        return this;
    }

    public void setECommandes(Set<ECommande> eCommandes) {
        this.eCommandes = eCommandes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        if (transaction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
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
