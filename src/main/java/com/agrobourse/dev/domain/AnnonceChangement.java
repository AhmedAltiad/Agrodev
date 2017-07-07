package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AnnonceChangement.
 */
@Entity
@Table(name = "annonce_changement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "annoncechangement")
public class AnnonceChangement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "changement")
    private Integer changement;

    @Column(name = "createddate")
    private ZonedDateTime createddate;

    @ManyToOne
    private Profil profil;

    @ManyToOne
    private AnnonceHistorique annonceHistorique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AnnonceChangement name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChangement() {
        return changement;
    }

    public AnnonceChangement changement(Integer changement) {
        this.changement = changement;
        return this;
    }

    public void setChangement(Integer changement) {
        this.changement = changement;
    }

    public ZonedDateTime getCreateddate() {
        return createddate;
    }

    public AnnonceChangement createddate(ZonedDateTime createddate) {
        this.createddate = createddate;
        return this;
    }

    public void setCreateddate(ZonedDateTime createddate) {
        this.createddate = createddate;
    }

    public Profil getProfil() {
        return profil;
    }

    public AnnonceChangement profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public AnnonceHistorique getAnnonceHistorique() {
        return annonceHistorique;
    }

    public AnnonceChangement annonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistorique = annonceHistorique;
        return this;
    }

    public void setAnnonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistorique = annonceHistorique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnnonceChangement annonceChangement = (AnnonceChangement) o;
        if (annonceChangement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, annonceChangement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AnnonceChangement{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", changement='" + changement + "'" +
            ", createddate='" + createddate + "'" +
            '}';
    }
}
