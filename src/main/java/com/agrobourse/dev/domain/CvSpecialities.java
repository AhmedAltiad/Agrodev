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
 * A CvSpecialities.
 */
@Entity
@Table(name = "cv_specialities")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cvspecialities")
public class CvSpecialities implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "cvSpecialities")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpCV> empCVS = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CvSpecialities name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmpCV> getEmpCVS() {
        return empCVS;
    }

    public CvSpecialities empCVS(Set<EmpCV> empCVS) {
        this.empCVS = empCVS;
        return this;
    }

    public CvSpecialities addEmpCV(EmpCV empCV) {
        this.empCVS.add(empCV);
        empCV.getCvSpecialities().add(this);
        return this;
    }

    public CvSpecialities removeEmpCV(EmpCV empCV) {
        this.empCVS.remove(empCV);
        empCV.getCvSpecialities().remove(this);
        return this;
    }

    public void setEmpCVS(Set<EmpCV> empCVS) {
        this.empCVS = empCVS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CvSpecialities cvSpecialities = (CvSpecialities) o;
        if (cvSpecialities.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cvSpecialities.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CvSpecialities{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
