package com.teamrecipefy.recipefy_api.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateCommentRequest {

    @NotBlank(message = "Yorum metni boş olamaz")
    private String text;

    // --- Getter ve Setter'lar ---

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}