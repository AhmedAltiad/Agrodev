package com.agrobourse.dev.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Annonce.
 */
@Entity
@Table(name = "annonce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "annonce")
public class Annonce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numann")
    private Long numann;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "createddate")
    private ZonedDateTime createddate;

    @Column(name = "lastmodifieddate")
    private ZonedDateTime lastmodifieddate;

    @Column(name = "date_activation")
    private LocalDate dateActivation;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private Variete variete;

    @ManyToOne
    private Profil lastModifiedBy;

    @ManyToOne
    private Localite localite;

    @ManyToOne
    private Profil annonceur;

    @ManyToOne
    private Palier palier;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "annonce_liked_by",
               joinColumns = @JoinColumn(name="annonces_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="liked_bies_id", referencedColumnName="id"))
    private Set<Profil> likedBies = new HashSet<>();

    @OneToMany(mappedBy = "annonce")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommande> eCommandes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumann() {
        return numann;
    }

    public Annonce numann(Long numann) {
        this.numann = numann;
        return this;
    }

    public void setNumann(Long numann) {
        this.numann = numann;
    }

    public Integer getEtat() {
        return etat;
    }

    public Annonce etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public ZonedDateTime getCreateddate() {
        return createddate;
    }

    public Annonce createddate(ZonedDateTime createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getLastmodifieddate() {
        return lastmodifieddate;
    }

    public Annonce lastmodifieddate(ZonedDateTime lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(ZonedDateTime lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public LocalDate getDateActivation() {
        return dateActivation;
    }

    public Annonce dateActivation(LocalDate dateActivation) {
        this.dateActivation = dateActivation;
        return this;
    }

    public void setDateActivation(LocalDate dateActivation) {
        this.dateActivation = dateActivation;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public Annonce dateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
        return this;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Double getPrix() {
        return prix;
    }

    public Annonce prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Annonce quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public Annonce image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public Annonce description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Variete getVariete() {
        return variete;
    }

    public Annonce variete(Variete variete) {
        this.variete = variete;
        return this;
    }

    public void setVariete(Variete variete) {
        this.variete = variete;
    }

    public Profil getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Annonce lastModifiedBy(Profil profil) {
        this.lastModifiedBy = profil;
        return this;
    }

    public void setLastModifiedBy(Profil profil) {
        this.lastModifiedBy = profil;
    }

    public Localite getLocalite() {
        return localite;
    }

    public Annonce localite(Localite localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Profil getAnnonceur() {
        return annonceur;
    }

    public Annonce annonceur(Profil profil) {
        this.annonceur = profil;
        return this;
    }

    public void setAnnonceur(Profil profil) {
        this.annonceur = profil;
    }

    public Palier getPalier() {
        return palier;
    }

    public Annonce palier(Palier palier) {
        this.palier = palier;
        return this;
    }

    public void setPalier(Palier palier) {
        this.palier = palier;
    }

    public Set<Profil> getLikedBies() {
        return likedBies;
    }

    public Annonce likedBies(Set<Profil> profils) {
        this.likedBies = profils;
        return this;
    }

    public Annonce addLikedBy(Profil profil) {
        this.likedBies.add(profil);
        profil.getAnnoncesFavorises().add(this);
        return this;
    }

    public Annonce removeLikedBy(Profil profil) {
        this.likedBies.remove(profil);
        profil.getAnnoncesFavorises().remove(this);
        return this;
    }

    public void setLikedBies(Set<Profil> profils) {
        this.likedBies = profils;
    }

    public Set<ECommande> getECommandes() {
        return eCommandes;
    }

    public Annonce eCommandes(Set<ECommande> eCommandes) {
        this.eCommandes = eCommandes;
        return this;
    }

    public Annonce addECommande(ECommande eCommande) {
        this.eCommandes.add(eCommande);
        eCommande.setAnnonce(this);
        return this;
    }

    public Annonce removeECommande(ECommande eCommande) {
        this.eCommandes.remove(eCommande);
        eCommande.setAnnonce(null);
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
        Annonce annonce = (Annonce) o;
        if (annonce.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, annonce.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Annonce{" +
            "id=" + id +
            ", numann='" + numann + "'" +
            ", etat='" + etat + "'" +
            ", createddate='" + createddate + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            ", dateActivation='" + dateActivation + "'" +
            ", dateExpiration='" + dateExpiration + "'" +
            ", prix='" + prix + "'" +
            ", quantite='" + quantite + "'" +
            ", image='" + image + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
