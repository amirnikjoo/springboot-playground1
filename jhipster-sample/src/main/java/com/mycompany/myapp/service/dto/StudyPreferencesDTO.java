package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.CourseIntensity;
import com.mycompany.myapp.domain.enumeration.LearningType;
import com.mycompany.myapp.domain.enumeration.SpacedRepetition;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.StudyPreferences} entity.
 */
public class StudyPreferencesDTO implements Serializable {

    private Long id;

    private SpacedRepetition spacedRepetition;

    private CourseIntensity courseIntensity;

    private LearningType learningType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SpacedRepetition getSpacedRepetition() {
        return spacedRepetition;
    }

    public void setSpacedRepetition(SpacedRepetition spacedRepetition) {
        this.spacedRepetition = spacedRepetition;
    }

    public CourseIntensity getCourseIntensity() {
        return courseIntensity;
    }

    public void setCourseIntensity(CourseIntensity courseIntensity) {
        this.courseIntensity = courseIntensity;
    }

    public LearningType getLearningType() {
        return learningType;
    }

    public void setLearningType(LearningType learningType) {
        this.learningType = learningType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyPreferencesDTO)) {
            return false;
        }

        StudyPreferencesDTO studyPreferencesDTO = (StudyPreferencesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, studyPreferencesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyPreferencesDTO{" +
            "id=" + getId() +
            ", spacedRepetition='" + getSpacedRepetition() + "'" +
            ", courseIntensity='" + getCourseIntensity() + "'" +
            ", learningType='" + getLearningType() + "'" +
            "}";
    }
}
