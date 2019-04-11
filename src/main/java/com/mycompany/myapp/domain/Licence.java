package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Licence.
 */
@Entity
@Table(name = "licence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Licence implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "prix")
    private Integer prix;

    @ManyToOne
    @JsonIgnoreProperties("thirdpartyLicences")
    private Thirdparty thirdparty;

    @OneToOne
    @JoinColumn(unique = true)
    private UserApp userLicence;

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

    public Licence titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getPrix() {
        return prix;
    }

    public Licence prix(Integer prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Thirdparty getThirdparty() {
        return thirdparty;
    }

    public Licence thirdparty(Thirdparty thirdparty) {
        this.thirdparty = thirdparty;
        return this;
    }

    public void setThirdparty(Thirdparty thirdparty) {
        this.thirdparty = thirdparty;
    }

    public UserApp getUserLicence() {
        return userLicence;
    }

    public Licence userLicence(UserApp userApp) {
        this.userLicence = userApp;
        return this;
    }

    public void setUserLicence(UserApp userApp) {
        this.userLicence = userApp;
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
        Licence licence = (Licence) o;
        if (licence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), licence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Licence{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
