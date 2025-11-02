package com.teamrecipefy.recipefy_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Rol ID'si (1: USER, 2: ADMIN gibi)

    // Rollerimizin adını tutacak (Örn: "ROLE_USER", "ROLE_ADMIN")
    // Spring Security 'ROLE_' önekini standart olarak bekler.
    @Column(length = 20, unique = true, nullable = false)
    private String name;

    // --- Constructor'lar ---
    
    public Role() {
        // JPA için boş constructor gerekir
    }

    public Role(String name) {
        this.name = name;
    }

    // --- Getter ve Setter'lar ---
    // (Eclipse'te otomatik oluştur: Sağ Tık -> Source -> Generate Getters and Setters...)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}