package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Trade.
 */
@Entity
@Table(name = "trade")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "trade")
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_of_order")
    private LocalDate dateOfOrder;

    @Column(name = "date_of_delivery")
    private LocalDate dateOfDelivery;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "position")
    private String position;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Variete variete;

    @ManyToOne
    private TraderAGB traderAGB;

    @ManyToOne
    private TraderCA traderCA;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public Trade dateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
        return this;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public LocalDate getDateOfDelivery() {
        return dateOfDelivery;
    }

    public Trade dateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
        return this;
    }

    public void setDateOfDelivery(LocalDate dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public Trade prixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getPosition() {
        return position;
    }

    public Trade position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Trade quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getMontant() {
        return montant;
    }

    public Trade montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Integer getStatus() {
        return status;
    }

    public Trade status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Trade currency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Variete getVariete() {
        return variete;
    }

    public Trade variete(Variete variete) {
        this.variete = variete;
        return this;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    public TraderAGB getTraderAGB() {
        return traderAGB;
    }

    public Trade traderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
        return this;
    }

    public void setTraderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
    }

    public TraderCA getTraderCA() {
        return traderCA;
    }

    public Trade traderCA(TraderCA traderCA) {
        this.traderCA = traderCA;
        return this;
    }

    public void setTraderCA(TraderCA traderCA) {
        this.traderCA = traderCA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trade trade = (Trade) o;
        if (trade.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, trade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Trade{" +
            "id=" + id +
            ", dateOfOrder='" + dateOfOrder + "'" +
            ", dateOfDelivery='" + dateOfDelivery + "'" +
            ", prixUnitaire='" + prixUnitaire + "'" +
            ", position='" + position + "'" +
            ", quantity='" + quantity + "'" +
            ", montant='" + montant + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
