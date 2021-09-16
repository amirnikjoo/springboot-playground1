package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.CourseIntensity;
import com.mycompany.myapp.domain.enumeration.LearningType;
import com.mycompany.myapp.domain.enumeration.SpacedRepetition;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.StudyPreferences} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.StudyPreferencesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /study-preferences?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudyPreferencesCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SpacedRepetition
     */
    public static class SpacedRepetitionFilter extends Filter<SpacedRepetition> {

        public SpacedRepetitionFilter() {}

        public SpacedRepetitionFilter(SpacedRepetitionFilter filter) {
            super(filter);
        }

        @Override
        public SpacedRepetitionFilter copy() {
            return new SpacedRepetitionFilter(this);
        }
    }

    /**
     * Class for filtering CourseIntensity
     */
    public static class CourseIntensityFilter extends Filter<CourseIntensity> {

        public CourseIntensityFilter() {}

        public CourseIntensityFilter(CourseIntensityFilter filter) {
            super(filter);
        }

        @Override
        public CourseIntensityFilter copy() {
            return new CourseIntensityFilter(this);
        }
    }

    /**
     * Class for filtering LearningType
     */
    public static class LearningTypeFilter extends Filter<LearningType> {

        public LearningTypeFilter() {}

        public LearningTypeFilter(LearningTypeFilter filter) {
            super(filter);
        }

        @Override
        public LearningTypeFilter copy() {
            return new LearningTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SpacedRepetitionFilter spacedRepetition;

    private CourseIntensityFilter courseIntensity;

    private LearningTypeFilter learningType;

    public StudyPreferencesCriteria() {}

    public StudyPreferencesCriteria(StudyPreferencesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.spacedRepetition = other.spacedRepetition == null ? null : other.spacedRepetition.copy();
        this.courseIntensity = other.courseIntensity == null ? null : other.courseIntensity.copy();
        this.learningType = other.learningType == null ? null : other.learningType.copy();
    }

    @Override
    public StudyPreferencesCriteria copy() {
        return new StudyPreferencesCriteria(this);
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

    public SpacedRepetitionFilter getSpacedRepetition() {
        return spacedRepetition;
    }

    public SpacedRepetitionFilter spacedRepetition() {
        if (spacedRepetition == null) {
            spacedRepetition = new SpacedRepetitionFilter();
        }
        return spacedRepetition;
    }

    public void setSpacedRepetition(SpacedRepetitionFilter spacedRepetition) {
        this.spacedRepetition = spacedRepetition;
    }

    public CourseIntensityFilter getCourseIntensity() {
        return courseIntensity;
    }

    public CourseIntensityFilter courseIntensity() {
        if (courseIntensity == null) {
            courseIntensity = new CourseIntensityFilter();
        }
        return courseIntensity;
    }

    public void setCourseIntensity(CourseIntensityFilter courseIntensity) {
        this.courseIntensity = courseIntensity;
    }

    public LearningTypeFilter getLearningType() {
        return learningType;
    }

    public LearningTypeFilter learningType() {
        if (learningType == null) {
            learningType = new LearningTypeFilter();
        }
        return learningType;
    }

    public void setLearningType(LearningTypeFilter learningType) {
        this.learningType = learningType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final StudyPreferencesCriteria that = (StudyPreferencesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(spacedRepetition, that.spacedRepetition) &&
            Objects.equals(courseIntensity, that.courseIntensity) &&
            Objects.equals(learningType, that.learningType)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spacedRepetition, courseIntensity, learningType);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyPreferencesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (spacedRepetition != null ? "spacedRepetition=" + spacedRepetition + ", " : "") +
            (courseIntensity != null ? "courseIntensity=" + courseIntensity + ", " : "") +
            (learningType != null ? "learningType=" + learningType + ", " : "") +
            "}";
    }
}
