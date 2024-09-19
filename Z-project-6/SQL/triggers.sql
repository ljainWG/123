show triggers;
DROP TRIGGER IF EXISTS after_appointment_booked;

DELIMITER //

CREATE TRIGGER after_appointment_booked
AFTER INSERT ON Appointment
FOR EACH ROW
BEGIN
    DECLARE doctor_name VARCHAR(255);
    DECLARE patient_name VARCHAR(255);
    DECLARE doctor_department VARCHAR(255); -- Changed ENUM to VARCHAR to match the table schema
    DECLARE system_generator_id VARCHAR(255) DEFAULT 'SYSTEM_USER_UUID'; -- Constant value

    -- Get the names and department of the doctor and patient
    SELECT username, department INTO doctor_name, doctor_department FROM User WHERE uuid = NEW.doctor_id;
    SELECT username INTO patient_name FROM User WHERE uuid = NEW.patient_id;

    -- Insert notifications for the doctor and patient
    INSERT INTO Notification (uuid, generator_id, receiver_id, title, description, generated_at)
    VALUES
    (UUID(), system_generator_id, NEW.patient_id, 'New Appointment Scheduled',
    CONCAT('A new appointment has been scheduled with Dr. ', doctor_name, ' from the ', doctor_department, ' department. Details: Scheduled Date: ', NEW.scheduled_date, ', Booking Date: ', NEW.booking_date, ', Slot: ', NEW.slot_no, '. Please check your scheduled appointments.'),NOW()),
    (UUID(), system_generator_id, NEW.doctor_id, 'New Appointment Scheduled',
    CONCAT('You have a new appointment scheduled with Mr./Ms./Mrs. ', patient_name, '. Details: Scheduled Date: ', NEW.scheduled_date, ', Booking Date: ', NEW.booking_date, ', Slot: ', NEW.slot_no, '. Please check your scheduled appointments.'),NOW());
END; //

DELIMITER ;