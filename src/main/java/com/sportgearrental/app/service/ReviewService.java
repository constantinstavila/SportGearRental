package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(Long id, ReviewDTO reviewDTO);
    void deleteReview(Long id);
    ReviewDTO findReviewById(Long id);
    List<ReviewDTO> findReviewsByEquipment(Long equipmentId);
}