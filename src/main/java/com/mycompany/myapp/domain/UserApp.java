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
 * A UserApp.
 */
@Entity
@Table(name = "user_app")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserApp implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "userApp")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UserIncidentAssigment> assigmentUsers = new HashSet<>();
    @OneToMany(mappedBy = "userApp")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Incident> incidentClients = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("userMemberShips")
    private UserThirdpartyMembership userThirdpartyMembership;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<UserIncidentAssigment> getAssigmentUsers() {
        return assigmentUsers;
    }

    public UserApp assigmentUsers(Set<UserIncidentAssigment> userIncidentAssigments) {
        this.assigmentUsers = userIncidentAssigments;
        return this;
    }

    public UserApp addAssigmentUser(UserIncidentAssigment userIncidentAssigment) {
        this.assigmentUsers.add(userIncidentAssigment);
        userIncidentAssigment.setUserApp(this);
        return this;
    }

    public UserApp removeAssigmentUser(UserIncidentAssigment userIncidentAssigment) {
        this.assigmentUsers.remove(userIncidentAssigment);
        userIncidentAssigment.setUserApp(null);
        return this;
    }

    public void setAssigmentUsers(Set<UserIncidentAssigment> userIncidentAssigments) {
        this.assigmentUsers = userIncidentAssigments;
    }

    public Set<Incident> getIncidentClients() {
        return incidentClients;
    }

    public UserApp incidentClients(Set<Incident> incidents) {
        this.incidentClients = incidents;
        return this;
    }

    public UserApp addIncidentClient(Incident incident) {
        this.incidentClients.add(incident);
        incident.setUserApp(this);
        return this;
    }

    public UserApp removeIncidentClient(Incident incident) {
        this.incidentClients.remove(incident);
        incident.setUserApp(null);
        return this;
    }

    public void setIncidentClients(Set<Incident> incidents) {
        this.incidentClients = incidents;
    }

    public UserThirdpartyMembership getUserThirdpartyMembership() {
        return userThirdpartyMembership;
    }

    public UserApp userThirdpartyMembership(UserThirdpartyMembership userThirdpartyMembership) {
        this.userThirdpartyMembership = userThirdpartyMembership;
        return this;
    }

    public void setUserThirdpartyMembership(UserThirdpartyMembership userThirdpartyMembership) {
        this.userThirdpartyMembership = userThirdpartyMembership;
    }

    public User getUser() {
        return user;
    }

    public UserApp user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserApp userApp = (UserApp) o;
        if (userApp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userApp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserApp{" +
            "id=" + getId() +
            "}";
    }
}
