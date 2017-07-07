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
 * A Souscategorie.
 */
@Entity
@Table(name = "souscategorie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "souscategorie")
public class Souscategorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "souscategorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "souscategorie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Categorie> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Souscategorie name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Souscategorie produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Souscategorie addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setSouscategorie(this);
        return this;
    }

    public Souscategorie removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setSouscategorie(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Souscategorie categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Souscategorie addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.setSouscategorie(this);
        return this;
    }

    public Souscategorie removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.setSouscategorie(null);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Souscategorie souscategorie = (Souscategorie) o;
        if (souscategorie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, souscategorie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Souscategorie{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
