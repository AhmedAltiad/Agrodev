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
 * A Gouvernorat.
 */
@Entity
@Table(name = "gouvernorat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gouvernorat")
public class Gouvernorat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Region region;

    @OneToMany(mappedBy = "gouvernorat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Delegation> delegations = new HashSet<>();

    @OneToMany(mappedBy = "gouvernorat")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Desk> desks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Gouvernorat name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public Gouvernorat region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Delegation> getDelegations() {
        return delegations;
    }

    public Gouvernorat delegations(Set<Delegation> delegations) {
        this.delegations = delegations;
        return this;
    }

    public Gouvernorat addDelegation(Delegation delegation) {
        this.delegations.add(delegation);
        delegation.setGouvernorat(this);
        return this;
    }

    public Gouvernorat removeDelegation(Delegation delegation) {
        this.delegations.remove(delegation);
        delegation.setGouvernorat(null);
        return this;
    }

    public void setDelegations(Set<Delegation> delegations) {
        this.delegations = delegations;
    }

    public Set<Desk> getDesks() {
        return desks;
    }

    public Gouvernorat desks(Set<Desk> desks) {
        this.desks = desks;
        return this;
    }

    public Gouvernorat addDesk(Desk desk) {
        this.desks.add(desk);
        desk.setGouvernorat(this);
        return this;
    }

    public Gouvernorat removeDesk(Desk desk) {
        this.desks.remove(desk);
        desk.setGouvernorat(null);
        return this;
    }

    public void setDesks(Set<Desk> desks) {
        this.desks = desks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gouvernorat gouvernorat = (Gouvernorat) o;
        if (gouvernorat.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gouvernorat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Gouvernorat{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
