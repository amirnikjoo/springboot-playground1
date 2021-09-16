package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.AuthenticationType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PersonalInfo} entity.
 */
public class PersonalInfoDTO implements Serializable {

    private Long id;

    private String name;

    private String lastName;

    private String username;

    private String consumerPhoto;

    private String backgroundPhoto;

    private String countryCode;

    private AuthenticationType authenticationType;

    private String email;

    private String phoneNumber;

    private String address;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConsumerPhoto() {
        return consumerPhoto;
    }

    public void setConsumerPhoto(String consumerPhoto) {
        this.consumerPhoto = consumerPhoto;
    }

    public String getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(String backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonalInfoDTO)) {
            return false;
        }

        PersonalInfoDTO personalInfoDTO = (PersonalInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personalInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalInfoDTO{" +
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
