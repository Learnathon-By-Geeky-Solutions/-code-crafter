INSERT INTO table_user (first_name, last_name, phone, email, password, role, status, upazila_id, area, latitude,
                        longitude, gender)
VALUES
-- USER
('Alice', 'Rahman', '01711111111', 'alice@example.com', '222222', 'USER', 'ACTIVE', 1, 'Sector 1, Uttara', 23.8759,
 90.3795, 'FEMALE'),

-- DOCTOR
('Dr. Imran', 'Khan', '01722222222', 'dr.imran@example.com', '222222', 'DOCTOR', 'ACTIVE', 2, 'Banani, Dhaka',
 23.7916, 90.4071, 'MALE'),

-- HOSPITAL
('Green', 'Hospital', '01733333333', 'hospital@example.com', '222222', 'HOSPITAL', 'ACTIVE', 3,
 'Dhanmondi 27, Dhaka', 23.7465, 90.3760, 'OTHER'),

-- HEALTH_AUTHORIZATION
('Health', 'Dept', '01744444444', 'healthauth@example.com', '222222', 'HEALTH_AUTHORIZATION', 'ACTIVE', 4,
 'Mohakhali, Dhaka', 23.7806, 90.4195, 'OTHER'),

-- ADMIN
('Admin', 'User', '01755555555', 'admin@example.com', '222222', 'ADMIN', 'ACTIVE', 5, 'Gulshan 2, Dhaka', 23.7925,
 90.4078, 'MALE'),

-- BLOOD_BANK
('Red', 'Cross', '01766666666', 'bloodbank@example.com', '222222', 'BLOOD_BANK', 'ACTIVE', 6, 'Mirpur 10, Dhaka',
 23.8041, 90.3663, 'OTHER'),

-- AMBULANCE
('Ambulance', 'Service', '01777777777', 'ambulance@example.com', '222222', 'AMBULANCE', 'ACTIVE', 7,
 'Tejgaon, Dhaka', 23.7580, 90.3931, 'OTHER'),
('Ambulance', 'Service', '01777777778', 'ambulance2@example.com', '222222', 'AMBULANCE', 'ACTIVE', 7,
 'Tejgaon, Dhaka', 23.7580, 90.3931, 'OTHER'),

-- PHARMACY
('Medi', 'Plus', '01788888888', 'pharmacy@example.com', '222222', 'PHARMACY', 'ACTIVE', 8, 'Shantinagar, Dhaka',
 23.7415, 90.4167, 'OTHER');



INSERT INTO donor (user_id, blood_type, age, weight, interested)
VALUES (1, 'O_POS', 28, 72, 'YES');

INSERT INTO blood_donation_history (donor_id, patient_name, quantity, hospital_name, last_donation)
VALUES (1, 'Rakib Hasan', 1, 'Dhaka Medical College Hospital', '2025-04-10');

INSERT INTO blood_request_post (user_id, upazila_id, patient_name, blood_type, quantity, hospital_name,
                                contact_number, description, date)
VALUES (1, 50, 'Shamim Ahmed', 'B_POS', 2, 'Square Hospital',
        '01712345678', 'Urgent need for surgery', '2025-04-21');


INSERT INTO blood_comment_table (blood_request_post_id, user_id, comment)
VALUES (1, 2, 'I can donate, available tomorrow morning.'),
       (1, 1, 'I am nearby and can help within an hour.'),
       (1, 3, 'Green Hospital can arrange a donor by this evening.'),
       (1, 4, 'Health Department is coordinating with local donors.'),
       (1, 5, 'Admin has flagged this request for priority support.'),
       (1, 6, 'Red Cross will dispatch a donor if no one responds soon.'),
       (1, 7, 'Ambulance is on standby for transport if needed.'),
       (1, 8, 'Backup ambulance also available for any emergency.'),
       (1, 9, 'Medi Plus has some donors in our network. Will check.'),
       (1, 1, 'Alice here again, confirming availability by 10 AM.');


INSERT INTO ambulance (user_id,
                       ambulance_type,
                       ambulance_number,
                       ambulance_status,
                       about,
                       service_offers,
                       hospital_affiliation,
                       coverage_areas,
                       response_time,
                       doctors,
                       nurses,
                       paramedics,
                       team_qualification,
                       starting_fee)
VALUES (3,
        'ICU',
        'AMB-ICU-3344',
        'AVAILABLE',
        'Our ICU ambulance is equipped with life-saving medical technology for critical patients.',
        '24/7 Emergency Support, On-board Oxygen, Cardiac Monitor',
        'City General Hospital',
        'Dhaka North, Gulshan, Banani, Uttara',
        15,
        1,
        2,
        1,
        'All medical staff are certified in BLS and ACLS with 5+ years of experience.',
        1500),
       (4,
        'ICU',
        'AMB-ICU-3345',
        'AVAILABLE',
        'Our ICU ambulance is equipped with life-saving medical technology for critical patients.',
        '24/7 Emergency Support, On-board Oxygen, Cardiac Monitor',
        'City General Hospital',
        'Dhaka North, Gulshan, Banani, Uttara',
        15,
        1,
        2,
        1,
        'All medical staff are certified in BLS and ACLS with 5+ years of experience.',
        1500);

INSERT INTO ambulance_review (ambulance_id,
                              user_id,
                              review,
                              rating,
                              review_date)
VALUES (1,
        1,
        'The ICU ambulance arrived quickly and the medical staff was very professional. Great service!',
        5,
        CURRENT_DATE);

INSERT INTO blog (user_id,
                  title,
                  content,
                  category,
                  media)
