package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserIncidentAssigment.
 */
@Entity
@Table(name = "user_incident_assigment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserIncidentAssigment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private String dateDebut;

    @Column(name = "date_fin")
    private String dateFin;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties("assigmentUsers")
    private UserApp userApp;

    @ManyToOne
    @JsonIgnoreProperties("assigmentIncidents")
    private Incident incident;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public UserIncidentAssigment dateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public UserIncidentAssigment dateFin(String dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public UserIncidentAssigment commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getStatus() {
        return status;
    }

    public UserIncidentAssigment status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public UserIncidentAssigment userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Incident getIncident() {
        return incident;
    }

    public UserIncidentAssigment incident(Incident incident) {
        this.incident = incident;
        return this;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
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
        UserIncidentAssigment userIncidentAssigment = (UserIncidentAssigment) o;
        if (userIncidentAssigment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userIncidentAssigment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserIncidentAssigment{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
