package com.sportgearrental.app.controller;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.entity.Equipment;
import com.sportgearrental.app.entity.Category;
import com.sportgearrental.app.service.CustomerService;
import com.sportgearrental.app.service.EquipmentService;
import com.sportgearrental.app.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final EquipmentService equipmentService;
    private final CategoryService categoryService;
    private final CustomerService customerService;

    public AdminController(EquipmentService equipmentService, CategoryService categoryService, CustomerService customerService) {
        this.equipmentService = equipmentService;
        this.categoryService = categoryService;
        this.customerService = customerService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Equipment> equipmentsPage = equipmentService.findAllEquipments(pageable);
        Page<Category> categoriesPage = categoryService.findAllCategories(pageable);
        model.addAttribute("equipments", equipmentsPage.getContent());
        model.addAttribute("categories", categoriesPage.getContent());
        model.addAttribute("equipPage", equipmentsPage);
        model.addAttribute("catPage", categoriesPage);
        return "admin-dashboard";
    }

    @GetMapping("/equipment/new")
    public String showNewEquipmentForm(Model model) {
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("categories", categoryService.findAllCategories()); // Use non-paginated overload
        return "admin-new-equipment";
    }

    @PostMapping("/equipment/new")
    public String createEquipment(Equipment equipment) {
        equipmentService.createEquipment(equipment);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/equipment/{id}/edit")
    public String showEditEquipmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("equipment", equipmentService.findEquipmentById(id));
        model.addAttribute("categories", categoryService.findAllCategories()); // Use non-paginated
        return "admin-edit-equipment";
    }

    @PostMapping("/equipment/{id}/edit")
    public String updateEquipment(@PathVariable Long id, Equipment equipment) {
        equipmentService.updateEquipment(id, equipment);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/equipment/{id}/delete")
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/users")
    public String listUsers(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Page<Customer> usersPage = customerService.findAllCustomers(pageable);
        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("userPage", usersPage);
        return "admin-users"; // New template
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/promote")
    public String promoteToAdmin(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        customer.setRole(Customer.Role.ADMIN);
        customerService.updateCustomer(id, customer);
        return "redirect:/admin/users";
    }
}