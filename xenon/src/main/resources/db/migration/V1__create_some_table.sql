CREATE TABLE division
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE district
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    division_id BIGINT      NOT NULL,
    FOREIGN KEY (division_id) REFERENCES division (id) ON DELETE SET NULL
);

CREATE TABLE upazila
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    district_id BIGINT      NOT NULL,
    FOREIGN KEY (district_id) REFERENCES district (id) ON DELETE SET NULL
);

CREATE TABLE table_user
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    phone      VARCHAR(20)                                           NOT NULL UNIQUE,
    email      VARCHAR(50) UNIQUE,
    password   VARCHAR(255)                                          NOT NULL,
    role       VARCHAR(20) CHECK (role IN ('USER', 'DOCTOR', 'HOSPITAL', 'HEALTH_AUTHORIZATION', 'ADMIN', 'BLOOD_BANK',
                                           'AMBULANCE', 'PHARMACY')) NOT NULL DEFAULT 'USER',
    status     VARCHAR(10) CHECK (status IN ('ACTIVE', 'INACTIVE', 'BANNED')) DEFAULT 'ACTIVE',
    upazila_id BIGINT,
    area       VARCHAR(255),
    latitude   DOUBLE PRECISION,
    longitude  DOUBLE PRECISION,
    gender     VARCHAR(10) CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),
    created_at TIMESTAMPTZ                                                    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ                                                    DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (upazila_id) REFERENCES upazila (id) ON DELETE SET NULL
);

CREATE TABLE donor
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL UNIQUE,
    blood_type VARCHAR(10) NOT NULL CHECK (
        blood_type IN (
                       'A_POS', 'A_NEG',
                       'B_POS', 'B_NEG',
                       'AB_POS', 'AB_NEG',
                       'O_POS', 'O_NEG'
            )
        ),
    age        INT         NOT NULL,
    weight     INT         NOT NULL,
    interested VARCHAR(10) CHECK (interested IN ('YES', 'NO')),

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE blood_donation_history
(
    id            BIGSERIAL PRIMARY KEY,
    donor_id      BIGINT       NOT NULL,
    patient_name  VARCHAR(100) NOT NULL,
    quantity      INT          NOT NULL,
    hospital_name VARCHAR(100) NOT NULL,
    last_donation DATE         NOT NULL,
    FOREIGN KEY (donor_id) REFERENCES donor (id) ON DELETE SET NULL
);

CREATE TABLE blood_request_post
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT       NOT NULL,
    upazila_id     BIGINT       NOT NULL,
    patient_name   VARCHAR(100) NOT NULL,
    blood_type     VARCHAR(10)  NOT NULL CHECK (
        blood_type IN (
                       'A_POS', 'A_NEG',
                       'B_POS', 'B_NEG',
                       'AB_POS', 'AB_NEG',
                       'O_POS', 'O_NEG'
            )
        ),
    quantity       INT          NOT NULL,
    hospital_name  VARCHAR(100) NOT NULL,
    contact_number VARCHAR(100) NOT NULL,
    description    VARCHAR(100) NOT NULL,
    date           DATE         NOT NULL,
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL,
    FOREIGN KEY (upazila_id) REFERENCES upazila (id) ON DELETE SET NULL
);

