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
 * Criteria class for the UserThirdpartyMembership entity. This class is used in UserThirdpartyMembershipResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /user-thirdparty-memberships?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserThirdpartyMembershipCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fonction;

    private StringFilter specialite;

    private LongFilter userMemberShipId;

    private LongFilter thirdpartyId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFonction() {
        return fonction;
    }

    public void setFonction(StringFilter fonction) {
        this.fonction = fonction;
    }

    public StringFilter getSpecialite() {
        return specialite;
    }

    public void setSpecialite(StringFilter specialite) {
        this.specialite = specialite;
    }

    public LongFilter getUserMemberShipId() {
        return userMemberShipId;
    }

    public void setUserMemberShipId(LongFilter userMemberShipId) {
        this.userMemberShipId = userMemberShipId;
    }

    public LongFilter getThirdpartyId() {
        return thirdpartyId;
    }

    public void setThirdpartyId(LongFilter thirdpartyId) {
        this.thirdpartyId = thirdpartyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserThirdpartyMembershipCriteria that = (UserThirdpartyMembershipCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fonction, that.fonction) &&
            Objects.equals(specialite, that.specialite) &&
            Objects.equals(userMemberShipId, that.userMemberShipId) &&
            Objects.equals(thirdpartyId, that.thirdpartyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fonction,
        specialite,
        userMemberShipId,
        thirdpartyId
        );
    }

    @Override
    public String toString() {
        return "UserThirdpartyMembershipCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fonction != null ? "fonction=" + fonction + ", " : "") +
                (specialite != null ? "specialite=" + specialite + ", " : "") +
                (userMemberShipId != null ? "userMemberShipId=" + userMemberShipId + ", " : "") +
                (thirdpartyId != null ? "thirdpartyId=" + thirdpartyId + ", " : "") +
            "}";
    }

}
