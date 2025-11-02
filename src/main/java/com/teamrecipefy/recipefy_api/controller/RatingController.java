package com.teamrecipefy.recipefy_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamrecipefy.recipefy_api.dto.RateRecipeRequest;
import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.security.services.UserDetailsImpl;
import com.teamrecipefy.recipefy_api.service.RatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recipes/{recipeId}/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Recipe> rateRecipe(@PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetailsImpl currentUser,
            @Valid @RequestBody RateRecipeRequest request) {
        Recipe updatedRecipe = ratingService.rateRecipe(recipeId, currentUser.getId(), request.getScore());
        return ResponseEntity.ok(updatedRecipe);
    }
}

