-- First, drop the check constraint
ALTER TABLE doctor DROP CONSTRAINT IF EXISTS doctor_doctor_type_check;

-- Then, drop the NOT NULL constraint
ALTER TABLE doctor ALTER COLUMN doctor_type DROP NOT NULL;

-- Finally, drop the column itself (if needed)
ALTER TABLE doctor DROP COLUMN doctor_type;