CREATE TABLE blood_comment_table
(
    id                    BIGSERIAL PRIMARY KEY,
    blood_request_post_id BIGINT       NOT NULL,
    user_id               BIGINT       NOT NULL,
    comment               VARCHAR(100) NOT NULL,
    created_at            TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (blood_request_post_id) REFERENCES blood_request_post (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE ambulance
(
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT UNIQUE,
    ambulance_type       VARCHAR(10)   NOT NULL CHECK (ambulance_type IN ('GENERAL', 'ICU', 'FREEZING')),
    ambulance_number     VARCHAR(30)   NOT NULL UNIQUE,
    ambulance_status     VARCHAR(10)   NOT NULL CHECK (ambulance_status IN ('AVAILABLE', 'UNAVAILABLE')),
    about                VARCHAR(1000) NOT NULL,
    service_offers       VARCHAR(500)  NOT NULL,
    hospital_affiliation VARCHAR(500),
    coverage_areas       VARCHAR(200)  NOT NULL,
    response_time        INT           NOT NULL,
    doctors              INT,
    nurses               INT,
    paramedics           INT,
    team_qualification   VARCHAR(700)  NOT NULL,
    starting_fee         INT           NOT NULL,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE ambulance_review
(
    id           BIGSERIAL PRIMARY KEY,
    ambulance_id BIGINT       NOT NULL,
    user_id      BIGINT       NOT NULL,
    review       VARCHAR(255) NOT NULL,
    rating       INT          NOT NULL,
    review_date  DATE         NOT NULL,
    FOREIGN KEY (ambulance_id) REFERENCES ambulance (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE blog
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT        NOT NULL,
    title      VARCHAR(100)  NOT NULL,
    content    VARCHAR(1000) NOT NULL,
    category   VARCHAR(30)   NOT NULL,
    media      VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE blog_like
(
    id      BIGSERIAL PRIMARY KEY,
    blog_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (blog_id) REFERENCES blog (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE comment
(
    id         BIGSERIAL PRIMARY KEY,
    blog_id    BIGINT       NOT NULL,
    user_id    BIGINT       NOT NULL,
    content    VARCHAR(300) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (blog_id) REFERENCES blog (id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE DOCTOR
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT        NOT NULL UNIQUE,
    doctor_title        VARCHAR(30)   NOT NULL CHECK (doctor_title IN ('Dr', 'Prof_Dr', 'Assoc_Prof_Dr', 'Asst_Prof_Dr')),
    doctor_type         VARCHAR(30)   NOT NULL CHECK (doctor_type IN ('MEDICAL', 'DENTAL', 'VETERINARY')),
    specialist_category VARCHAR(40)   NOT NULL CHECK (specialist_category IN (
                                                                              'ONCOPLASTIC_BREAST_SURGEON',
                                                                              'ORAL_MAXILLOFACIAL_SURGERY',
                                                                              'SURGICAL_ONCOLOGIST',
                                                                              'NEPHROLOGY',
                                                                              'MEDICINE_SPECIALIST',
                                                                              'CANCER_SPECIALIST',
                                                                              'CARDIOLOGY',
                                                                              'OBS_GYNEA',
                                                                              'ENT_HEAD_NECK_SURGERY',
                                                                              'ORTHOPEDICS',
                                                                              'CHILD_SPECIALIST',
                                                                              'DERMATOLOGIST',
                                                                              'CHEST_RESPIRATORY',
                                                                              'NEUROLOGY',
                                                                              'KIDNEY_SPECIALIST',
                                                                              'LIVER_SPECIALIST',
                                                                              'UROLOGIST_ANDROLOGIST_URO_ONCOLOGIST',
                                                                              'PULMONOLOGIST_CHEST_DISEASE_SPECIALIST',
                                                                              'SURGERY',
                                                                              'GASTROENTEROLOGY',
                                                                              'HEPATOLOGY',
                                                                              'EYE_CATARACT_SPECIALIST',
                                                                              'COVID_SUPPORT',
                                                                              'DIETITIAN_NUTRITIONIST',
                                                                              'DENTAL',
                                                                              'HEMATOLOGY',
                                                                              'DIABETES',
                                                                              'HORMONE_DISEASE_SPECIALIST',
                                                                              'RHEUMATOLOGY_SPECIALIST',
                                                                              'PSYCHIATRIST',
                                                                              'SEX_LEPROSY_SPECIALIST',
                                                                              'PSYCHOLOGIST_THERAPEUTIC_COUNSELOR',
                                                                              'BURN_PLASTIC_RECONSTRUCTIVE_SURGERY',
                                                                              'GENERAL_PHYSICIAN',
                                                                              'AYURVEDIC_PHYSICIAN'
        )),
    date_of_birth       DATE          NOT NULL,
    nid                 VARCHAR(30) UNIQUE,
    passport            VARCHAR(30) UNIQUE,
    registration_no     VARCHAR(30)   NOT NULL UNIQUE,
    experience          INT           NOT NULL,
    hospital            VARCHAR(100)  NOT NULL,
    about               VARCHAR(1000) NOT NULL,
    area_of_expertise   VARCHAR(1000) NOT NULL,
    patient_care_policy VARCHAR(1000) NOT NULL,
    education           VARCHAR(1000) NOT NULL,
    experience_info     VARCHAR(1000) NOT NULL,
    awards              VARCHAR(1000),
    publications        VARCHAR(1000),
    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE hospital
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT      NOT NULL UNIQUE,
    registration_no VARCHAR(30) NOT NULL UNIQUE,
    hospital_type   VARCHAR(30) NOT NULL CHECK (hospital_type IN ('GOVERNMENT', 'PRIVATE')),

    FOREIGN KEY (user_id) REFERENCES table_user (id) ON DELETE SET NULL
);

CREATE TABLE hospital_branch
(
    id             BIGSERIAL PRIMARY KEY,
    hospital_id    BIGINT       NOT NULL,
    upazila_id     BIGINT       NOT NULL,
    branch_name    VARCHAR(100) NOT NULL,
    address        VARCHAR(255) NOT NULL,
    phone          VARCHAR(20)  NOT NULL UNIQUE,
    email          VARCHAR(100) NOT NULL UNIQUE,
    doctors        INT          NOT NULL,
    beds           INT          NOT NULL,
    establish_date DATE         NOT NULL,

    FOREIGN KEY (hospital_id) REFERENCES hospital (id) ON DELETE SET NULL,
    FOREIGN KEY (upazila_id) REFERENCES upazila (id) ON DELETE SET NULL
);

CREATE TABLE offline_doctor_affiliation
(
    doctor_id          BIGINT NOT NULL,
    hospital_branch_id BIGINT NOT NULL,
    fee                INTEGER,
    PRIMARY KEY (doctor_id, hospital_branch_id),
    FOREIGN KEY (doctor_id)
        REFERENCES doctor (id)
        ON DELETE CASCADE,
    FOREIGN KEY (hospital_branch_id)
        REFERENCES hospital_branch (id)
        ON DELETE CASCADE
);


CREATE TABLE doctor_schedule
(
    id                 BIGSERIAL PRIMARY KEY,
    doctor_id          BIGINT      NOT NULL,
    hospital_branch_id BIGINT      NOT NULL,
    day                VARCHAR(10) NOT NULL CHECK (day IN (
        'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'
        )
) ,

    start_time         TIME NOT NULL,
    end_time           TIME NOT NULL,
    availability       VARCHAR(20) NOT NULL CHECK (availability IN ('AVAILABLE', 'UNAVAILABLE')),
    booking_quantity   INT NOT NULL,
    duration           INTEGER NOT NULL,

    CONSTRAINT fk_doctor_schedule_affiliation FOREIGN KEY (doctor_id, hospital_branch_id)
        REFERENCES offline_doctor_affiliation (doctor_id, hospital_branch_id)
        ON DELETE CASCADE
);



CREATE TABLE offline_appointment_table
(
    id                 BIGSERIAL PRIMARY KEY,
    doctor_schedule_id BIGINT      NOT NULL,
    user_id            BIGINT      NOT NULL,
    appointment_status VARCHAR(20) NOT NULL CHECK (appointment_status IN ('PENDING', 'CONFIRMED', 'CANCELLED')),

    FOREIGN KEY (doctor_schedule_id)
        REFERENCES doctor_schedule (id)
        ON DELETE SET NULL,
    FOREIGN KEY (user_id)
        REFERENCES table_user (id)
        ON DELETE SET NULL
);

CREATE TABLE emergency_consultation
(
    id           BIGSERIAL PRIMARY KEY,
    doctor_id    BIGINT      NOT NULL,
    availability VARCHAR(20) NOT NULL CHECK (availability IN ('AVAILABLE', 'UNAVAILABLE')),
    fee          INTEGER     NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctor (id) ON DELETE SET NULL
);

CREATE TABLE specialist_consultation
(
    id           BIGSERIAL PRIMARY KEY,
    doctor_id    BIGINT      NOT NULL,
    status       VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    availability VARCHAR(20) NOT NULL CHECK (availability IN ('AVAILABLE', 'UNAVAILABLE')),
    day_date     VARCHAR(10) NOT NULL CHECK (day_date IN (
        'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY')
        ),
    start_time   TIME        NOT NULL,
    end_time     TIME        NOT NULL,
    duration     INTEGER     NOT NULL,
    fee          INTEGER     NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctor (id) ON DELETE SET NULL
);

CREATE TABLE specialist_appointment_table
(
    id                         BIGSERIAL PRIMARY KEY,
    specialist_consultation_id BIGINT      NOT NULL,
    user_id                    BIGINT      NOT NULL,
    appointment_status         VARCHAR(20) NOT NULL CHECK (appointment_status IN ('PENDING', 'CONFIRMED', 'CANCELLED')),

    FOREIGN KEY (specialist_consultation_id)
        REFERENCES specialist_consultation (id)
        ON DELETE SET NULL,
    FOREIGN KEY (user_id)
        REFERENCES table_user (id)
        ON DELETE SET NULL
);

CREATE TABLE emergency_appointment_table
(
    id                        BIGSERIAL PRIMARY KEY,
    emergency_consultation_id BIGINT      NOT NULL,
    user_id                   BIGINT      NOT NULL,
    appointment_status        VARCHAR(20) NOT NULL CHECK (appointment_status IN ('PENDING', 'CONFIRMED', 'CANCELLED')),

    FOREIGN KEY (emergency_consultation_id)
        REFERENCES emergency_consultation (id)
        ON DELETE SET NULL,
    FOREIGN KEY (user_id)
        REFERENCES table_user (id)
        ON DELETE SET NULL
);

CREATE TABLE health_authority
(
    id              BIGSERIAL PRIMARY KEY,
    registration_no VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE alert
(
    id                  BIGSERIAL PRIMARY KEY,
    health_authority_id BIGINT           NOT NULL,
    title               VARCHAR(100)     NOT NULL,
    description         VARCHAR(1000)    NOT NULL,
    alertness           VARCHAR(1000)    NOT NULL,
    latitude            DOUBLE PRECISION NOT NULL,
    longitude           DOUBLE PRECISION NOT NULL,
    radius              DOUBLE PRECISION NOT NULL,
    created_at          TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (health_authority_id) REFERENCES health_authority (id) ON DELETE SET NULL
);