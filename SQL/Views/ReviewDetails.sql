CREATE VIEW ReviewDetails AS
SELECT
    r.uuid AS review_id,
    r.description AS review_description,
    r.rating AS review_rating,
    r.is_reported,
    r.is_hidden,
    r.created_at AS review_created_at,
    r.updated_at AS review_updated_at,
    r.reviewee_id,
    r.reviewer_id,
    reviewee.username AS reviewee_username,
    reviewee.email AS reviewee_email,
    reviewee.phone_no AS reviewee_phone_no,
    reviewee.department AS reviewee_department,
reviewee.avg_rating AS reviewee_avg_rating,
reviewee.no_of_review AS reviewee_no_of_review,
reviewee.experience AS reviewee_experience,
    reviewer.username AS reviewer_username,
    reviewer.email AS reviewer_email,
    reviewer.phone_no AS reviewer_phone_no
FROM
    Review r
JOIN
    User reviewee ON r.reviewee_id = reviewee.uuid
JOIN
    User reviewer ON r.reviewer_id = reviewer.uuid;