VALUES (2,
        '5 Tips for a Healthy Heart',
        'Keeping your heart healthy is crucial. In this blog, I share five practical tips to maintain cardiovascular health through diet, exercise, and lifestyle.',
        'MEDICAL_TIPS',
        'https://example.com/media/heart-health.jpg');

INSERT INTO blog_like (blog_id,
                       user_id)
VALUES (1, -- assuming blog with id=1 exists
        3 -- assuming user with id=3 exists
       );

INSERT INTO comment (blog_id,
                     user_id,
                     content)
VALUES (1, -- assuming blog with id=1 exists
        3, -- assuming user with id=3 exists
        'This is a great blog post! Very informative and helpful.');

INSERT INTO DOCTOR (user_id,
                    doctor_title,
                    doctor_type,
                    specialist_category,
                    date_of_birth,
                    nid,
                    passport,
                    registration_no,
                    experience,
                    hospital,
                    about,
                    area_of_expertise,
                    patient_care_policy,
                    education,
                    experience_info,
                    awards,
                    publications)
VALUES (1, -- assuming user with id=1 exists
        'Dr', -- Doctor title
        'MEDICAL', -- Doctor type
        'CARDIOLOGY', -- Specialist category
        '1975-06-15', -- Date of birth
        '123456789012', -- NID
        'AB1234567', -- Passport
        'REG123456', -- Registration number
        10, -- Experience (in years)
        'Heart Care Hospital', -- Hospital
        'Experienced cardiologist specializing in heart diseases.', -- About
        'Cardiology, Heart Surgery, Angioplasty', -- Area of expertise
        'We provide personalized care for heart patients.', -- Patient care policy
        'MBBS, MD in Cardiology', -- Education
        '10 years of experience in treating cardiovascular diseases.', -- Experience info
        'Best Cardiologist Award 2021', -- Awards
        'Research on cardiac surgeries and new techniques in cardiology' -- Publications
       );

INSERT INTO hospital (user_id,
                      registration_no,
                      hospital_type)
VALUES (1, -- assuming user with id=1 exists
        'HOSP123456', -- Hospital registration number
        'PRIVATE' -- Hospital type (can be 'GOVERNMENT' or 'PRIVATE')
       );


INSERT INTO hospital_branch (hospital_id,
                             upazila_id,
                             branch_name,
                             address,
                             phone,
                             email,
                             doctors,
                             beds,
                             establish_date)
VALUES (1, -- assuming hospital_id 1 exists
        101, -- assuming upazila_id 101 exists
        'City Hospital Branch', -- Branch name
        '123 Main Street, Dhaka', -- Branch address
        '017XXXXXXXX', -- Branch phone number
        'branch@cityhospital.com', -- Branch email
        50, -- Number of doctors
        100, -- Number of beds
        '2010-06-15' -- Establishment date
       );

INSERT INTO offline_doctor_affiliation (doctor_id,
                                        hospital_branch_id,
                                        fee)
VALUES (1, -- assuming doctor_id 1 exists
        1, -- assuming hospital_branch_id 1 exists
        1000 -- Fee for the doctor in this branch
       );

INSERT INTO doctor_schedule (doctor_id,
                             hospital_branch_id,
                             day,
                             start_time,
                             end_time,
                             availability,
                             booking_quantity,
                             duration)
VALUES (1, -- assuming doctor_id 1 exists
        1, -- assuming hospital_branch_id 1 exists
        'MONDAY', -- Day of the week
        '09:00:00', -- Start time
        '17:00:00', -- End time
        'AVAILABLE', -- Availability status
        10, -- Number of available bookings
        30 -- Duration of each appointment in minutes
       );

INSERT INTO offline_appointment_table (doctor_schedule_id,
                                       user_id,
                                       appointment_status)
VALUES (1, -- assuming doctor_schedule_id 1 exists
        1, -- assuming user_id 1 exists
        'PENDING' -- Initial appointment status
       );

INSERT INTO emergency_consultation (doctor_id,
                                    availability,
                                    fee)
VALUES (1, -- assuming doctor_id 1 exists in the doctor table
        'AVAILABLE', -- availability status, could also be 'UNAVAILABLE'
        500 -- fee for the consultation
       );


INSERT INTO specialist_consultation (doctor_id,
                                     status,
                                     availability,
                                     day_date,
                                     start_time,
                                     end_time,
                                     duration,
                                     fee)
VALUES (1, -- assuming doctor_id 1 exists in the doctor table
        'ACTIVE', -- status of the consultation, could be 'ACTIVE' or 'INACTIVE'
        'AVAILABLE', -- availability status, could be 'AVAILABLE' or 'UNAVAILABLE'
        'MONDAY', -- date of consultation
        '10:00:00', -- start time of consultation
        '12:00:00', -- end time of consultation
        120, -- duration in minutes (e.g., 2 hours = 120 minutes)
        1000 -- fee for the consultation
       );

INSERT INTO specialist_appointment_table (specialist_consultation_id,
                                          user_id,
                                          appointment_status)
VALUES (1, -- assuming specialist_consultation_id 1 exists in the specialist_consultation table
        2, -- assuming user_id 2 exists in the table_user table
        'PENDING' -- status can be 'PENDING', 'CONFIRMED', or 'CANCELLED'
       );

INSERT INTO emergency_appointment_table (emergency_consultation_id,
                                         user_id,
                                         appointment_status)
VALUES (1, -- assuming emergency_consultation_id 1 exists in the emergency_consultation table
        2, -- assuming user_id 2 exists in the table_user table
        'PENDING' -- status can be 'PENDING', 'CONFIRMED', or 'CANCELLED'
       );



