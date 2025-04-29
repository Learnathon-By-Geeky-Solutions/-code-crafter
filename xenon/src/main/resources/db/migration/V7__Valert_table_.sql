CREATE TABLE health_authorization
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL UNIQUE,
    authorization_number VARCHAR(30) NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE alert_table
(
    id          BIGSERIAL PRIMARY KEY,
    health_authorization_id BIGINT      NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description     VARCHAR(1000) NOT NULL,
    alertness   VARCHAR(1000) NOT NULL,
    latitude    DOUBLE PRECISION NOT NULL,
    longitude   DOUBLE PRECISION NOT NULL,
    radius      DOUBLE PRECISION NOT NULL,
    severity_level VARCHAR(30) NOT NULL CHECK (severity_level IN ('HIGH', 'MEDIUM', 'LOW')),
    is_Active   BOOLEAN DEFAULT TRUE,
    start_date  DATE NOT NULL,
    end_date    DATE DEFAULT CURRENT_TIMESTAMP,
    created_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (health_authorization_id) REFERENCES health_authorization (id) ON DELETE SET NULL
);

CREATE TABLE user_alert_notification
(
    id         SERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    alert_id   BIGINT NOT NULL,
    is_read    BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Foreign key constraints without naming
    FOREIGN KEY (alert_id) REFERENCES alert_table(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES table_user(id) ON DELETE CASCADE
);

CREATE TABLE user_location
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL UNIQUE,
    latitude    DOUBLE PRECISION NOT NULL,
    longitude   DOUBLE PRECISION NOT NULL,
    location_allowed BOOLEAN DEFAULT FALSE,
    last_updated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);


