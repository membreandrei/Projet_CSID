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
 * A UserThirdpartyMembership.
 */
@Entity
@Table(name = "user_thirdparty_membership")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserThirdpartyMembership implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "specialite")
    private String specialite;

    @OneToMany(mappedBy = "userThirdpartyMembership")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserApp> userMemberShips = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("thirdpartyMemberShips")
    private Thirdparty thirdparty;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFonction() {
        return fonction;
    }

    public UserThirdpartyMembership fonction(String fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getSpecialite() {
        return specialite;
    }

    public UserThirdpartyMembership specialite(String specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Set<UserApp> getUserMemberShips() {
        return userMemberShips;
    }

    public UserThirdpartyMembership userMemberShips(Set<UserApp> userApps) {
        this.userMemberShips = userApps;
        return this;
    }

    public UserThirdpartyMembership addUserMemberShip(UserApp userApp) {
        this.userMemberShips.add(userApp);
        userApp.setUserThirdpartyMembership(this);
        return this;
    }

    public UserThirdpartyMembership removeUserMemberShip(UserApp userApp) {
        this.userMemberShips.remove(userApp);
        userApp.setUserThirdpartyMembership(null);
        return this;
    }

    public void setUserMemberShips(Set<UserApp> userApps) {
        this.userMemberShips = userApps;
    }

    public Thirdparty getThirdparty() {
        return thirdparty;
    }

    public UserThirdpartyMembership thirdparty(Thirdparty thirdparty) {
        this.thirdparty = thirdparty;
        return this;
    }

    public void setThirdparty(Thirdparty thirdparty) {
        this.thirdparty = thirdparty;
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
        UserThirdpartyMembership userThirdpartyMembership = (UserThirdpartyMembership) o;
        if (userThirdpartyMembership.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userThirdpartyMembership.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserThirdpartyMembership{" +
            "id=" + getId() +
            ", fonction='" + getFonction() + "'" +
            ", specialite='" + getSpecialite() + "'" +
            "}";
    }
}
