package com.agrobourse.dev.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A GPO.
 */
@Entity
@Table(name = "gpo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "gpo")
public class GPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "societepostale")
    private String societepostale;

    @Column(name = "societelegale")
    private String societelegale;

    @Column(name = "qualite")
    private String qualite;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "addadresse_1")
    private String addadresse1;

    @Column(name = "addadresse_2")
    private String addadresse2;

    @Column(name = "boitepostale")
    private String boitepostale;

    @Column(name = "addcodepostal")
    private Integer addcodepostal;

    @Column(name = "addville")
    private String addville;

    @Column(name = "telephoneunique")
    private String telephoneunique;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "telephone_num_international")
    private String telephoneNumInternational;

    @Column(name = "messagerie")
    private String messagerie;

    @Column(name = "messagerieunique")
    private String messagerieunique;

    @Column(name = "region")
    private String region;

    @Column(name = "departement")
    private String departement;

    @Column(name = "numdepartement")
    private Integer numdepartement;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "code_naf_import")
    private String codeNafImport;

    @Column(name = "sivet")
    private String sivet;

    @Column(name = "web")
    private String web;

    @Column(name = "populationtotale")
    private Integer populationtotale;

    @Column(name = "reseau")
    private String reseau;

    @Column(name = "enseigne")
    private String enseigne;

    @Column(name = "statut_etablissement")
    private String statutEtablissement;

    @Column(name = "statut_judiciaire")
    private String statutJudiciaire;

    @Column(name = "ca")
    private String ca;

    @Column(name = "capital_social")
    private String capitalSocial;

    @Column(name = "date_creation_entreprise")
    private LocalDate dateCreationEntreprise;

    @Column(name = "effectifs")
    private String effectifs;

    @Column(name = "fax")
    private String fax;

    @Column(name = "faxfaxinginternational")
    private String faxfaxinginternational;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocietepostale() {
        return societepostale;
    }

    public GPO societepostale(String societepostale) {
        this.societepostale = societepostale;
        return this;
    }

    public void setSocietepostale(String societepostale) {
        this.societepostale = societepostale;
    }

    public String getSocietelegale() {
        return societelegale;
    }

    public GPO societelegale(String societelegale) {
        this.societelegale = societelegale;
        return this;
    }

    public void setSocietelegale(String societelegale) {
        this.societelegale = societelegale;
    }

    public String getQualite() {
        return qualite;
    }

    public GPO qualite(String qualite) {
        this.qualite = qualite;
        return this;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getPrenom() {
        return prenom;
    }

    public GPO prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public GPO nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddadresse1() {
        return addadresse1;
    }

    public GPO addadresse1(String addadresse1) {
        this.addadresse1 = addadresse1;
        return this;
    }

    public void setAddadresse1(String addadresse1) {
        this.addadresse1 = addadresse1;
    }

    public String getAddadresse2() {
        return addadresse2;
    }

    public GPO addadresse2(String addadresse2) {
        this.addadresse2 = addadresse2;
        return this;
    }

    public void setAddadresse2(String addadresse2) {
        this.addadresse2 = addadresse2;
    }

    public String getBoitepostale() {
        return boitepostale;
    }

    public GPO boitepostale(String boitepostale) {
        this.boitepostale = boitepostale;
        return this;
    }

    public void setBoitepostale(String boitepostale) {
        this.boitepostale = boitepostale;
    }

    public Integer getAddcodepostal() {
        return addcodepostal;
    }

    public GPO addcodepostal(Integer addcodepostal) {
        this.addcodepostal = addcodepostal;
        return this;
    }

    public void setAddcodepostal(Integer addcodepostal) {
        this.addcodepostal = addcodepostal;
    }

    public String getAddville() {
        return addville;
    }

    public GPO addville(String addville) {
        this.addville = addville;
        return this;
    }

    public void setAddville(String addville) {
        this.addville = addville;
    }

    public String getTelephoneunique() {
        return telephoneunique;
    }

    public GPO telephoneunique(String telephoneunique) {
        this.telephoneunique = telephoneunique;
        return this;
    }

    public void setTelephoneunique(String telephoneunique) {
        this.telephoneunique = telephoneunique;
    }

    public String getTelephone() {
        return telephone;
    }

    public GPO telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephoneNumInternational() {
        return telephoneNumInternational;
    }

    public GPO telephoneNumInternational(String telephoneNumInternational) {
        this.telephoneNumInternational = telephoneNumInternational;
        return this;
    }

    public void setTelephoneNumInternational(String telephoneNumInternational) {
        this.telephoneNumInternational = telephoneNumInternational;
    }

    public String getMessagerie() {
        return messagerie;
    }

    public GPO messagerie(String messagerie) {
        this.messagerie = messagerie;
        return this;
    }

    public void setMessagerie(String messagerie) {
        this.messagerie = messagerie;
    }

    public String getMessagerieunique() {
        return messagerieunique;
    }

    public GPO messagerieunique(String messagerieunique) {
        this.messagerieunique = messagerieunique;
        return this;
    }

    public void setMessagerieunique(String messagerieunique) {
        this.messagerieunique = messagerieunique;
    }

    public String getRegion() {
        return region;
    }

    public GPO region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public GPO departement(String departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public Integer getNumdepartement() {
        return numdepartement;
    }

    public GPO numdepartement(Integer numdepartement) {
        this.numdepartement = numdepartement;
        return this;
    }

    public void setNumdepartement(Integer numdepartement) {
        this.numdepartement = numdepartement;
    }

    public String getCategorie() {
        return categorie;
    }

    public GPO categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCodeNafImport() {
        return codeNafImport;
    }

    public GPO codeNafImport(String codeNafImport) {
        this.codeNafImport = codeNafImport;
        return this;
    }

    public void setCodeNafImport(String codeNafImport) {
        this.codeNafImport = codeNafImport;
    }

    public String getSivet() {
        return sivet;
    }

    public GPO sivet(String sivet) {
        this.sivet = sivet;
        return this;
    }

    public void setSivet(String sivet) {
        this.sivet = sivet;
    }

    public String getWeb() {
        return web;
    }

    public GPO web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Integer getPopulationtotale() {
        return populationtotale;
    }

    public GPO populationtotale(Integer populationtotale) {
        this.populationtotale = populationtotale;
        return this;
    }

    public void setPopulationtotale(Integer populationtotale) {
        this.populationtotale = populationtotale;
    }

    public String getReseau() {
        return reseau;
    }

    public GPO reseau(String reseau) {
        this.reseau = reseau;
        return this;
    }

    public void setReseau(String reseau) {
        this.reseau = reseau;
    }

    public String getEnseigne() {
        return enseigne;
    }

    public GPO enseigne(String enseigne) {
        this.enseigne = enseigne;
        return this;
    }

    public void setEnseigne(String enseigne) {
        this.enseigne = enseigne;
    }

    public String getStatutEtablissement() {
        return statutEtablissement;
    }

    public GPO statutEtablissement(String statutEtablissement) {
        this.statutEtablissement = statutEtablissement;
        return this;
    }

    public void setStatutEtablissement(String statutEtablissement) {
        this.statutEtablissement = statutEtablissement;
    }

    public String getStatutJudiciaire() {
        return statutJudiciaire;
    }

    public GPO statutJudiciaire(String statutJudiciaire) {
        this.statutJudiciaire = statutJudiciaire;
        return this;
    }

    public void setStatutJudiciaire(String statutJudiciaire) {
        this.statutJudiciaire = statutJudiciaire;
    }

    public String getCa() {
        return ca;
    }

    public GPO ca(String ca) {
        this.ca = ca;
        return this;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public GPO capitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
        return this;
    }

    public void setCapitalSocial(String capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public LocalDate getDateCreationEntreprise() {
        return dateCreationEntreprise;
    }

    public GPO dateCreationEntreprise(LocalDate dateCreationEntreprise) {
        this.dateCreationEntreprise = dateCreationEntreprise;
        return this;
    }

    public void setDateCreationEntreprise(LocalDate dateCreationEntreprise) {
        this.dateCreationEntreprise = dateCreationEntreprise;
    }

    public String getEffectifs() {
        return effectifs;
    }

    public GPO effectifs(String effectifs) {
        this.effectifs = effectifs;
        return this;
    }

    public void setEffectifs(String effectifs) {
        this.effectifs = effectifs;
    }

    public String getFax() {
        return fax;
    }

    public GPO fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFaxfaxinginternational() {
        return faxfaxinginternational;
    }

    public GPO faxfaxinginternational(String faxfaxinginternational) {
        this.faxfaxinginternational = faxfaxinginternational;
        return this;
    }

    public void setFaxfaxinginternational(String faxfaxinginternational) {
        this.faxfaxinginternational = faxfaxinginternational;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GPO gPO = (GPO) o;
        if (gPO.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, gPO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GPO{" +
            "id=" + id +
            ", societepostale='" + societepostale + "'" +
            ", societelegale='" + societelegale + "'" +
            ", qualite='" + qualite + "'" +
            ", prenom='" + prenom + "'" +
            ", nom='" + nom + "'" +
            ", addadresse1='" + addadresse1 + "'" +
            ", addadresse2='" + addadresse2 + "'" +
            ", boitepostale='" + boitepostale + "'" +
            ", addcodepostal='" + addcodepostal + "'" +
            ", addville='" + addville + "'" +
            ", telephoneunique='" + telephoneunique + "'" +
            ", telephone='" + telephone + "'" +
            ", telephoneNumInternational='" + telephoneNumInternational + "'" +
            ", messagerie='" + messagerie + "'" +
            ", messagerieunique='" + messagerieunique + "'" +
            ", region='" + region + "'" +
            ", departement='" + departement + "'" +
            ", numdepartement='" + numdepartement + "'" +
            ", categorie='" + categorie + "'" +
            ", codeNafImport='" + codeNafImport + "'" +
            ", sivet='" + sivet + "'" +
            ", web='" + web + "'" +
            ", populationtotale='" + populationtotale + "'" +
            ", reseau='" + reseau + "'" +
            ", enseigne='" + enseigne + "'" +
            ", statutEtablissement='" + statutEtablissement + "'" +
            ", statutJudiciaire='" + statutJudiciaire + "'" +
            ", ca='" + ca + "'" +
            ", capitalSocial='" + capitalSocial + "'" +
            ", dateCreationEntreprise='" + dateCreationEntreprise + "'" +
            ", effectifs='" + effectifs + "'" +
            ", fax='" + fax + "'" +
            ", faxfaxinginternational='" + faxfaxinginternational + "'" +
            '}';
    }
}
