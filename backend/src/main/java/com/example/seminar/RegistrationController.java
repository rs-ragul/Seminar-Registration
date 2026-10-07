package com.example.seminar;

import java.util.Locale;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private final RegistrationRepository repository;

    public RegistrationController(RegistrationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Registration student) {
        // Check on the server too. JavaScript validation can be bypassed.
        if (student.getName() == null || student.getEmail() == null ||
            student.getPhone() == null || student.getCollege() == null ||
            student.getSeminar() == null) {
            return ResponseEntity.badRequest().body("Please fill in all fields.");
        }

        student.setName(student.getName().trim());
        student.setEmail(student.getEmail().trim().toLowerCase(Locale.ROOT));
        student.setPhone(student.getPhone().trim());
        student.setCollege(student.getCollege().trim());

        if (student.getName().length() < 2 || student.getName().length() > 80) {
            return ResponseEntity.badRequest().body("Name must contain 2 to 80 characters.");
        }

        if (!student.getEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$") ||
            student.getEmail().length() > 120) {
            return ResponseEntity.badRequest().body("Enter a valid email address.");
        }

        if (!student.getPhone().matches("[6-9][0-9]{9}")) {
            return ResponseEntity.badRequest().body("Enter a valid 10-digit Indian mobile number.");
        }

        if (student.getCollege().length() < 2 || student.getCollege().length() > 120) {
            return ResponseEntity.badRequest().body("College name must contain 2 to 120 characters.");
        }

        String seminar = student.getSeminar();
        if (!seminar.equals("Java Full Stack") && !seminar.equals("Web Development") &&
            !seminar.equals("Introduction to AI")) {
            return ResponseEntity.badRequest().body("Please select a valid seminar.");
        }

        if (repository.existsByEmailAndSeminar(student.getEmail(), seminar)) {
            return ResponseEntity.status(409).body("You already registered for this seminar using this email.");
        }

        try {
            // The database generates the registration number.
            Registration savedStudent = repository.saveAndFlush(student);
            return ResponseEntity.status(201).body(
                "Registration successful! Your registration number is " + savedStudent.getId() + ".");
        } catch (DataIntegrityViolationException exception) {
            // The unique constraint also protects against simultaneous duplicate requests.
            return ResponseEntity.status(409).body("This registration already exists. Please check your details.");
        }
    }
}
