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
 * A AnnonceHistorique.
 */
@Entity
@Table(name = "annonce_historique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "annoncehistorique")
public class AnnonceHistorique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "num_annonce")
    private Long numAnnonce;

    @Column(name = "etat")
    private Integer etat;

    @Column(name = "createddate")
    private ZonedDateTime createddate;

    @Column(name = "lastmodifieddate")
    private ZonedDateTime lastmodifieddate;

    @Column(name = "dateactivation")
    private LocalDate dateactivation;

    @Column(name = "dateexpiration")
    private LocalDate dateexpiration;

    @Column(name = "prix")
    private Double prix;

    @Column(name = "quantite")
    private Long quantite;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "annonceHistorique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnnonceChangement> annonceChangements = new HashSet<>();

    @OneToMany(mappedBy = "annonceHistorique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<View> views = new HashSet<>();

    @ManyToOne
    private Profil profil;

    @OneToMany(mappedBy = "annonceHistorique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommandeHistorique> eCommandeHistoriques = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumAnnonce() {
        return numAnnonce;
    }

    public AnnonceHistorique numAnnonce(Long numAnnonce) {
        this.numAnnonce = numAnnonce;
        return this;
    }

    public void setNumAnnonce(Long numAnnonce) {
        this.numAnnonce = numAnnonce;
    }

    public Integer getEtat() {
        return etat;
    }

    public AnnonceHistorique etat(Integer etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }

    public ZonedDateTime getCreateddate() {
        return createddate;
    }

    public AnnonceHistorique createddate(ZonedDateTime createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public ZonedDateTime getLastmodifieddate() {
        return lastmodifieddate;
    }

    public AnnonceHistorique lastmodifieddate(ZonedDateTime lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
        return this;
    }

    public void setLastmodifieddate(ZonedDateTime lastmodifieddate) {
        this.lastmodifieddate = lastmodifieddate;
    }

    public LocalDate getDateactivation() {
        return dateactivation;
    }

    public AnnonceHistorique dateactivation(LocalDate dateactivation) {
        this.dateactivation = dateactivation;
        return this;
    }

    public void setDateactivation(LocalDate dateactivation) {
        this.dateactivation = dateactivation;
    }

    public LocalDate getDateexpiration() {
        return dateexpiration;
    }

    public AnnonceHistorique dateexpiration(LocalDate dateexpiration) {
        this.dateexpiration = dateexpiration;
        return this;
    }

    public void setDateexpiration(LocalDate dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public Double getPrix() {
        return prix;
    }

    public AnnonceHistorique prix(Double prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Long getQuantite() {
        return quantite;
    }

    public AnnonceHistorique quantite(Long quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public AnnonceHistorique image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public AnnonceHistorique description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<AnnonceChangement> getAnnonceChangements() {
        return annonceChangements;
    }

    public AnnonceHistorique annonceChangements(Set<AnnonceChangement> annonceChangements) {
        this.annonceChangements = annonceChangements;
        return this;
    }

    public AnnonceHistorique addAnnonceChangement(AnnonceChangement annonceChangement) {
        this.annonceChangements.add(annonceChangement);
        annonceChangement.setAnnonceHistorique(this);
        return this;
    }

    public AnnonceHistorique removeAnnonceChangement(AnnonceChangement annonceChangement) {
        this.annonceChangements.remove(annonceChangement);
        annonceChangement.setAnnonceHistorique(null);
        return this;
    }

    public void setAnnonceChangements(Set<AnnonceChangement> annonceChangements) {
        this.annonceChangements = annonceChangements;
    }

    public Set<View> getViews() {
        return views;
    }

    public AnnonceHistorique views(Set<View> views) {
        this.views = views;
        return this;
    }

    public AnnonceHistorique addView(View view) {
        this.views.add(view);
        view.setAnnonceHistorique(this);
        return this;
    }

    public AnnonceHistorique removeView(View view) {
        this.views.remove(view);
        view.setAnnonceHistorique(null);
        return this;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public Profil getProfil() {
        return profil;
    }

    public AnnonceHistorique profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Set<ECommandeHistorique> getECommandeHistoriques() {
        return eCommandeHistoriques;
    }

    public AnnonceHistorique eCommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
        return this;
    }

    public AnnonceHistorique addECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.add(eCommandeHistorique);
        eCommandeHistorique.setAnnonceHistorique(this);
        return this;
    }

    public AnnonceHistorique removeECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.remove(eCommandeHistorique);
        eCommandeHistorique.setAnnonceHistorique(null);
        return this;
    }

    public void setECommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnnonceHistorique annonceHistorique = (AnnonceHistorique) o;
        if (annonceHistorique.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, annonceHistorique.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AnnonceHistorique{" +
            "id=" + id +
            ", numAnnonce='" + numAnnonce + "'" +
            ", etat='" + etat + "'" +
            ", createddate='" + createddate + "'" +
            ", lastmodifieddate='" + lastmodifieddate + "'" +
            ", dateactivation='" + dateactivation + "'" +
            ", dateexpiration='" + dateexpiration + "'" +
            ", prix='" + prix + "'" +
            ", quantite='" + quantite + "'" +
            ", image='" + image + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
