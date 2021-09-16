package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.AuthenticationType;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A PersonalInfo.
 */
@Entity
@Table(name = "personal_info")
public class PersonalInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "consumer_photo")
    private String consumerPhoto;

    @Column(name = "background_photo")
    private String backgroundPhoto;

    @Column(name = "country_code")
    private String countryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type")
    private AuthenticationType authenticationType;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonalInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public PersonalInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public PersonalInfo lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public PersonalInfo username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConsumerPhoto() {
        return this.consumerPhoto;
    }

    public PersonalInfo consumerPhoto(String consumerPhoto) {
        this.consumerPhoto = consumerPhoto;
        return this;
    }

    public void setConsumerPhoto(String consumerPhoto) {
        this.consumerPhoto = consumerPhoto;
    }

    public String getBackgroundPhoto() {
        return this.backgroundPhoto;
    }

    public PersonalInfo backgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
        return this;
    }

    public void setBackgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public PersonalInfo countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public AuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }

    public PersonalInfo authenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getEmail() {
        return this.email;
    }

    public PersonalInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public PersonalInfo phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public PersonalInfo address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return this.password;
    }

    public PersonalInfo password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalInfo)) {
            return false;
        }
        return id != null && id.equals(((PersonalInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", username='" + getUsername() + "'" +
            ", consumerPhoto='" + getConsumerPhoto() + "'" +
            ", backgroundPhoto='" + getBackgroundPhoto() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", authenticationType='" + getAuthenticationType() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
