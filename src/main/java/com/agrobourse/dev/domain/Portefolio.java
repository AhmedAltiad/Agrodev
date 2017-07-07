package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Portefolio.
 */
@Entity
@Table(name = "portefolio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "portefolio")
public class Portefolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nbrerealisation")
    private Integer nbrerealisation;

    @Column(name = "nbreencours")
    private Integer nbreencours;

    @Column(name = "specialite")
    private String specialite;

    @ManyToOne
    private TraderAGB traderAGB;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "portefolio_variete",
               joinColumns = @JoinColumn(name="portefolios_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="varietes_id", referencedColumnName="id"))
    private Set<Variete> varietes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbrerealisation() {
        return nbrerealisation;
    }

    public Portefolio nbrerealisation(Integer nbrerealisation) {
        this.nbrerealisation = nbrerealisation;
        return this;
    }

    public void setNbrerealisation(Integer nbrerealisation) {
        this.nbrerealisation = nbrerealisation;
    }

    public Integer getNbreencours() {
        return nbreencours;
    }

    public Portefolio nbreencours(Integer nbreencours) {
        this.nbreencours = nbreencours;
        return this;
    }

    public void setNbreencours(Integer nbreencours) {
        this.nbreencours = nbreencours;
    }

    public String getSpecialite() {
        return specialite;
    }

    public Portefolio specialite(String specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public TraderAGB getTraderAGB() {
        return traderAGB;
    }

    public Portefolio traderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
        return this;
    }

    public void setTraderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
    }

    public Set<Variete> getVarietes() {
        return varietes;
    }

    public Portefolio varietes(Set<Variete> varietes) {
        this.varietes = varietes;
        return this;
    }

    public Portefolio addVariete(Variete variete) {
        this.varietes.add(variete);
        variete.getPortefolios().add(this);
        return this;
    }

    public Portefolio removeVariete(Variete variete) {
        this.varietes.remove(variete);
        variete.getPortefolios().remove(this);
        return this;
    }

    public void setVarietes(Set<Variete> varietes) {
        this.varietes = varietes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Portefolio portefolio = (Portefolio) o;
        if (portefolio.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, portefolio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Portefolio{" +
            "id=" + id +
            ", nbrerealisation='" + nbrerealisation + "'" +
            ", nbreencours='" + nbreencours + "'" +
            ", specialite='" + specialite + "'" +
            '}';
    }
}
