CREATE TABLE ambulance_booking
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               BIGINT       NOT NULL,
    ambulance_id          BIGINT       NOT NULL,
    pickup_location       VARCHAR(255) NOT NULL,
    destination_location  VARCHAR(255) NOT NULL,
    patient_name          VARCHAR(100) NOT NULL,
    patient_condition     VARCHAR(500),
    contact_number        VARCHAR(20),
    booking_time          TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    scheduled_pickup_time TIMESTAMPTZ,
    status                VARCHAR(20)  NOT NULL CHECK (status IN
                                                       ('PENDING', 'CONFIRMED', 'PICKED_UP', 'COMPLETED', 'CANCELLED')),

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL,
    FOREIGN KEY (ambulance_id) REFERENCES ambulance (id) ON DELETE SET NULL
);
ALTER TABLE ambulance_booking ADD COLUMN created_at TIMESTAMP;
ALTER TABLE ambulance_booking ADD COLUMN updated_at TIMESTAMP;

-- Create indexes for better performance
CREATE INDEX idx_ambulance_booking_user ON ambulance_booking (user_id);
CREATE INDEX idx_ambulance_booking_ambulance ON ambulance_booking (ambulance_id);
CREATE INDEX idx_ambulance_booking_status ON ambulance_booking (status);