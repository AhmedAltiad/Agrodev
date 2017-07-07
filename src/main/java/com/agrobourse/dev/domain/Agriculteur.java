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
 * A Agriculteur.
 */
@Entity
@Table(name = "agriculteur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agriculteur")
public class Agriculteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "type_societe")
    private String typeSociete;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "adresse_livraison")
    private String adresseLivraison;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "type_agricole")
    private String typeAgricole;

    @Column(name = "superficie")
    private Double superficie;

    @Column(name = "superficie_irriguee")
    private Double superficieIrriguee;

    @Column(name = "bio")
    private Integer bio;

    @Column(name = "nbre_employee_permanant")
    private Integer nbreEmployeePermanant;

    @Column(name = "specialite_production")
    private String specialiteProduction;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @OneToMany(mappedBy = "agriculteur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActiviteAgricole> activiteAgricoles = new HashSet<>();

    @OneToMany(mappedBy = "agriculteur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SpecialiteAgricole> specialitesAgricoles = new HashSet<>();

    @OneToMany(mappedBy = "agriculteur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeDetails> commandeDetails = new HashSet<>();

    @OneToMany(mappedBy = "agriculteur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> commandes = new HashSet<>();

    @OneToMany(mappedBy = "agriculteur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Production> productions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeSociete() {
        return typeSociete;
    }

    public Agriculteur typeSociete(String typeSociete) {
        this.typeSociete = typeSociete;
        return this;
    }

    public void setTypeSociete(String typeSociete) {
        this.typeSociete = typeSociete;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public Agriculteur raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getFirstName() {
        return firstName;
    }

    public Agriculteur firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Agriculteur lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdresse() {
        return adresse;
    }

    public Agriculteur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public Agriculteur adresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getTelephone() {
        return telephone;
    }

    public Agriculteur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTypeAgricole() {
        return typeAgricole;
    }

    public Agriculteur typeAgricole(String typeAgricole) {
        this.typeAgricole = typeAgricole;
        return this;
    }

    public void setTypeAgricole(String typeAgricole) {
        this.typeAgricole = typeAgricole;
    }

    public Double getSuperficie() {
        return superficie;
    }

    public Agriculteur superficie(Double superficie) {
        this.superficie = superficie;
        return this;
    }

    public void setSuperficie(Double superficie) {
        this.superficie = superficie;
    }

    public Double getSuperficieIrriguee() {
        return superficieIrriguee;
    }

    public Agriculteur superficieIrriguee(Double superficieIrriguee) {
        this.superficieIrriguee = superficieIrriguee;
        return this;
    }

    public void setSuperficieIrriguee(Double superficieIrriguee) {
        this.superficieIrriguee = superficieIrriguee;
    }

    public Integer getBio() {
        return bio;
    }

    public Agriculteur bio(Integer bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(Integer bio) {
        this.bio = bio;
    }

    public Integer getNbreEmployeePermanant() {
        return nbreEmployeePermanant;
    }

    public Agriculteur nbreEmployeePermanant(Integer nbreEmployeePermanant) {
        this.nbreEmployeePermanant = nbreEmployeePermanant;
        return this;
    }

    public void setNbreEmployeePermanant(Integer nbreEmployeePermanant) {
        this.nbreEmployeePermanant = nbreEmployeePermanant;
    }

    public String getSpecialiteProduction() {
        return specialiteProduction;
    }

    public Agriculteur specialiteProduction(String specialiteProduction) {
        this.specialiteProduction = specialiteProduction;
        return this;
    }

    public void setSpecialiteProduction(String specialiteProduction) {
        this.specialiteProduction = specialiteProduction;
    }

    public Profil getProfil() {
        return profil;
    }

    public Agriculteur profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Set<ActiviteAgricole> getActiviteAgricoles() {
        return activiteAgricoles;
    }

    public Agriculteur activiteAgricoles(Set<ActiviteAgricole> activiteAgricoles) {
        this.activiteAgricoles = activiteAgricoles;
        return this;
    }

    public Agriculteur addActiviteAgricoles(ActiviteAgricole activiteAgricole) {
        this.activiteAgricoles.add(activiteAgricole);
        activiteAgricole.setAgriculteur(this);
        return this;
    }

    public Agriculteur removeActiviteAgricoles(ActiviteAgricole activiteAgricole) {
        this.activiteAgricoles.remove(activiteAgricole);
        activiteAgricole.setAgriculteur(null);
        return this;
    }

    public void setActiviteAgricoles(Set<ActiviteAgricole> activiteAgricoles) {
        this.activiteAgricoles = activiteAgricoles;
    }

    public Set<SpecialiteAgricole> getSpecialitesAgricoles() {
        return specialitesAgricoles;
    }

    public Agriculteur specialitesAgricoles(Set<SpecialiteAgricole> specialiteAgricoles) {
        this.specialitesAgricoles = specialiteAgricoles;
        return this;
    }

    public Agriculteur addSpecialitesAgricole(SpecialiteAgricole specialiteAgricole) {
        this.specialitesAgricoles.add(specialiteAgricole);
        specialiteAgricole.setAgriculteur(this);
        return this;
    }

    public Agriculteur removeSpecialitesAgricole(SpecialiteAgricole specialiteAgricole) {
        this.specialitesAgricoles.remove(specialiteAgricole);
        specialiteAgricole.setAgriculteur(null);
        return this;
    }

    public void setSpecialitesAgricoles(Set<SpecialiteAgricole> specialiteAgricoles) {
        this.specialitesAgricoles = specialiteAgricoles;
    }

    public Set<CommandeDetails> getCommandeDetails() {
        return commandeDetails;
    }

    public Agriculteur commandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
        return this;
    }

    public Agriculteur addCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.add(commandeDetails);
        commandeDetails.setAgriculteur(this);
        return this;
    }

    public Agriculteur removeCommandeDetails(CommandeDetails commandeDetails) {
        this.commandeDetails.remove(commandeDetails);
        commandeDetails.setAgriculteur(null);
        return this;
    }

    public void setCommandeDetails(Set<CommandeDetails> commandeDetails) {
        this.commandeDetails = commandeDetails;
    }

    public Set<Commande> getCommandes() {
        return commandes;
    }

    public Agriculteur commandes(Set<Commande> commandes) {
        this.commandes = commandes;
        return this;
    }

    public Agriculteur addCommandes(Commande commande) {
        this.commandes.add(commande);
        commande.setAgriculteur(this);
        return this;
    }

    public Agriculteur removeCommandes(Commande commande) {
        this.commandes.remove(commande);
        commande.setAgriculteur(null);
        return this;
    }

    public void setCommandes(Set<Commande> commandes) {
        this.commandes = commandes;
    }

    public Set<Production> getProductions() {
        return productions;
    }

    public Agriculteur productions(Set<Production> productions) {
        this.productions = productions;
        return this;
    }

    public Agriculteur addProduction(Production production) {
        this.productions.add(production);
        production.setAgriculteur(this);
        return this;
    }

    public Agriculteur removeProduction(Production production) {
        this.productions.remove(production);
        production.setAgriculteur(null);
        return this;
    }

    public void setProductions(Set<Production> productions) {
        this.productions = productions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Agriculteur agriculteur = (Agriculteur) o;
        if (agriculteur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, agriculteur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Agriculteur{" +
            "id=" + id +
            ", typeSociete='" + typeSociete + "'" +
            ", raisonSociale='" + raisonSociale + "'" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", adresse='" + adresse + "'" +
            ", adresseLivraison='" + adresseLivraison + "'" +
            ", telephone='" + telephone + "'" +
            ", typeAgricole='" + typeAgricole + "'" +
            ", superficie='" + superficie + "'" +
            ", superficieIrriguee='" + superficieIrriguee + "'" +
            ", bio='" + bio + "'" +
            ", nbreEmployeePermanant='" + nbreEmployeePermanant + "'" +
            ", specialiteProduction='" + specialiteProduction + "'" +
            '}';
    }
}
