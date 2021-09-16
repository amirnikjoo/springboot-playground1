package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.ConsumerType;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Consumer.
 */
@Entity
@Table(name = "consumer")
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "device_id")
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private ConsumerType userType;

    @OneToOne
    @JoinColumn(unique = true)
    private PersonalInfo personalInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consumer id(Long id) {
        this.id = id;
        return this;
    }

    public String getIp() {
        return this.ip;
    }

    public Consumer ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public Consumer macAddress(String macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public Consumer deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ConsumerType getUserType() {
        return this.userType;
    }

    public Consumer userType(ConsumerType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(ConsumerType userType) {
        this.userType = userType;
    }

    public PersonalInfo getPersonalInfo() {
        return this.personalInfo;
    }

    public Consumer personalInfo(PersonalInfo personalInfo) {
        this.setPersonalInfo(personalInfo);
        return this;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consumer)) {
            return false;
        }
        return id != null && id.equals(((Consumer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consumer{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", macAddress='" + getMacAddress() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", userType='" + getUserType() + "'" +
            "}";
    }
}
