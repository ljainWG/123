CREATE VIEW NotificationDetails AS
SELECT
    n.uuid AS notification_id,
    n.title AS notification_title,
    n.description AS notification_description,
    n.generated_at AS notification_generated_at,
    n.updated_at AS notification_updated_at,
    n.generator_id,
    n.receiver_id,
    generator.username AS generator_username,
    generator.email AS generator_email,
    generator.phone_no AS generator_phone_no,
generator.department AS generator_department,

    receiver.username AS receiver_username,
    receiver.email AS receiver_email,
    receiver.phone_no AS receiver_phone_no
FROM
    Notification n
JOIN
    User generator ON n.generator_id = generator.uuid
JOIN
    User receiver ON n.receiver_id = receiver.uuid;
