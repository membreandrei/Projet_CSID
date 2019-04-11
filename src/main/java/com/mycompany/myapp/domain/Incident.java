package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "statut")
    private String statut;

    @Column(name = "priorite")
    private String priorite;

    @Column(name = "sujet")
    private String sujet;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "description")
    private String description;

    @Column(name = "date_debut")
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @ManyToOne
    @JsonIgnoreProperties("incidentClients")
    private UserApp userApp;

    @OneToMany(mappedBy = "incident")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserIncidentAssigment> assigmentIncidents = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public Incident titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getStatut() {
        return statut;
    }

    public Incident statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getPriorite() {
        return priorite;
    }

    public Incident priorite(String priorite) {
        this.priorite = priorite;
        return this;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getSujet() {
        return sujet;
    }

    public Incident sujet(String sujet) {
        this.sujet = sujet;
        return this;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getCategorie() {
        return categorie;
    }

    public Incident categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public Incident description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public Incident dateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public Incident dateFin(String dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Incident userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<UserIncidentAssigment> getAssigmentIncidents() {
        return assigmentIncidents;
    }

    public Incident assigmentIncidents(Set<UserIncidentAssigment> userIncidentAssigments) {
        this.assigmentIncidents = userIncidentAssigments;
        return this;
    }

    public Incident addAssigmentIncident(UserIncidentAssigment userIncidentAssigment) {
        this.assigmentIncidents.add(userIncidentAssigment);
        userIncidentAssigment.setIncident(this);
        return this;
    }

    public Incident removeAssigmentIncident(UserIncidentAssigment userIncidentAssigment) {
        this.assigmentIncidents.remove(userIncidentAssigment);
        userIncidentAssigment.setIncident(null);
        return this;
    }

    public void setAssigmentIncidents(Set<UserIncidentAssigment> userIncidentAssigments) {
        this.assigmentIncidents = userIncidentAssigments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Incident incident = (Incident) o;
        if (incident.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incident.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", statut='" + getStatut() + "'" +
            ", priorite='" + getPriorite() + "'" +
            ", sujet='" + getSujet() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
