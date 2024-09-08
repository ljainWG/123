package com.wg.dabms.a.service;

import java.util.List;

import com.wg.dabms.a.dao.ReviewDAO;
import com.wg.dabms.model.Review;

public class ReviewService {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    public Review getReviewById(String uuid) {
        return reviewDAO.getReviewById(uuid);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    public boolean createReview(Review review) {
        return reviewDAO.createReview(review);
    }

    public boolean updateReview(Review review) {
        return reviewDAO.updateReview(review);
    }

    public boolean deleteReview(String uuid) {
        return reviewDAO.deleteReview(uuid);
    }
}
