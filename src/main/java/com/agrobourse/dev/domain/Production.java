package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Production.
 */
@Entity
@Table(name = "production")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "production")
public class Production implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dateofproduction")
    private LocalDate dateofproduction;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    private Agriculteur agriculteur;

    @ManyToOne
    private Variete variete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateofproduction() {
        return dateofproduction;
    }

    public Production dateofproduction(LocalDate dateofproduction) {
        this.dateofproduction = dateofproduction;
        return this;
    }

    public void setDateofproduction(LocalDate dateofproduction) {
        this.dateofproduction = dateofproduction;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Production quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public Production agriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
        return this;
    }

    public void setAgriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
    }

    public Variete getVariete() {
        return variete;
    }

    public Production variete(Variete variete) {
        this.variete = variete;
        return this;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Production production = (Production) o;
        if (production.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, production.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Production{" +
            "id=" + id +
            ", dateofproduction='" + dateofproduction + "'" +
            ", quantity='" + quantity + "'" +
            '}';
    }
}
