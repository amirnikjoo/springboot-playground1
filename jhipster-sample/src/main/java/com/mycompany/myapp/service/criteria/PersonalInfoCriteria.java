package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.AuthenticationType;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.PersonalInfo} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PersonalInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personal-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonalInfoCriteria implements Serializable, Criteria {

    /**
     * Class for filtering AuthenticationType
     */
    public static class AuthenticationTypeFilter extends Filter<AuthenticationType> {

        public AuthenticationTypeFilter() {}

        public AuthenticationTypeFilter(AuthenticationTypeFilter filter) {
            super(filter);
        }

        @Override
        public AuthenticationTypeFilter copy() {
            return new AuthenticationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter lastName;

    private StringFilter username;

    private StringFilter consumerPhoto;

    private StringFilter backgroundPhoto;

    private StringFilter countryCode;

    private AuthenticationTypeFilter authenticationType;

    private StringFilter email;

    private StringFilter phoneNumber;

    private StringFilter address;

    private StringFilter password;

    public PersonalInfoCriteria() {}

    public PersonalInfoCriteria(PersonalInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.consumerPhoto = other.consumerPhoto == null ? null : other.consumerPhoto.copy();
        this.backgroundPhoto = other.backgroundPhoto == null ? null : other.backgroundPhoto.copy();
        this.countryCode = other.countryCode == null ? null : other.countryCode.copy();
        this.authenticationType = other.authenticationType == null ? null : other.authenticationType.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.password = other.password == null ? null : other.password.copy();
    }

    @Override
    public PersonalInfoCriteria copy() {
        return new PersonalInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getConsumerPhoto() {
        return consumerPhoto;
    }

    public StringFilter consumerPhoto() {
        if (consumerPhoto == null) {
            consumerPhoto = new StringFilter();
        }
        return consumerPhoto;
    }

    public void setConsumerPhoto(StringFilter consumerPhoto) {
        this.consumerPhoto = consumerPhoto;
    }

    public StringFilter getBackgroundPhoto() {
        return backgroundPhoto;
    }

    public StringFilter backgroundPhoto() {
        if (backgroundPhoto == null) {
            backgroundPhoto = new StringFilter();
        }
        return backgroundPhoto;
    }

    public void setBackgroundPhoto(StringFilter backgroundPhoto) {
        this.backgroundPhoto = backgroundPhoto;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public StringFilter countryCode() {
        if (countryCode == null) {
            countryCode = new StringFilter();
        }
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public AuthenticationTypeFilter getAuthenticationType() {
        return authenticationType;
    }

    public AuthenticationTypeFilter authenticationType() {
        if (authenticationType == null) {
            authenticationType = new AuthenticationTypeFilter();
        }
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationTypeFilter authenticationType) {
        this.authenticationType = authenticationType;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonalInfoCriteria that = (PersonalInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(username, that.username) &&
            Objects.equals(consumerPhoto, that.consumerPhoto) &&
            Objects.equals(backgroundPhoto, that.backgroundPhoto) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(authenticationType, that.authenticationType) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(address, that.address) &&
            Objects.equals(password, that.password)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            lastName,
            username,
            consumerPhoto,
            backgroundPhoto,
            countryCode,
            authenticationType,
            email,
            phoneNumber,
            address,
            password
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonalInfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (consumerPhoto != null ? "consumerPhoto=" + consumerPhoto + ", " : "") +
            (backgroundPhoto != null ? "backgroundPhoto=" + backgroundPhoto + ", " : "") +
            (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
            (authenticationType != null ? "authenticationType=" + authenticationType + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            "}";
    }
}
