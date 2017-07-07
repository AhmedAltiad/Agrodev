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
 * A Delegation.
 */
@Entity
@Table(name = "delegation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "delegation")
public class Delegation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Gouvernorat gouvernorat;

    @OneToMany(mappedBy = "delegation")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Localite> localites = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Delegation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gouvernorat getGouvernorat() {
        return gouvernorat;
    }

    public Delegation gouvernorat(Gouvernorat gouvernorat) {
        this.gouvernorat = gouvernorat;
        return this;
    }

    public void setGouvernorat(Gouvernorat gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public Set<Localite> getLocalites() {
        return localites;
    }

    public Delegation localites(Set<Localite> localites) {
        this.localites = localites;
        return this;
    }

    public Delegation addLocalite(Localite localite) {
        this.localites.add(localite);
        localite.setDelegation(this);
        return this;
    }

    public Delegation removeLocalite(Localite localite) {
        this.localites.remove(localite);
        localite.setDelegation(null);
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
        Delegation delegation = (Delegation) o;
        if (delegation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, delegation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Delegation{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
