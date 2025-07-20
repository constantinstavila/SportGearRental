package com.sportgearrental.app.config;

import com.sportgearrental.app.entity.Rental;
import com.sportgearrental.app.repository.RentalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasks {

    private final RentalRepository rentalRepository;

    public ScheduledTasks(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateEquipmentAvailability() {
        List<Rental> endedRentals = rentalRepository.findAll().stream()
                .filter(r -> r.getEndDate().isBefore(LocalDate.now()) && "COMPLETED".equals(r.getStatus()))
                .toList();
        for (Rental rental : endedRentals) {
            rental.getEquipment().setAvailable(true);
            rentalRepository.save(rental);
        }
    }
}