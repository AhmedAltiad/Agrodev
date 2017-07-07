package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A View.
 */
@Entity
@Table(name = "view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "view")
public class View implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "console_type")
    private String consoleType;

    @Column(name = "duration")
    private LocalDate duration;

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

    public String getConsoleType() {
        return consoleType;
    }

    public View consoleType(String consoleType) {
        this.consoleType = consoleType;
        return this;
    }

    public void setConsoleType(String consoleType) {
        this.consoleType = consoleType;
    }

    public LocalDate getDuration() {
        return duration;
    }

    public View duration(LocalDate duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    public Profil getProfil() {
        return profil;
    }

    public View profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public AnnonceHistorique getAnnonceHistorique() {
        return annonceHistorique;
    }

    public View annonceHistorique(AnnonceHistorique annonceHistorique) {
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
        View view = (View) o;
        if (view.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, view.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "View{" +
            "id=" + id +
            ", consoleType='" + consoleType + "'" +
            ", duration='" + duration + "'" +
            '}';
    }
}
