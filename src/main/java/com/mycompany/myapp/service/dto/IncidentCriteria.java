package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Incident entity. This class is used in IncidentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /incidents?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IncidentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter titre;

    private StringFilter statut;

    private StringFilter priorite;

    private StringFilter sujet;

    private StringFilter categorie;

    private StringFilter description;

    private StringFilter dateDebut;

    private StringFilter dateFin;

    private LongFilter userAppId;

    private LongFilter assigmentIncidentId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitre() {
        return titre;
    }

    public void setTitre(StringFilter titre) {
        this.titre = titre;
    }

    public StringFilter getStatut() {
        return statut;
    }

    public void setStatut(StringFilter statut) {
        this.statut = statut;
    }

    public StringFilter getPriorite() {
        return priorite;
    }

    public void setPriorite(StringFilter priorite) {
        this.priorite = priorite;
    }

    public StringFilter getSujet() {
        return sujet;
    }

    public void setSujet(StringFilter sujet) {
        this.sujet = sujet;
    }

    public StringFilter getCategorie() {
        return categorie;
    }

    public void setCategorie(StringFilter categorie) {
        this.categorie = categorie;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(StringFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public StringFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(StringFilter dateFin) {
        this.dateFin = dateFin;
    }

    public LongFilter getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(LongFilter userAppId) {
        this.userAppId = userAppId;
    }

    public LongFilter getAssigmentIncidentId() {
        return assigmentIncidentId;
    }

    public void setAssigmentIncidentId(LongFilter assigmentIncidentId) {
        this.assigmentIncidentId = assigmentIncidentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IncidentCriteria that = (IncidentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(titre, that.titre) &&
            Objects.equals(statut, that.statut) &&
            Objects.equals(priorite, that.priorite) &&
            Objects.equals(sujet, that.sujet) &&
            Objects.equals(categorie, that.categorie) &&
            Objects.equals(description, that.description) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(userAppId, that.userAppId) &&
            Objects.equals(assigmentIncidentId, that.assigmentIncidentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        titre,
        statut,
        priorite,
        sujet,
        categorie,
        description,
        dateDebut,
        dateFin,
        userAppId,
        assigmentIncidentId
        );
    }

    @Override
    public String toString() {
        return "IncidentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titre != null ? "titre=" + titre + ", " : "") +
                (statut != null ? "statut=" + statut + ", " : "") +
                (priorite != null ? "priorite=" + priorite + ", " : "") +
                (sujet != null ? "sujet=" + sujet + ", " : "") +
                (categorie != null ? "categorie=" + categorie + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (userAppId != null ? "userAppId=" + userAppId + ", " : "") +
                (assigmentIncidentId != null ? "assigmentIncidentId=" + assigmentIncidentId + ", " : "") +
            "}";
    }

}
