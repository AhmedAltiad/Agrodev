package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "formation")
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "certificat")
    private String certificat;

    @Column(name = "duration")
    private LocalDate duration;

    @ManyToOne
    private EmpCV empCV;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Formation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertificat() {
        return certificat;
    }

    public Formation certificat(String certificat) {
        this.certificat = certificat;
        return this;
    }

    public void setCertificat(String certificat) {
        this.certificat = certificat;
    }

    public LocalDate getDuration() {
        return duration;
    }

    public Formation duration(LocalDate duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(LocalDate duration) {
        this.duration = duration;
    }

    public EmpCV getEmpCV() {
        return empCV;
    }

    public Formation empCV(EmpCV empCV) {
        this.empCV = empCV;
        return this;
    }

    public void setEmpCV(EmpCV empCV) {
        this.empCV = empCV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Formation formation = (Formation) o;
        if (formation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, formation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", certificat='" + certificat + "'" +
            ", duration='" + duration + "'" +
            '}';
    }
}
