package com.sportgearrental.app.config;

import com.sportgearrental.app.entity.Category;
import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.service.CategoryService;
import com.sportgearrental.app.service.CustomerService;
import com.sportgearrental.app.service.EquipmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryService categoryService;
    private final EquipmentService equipmentService;
    private final CustomerService customerService;

    public DataInitializer(CategoryService categoryService, EquipmentService equipmentService, CustomerService customerService) {
        this.categoryService = categoryService;
        this.equipmentService = equipmentService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryService.findAllCategories().isEmpty()) {
            Category ski = new Category();
            ski.setName("Ski Equipment");
            ski.setDescription("Gear for skiing and snowboarding");
            categoryService.createCategory(ski);

            Category bike = new Category();
            bike.setName("Biking Gear");
            bike.setDescription("Bikes and accessories for cycling");
            categoryService.createCategory(bike);

            Category camping = new Category();
            camping.setName("Camping Tools");
            camping.setDescription("Tents, sleeping bags, etc.");
            categoryService.createCategory(camping);
        }


        if (equipmentService.findAllEquipments().isEmpty()) {
            Category skiCat = categoryService.findAllCategories().get(0); // Ski
            Equipment skis = new Equipment();
            skis.setName("Alpine Skis");
            skis.setDescription("High-quality skis for beginners");
            skis.setPricePerDay(new BigDecimal("50"));
            skis.setAvailable(true);
            skis.setEquipmentCondition("New");
            skis.setCategory(skiCat);
            equipmentService.createEquipment(skis);

            Equipment snowboard = new Equipment();
            snowboard.setName("Snowboard");
            snowboard.setDescription("Freestyle snowboard");
            snowboard.setPricePerDay(new BigDecimal("60"));
            snowboard.setAvailable(true);
            snowboard.setEquipmentCondition("Good");
            snowboard.setCategory(skiCat);
            equipmentService.createEquipment(snowboard);

            Category bikeCat = categoryService.findAllCategories().get(1); // Bike
            Equipment mountainBike = new Equipment();
            mountainBike.setName("Mountain Bike");
            mountainBike.setDescription("Off-road bike");
            mountainBike.setPricePerDay(new BigDecimal("40"));
            mountainBike.setAvailable(true);
            mountainBike.setEquipmentCondition("Excellent");
            mountainBike.setCategory(bikeCat);
            equipmentService.createEquipment(mountainBike);

        }


        if (customerService.findCustomerByEmail("admin@example.com") == null) {
            Customer admin = new Customer();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword("admin123"); // Va fi encoded în service
            admin.setRole(Customer.Role.ADMIN);
            customerService.createCustomer(admin);
            System.out.println("Admin created: email = admin@example.com, password = admin123");
        }
    }
}