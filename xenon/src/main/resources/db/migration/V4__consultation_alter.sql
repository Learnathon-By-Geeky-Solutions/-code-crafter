-- Add meeting link and payment related fields to appointment tables

-- For emergency appointments
ALTER TABLE emergency_appointment_table
    ADD COLUMN meeting_link VARCHAR(255),
ADD COLUMN payment_id VARCHAR(100),
ADD COLUMN payment_status VARCHAR(20) CHECK (payment_status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
ADD COLUMN is_beneficiary BOOLEAN DEFAULT FALSE,
ADD COLUMN beneficiary_name VARCHAR(100),
ADD COLUMN beneficiary_phone VARCHAR(20),
ADD COLUMN beneficiary_address VARCHAR(255),
ADD COLUMN beneficiary_gender VARCHAR(10) CHECK (beneficiary_gender IN ('MALE', 'FEMALE', 'OTHER')),
ADD COLUMN beneficiary_age INT,
ADD COLUMN medical_history_file VARCHAR(255),
ADD COLUMN consultation_date TIMESTAMP;

-- For specialist appointments
ALTER TABLE specialist_appointment_table
    ADD COLUMN meeting_link VARCHAR(255),
ADD COLUMN payment_id VARCHAR(100),
ADD COLUMN payment_status VARCHAR(20) CHECK (payment_status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
ADD COLUMN is_beneficiary BOOLEAN DEFAULT FALSE,
ADD COLUMN beneficiary_name VARCHAR(100),
ADD COLUMN beneficiary_phone VARCHAR(20),
ADD COLUMN beneficiary_address VARCHAR(255),
ADD COLUMN beneficiary_gender VARCHAR(10) CHECK (beneficiary_gender IN ('MALE', 'FEMALE', 'OTHER')),
ADD COLUMN beneficiary_age INT,
ADD COLUMN medical_history_file VARCHAR(255),
ADD COLUMN consultation_date TIMESTAMP,
ADD COLUMN consultation_slot_start TIME,
ADD COLUMN consultation_slot_end TIME;

-- For offline appointments
ALTER TABLE offline_appointment_table
    ADD COLUMN payment_id VARCHAR(100),
ADD COLUMN payment_status VARCHAR(20) CHECK (payment_status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
ADD COLUMN is_beneficiary BOOLEAN DEFAULT FALSE,
ADD COLUMN beneficiary_name VARCHAR(100),
ADD COLUMN beneficiary_phone VARCHAR(20),
ADD COLUMN beneficiary_address VARCHAR(255),
ADD COLUMN beneficiary_gender VARCHAR(10) CHECK (beneficiary_gender IN ('MALE', 'FEMALE', 'OTHER')),
ADD COLUMN beneficiary_age INT,
ADD COLUMN medical_history_file VARCHAR(255),
ADD COLUMN appointment_date DATE,
ADD COLUMN appointment_time TIME;

-- Create table for doctor ratings
CREATE TABLE doctor_rating
(
    id                        BIGSERIAL PRIMARY KEY,
    doctor_id                 BIGINT  NOT NULL,
    user_id                   BIGINT  NOT NULL,
    rating                    INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    review                    TEXT,
    consultation_type         VARCHAR(20) CHECK (consultation_type IN ('EMERGENCY', 'SPECIALIST', 'OFFLINE')),
    emergency_appointment_id  BIGINT,
    specialist_appointment_id BIGINT,
    offline_appointment_id    BIGINT,
    created_at                TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctor (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE CASCADE,
    FOREIGN KEY (emergency_appointment_id) REFERENCES emergency_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (specialist_appointment_id) REFERENCES specialist_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (offline_appointment_id) REFERENCES offline_appointment_table (id) ON DELETE SET NULL
);

-- Create table for prescription
CREATE TABLE prescription
(
    id                        BIGSERIAL PRIMARY KEY,
    doctor_id                 BIGINT NOT NULL,
    user_id                   BIGINT NOT NULL,
    consultation_type         VARCHAR(20) CHECK (consultation_type IN ('EMERGENCY', 'SPECIALIST', 'OFFLINE')),
    emergency_appointment_id  BIGINT,
    specialist_appointment_id BIGINT,
    offline_appointment_id    BIGINT,
    diagnosis                 TEXT,
    instructions              TEXT,
    follow_up_date            DATE,
    created_at                TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctor (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE CASCADE,
    FOREIGN KEY (emergency_appointment_id) REFERENCES emergency_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (specialist_appointment_id) REFERENCES specialist_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (offline_appointment_id) REFERENCES offline_appointment_table (id) ON DELETE SET NULL
);

-- Create table for prescription medications
CREATE TABLE prescription_medication
(
    id              BIGSERIAL PRIMARY KEY,
    prescription_id BIGINT       NOT NULL,
    medication_name VARCHAR(100) NOT NULL,
    dosage          VARCHAR(100) NOT NULL,
    frequency       VARCHAR(100) NOT NULL,
    duration        VARCHAR(100) NOT NULL,
    instructions    TEXT,
    FOREIGN KEY (prescription_id) REFERENCES prescription (id) ON DELETE CASCADE
);

-- Create table for medical tests
CREATE TABLE medical_test
(
    id              BIGSERIAL PRIMARY KEY,
    prescription_id BIGINT       NOT NULL,
    test_name       VARCHAR(100) NOT NULL,
    instructions    TEXT,
    FOREIGN KEY (prescription_id) REFERENCES prescription (id) ON DELETE CASCADE
);

-- Create table for payment tracking
CREATE TABLE payment
(
    id                        BIGSERIAL PRIMARY KEY,
    user_id                   BIGINT         NOT NULL,
    payment_id                VARCHAR(100)   NOT NULL,
    payment_method            VARCHAR(50)    NOT NULL,
    amount                    DECIMAL(10, 2) NOT NULL,
    currency                  VARCHAR(10)    NOT NULL DEFAULT 'BDT',
    status                    VARCHAR(20) CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
    consultation_type         VARCHAR(20) CHECK (consultation_type IN ('EMERGENCY', 'SPECIALIST', 'OFFLINE')),
    emergency_appointment_id  BIGINT,
    specialist_appointment_id BIGINT,
    offline_appointment_id    BIGINT,
    created_at                TIMESTAMPTZ             DEFAULT CURRENT_TIMESTAMP,
    updated_at                TIMESTAMPTZ             DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE CASCADE,
    FOREIGN KEY (emergency_appointment_id) REFERENCES emergency_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (specialist_appointment_id) REFERENCES specialist_appointment_table (id) ON DELETE SET NULL,
    FOREIGN KEY (offline_appointment_id) REFERENCES offline_appointment_table (id) ON DELETE SET NULL
);

-- Create table for notifications
CREATE TABLE notification
(
    id                BIGSERIAL PRIMARY KEY,
    user_id           BIGINT       NOT NULL,
    title             VARCHAR(100) NOT NULL,
    message           TEXT         NOT NULL,
    is_read           BOOLEAN     DEFAULT FALSE,
    notification_type VARCHAR(50)  NOT NULL,
    related_id        BIGINT,
    created_at        TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE CASCADE
);