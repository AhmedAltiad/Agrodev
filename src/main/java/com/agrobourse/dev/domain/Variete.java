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
 * A Variete.
 */
@Entity
@Table(name = "variete")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "variete")
public class Variete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @ManyToOne
    private Produit produit;

    @OneToMany(mappedBy = "variete")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annonces = new HashSet<>();

    @OneToMany(mappedBy = "variete")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeDetails> commandeDetails = new HashSet<>();

    @OneToMany(mappedBy = "variete")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trade> varietes = new HashSet<>();

    @OneToMany(mappedBy = "variete")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Production> productions = new HashSet<>();

    @ManyToMany(mappedBy = "varietes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Portefolio> portefolios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Variete name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Variete description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public Variete image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Produit getProduit() {
        return produit;
    }

    public Variete produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public Variete annonces(Set<Annonce> annonces) {
        this.annonces = annonces;
        return this;
    }

    public Variete addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.setVariete(this);
        return this;
    }

    public Variete removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.setVariete(null);
        return this;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Set<CommandeDetails> getCommandeDetails() {
        return commandeDetails;
    }

    public Variete commandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
        return this;
    }

    public Variete addCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.add(commandeDetails);
        commandeDetails.setVariete(this);
        return this;
    }

    public Variete removeCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.remove(commandeDetails);
        commandeDetails.setVariete(null);
        return this;
    }

    public void setCommandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
    }

    public Set<Trade> getVarietes() {
        return varietes;
    }

    public Variete varietes(Set<Trade> trades) {
        this.varietes = trades;
        return this;
    }

    public Variete addVariete(Trade trade) {
        this.varietes.add(trade);
        trade.setVariete(this);
        return this;
    }

    public Variete removeVariete(Trade trade) {
        this.varietes.remove(trade);
        trade.setVariete(null);
        return this;
    }

    public void setVarietes(Set<Trade> trades) {
        this.varietes = trades;
    }

    public Set<Production> getProductions() {
        return productions;
    }

    public Variete productions(Set<Production> productions) {
        this.productions = productions;
        return this;
    }

    public Variete addProduction(Production production) {
        this.productions.add(production);
        production.setVariete(this);
        return this;
    }

    public Variete removeProduction(Production production) {
        this.productions.remove(production);
        production.setVariete(null);
        return this;
    }

    public void setProductions(Set<Production> productions) {
        this.productions = productions;
    }

    public Set<Portefolio> getPortefolios() {
        return portefolios;
    }

    public Variete portefolios(Set<Portefolio> portefolios) {
        this.portefolios = portefolios;
        return this;
    }

    public Variete addPortefolio(Portefolio portefolio) {
        this.portefolios.add(portefolio);
        portefolio.getVarietes().add(this);
        return this;
    }

    public Variete removePortefolio(Portefolio portefolio) {
        this.portefolios.remove(portefolio);
        portefolio.getVarietes().remove(this);
        return this;
    }

    public void setPortefolios(Set<Portefolio> portefolios) {
        this.portefolios = portefolios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variete variete = (Variete) o;
        if (variete.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, variete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Variete{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", image='" + image + "'" +
            '}';
    }
}
