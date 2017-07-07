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

/**
 * A EmpAnnonce.
 */
@Entity
@Table(name = "emp_annonce")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empannonce")
public class EmpAnnonce implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "init_date")
    private LocalDate initDate;

    @Column(name = "fin_date")
    private LocalDate finDate;

    @Column(name = "title")
    private String title;

    @Column(name = "type_contrat")
    private String typeContrat;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "jhi_validation")
    private Long validation;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "nbredepostes")
    private Integer nbredepostes;

    @ManyToOne
    private Employer employer;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "emp_annonce_postuls",
               joinColumns = @JoinColumn(name="emp_annonces_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="postuls_id", referencedColumnName="id"))
    private Set<Employee> postuls = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public EmpAnnonce initDate(LocalDate initDate) {
        this.initDate = initDate;
        return this;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getFinDate() {
        return finDate;
    }

    public EmpAnnonce finDate(LocalDate finDate) {
        this.finDate = finDate;
        return this;
    }

    public void setFinDate(LocalDate finDate) {
        this.finDate = finDate;
    }

    public String getTitle() {
        return title;
    }

    public EmpAnnonce title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public EmpAnnonce typeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
        return this;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public EmpAnnonce shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public EmpAnnonce longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Long getValidation() {
        return validation;
    }

    public EmpAnnonce validation(Long validation) {
        this.validation = validation;
        return this;
    }

    public void setValidation(Long validation) {
        this.validation = validation;
    }

    public String getAdresse() {
        return adresse;
    }

    public EmpAnnonce adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getNbredepostes() {
        return nbredepostes;
    }

    public EmpAnnonce nbredepostes(Integer nbredepostes) {
        this.nbredepostes = nbredepostes;
        return this;
    }

    public void setNbredepostes(Integer nbredepostes) {
        this.nbredepostes = nbredepostes;
    }

    public Employer getEmployer() {
        return employer;
    }

    public EmpAnnonce employer(Employer employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Set<Employee> getPostuls() {
        return postuls;
    }

    public EmpAnnonce postuls(Set<Employee> employees) {
        this.postuls = employees;
        return this;
    }

    public EmpAnnonce addPostuls(Employee employee) {
        this.postuls.add(employee);
        employee.getEmpAnnonces().add(this);
        return this;
    }

    public EmpAnnonce removePostuls(Employee employee) {
        this.postuls.remove(employee);
        employee.getEmpAnnonces().remove(this);
        return this;
    }

    public void setPostuls(Set<Employee> employees) {
        this.postuls = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmpAnnonce empAnnonce = (EmpAnnonce) o;
        if (empAnnonce.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, empAnnonce.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmpAnnonce{" +
            "id=" + id +
            ", initDate='" + initDate + "'" +
            ", finDate='" + finDate + "'" +
            ", title='" + title + "'" +
            ", typeContrat='" + typeContrat + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", longDescription='" + longDescription + "'" +
            ", validation='" + validation + "'" +
            ", adresse='" + adresse + "'" +
            ", nbredepostes='" + nbredepostes + "'" +
            '}';
    }
}
