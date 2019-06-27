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
 * Criteria class for the UserIncidentAssigment entity. This class is used in UserIncidentAssigmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-incident-assigments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserIncidentAssigmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dateDebut;

    private StringFilter dateFin;

    private StringFilter commentaire;

    private StringFilter status;

    private LongFilter userAppId;

    private LongFilter incidentId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(StringFilter commentaire) {
        this.commentaire = commentaire;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(LongFilter userAppId) {
        this.userAppId = userAppId;
    }

    public LongFilter getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(LongFilter incidentId) {
        this.incidentId = incidentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserIncidentAssigmentCriteria that = (UserIncidentAssigmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(commentaire, that.commentaire) &&
            Objects.equals(status, that.status) &&
            Objects.equals(userAppId, that.userAppId) &&
            Objects.equals(incidentId, that.incidentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateDebut,
        dateFin,
        commentaire,
        status,
        userAppId,
        incidentId
        );
    }

    @Override
    public String toString() {
        return "UserIncidentAssigmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (commentaire != null ? "commentaire=" + commentaire + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (userAppId != null ? "userAppId=" + userAppId + ", " : "") +
                (incidentId != null ? "incidentId=" + incidentId + ", " : "") +
            "}";
    }

}
