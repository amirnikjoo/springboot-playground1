package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.CourseIntensity;
import com.mycompany.myapp.domain.enumeration.LearningType;
import com.mycompany.myapp.domain.enumeration.SpacedRepetition;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A StudyPreferences.
 */
@Entity
@Table(name = "study_preferences")
public class StudyPreferences implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "spaced_repetition")
    private SpacedRepetition spacedRepetition;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_intensity")
    private CourseIntensity courseIntensity;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_type")
    private LearningType learningType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudyPreferences id(Long id) {
        this.id = id;
        return this;
    }

    public SpacedRepetition getSpacedRepetition() {
        return this.spacedRepetition;
    }

    public StudyPreferences spacedRepetition(SpacedRepetition spacedRepetition) {
        this.spacedRepetition = spacedRepetition;
        return this;
    }

    public void setSpacedRepetition(SpacedRepetition spacedRepetition) {
        this.spacedRepetition = spacedRepetition;
    }

    public CourseIntensity getCourseIntensity() {
        return this.courseIntensity;
    }

    public StudyPreferences courseIntensity(CourseIntensity courseIntensity) {
        this.courseIntensity = courseIntensity;
        return this;
    }

    public void setCourseIntensity(CourseIntensity courseIntensity) {
        this.courseIntensity = courseIntensity;
    }

    public LearningType getLearningType() {
        return this.learningType;
    }

    public StudyPreferences learningType(LearningType learningType) {
        this.learningType = learningType;
        return this;
    }

    public void setLearningType(LearningType learningType) {
        this.learningType = learningType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyPreferences)) {
            return false;
        }
        return id != null && id.equals(((StudyPreferences) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyPreferences{" +
            "id=" + getId() +
            ", spacedRepetition='" + getSpacedRepetition() + "'" +
            ", courseIntensity='" + getCourseIntensity() + "'" +
            ", learningType='" + getLearningType() + "'" +
            "}";
    }
}
