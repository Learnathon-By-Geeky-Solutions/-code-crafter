CREATE TABLE blood_bank
(
    id                  SERIAL PRIMARY KEY,
    user_id             BIGINT      NOT NULL,
    registration_number VARCHAR(30) NOT NULL UNIQUE,

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL

);
