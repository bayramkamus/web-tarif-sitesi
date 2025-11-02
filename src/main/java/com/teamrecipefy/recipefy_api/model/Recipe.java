package com.teamrecipefy.recipefy_api.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // Tarif Başlığı

    // @Lob ve columnDefinition="TEXT" uzun metinler (paragraflar) için kullanılır
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description; // Açıklama

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String ingredients; // Malzemeler (Metin olarak)

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String instructions; // Yapılışı (Metin olarak)

    private String imageUrl; // Tarif Fotoğrafı URL'i [cite: 36]

    // "Algoritmik Puanlama" [cite: 107] için ortalama puanı tutacağımız alan.
    // Varsayılan olarak 0.0
    @Column(nullable = false)
    private Double averageRating = 0.0;

    // --- İLİŞKİLER ---

    // "Tariflerim" [cite: 25, 68] özelliği için:
    // Bir tarifin bir sahibi (kullanıcı) vardır.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({ "password", "recipes", "favoriteRecipes", "roles" })
    private User author; // Tarife ekleyen kullanıcı (author)

    // "Tariflere yorum yapma" [cite: 26, 70] özelliği için:
    // Bir tarifin birden çok yorumu olabilir.
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments = new HashSet<>();

    // "Tariflere puan verme" [cite: 26, 72, 107] özelliği için:
    // Bir tarifin birden çok puanı olabilir.
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Rating> ratings = new HashSet<>();

    // "Tarif Defterim" (Favoriler) [cite: 27, 73] özelliği için:
    // User sınıfındaki 'favoriteRecipes' alanına karşılık gelir.
    @ManyToMany(mappedBy = "favoriteRecipes", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<User> favoritedByUsers = new HashSet<>();


    // --- Constructor'lar ---

    public Recipe() {
        // JPA için boş constructor
    }

    // --- Getter ve Setter'lar ---
    // (Eclipse'te otomatik oluştur: Sağ Tık -> Source -> Generate Getters and Setters... -> Select All)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<User> getFavoritedByUsers() {
        return favoritedByUsers;
    }

    public void setFavoritedByUsers(Set<User> favoritedByUsers) {
        this.favoritedByUsers = favoritedByUsers;
    }
}