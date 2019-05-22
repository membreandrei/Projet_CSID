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
 * Criteria class for the Thirdparty entity. This class is used in ThirdpartyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /thirdparties?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ThirdpartyCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter denomination;

    private IntegerFilter siret;

    private LongFilter thirdpartyMemberShipId;

    private LongFilter thirdpartyLicenceId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDenomination() {
        return denomination;
    }

    public void setDenomination(StringFilter denomination) {
        this.denomination = denomination;
    }

    public IntegerFilter getSiret() {
        return siret;
    }

    public void setSiret(IntegerFilter siret) {
        this.siret = siret;
    }

    public LongFilter getThirdpartyMemberShipId() {
        return thirdpartyMemberShipId;
    }

    public void setThirdpartyMemberShipId(LongFilter thirdpartyMemberShipId) {
        this.thirdpartyMemberShipId = thirdpartyMemberShipId;
    }

    public LongFilter getThirdpartyLicenceId() {
        return thirdpartyLicenceId;
    }

    public void setThirdpartyLicenceId(LongFilter thirdpartyLicenceId) {
        this.thirdpartyLicenceId = thirdpartyLicenceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ThirdpartyCriteria that = (ThirdpartyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(denomination, that.denomination) &&
            Objects.equals(siret, that.siret) &&
            Objects.equals(thirdpartyMemberShipId, that.thirdpartyMemberShipId) &&
            Objects.equals(thirdpartyLicenceId, that.thirdpartyLicenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        denomination,
        siret,
        thirdpartyMemberShipId,
        thirdpartyLicenceId
        );
    }

    @Override
    public String toString() {
        return "ThirdpartyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (denomination != null ? "denomination=" + denomination + ", " : "") +
                (siret != null ? "siret=" + siret + ", " : "") +
                (thirdpartyMemberShipId != null ? "thirdpartyMemberShipId=" + thirdpartyMemberShipId + ", " : "") +
                (thirdpartyLicenceId != null ? "thirdpartyLicenceId=" + thirdpartyLicenceId + ", " : "") +
            "}";
    }

}
