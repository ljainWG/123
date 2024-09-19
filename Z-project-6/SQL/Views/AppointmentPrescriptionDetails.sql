CREATE VIEW AppointmentPrescriptionDetails AS
SELECT
    a.uuid AS appointment_id,
    a.scheduled_date,
    a.slot_no,
    a.status AS appointment_status,
    a.booking_date,
    a.status_updation_date,
    p.uuid AS prescription_id,
    p.description AS prescription_description,
    p.created_at AS prescription_created_at,
    p.updated_at AS prescription_updated_at,
    d.uuid AS doctor_id,
    d.username AS doctor_username,
    d.email AS doctor_email,
    d.phone_no AS doctor_phone_no,
    d.department AS doctor_department,
    p2.uuid AS patient_id,
    p2.username AS patient_username,
    p2.email AS patient_email,
    p2.phone_no AS patient_phone_no
FROM
    Appointment a
JOIN
    User d ON a.doctor_id = d.uuid
JOIN
    User p2 ON a.patient_id = p2.uuid
LEFT JOIN
    Prescription p ON a.uuid = p.appointment_id;
