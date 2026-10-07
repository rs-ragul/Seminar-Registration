package com.example.seminar;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "seminar_registrations", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email", "seminar"})
})
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 10)
    private String phone;

    @Column(nullable = false, length = 120)
    private String college;

    @Column(nullable = false, length = 50)
    private String seminar;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getSeminar() {
        return seminar;
    }

    public void setSeminar(String seminar) {
        this.seminar = seminar;
    }
}
