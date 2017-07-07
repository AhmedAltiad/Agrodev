package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActiviteAgricole.
 */
@Entity
@Table(name = "activite_agricole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activiteagricole")
public class ActiviteAgricole implements Serializable {

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

    public ActiviteAgricole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public ActiviteAgricole agriculteur(Agriculteur agriculteur) {
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
        ActiviteAgricole activiteAgricole = (ActiviteAgricole) o;
        if (activiteAgricole.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, activiteAgricole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ActiviteAgricole{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
