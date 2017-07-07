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
 * A TraderAGB.
 */
@Entity
@Table(name = "trader_agb")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "traderagb")
public class TraderAGB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "adresse")
    private String adresse;

    @OneToOne
    @JoinColumn(unique = true)
    private Profil profil;

    @ManyToOne
    private Desk desk;

    @OneToMany(mappedBy = "traderAGB")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Commande> tradedBies = new HashSet<>();

    @OneToMany(mappedBy = "traderAGB")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trade> trades = new HashSet<>();

    @OneToMany(mappedBy = "traderAGB")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Portefolio> portefolios = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public TraderAGB firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public TraderAGB lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAdresse() {
        return adresse;
    }

    public TraderAGB adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Profil getProfil() {
        return profil;
    }

    public TraderAGB profil(Profil profil) {
        this.profil = profil;
        return this;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Desk getDesk() {
        return desk;
    }

    public TraderAGB desk(Desk desk) {
        this.desk = desk;
        return this;
    }

    public void setDesk(Desk desk) {
        this.desk = desk;
    }

    public Set<Commande> getTradedBies() {
        return tradedBies;
    }

    public TraderAGB tradedBies(Set<Commande> commandes) {
        this.tradedBies = commandes;
        return this;
    }

    public TraderAGB addTradedBy(Commande commande) {
        this.tradedBies.add(commande);
        commande.setTraderAGB(this);
        return this;
    }

    public TraderAGB removeTradedBy(Commande commande) {
        this.tradedBies.remove(commande);
        commande.setTraderAGB(null);
        return this;
    }

    public void setTradedBies(Set<Commande> commandes) {
        this.tradedBies = commandes;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public TraderAGB trades(Set<Trade> trades) {
        this.trades = trades;
        return this;
    }

    public TraderAGB addTrade(Trade trade) {
        this.trades.add(trade);
        trade.setTraderAGB(this);
        return this;
    }

    public TraderAGB removeTrade(Trade trade) {
        this.trades.remove(trade);
        trade.setTraderAGB(null);
        return this;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    public Set<Portefolio> getPortefolios() {
        return portefolios;
    }

    public TraderAGB portefolios(Set<Portefolio> portefolios) {
        this.portefolios = portefolios;
        return this;
    }

    public TraderAGB addPortefolio(Portefolio portefolio) {
        this.portefolios.add(portefolio);
        portefolio.setTraderAGB(this);
        return this;
    }

    public TraderAGB removePortefolio(Portefolio portefolio) {
        this.portefolios.remove(portefolio);
        portefolio.setTraderAGB(null);
        return this;
    }

    public void setPortefolios(Set<Portefolio> portefolios) {
        this.portefolios = portefolios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraderAGB traderAGB = (TraderAGB) o;
        if (traderAGB.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, traderAGB.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TraderAGB{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", adresse='" + adresse + "'" +
            '}';
    }
}
