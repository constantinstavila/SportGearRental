package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByCustomerId(Long customerId);




}
