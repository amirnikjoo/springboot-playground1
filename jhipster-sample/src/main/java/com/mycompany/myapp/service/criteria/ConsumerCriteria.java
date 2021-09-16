package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.ConsumerType;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.Consumer} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ConsumerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /consumers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConsumerCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ConsumerType
     */
    public static class ConsumerTypeFilter extends Filter<ConsumerType> {

        public ConsumerTypeFilter() {}

        public ConsumerTypeFilter(ConsumerTypeFilter filter) {
            super(filter);
        }

        @Override
        public ConsumerTypeFilter copy() {
            return new ConsumerTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter ip;

    private StringFilter macAddress;

    private StringFilter deviceId;

    private ConsumerTypeFilter userType;

    private LongFilter personalInfoId;

    public ConsumerCriteria() {}

    public ConsumerCriteria(ConsumerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.ip = other.ip == null ? null : other.ip.copy();
        this.macAddress = other.macAddress == null ? null : other.macAddress.copy();
        this.deviceId = other.deviceId == null ? null : other.deviceId.copy();
        this.userType = other.userType == null ? null : other.userType.copy();
        this.personalInfoId = other.personalInfoId == null ? null : other.personalInfoId.copy();
    }

    @Override
    public ConsumerCriteria copy() {
        return new ConsumerCriteria(this);
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

    public StringFilter getIp() {
        return ip;
    }

    public StringFilter ip() {
        if (ip == null) {
            ip = new StringFilter();
        }
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public StringFilter getMacAddress() {
        return macAddress;
    }

    public StringFilter macAddress() {
        if (macAddress == null) {
            macAddress = new StringFilter();
        }
        return macAddress;
    }

    public void setMacAddress(StringFilter macAddress) {
        this.macAddress = macAddress;
    }

    public StringFilter getDeviceId() {
        return deviceId;
    }

    public StringFilter deviceId() {
        if (deviceId == null) {
            deviceId = new StringFilter();
        }
        return deviceId;
    }

    public void setDeviceId(StringFilter deviceId) {
        this.deviceId = deviceId;
    }

    public ConsumerTypeFilter getUserType() {
        return userType;
    }

    public ConsumerTypeFilter userType() {
        if (userType == null) {
            userType = new ConsumerTypeFilter();
        }
        return userType;
    }

    public void setUserType(ConsumerTypeFilter userType) {
        this.userType = userType;
    }

    public LongFilter getPersonalInfoId() {
        return personalInfoId;
    }

    public LongFilter personalInfoId() {
        if (personalInfoId == null) {
            personalInfoId = new LongFilter();
        }
        return personalInfoId;
    }

    public void setPersonalInfoId(LongFilter personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConsumerCriteria that = (ConsumerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(ip, that.ip) &&
            Objects.equals(macAddress, that.macAddress) &&
            Objects.equals(deviceId, that.deviceId) &&
            Objects.equals(userType, that.userType) &&
            Objects.equals(personalInfoId, that.personalInfoId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ip, macAddress, deviceId, userType, personalInfoId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsumerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (ip != null ? "ip=" + ip + ", " : "") +
            (macAddress != null ? "macAddress=" + macAddress + ", " : "") +
            (deviceId != null ? "deviceId=" + deviceId + ", " : "") +
            (userType != null ? "userType=" + userType + ", " : "") +
            (personalInfoId != null ? "personalInfoId=" + personalInfoId + ", " : "") +
            "}";
    }
}
