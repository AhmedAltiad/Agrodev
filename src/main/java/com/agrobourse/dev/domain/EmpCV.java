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
 * A EmpCV.
 */
@Entity
@Table(name = "emp_cv")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "empcv")
public class EmpCV implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "anneexperience")
    private String anneexperience;

    @Column(name = "permis")
    private Integer permis;

    @Column(name = "niveau_scolaire")
    private Integer niveauScolaire;

    @Column(name = "diplome")
    private String diplome;

    @OneToMany(mappedBy = "empCV")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Formation> formations = new HashSet<>();

    @ManyToOne
    private Employee employee;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "empcv_cv_specialities",
               joinColumns = @JoinColumn(name="empcvs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="cv_specialities_id", referencedColumnName="id"))
    private Set<CvSpecialities> cvSpecialities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public EmpCV title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnneexperience() {
        return anneexperience;
    }

    public EmpCV anneexperience(String anneexperience) {
        this.anneexperience = anneexperience;
        return this;
    }

    public void setAnneexperience(String anneexperience) {
        this.anneexperience = anneexperience;
    }

    public Integer getPermis() {
        return permis;
    }

    public EmpCV permis(Integer permis) {
        this.permis = permis;
        return this;
    }

    public void setPermis(Integer permis) {
        this.permis = permis;
    }

    public Integer getNiveauScolaire() {
        return niveauScolaire;
    }

    public EmpCV niveauScolaire(Integer niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
        return this;
    }

    public void setNiveauScolaire(Integer niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public String getDiplome() {
        return diplome;
    }

    public EmpCV diplome(String diplome) {
        this.diplome = diplome;
        return this;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public EmpCV formations(Set<Formation> formations) {
        this.formations = formations;
        return this;
    }

    public EmpCV addFormation(Formation formation) {
        this.formations.add(formation);
        formation.setEmpCV(this);
        return this;
    }

    public EmpCV removeFormation(Formation formation) {
        this.formations.remove(formation);
        formation.setEmpCV(null);
        return this;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmpCV employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<CvSpecialities> getCvSpecialities() {
        return cvSpecialities;
    }

    public EmpCV cvSpecialities(Set<CvSpecialities> cvSpecialities) {
        this.cvSpecialities = cvSpecialities;
        return this;
    }

    public EmpCV addCvSpecialities(CvSpecialities cvSpecialities) {
        this.cvSpecialities.add(cvSpecialities);
        cvSpecialities.getEmpCVS().add(this);
        return this;
    }

    public EmpCV removeCvSpecialities(CvSpecialities cvSpecialities) {
        this.cvSpecialities.remove(cvSpecialities);
        cvSpecialities.getEmpCVS().remove(this);
        return this;
    }

    public void setCvSpecialities(Set<CvSpecialities> cvSpecialities) {
        this.cvSpecialities = cvSpecialities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmpCV empCV = (EmpCV) o;
        if (empCV.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, empCV.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmpCV{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", anneexperience='" + anneexperience + "'" +
            ", permis='" + permis + "'" +
            ", niveauScolaire='" + niveauScolaire + "'" +
            ", diplome='" + diplome + "'" +
            '}';
    }
}
