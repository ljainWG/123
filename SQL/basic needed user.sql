INSERT INTO User (uuid, username, salt, password, email, gender, role, department, phone_no, address, avg_rating, no_of_review, experience, dob, created_at, updated_at)
VALUES ('SYSTEM_USER_UUID', 'System', 'system_salt', 'system_password', 'system@gmail.com', 'OTHER', 'ADMIN', 'ADMINISTRATION', NULL, NULL, NULL, NULL, NULL, NULL, NOW(), NOW());
