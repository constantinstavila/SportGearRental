package com.sportgearrental.app.service;


import com.sportgearrental.app.entity.Review;

import java.util.List;

public interface ReviewService {

    Review  createReview (Review review);
    Review updateReview (Long id, Review review);
    void deleteReview (Long id);
    Review findReviewById (Long id);
    List<Review> findReviewsByEquipment (Long equipmentId);
}
