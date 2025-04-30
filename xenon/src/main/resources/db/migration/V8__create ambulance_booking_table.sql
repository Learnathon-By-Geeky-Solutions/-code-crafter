CREATE TABLE ambulance_booking
(
    id                    BIGSERIAL PRIMARY KEY,
    user_id               BIGINT       NOT NULL,
    ambulance_id          BIGINT       NOT NULL,
    booking_time          TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    status                VARCHAR(20)  NOT NULL CHECK (status IN
                                                       ('PENDING', 'CONFIRMED', 'CANCELLED')),
    created_at            TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL,
    FOREIGN KEY (ambulance_id) REFERENCES ambulance (id) ON DELETE SET NULL
);

-- Create indexes for better performance
CREATE INDEX idx_ambulance_booking_user ON ambulance_booking (user_id);
CREATE INDEX idx_ambulance_booking_ambulance ON ambulance_booking (ambulance_id);
CREATE INDEX idx_ambulance_booking_status ON ambulance_booking (status);