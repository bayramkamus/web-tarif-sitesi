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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Lob ve columnDefinition="TEXT" uzun yorum metinleri için
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    // --- İLİŞKİLER ---

    // Yorumu yapan kullanıcı (Bir kullanıcı çok yorum yapabilir)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({ "password", "recipes", "favoriteRecipes", "roles", "hibernateLazyInitializer", "handler" })
    private User author;

    // Yorumun yapıldığı tarif (Bir tarifin çok yorumu olabilir)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonIgnore
    private Recipe recipe;
    
    // --- Constructor'lar ---

    public Comment() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}