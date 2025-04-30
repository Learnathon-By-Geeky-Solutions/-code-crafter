-- Drop the existing constraint
ALTER TABLE blog DROP CONSTRAINT IF EXISTS blog_doctor_category_check;

-- Add a new constraint that only enforces validation when necessary
ALTER TABLE blog ADD CONSTRAINT blog_doctor_category_check
    CHECK (
        (category != 'DOCTOR_ARTICLE' OR
         doctor_category IN ('MENTAL_HEALTH', 'PHYSICAL_HEALTH', 'PREVENTIVE_CARE', 'NUTRITION',
                            'ALTERNATIVE_MEDICINE', 'MEDICAL_RESEARCH', 'FITNESS'))
            AND
        (doctor_category IS NULL OR
         doctor_category IN ('MENTAL_HEALTH', 'PHYSICAL_HEALTH', 'PREVENTIVE_CARE', 'NUTRITION',
                             'ALTERNATIVE_MEDICINE', 'MEDICAL_RESEARCH', 'FITNESS'))
        );