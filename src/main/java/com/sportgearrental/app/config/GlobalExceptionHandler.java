package com.sportgearrental.app.config;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFound(EntityNotFoundException ex, Model model) {
        logger.error("EntityNotFoundException: {}", ex.getMessage(), ex);
        model.addAttribute("error", "Resource not found: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateError(TemplateInputException ex, Model model) {
        logger.error("Template error: {}", ex.getMessage(), ex);
        model.addAttribute("error", "Template rendering error: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Unexpected error: {}", ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
        return "error";
    }
}