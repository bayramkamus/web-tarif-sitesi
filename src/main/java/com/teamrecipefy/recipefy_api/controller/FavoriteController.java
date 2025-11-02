package com.teamrecipefy.recipefy_api.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.security.services.UserDetailsImpl;
import com.teamrecipefy.recipefy_api.service.FavoriteService;

@RestController
@RequestMapping("/api/users/me/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public Set<Recipe> getFavorites(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        return favoriteService.getFavoriteRecipes(currentUser.getId());
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<Void> addFavorite(@PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        favoriteService.addRecipeToFavorites(currentUser.getId(), recipeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        favoriteService.removeRecipeFromFavorites(currentUser.getId(), recipeId);
        return ResponseEntity.noContent().build();
    }
}

