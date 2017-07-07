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
 * A Desk.
 */
@Entity
@Table(name = "desk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "desk")
public class Desk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @OneToMany(mappedBy = "desk")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TraderCA> traderCAS = new HashSet<>();

    @OneToMany(mappedBy = "desk")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TraderAGB> traderAGBS = new HashSet<>();

    @ManyToOne
    private Gouvernorat gouvernorat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public Desk adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Set<TraderCA> getTraderCAS() {
        return traderCAS;
    }

    public Desk traderCAS(Set<TraderCA> traderCAS) {
        this.traderCAS = traderCAS;
        return this;
    }

    public Desk addTraderCA(TraderCA traderCA) {
        this.traderCAS.add(traderCA);
        traderCA.setDesk(this);
        return this;
    }

    public Desk removeTraderCA(TraderCA traderCA) {
        this.traderCAS.remove(traderCA);
        traderCA.setDesk(null);
        return this;
    }

    public void setTraderCAS(Set<TraderCA> traderCAS) {
        this.traderCAS = traderCAS;
    }

    public Set<TraderAGB> getTraderAGBS() {
        return traderAGBS;
    }

    public Desk traderAGBS(Set<TraderAGB> traderAGBS) {
        this.traderAGBS = traderAGBS;
        return this;
    }

    public Desk addTraderAGB(TraderAGB traderAGB) {
        this.traderAGBS.add(traderAGB);
        traderAGB.setDesk(this);
        return this;
    }

    public Desk removeTraderAGB(TraderAGB traderAGB) {
        this.traderAGBS.remove(traderAGB);
        traderAGB.setDesk(null);
        return this;
    }

    public void setTraderAGBS(Set<TraderAGB> traderAGBS) {
        this.traderAGBS = traderAGBS;
    }

    public Gouvernorat getGouvernorat() {
        return gouvernorat;
    }

    public Desk gouvernorat(Gouvernorat gouvernorat) {
        this.gouvernorat = gouvernorat;
        return this;
    }

    public void setGouvernorat(Gouvernorat gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Desk desk = (Desk) o;
        if (desk.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, desk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Desk{" +
            "id=" + id +
            ", adresse='" + adresse + "'" +
            '}';
    }
}
