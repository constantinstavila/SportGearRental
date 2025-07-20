package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Customer;
import com.sportgearrental.app.repository.CustomerRepository;
import com.sportgearrental.app.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @Test
    void createCustomer_Success() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        customer.setPassword("plainPassword");
        customer.setFirstName("Test");
        customer.setLastName("User");

        when(customerRepository.findByEmail("test@example.com")).thenReturn(null);
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Customer saved = customerService.createCustomer(customer);

        // Assert
        assertNotNull(saved);
        assertEquals("encodedPassword", saved.getPassword());
        assertEquals(Customer.Role.USER, saved.getRole());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).saveAndFlush(captor.capture());
        assertEquals("encodedPassword", captor.getValue().getPassword());
    }

    @Test
    void createCustomer_EmailAlreadyInUse() {
        // Arrange
        Customer customer = new Customer();
        customer.setEmail("existing@example.com");
        customer.setPassword("password");

        when(customerRepository.findByEmail("existing@example.com")).thenReturn(new Customer());

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customer));
        assertEquals("Email already in use", ex.getMessage());
        verify(customerRepository, never()).saveAndFlush(any());
    }

    @Test
    void findCustomerByEmail_Success() {
        // Arrange
        Customer expected = new Customer();
        expected.setEmail("test@example.com");
        when(customerRepository.findByEmail("test@example.com")).thenReturn(expected);

        // Act
        Customer found = customerService.findCustomerByEmail("test@example.com");

        // Assert
        assertEquals(expected, found);
    }

    @Test
    void findCustomerByEmail_NotFound() {
        // Arrange
        when(customerRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act
        Customer found = customerService.findCustomerByEmail("nonexistent@example.com");

        // Assert
        assertNull(found);  // Service returns null if not found, as per code
    }
}