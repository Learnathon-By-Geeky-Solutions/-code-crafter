package com.xenon.data.entity.blog.doctorArticle;

/**
 * Enum for doctor article categories.
 * These values must match the CHECK constraint in the database.
 */
public enum DoctorArticleCategory {
    MENTAL_HEALTH,
    PHYSICAL_HEALTH,
    PREVENTIVE_CARE,
    NUTRITION,
    ALTERNATIVE_MEDICINE,
    MEDICAL_RESEARCH,
    FITNESS
}