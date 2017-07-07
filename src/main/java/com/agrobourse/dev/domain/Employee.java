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
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "civilite")
    private String civilite;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "statut")
    private String statut;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "secteuractivite")
    private String secteuractivite;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpCV> employees = new HashSet<>();

    @ManyToMany(mappedBy = "postuls")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmpAnnonce> empAnnonces = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Employee type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCivilite() {
        return civilite;
    }

    public Employee civilite(String civilite) {
        this.civilite = civilite;
        return this;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Employee companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatut() {
        return statut;
    }

    public Employee statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTelephone() {
        return telephone;
    }

    public Employee telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Employee adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSecteuractivite() {
        return secteuractivite;
    }

    public Employee secteuractivite(String secteuractivite) {
        this.secteuractivite = secteuractivite;
        return this;
    }

    public void setSecteuractivite(String secteuractivite) {
        this.secteuractivite = secteuractivite;
    }

    public Profil getProfil() {
        return profil;
    }

    public Employee profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Set<EmpCV> getEmployees() {
        return employees;
    }

    public Employee employees(Set<EmpCV> empCVS) {
        this.employees = empCVS;
        return this;
    }

    public Employee addEmployee(EmpCV empCV) {
        this.employees.add(empCV);
        empCV.setEmployee(this);
        return this;
    }

    public Employee removeEmployee(EmpCV empCV) {
        this.employees.remove(empCV);
        empCV.setEmployee(null);
        return this;
    }

    public void setEmployees(Set<EmpCV> empCVS) {
        this.employees = empCVS;
    }

    public Set<EmpAnnonce> getEmpAnnonces() {
        return empAnnonces;
    }

    public Employee empAnnonces(Set<EmpAnnonce> empAnnonces) {
        this.empAnnonces = empAnnonces;
        return this;
    }

    public Employee addEmpAnnonce(EmpAnnonce empAnnonce) {
        this.empAnnonces.add(empAnnonce);
        empAnnonce.getPostuls().add(this);
        return this;
    }

    public Employee removeEmpAnnonce(EmpAnnonce empAnnonce) {
        this.empAnnonces.remove(empAnnonce);
        empAnnonce.getPostuls().remove(this);
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
        Employee employee = (Employee) o;
        if (employee.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", civilite='" + civilite + "'" +
            ", companyName='" + companyName + "'" +
            ", statut='" + statut + "'" +
            ", telephone='" + telephone + "'" +
            ", adresse='" + adresse + "'" +
            ", secteuractivite='" + secteuractivite + "'" +
            '}';
    }
}
