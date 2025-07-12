package com.sportgearrental.app.service.impl;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.entity.Review;
import com.sportgearrental.app.repository.CustomerRepository;
import com.sportgearrental.app.repository.EquipmentRepository;
import com.sportgearrental.app.repository.ReviewRepository;
import com.sportgearrental.app.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final EquipmentRepository equipmentRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CustomerRepository customerRepository,
                             EquipmentRepository equipmentRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Review createReview(Review review) {
        Customer customer = customerRepository.findById(review.getCustomer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + review.getCustomer().getId()));
        Equipment equipment = equipmentRepository.findById(review.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + review.getEquipment().getId()));
        review.setCustomer(customer);
        review.setEquipment(equipment);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, Review review) {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
        Customer customer = customerRepository.findById(review.getCustomer().getId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + review.getCustomer().getId()));
        Equipment equipment = equipmentRepository.findById(review.getEquipment().getId())
                .orElseThrow(() -> new EntityNotFoundException("Equipment not found with id: " + review.getEquipment().getId()));
        existing.setRating(review.getRating());
        existing.setComment(review.getComment());
        existing.setReviewDate(review.getReviewDate());
        existing.setCustomer(customer);
        existing.setEquipment(equipment);
        return reviewRepository.save(existing);
    }

    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new EntityNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public Review findReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));
    }

    @Override
    public List<Review> findReviewsByEquipment(Long equipmentId) {
        return reviewRepository.findByEquipmentId(equipmentId);
    }
}