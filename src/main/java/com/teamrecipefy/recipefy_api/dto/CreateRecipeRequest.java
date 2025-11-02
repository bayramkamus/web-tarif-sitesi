package com.teamrecipefy.recipefy_api.dto;

// Validasyon için (pom.xml'de spring-boot-starter-validation olmalı)
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRecipeRequest {

    @NotBlank(message = "Başlık boş olamaz")
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank(message = "Açıklama boş olamaz")
    private String description;
    
    @NotBlank(message = "Malzemeler boş olamaz")
    private String ingredients; // Malzemeler (Metin olarak)

    @NotBlank(message = "Yapılışı boş olamaz")
    private String instructions; // Yapılışı (Metin olarak)

    private String imageUrl; // Opsiyonel olabilir

    // --- Getter ve Setter'lar ---

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
}