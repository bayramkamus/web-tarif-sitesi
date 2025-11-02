package com.teamrecipefy.recipefy_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Planda 1-5 arası olması isteniyor.
    // Bu kısıtlamayı DTO ve Servis katmanında kontrol edeceğiz.
    @Column(nullable = false)
    private int score; // 1, 2, 3, 4, veya 5

    // --- İLİŞKİLER ---

    // Puanı veren kullanıcı
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({ "password", "recipes", "favoriteRecipes", "roles" })
    private User user;

    // Puanın verildiği tarif
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private Recipe recipe;

    // --- Constructor'lar ---

    public Rating() {
        // JPA için boş constructor
    }

    // --- Getter ve Setter'lar ---
    // (Eclipse'te otomatik oluştur: Sağ Tık -> Source -> Generate Getters and Setters...)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}