package com.wg.dabms.a.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wg.dabms.model.Review;

public class ReviewDAO extends GenericDAO<Review> {

    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Review review = new Review();
        review.setUuid(resultSet.getString("uuid"));
        review.setRevieweeId(resultSet.getString("reviewee_id"));
        review.setReviewerId(resultSet.getString("reviewer_id"));
        review.setDescription(resultSet.getString("description"));
        review.setRating(resultSet.getBigDecimal("rating"));
        review.setReported(resultSet.getBoolean("is_reported"));
        review.setHidden(resultSet.getBoolean("is_hidden"));
        review.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        review.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return review;
    }
    public Review getReviewById(String uuid) {
        String query = "SELECT * FROM review WHERE uuid = ?";
        return executeGetQuery(query, uuid);
    }

    public List<Review> getAllReviews() {
        String query = "SELECT * FROM review";
        return executeGetAllQuery(query);
    }

    public boolean createReview(Review review) {
        String query = "INSERT INTO review (uuid, reviewee_id, reviewer_id, description, rating, is_reported, is_hidden, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(query, review.getUuid(), review.getRevieweeId(), review.getReviewerId(), review.getDescription(), review.getRating(), review.isReported(), review.isHidden(), review.getCreatedAt(), review.getUpdatedAt());
    }

    public boolean updateReview(Review review) {
        String query = "UPDATE review SET reviewee_id = ?, reviewer_id = ?, description = ?, rating = ?, is_reported = ?, is_hidden = ?, created_at = ?, updated_at = ? WHERE uuid = ?";
        return executeUpdate(query, review.getRevieweeId(), review.getReviewerId(), review.getDescription(), review.getRating(), review.isReported(), review.isHidden(), review.getCreatedAt(), review.getUpdatedAt(), review.getUuid());
    }

    public boolean deleteReview(String uuid) {
        String query = "DELETE FROM review WHERE uuid = ?";
        return executeDelete(query, uuid);
    }
}
