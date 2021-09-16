package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ConsumerType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Consumer} entity.
 */
public class ConsumerDTO implements Serializable {

    private Long id;

    private String ip;

    private String macAddress;

    private String deviceId;

    private ConsumerType userType;

    private PersonalInfoDTO personalInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ConsumerType getUserType() {
        return userType;
    }

    public void setUserType(ConsumerType userType) {
        this.userType = userType;
    }

    public PersonalInfoDTO getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfoDTO personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsumerDTO)) {
            return false;
        }

        ConsumerDTO consumerDTO = (ConsumerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consumerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumerDTO{" +
            "id=" + getId() +
            ", ip='" + getIp() + "'" +
            ", macAddress='" + getMacAddress() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", userType='" + getUserType() + "'" +
            ", personalInfo=" + getPersonalInfo() +
            "}";
    }
}
