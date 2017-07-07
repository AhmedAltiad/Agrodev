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
 * A Employer.
 */
@Entity
@Table(name = "employer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employer")
public class Employer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "adresse_siege")
    private String adresseSiege;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "nbre_employee_permanant")
    private Integer nbreEmployeePermanant;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @ManyToOne
    private Localite localite;

    @OneToMany(mappedBy = "employer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpAnnonce> empAnnonces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Employer companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getType() {
        return type;
    }

    public Employer type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public Employer companyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
        return this;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getAdresseSiege() {
        return adresseSiege;
    }

    public Employer adresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
        return this;
    }

    public void setAdresseSiege(String adresseSiege) {
        this.adresseSiege = adresseSiege;
    }

    public String getTelephone() {
        return telephone;
    }

    public Employer telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNbreEmployeePermanant() {
        return nbreEmployeePermanant;
    }

    public Employer nbreEmployeePermanant(Integer nbreEmployeePermanant) {
        this.nbreEmployeePermanant = nbreEmployeePermanant;
        return this;
    }

    public void setNbreEmployeePermanant(Integer nbreEmployeePermanant) {
        this.nbreEmployeePermanant = nbreEmployeePermanant;
    }

    public Profil getProfil() {
        return profil;
    }

    public Employer profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Localite getLocalite() {
        return localite;
    }

    public Employer localite(Localite localite) {
        this.localite = localite;
        return this;
    }

    public void setLocalite(Localite localite) {
        this.localite = localite;
    }

    public Set<EmpAnnonce> getEmpAnnonces() {
        return empAnnonces;
    }

    public Employer empAnnonces(Set<EmpAnnonce> empAnnonces) {
        this.empAnnonces = empAnnonces;
        return this;
    }

    public Employer addEmpAnnonce(EmpAnnonce empAnnonce) {
        this.empAnnonces.add(empAnnonce);
        empAnnonce.setEmployer(this);
        return this;
    }

    public Employer removeEmpAnnonce(EmpAnnonce empAnnonce) {
        this.empAnnonces.remove(empAnnonce);
        empAnnonce.setEmployer(null);
        return this;
    }

    public void setEmpAnnonces(Set<EmpAnnonce> empAnnonces) {
        this.empAnnonces = empAnnonces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employer employer = (Employer) o;
        if (employer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employer{" +
            "id=" + id +
            ", companyName='" + companyName + "'" +
            ", type='" + type + "'" +
            ", companyDescription='" + companyDescription + "'" +
            ", adresseSiege='" + adresseSiege + "'" +
            ", telephone='" + telephone + "'" +
            ", nbreEmployeePermanant='" + nbreEmployeePermanant + "'" +
            '}';
    }
}
