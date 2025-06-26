package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByEquipmentId(Long equipmentId);
}