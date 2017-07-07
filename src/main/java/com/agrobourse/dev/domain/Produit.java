package com.agrobourse.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "produit")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Variete> varietes = new HashSet<>();

    @ManyToOne
    private Localite localite;

    @ManyToOne
    private Palier palier;

    @ManyToOne
    private Souscategorie souscategorie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Produit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Variete> getVarietes() {
        return varietes;
    }

    public Produit varietes(Set<Variete> varietes) {
        this.varietes = varietes;
        return this;
    }

    public Produit addVarietes(Variete variete) {
        this.varietes.add(variete);
        variete.setProduit(this);
        return this;
    }

    public Produit removeVarietes(Variete variete) {
        this.varietes.remove(variete);
        variete.setProduit(null);
        return this;
    }

    public void setVarietes(Set<Variete> varietes) {
        this.varietes = varietes;
    }

    public Localite getLocalite() {
        return localite;
    }

    public Produit localite(Localite localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Palier getPalier() {
        return palier;
    }

    public Produit palier(Palier palier) {
        this.palier = palier;
        return this;
    }

    public void setPalier(Palier palier) {
        this.palier = palier;
    }

    public Souscategorie getSouscategorie() {
        return souscategorie;
    }

    public Produit souscategorie(Souscategorie souscategorie) {
        this.souscategorie = souscategorie;
        return this;
    }

    public void setSouscategorie(Souscategorie souscategorie) {
        this.souscategorie = souscategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Produit produit = (Produit) o;
        if (produit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, produit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
