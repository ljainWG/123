-- current working one--

show databases;

create database project;

show databases;

use project;

show tables;

CREATE TABLE `user` (
  `uuid` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') DEFAULT 'OTHER',
  `role` enum('DOCTOR','RECEPTIONIST','ADMIN','PATIENT') DEFAULT 'PATIENT',
  `department` enum('CARDIOLOGY','NEUROLOGY','ORTHOPEDICS','PEDIATRICS','GENERAL_MEDICINE','DERMATOLOGY','GYNECOLOGY','ADMINISTRATION','HOSPITALITY','NONE') DEFAULT 'NONE',
  `phone_no` varchar(15) DEFAULT NULL,
  `address` text,
  `avg_rating` decimal(3,2) DEFAULT NULL,
  `no_of_review` int DEFAULT NULL,
  `experience` decimal(10,2) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `appointment` (
  `uuid` varchar(255) NOT NULL,
  `doctor_id` varchar(255) ,
  `patient_id` varchar(255) ,
  `status` enum('SCHEDULED','COMPLETED','CANCELED','NO_SHOW_PATIENT','NO_SHOW_DOCTOR') DEFAULT 'SCHEDULED',
  `scheduled_date` date NOT NULL,
  `slot_no` enum('SLOT_8AM_830AM','SLOT_830AM_9AM','SLOT_9AM_930AM','SLOT_930AM_10AM','SLOT_10AM_1030AM','SLOT_1030AM_11AM','SLOT_11AM_1130AM','SLOT_1130AM_12PM','SLOT_12PM_1230PM','SLOT_1230PM_1PM','SLOT_2PM_230PM','SLOT_230PM_3PM','SLOT_3PM_330PM','SLOT_330PM_4PM','SLOT_4PM_430PM','SLOT_430PM_5PM','SLOT_5PM_530PM','SLOT_530PM_6PM','SLOT_6PM_630PM','SLOT_630PM_7PM') NOT NULL,
  `booking_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status_updation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `doctor_id` (`doctor_id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL,
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `notification` (
  `uuid` varchar(255) NOT NULL,
  `generator_id` varchar(255) ,
  `receiver_id` varchar(255),
  `title` varchar(255) NOT NULL,
  `description` text,
  `generated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `generator_id` (`generator_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`generator_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL,
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `prescription` (
  `uuid` varchar(255) NOT NULL,
  `appointment_id` varchar(255) ,
  `description` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `appointment_id` (`appointment_id`),
  CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `review` (
  `uuid` varchar(255) NOT NULL,
  `reviewee_id` varchar(255) ,
  `reviewer_id` varchar(255) ,
  `description` text,
  `rating` decimal(3,2) NOT NULL,
  `is_reported` tinyint(1) NOT NULL DEFAULT '0',
  `is_hidden` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  KEY `reviewee_id` (`reviewee_id`),
  KEY `reviewer_id` (`reviewer_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`reviewee_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`uuid`) ON UPDATE CASCADE
        ON DELETE SET NULL,
  CONSTRAINT `review_chk_1` CHECK (`rating` between 0 and 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

select * from user;
select * from appointment;
select * from notification;
select * from review;
select * from prescription;

delete from review;
delete from notification;
delete from prescription;
delete from appointment;
delete from user;
