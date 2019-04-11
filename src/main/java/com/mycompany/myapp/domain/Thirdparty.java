package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Thirdparty.
 */
@Entity
@Table(name = "thirdparty")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Thirdparty implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "denomination")
    private String denomination;

    @Column(name = "siret")
    private Integer siret;

    @OneToMany(mappedBy = "thirdparty")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserThirdpartyMembership> thirdpartyMemberShips = new HashSet<>();
    @OneToMany(mappedBy = "thirdparty")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Licence> thirdpartyLicences = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public Thirdparty denomination(String denomination) {
        this.denomination = denomination;
        return this;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Integer getSiret() {
        return siret;
    }

    public Thirdparty siret(Integer siret) {
        this.siret = siret;
        return this;
    }

    public void setSiret(Integer siret) {
        this.siret = siret;
    }

    public Set<UserThirdpartyMembership> getThirdpartyMemberShips() {
        return thirdpartyMemberShips;
    }

    public Thirdparty thirdpartyMemberShips(Set<UserThirdpartyMembership> userThirdpartyMemberships) {
        this.thirdpartyMemberShips = userThirdpartyMemberships;
        return this;
    }

    public Thirdparty addThirdpartyMemberShip(UserThirdpartyMembership userThirdpartyMembership) {
        this.thirdpartyMemberShips.add(userThirdpartyMembership);
        userThirdpartyMembership.setThirdparty(this);
        return this;
    }

    public Thirdparty removeThirdpartyMemberShip(UserThirdpartyMembership userThirdpartyMembership) {
        this.thirdpartyMemberShips.remove(userThirdpartyMembership);
        userThirdpartyMembership.setThirdparty(null);
        return this;
    }

    public void setThirdpartyMemberShips(Set<UserThirdpartyMembership> userThirdpartyMemberships) {
        this.thirdpartyMemberShips = userThirdpartyMemberships;
    }

    public Set<Licence> getThirdpartyLicences() {
        return thirdpartyLicences;
    }

    public Thirdparty thirdpartyLicences(Set<Licence> licences) {
        this.thirdpartyLicences = licences;
        return this;
    }

    public Thirdparty addThirdpartyLicence(Licence licence) {
        this.thirdpartyLicences.add(licence);
        licence.setThirdparty(this);
        return this;
    }

    public Thirdparty removeThirdpartyLicence(Licence licence) {
        this.thirdpartyLicences.remove(licence);
        licence.setThirdparty(null);
        return this;
    }

    public void setThirdpartyLicences(Set<Licence> licences) {
        this.thirdpartyLicences = licences;
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
        Thirdparty thirdparty = (Thirdparty) o;
        if (thirdparty.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), thirdparty.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Thirdparty{" +
            "id=" + getId() +
            ", denomination='" + getDenomination() + "'" +
            ", siret=" + getSiret() +
            "}";
    }
}
