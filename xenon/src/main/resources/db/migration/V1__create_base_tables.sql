CREATE TABLE division
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE district
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    division_id BIGINT NULL,
    FOREIGN KEY (division_id) REFERENCES division (id) ON DELETE SET NULL
);

CREATE TABLE upazila
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    district_id BIGINT NULL,
    FOREIGN KEY (district_id) REFERENCES district (id) ON DELETE SET NULL
);

CREATE TABLE table_user
(
    id         BIGSERIAL PRIMARY KEY,
    fname      VARCHAR(30),
    lname      VARCHAR(30),
    phone      VARCHAR(20)                                                                                 NOT NULL UNIQUE,
    email      VARCHAR(50) UNIQUE,
    password   VARCHAR(255)                                                                                NOT NULL,
    role       VARCHAR(20) CHECK (role IN ('USER', 'DOCTOR', 'HOSPITAL', 'HEALTH_AUTHORIZATION', 'ADMIN')) NOT NULL DEFAULT 'USER',
    status     VARCHAR(20) CHECK (status IN ('ACTIVE', 'INACTIVE', 'BANNED'))                                       DEFAULT 'INACTIVE',
    upazila_id BIGINT NULL,
    area       TEXT,
    latitude   DOUBLE PRECISION,
    longitude  DOUBLE PRECISION,
    gender     VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    created_at TIMESTAMPTZ                                                                                           DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ                                                                                           DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (upazila_id) REFERENCES upazila (id) ON DELETE SET NULL
);
