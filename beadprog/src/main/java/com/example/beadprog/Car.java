package com.example.beadprog;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class Car {
    private Long id;

    @NotBlank
    private String gyarto;

    @NotNull
    private Integer vegsebesseg;

    @NotNull
    @Email
    private String tulajEmail;

    private LocalDateTime hozzaadva;

    public Car() {
        this.hozzaadva = LocalDateTime.now();
    }

    // Getterek
    public Long getId() {return id;}
    public String getGyarto() {return gyarto;}
    public Integer getVegsebesseg() {return vegsebesseg;}
    public String getTulajEmail() {return tulajEmail;}
    public LocalDateTime getHozzaadva() { return hozzaadva; }

    // Setterek
    public void setId(Long id) {this.id = id;}
    public void setGyarto(String gyarto) {this.gyarto = gyarto;}
    public void setVegsebesseg(Integer vegsebesseg) {this.vegsebesseg = vegsebesseg;}
    public void setTulajEmail(String tulajEmail) {this.tulajEmail = tulajEmail;}
}