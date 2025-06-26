package com.sportgearrental.app.repository;

import com.sportgearrental.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}