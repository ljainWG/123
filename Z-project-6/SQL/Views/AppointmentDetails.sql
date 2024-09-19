CREATE VIEW AppointmentDetails AS
SELECT
    a.uuid AS appointment_id,
    a.scheduled_date,
    a.slot_no,
    a.status AS appointment_status,
    a.booking_date,
    a.status_updation_date,
    d.uuid AS doctor_id,
    d.username AS doctor_username,
    d.email AS doctor_email,
    d.phone_no AS doctor_phone_no,
    d.department AS doctor_department,
    p.uuid AS patient_id,
    p.username AS patient_username,
    p.email AS patient_email,
    p.phone_no AS patient_phone_no
   FROM
    Appointment a
JOIN
    User d ON a.doctor_id = d.uuid
JOIN
    User p ON a.patient_id = p.uuid;
