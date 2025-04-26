package com.xenon.data.entity.blog.doctorArticle;

/**
 * Categories specifically for doctor articles to provide more specialized medical topics
 */
public enum DoctorArticleCategory {
    MENTAL_HEALTH("Mental Health"),
    PHYSICAL_HEALTH("Physical Health"),
    PREVENTIVE_CARE("Preventive Care"),
    NUTRITION("Nutrition & Diet"),
    CHRONIC_DISEASE("Chronic Disease Management"),
    PEDIATRICS("Pediatric Health"),
    WOMENS_HEALTH("Women's Health"),
    MENS_HEALTH("Men's Health"),
    ELDERLY_CARE("Elderly Care"),
    EMERGENCY_MEDICINE("Emergency Medicine"),
    ALTERNATIVE_MEDICINE("Alternative Medicine"),
    MEDICAL_RESEARCH("Medical Research & News"),
    HEALTH_POLICY("Health Policy"),
    FITNESS("Fitness & Exercise"),
    PANDEMIC_AWARENESS("Pandemic & Disease Awareness");

    private final String displayName;

    DoctorArticleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}