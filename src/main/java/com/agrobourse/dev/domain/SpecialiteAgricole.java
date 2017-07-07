package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SpecialiteAgricole.
 */
@Entity
@Table(name = "specialite_agricole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "specialiteagricole")
public class SpecialiteAgricole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Agriculteur agriculteur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SpecialiteAgricole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public SpecialiteAgricole agriculteur(Agriculteur agriculteur) {
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
        SpecialiteAgricole specialiteAgricole = (SpecialiteAgricole) o;
        if (specialiteAgricole.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, specialiteAgricole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SpecialiteAgricole{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
