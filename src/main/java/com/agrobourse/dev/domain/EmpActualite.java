package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.agrobourse.dev.domain.enumeration.Empactualitetype;

/**
 * A EmpActualite.
 */
@Entity
@Table(name = "emp_actualite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empactualite")
public class EmpActualite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "init_date")
    private LocalDate initDate;

    @Column(name = "periode")
    private String periode;

    @Column(name = "secteur")
    private String secteur;

    @Column(name = "title")
    private String title;

    @Column(name = "short_disc")
    private String shortDisc;

    @Column(name = "long_disc")
    private String longDisc;

    @Column(name = "image")
    private String image;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "empacttype")
    private Empactualitetype empacttype;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "emp_actualite_localite",
               joinColumns = @JoinColumn(name="emp_actualites_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="localites_id", referencedColumnName="id"))
    private Set<Localite> localites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public EmpActualite initDate(LocalDate initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public String getPeriode() {
        return periode;
    }

    public EmpActualite periode(String periode) {
        this.periode = periode;
        return this;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getSecteur() {
        return secteur;
    }

    public EmpActualite secteur(String secteur) {
        this.secteur = secteur;
        return this;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getTitle() {
        return title;
    }

    public EmpActualite title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDisc() {
        return shortDisc;
    }

    public EmpActualite shortDisc(String shortDisc) {
        this.shortDisc = shortDisc;
        return this;
    }

    public void setShortDisc(String shortDisc) {
        this.shortDisc = shortDisc;
    }

    public String getLongDisc() {
        return longDisc;
    }

    public EmpActualite longDisc(String longDisc) {
        this.longDisc = longDisc;
        return this;
    }

    public void setLongDisc(String longDisc) {
        this.longDisc = longDisc;
    }

    public String getImage() {
        return image;
    }

    public EmpActualite image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdresse() {
        return adresse;
    }

    public EmpActualite adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Empactualitetype getEmpacttype() {
        return empacttype;
    }

    public EmpActualite empacttype(Empactualitetype empacttype) {
        this.empacttype = empacttype;
        return this;
    }

    public void setEmpacttype(Empactualitetype empacttype) {
        this.empacttype = empacttype;
    }

    public Set<Localite> getLocalites() {
        return localites;
    }

    public EmpActualite localites(Set<Localite> localites) {
        this.localites = localites;
        return this;
    }

    public EmpActualite addLocalite(Localite localite) {
        this.localites.add(localite);
        localite.getEmpActualites().add(this);
        return this;
    }

    public EmpActualite removeLocalite(Localite localite) {
        this.localites.remove(localite);
        localite.getEmpActualites().remove(this);
        return this;
    }

    public void setLocalites(Set<Localite> localites) {
        this.localites = localites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmpActualite empActualite = (EmpActualite) o;
        if (empActualite.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, empActualite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmpActualite{" +
            "id=" + id +
            ", initDate='" + initDate + "'" +
            ", periode='" + periode + "'" +
            ", secteur='" + secteur + "'" +
            ", title='" + title + "'" +
            ", shortDisc='" + shortDisc + "'" +
            ", longDisc='" + longDisc + "'" +
            ", image='" + image + "'" +
            ", adresse='" + adresse + "'" +
            ", empacttype='" + empacttype + "'" +
            '}';
    }
}
