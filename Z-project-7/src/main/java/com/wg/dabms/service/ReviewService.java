package com.wg.dabms.service;

import java.util.List;

import com.wg.dabms.dao.ReviewDAO;
import com.wg.dabms.model.Review;

public class ReviewService {

    private ReviewDAO reviewDAO = new ReviewDAO();
    
    public ReviewService() {
    }

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
