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
 * A Localite.
 */
@Entity
@Table(name = "localite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "localite")
public class Localite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "codepostal")
    private String codepostal;

    @ManyToOne
    private Delegation delegation;

    @OneToMany(mappedBy = "localite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Employer> employers = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annonces = new HashSet<>();

    @OneToMany(mappedBy = "localite")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @ManyToMany(mappedBy = "localites")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpActualite> empActualites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Localite name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodepostal() {
        return codepostal;
    }

    public Localite codepostal(String codepostal) {
        this.codepostal = codepostal;
        return this;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public Localite delegation(Delegation delegation) {
        this.delegation = delegation;
        return this;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }

    public Set<Employer> getEmployers() {
        return employers;
    }

    public Localite employers(Set<Employer> employers) {
        this.employers = employers;
        return this;
    }

    public Localite addEmployer(Employer employer) {
        this.employers.add(employer);
        employer.setLocalite(this);
        return this;
    }

    public Localite removeEmployer(Employer employer) {
        this.employers.remove(employer);
        employer.setLocalite(null);
        return this;
    }

    public void setEmployers(Set<Employer> employers) {
        this.employers = employers;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public Localite annonces(Set<Annonce> annonces) {
        this.annonces = annonces;
        return this;
    }

    public Localite addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.setLocalite(this);
        return this;
    }

    public Localite removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.setLocalite(null);
        return this;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Localite produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Localite addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setLocalite(this);
        return this;
    }

    public Localite removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setLocalite(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Set<EmpActualite> getEmpActualites() {
        return empActualites;
    }

    public Localite empActualites(Set<EmpActualite> empActualites) {
        this.empActualites = empActualites;
        return this;
    }

    public Localite addEmpActualite(EmpActualite empActualite) {
        this.empActualites.add(empActualite);
        empActualite.getLocalites().add(this);
        return this;
    }

    public Localite removeEmpActualite(EmpActualite empActualite) {
        this.empActualites.remove(empActualite);
        empActualite.getLocalites().remove(this);
        return this;
    }

    public void setEmpActualites(Set<EmpActualite> empActualites) {
        this.empActualites = empActualites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Localite localite = (Localite) o;
        if (localite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, localite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Localite{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", codepostal='" + codepostal + "'" +
            '}';
    }
}
