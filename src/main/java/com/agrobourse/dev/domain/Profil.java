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
 * A Profil.
 */
@Entity
@Table(name = "profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profil")
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "image")
    private String image;

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private Employee employee;

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private Employer employer;

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private Agriculteur agriculteur;

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private TraderAGB traderAGB;

    @OneToOne(mappedBy = "profil")
    @JsonIgnore
    private TraderCA traderCA;

    @OneToMany(mappedBy = "lastModifiedBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annonceModifs = new HashSet<>();

    @OneToMany(mappedBy = "annonceur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annonces = new HashSet<>();

    @OneToMany(mappedBy = "profil")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnnonceHistorique> annonceHistoriques = new HashSet<>();

    @OneToMany(mappedBy = "profil")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<View> views = new HashSet<>();

    @OneToMany(mappedBy = "profil")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnnonceChangement> annonceChangements = new HashSet<>();

    @OneToMany(mappedBy = "commandBy")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommande> eCommandes = new HashSet<>();

    @OneToMany(mappedBy = "userCommandHistorique")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ECommandeHistorique> eCommandeHistoriques = new HashSet<>();

    @OneToMany(mappedBy = "userTransaction")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "validePar")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactionValides = new HashSet<>();

    @OneToMany(mappedBy = "validePaiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactionValidePaiments = new HashSet<>();

    @OneToMany(mappedBy = "refuserPar")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactionRefuseds = new HashSet<>();

    @OneToMany(mappedBy = "refusePaiment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Transaction> transactionRefusedPaiments = new HashSet<>();

    @ManyToMany(mappedBy = "likedBies")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Annonce> annoncesFavorises = new HashSet<>();
    
    @OneToOne(optional = false)
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Profil firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Profil lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Profil email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public Profil login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImage() {
        return image;
    }

    public Profil image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Profil employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Profil employer(Employer employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    public Profil agriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
        return this;
    }

    public void setAgriculteur(Agriculteur agriculteur) {
        this.agriculteur = agriculteur;
    }

    public TraderAGB getTraderAGB() {
        return traderAGB;
    }

    public Profil traderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
        return this;
    }

    public void setTraderAGB(TraderAGB traderAGB) {
        this.traderAGB = traderAGB;
    }

    public TraderCA getTraderCA() {
        return traderCA;
    }

    public Profil traderCA(TraderCA traderCA) {
        this.traderCA = traderCA;
        return this;
    }

    public void setTraderCA(TraderCA traderCA) {
        this.traderCA = traderCA;
    }

    public Set<Annonce> getAnnonceModifs() {
        return annonceModifs;
    }

    public Profil annonceModifs(Set<Annonce> annonces) {
        this.annonceModifs = annonces;
        return this;
    }

    public Profil addAnnonceModif(Annonce annonce) {
        this.annonceModifs.add(annonce);
        annonce.setLastModifiedBy(this);
        return this;
    }

    public Profil removeAnnonceModif(Annonce annonce) {
        this.annonceModifs.remove(annonce);
        annonce.setLastModifiedBy(null);
        return this;
    }

    public void setAnnonceModifs(Set<Annonce> annonces) {
        this.annonceModifs = annonces;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public Profil annonces(Set<Annonce> annonces) {
        this.annonces = annonces;
        return this;
    }

    public Profil addAnnonce(Annonce annonce) {
        this.annonces.add(annonce);
        annonce.setAnnonceur(this);
        return this;
    }

    public Profil removeAnnonce(Annonce annonce) {
        this.annonces.remove(annonce);
        annonce.setAnnonceur(null);
        return this;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Set<AnnonceHistorique> getAnnonceHistoriques() {
        return annonceHistoriques;
    }

    public Profil annonceHistoriques(Set<AnnonceHistorique> annonceHistoriques) {
        this.annonceHistoriques = annonceHistoriques;
        return this;
    }

    public Profil addAnnonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistoriques.add(annonceHistorique);
        annonceHistorique.setProfil(this);
        return this;
    }

    public Profil removeAnnonceHistorique(AnnonceHistorique annonceHistorique) {
        this.annonceHistoriques.remove(annonceHistorique);
        annonceHistorique.setProfil(null);
        return this;
    }

    public void setAnnonceHistoriques(Set<AnnonceHistorique> annonceHistoriques) {
        this.annonceHistoriques = annonceHistoriques;
    }

    public Set<View> getViews() {
        return views;
    }

    public Profil views(Set<View> views) {
        this.views = views;
        return this;
    }

    public Profil addView(View view) {
        this.views.add(view);
        view.setProfil(this);
        return this;
    }

    public Profil removeView(View view) {
        this.views.remove(view);
        view.setProfil(null);
        return this;
    }

    public void setViews(Set<View> views) {
        this.views = views;
    }

    public Set<AnnonceChangement> getAnnonceChangements() {
        return annonceChangements;
    }

    public Profil annonceChangements(Set<AnnonceChangement> annonceChangements) {
        this.annonceChangements = annonceChangements;
        return this;
    }

    public Profil addAnnonceChangement(AnnonceChangement annonceChangement) {
        this.annonceChangements.add(annonceChangement);
        annonceChangement.setProfil(this);
        return this;
    }

    public Profil removeAnnonceChangement(AnnonceChangement annonceChangement) {
        this.annonceChangements.remove(annonceChangement);
        annonceChangement.setProfil(null);
        return this;
    }

    public void setAnnonceChangements(Set<AnnonceChangement> annonceChangements) {
        this.annonceChangements = annonceChangements;
    }

    public Set<ECommande> getECommandes() {
        return eCommandes;
    }

    public Profil eCommandes(Set<ECommande> eCommandes) {
        this.eCommandes = eCommandes;
        return this;
    }

    public Profil addECommande(ECommande eCommande) {
        this.eCommandes.add(eCommande);
        eCommande.setCommandBy(this);
        return this;
    }

    public Profil removeECommande(ECommande eCommande) {
        this.eCommandes.remove(eCommande);
        eCommande.setCommandBy(null);
        return this;
    }

    public void setECommandes(Set<ECommande> eCommandes) {
        this.eCommandes = eCommandes;
    }

    public Set<ECommandeHistorique> getECommandeHistoriques() {
        return eCommandeHistoriques;
    }

    public Profil eCommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
        return this;
    }

    public Profil addECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.add(eCommandeHistorique);
        eCommandeHistorique.setUserCommandHistorique(this);
        return this;
    }

    public Profil removeECommandeHistorique(ECommandeHistorique eCommandeHistorique) {
        this.eCommandeHistoriques.remove(eCommandeHistorique);
        eCommandeHistorique.setUserCommandHistorique(null);
        return this;
    }

    public void setECommandeHistoriques(Set<ECommandeHistorique> eCommandeHistoriques) {
        this.eCommandeHistoriques = eCommandeHistoriques;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Profil transactions(Set<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Profil addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setUserTransaction(this);
        return this;
    }

    public Profil removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setUserTransaction(null);
        return this;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Transaction> getTransactionValides() {
        return transactionValides;
    }

    public Profil transactionValides(Set<Transaction> transactions) {
        this.transactionValides = transactions;
        return this;
    }

    public Profil addTransactionValide(Transaction transaction) {
        this.transactionValides.add(transaction);
        transaction.setValidePar(this);
        return this;
    }

    public Profil removeTransactionValide(Transaction transaction) {
        this.transactionValides.remove(transaction);
        transaction.setValidePar(null);
        return this;
    }

    public void setTransactionValides(Set<Transaction> transactions) {
        this.transactionValides = transactions;
    }

    public Set<Transaction> getTransactionValidePaiments() {
        return transactionValidePaiments;
    }

    public Profil transactionValidePaiments(Set<Transaction> transactions) {
        this.transactionValidePaiments = transactions;
        return this;
    }

    public Profil addTransactionValidePaiment(Transaction transaction) {
        this.transactionValidePaiments.add(transaction);
        transaction.setValidePaiment(this);
        return this;
    }

    public Profil removeTransactionValidePaiment(Transaction transaction) {
        this.transactionValidePaiments.remove(transaction);
        transaction.setValidePaiment(null);
        return this;
    }

    public void setTransactionValidePaiments(Set<Transaction> transactions) {
        this.transactionValidePaiments = transactions;
    }

    public Set<Transaction> getTransactionRefuseds() {
        return transactionRefuseds;
    }

    public Profil transactionRefuseds(Set<Transaction> transactions) {
        this.transactionRefuseds = transactions;
        return this;
    }

    public Profil addTransactionRefused(Transaction transaction) {
        this.transactionRefuseds.add(transaction);
        transaction.setRefuserPar(this);
        return this;
    }

    public Profil removeTransactionRefused(Transaction transaction) {
        this.transactionRefuseds.remove(transaction);
        transaction.setRefuserPar(null);
        return this;
    }

    public void setTransactionRefuseds(Set<Transaction> transactions) {
        this.transactionRefuseds = transactions;
    }

    public Set<Transaction> getTransactionRefusedPaiments() {
        return transactionRefusedPaiments;
    }

    public Profil transactionRefusedPaiments(Set<Transaction> transactions) {
        this.transactionRefusedPaiments = transactions;
        return this;
    }

    public Profil addTransactionRefusedPaiment(Transaction transaction) {
        this.transactionRefusedPaiments.add(transaction);
        transaction.setRefusePaiment(this);
        return this;
    }

    public Profil removeTransactionRefusedPaiment(Transaction transaction) {
        this.transactionRefusedPaiments.remove(transaction);
        transaction.setRefusePaiment(null);
        return this;
    }

    public void setTransactionRefusedPaiments(Set<Transaction> transactions) {
        this.transactionRefusedPaiments = transactions;
    }

    public Set<Annonce> getAnnoncesFavorises() {
        return annoncesFavorises;
    }

    public Profil annoncesFavorises(Set<Annonce> annonces) {
        this.annoncesFavorises = annonces;
        return this;
    }

    public Profil addAnnoncesFavoris(Annonce annonce) {
        this.annoncesFavorises.add(annonce);
        annonce.getLikedBies().add(this);
        return this;
    }

    public Profil removeAnnoncesFavoris(Annonce annonce) {
        this.annoncesFavorises.remove(annonce);
        annonce.getLikedBies().remove(this);
        return this;
    }

    public void setAnnoncesFavorises(Set<Annonce> annonces) {
        this.annoncesFavorises = annonces;
    }

     public User getUser() {
        return user;
    }

    public Profil user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profil profil = (Profil) o;
        if (profil.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profil.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profil{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", email='" + email + "'" +
            ", login='" + login + "'" +
            ", image='" + image + "'" +
            '}';
    }
}
