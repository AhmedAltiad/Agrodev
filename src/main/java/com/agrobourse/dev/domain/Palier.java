package com.agrobourse.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Palier.
 */
@Entity
@Table(name = "palier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "palier")
public class Palier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datedebut")
    private LocalDate datedebut;

    @Column(name = "datefin")
    private LocalDate datefin;

    @Column(name = "nombrecons")
    private Integer nombrecons;

    @Column(name = "promotion")
    private Double promotion;

    @OneToMany(mappedBy = "palier")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @OneToMany(mappedBy = "palier")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annonces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatedebut() {
        return datedebut;
    }

    public Palier datedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
        return this;
    }

    public void setDatedebut(LocalDate datedebut) {
        this.datedebut = datedebut;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public Palier datefin(LocalDate datefin) {
        this.datefin = datefin;
        return this;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    public Integer getNombrecons() {
        return nombrecons;
    }

    public Palier nombrecons(Integer nombrecons) {
        this.nombrecons = nombrecons;
        return this;
    }

    public void setNombrecons(Integer nombrecons) {
        this.nombrecons = nombrecons;
    }

    public Double getPromotion() {
        return promotion;
    }

    public Palier promotion(Double promotion) {
        this.promotion = promotion;
        return this;
    }

    public void setPromotion(Double promotion) {
        this.promotion = promotion;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Palier produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Palier addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setPalier(this);
        return this;
    }

    public Palier removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setPalier(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public Palier annonces(Set<Annonce> annonces) {
        this.annonces = annonces;
        return this;
    }

    public Palier addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.setPalier(this);
        return this;
    }

    public Palier removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.setPalier(null);
        return this;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Palier palier = (Palier) o;
        if (palier.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, palier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Palier{" +
            "id=" + id +
            ", datedebut='" + datedebut + "'" +
            ", datefin='" + datefin + "'" +
            ", nombrecons='" + nombrecons + "'" +
            ", promotion='" + promotion + "'" +
            '}';
    }
}
