package com.teamrecipefy.recipefy_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamrecipefy.recipefy_api.dto.CreateRecipeRequest;
import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.security.services.UserDetailsImpl;
import com.teamrecipefy.recipefy_api.service.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public Page<Recipe> getAllRecipes(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeService.getAllRecipes(pageable);
    }

    @GetMapping("/search")
    public Page<Recipe> searchRecipes(@RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeService.searchRecipesByTitle(title, pageable);
    }

    @GetMapping("/{id}")
    public Recipe getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/me")
    public Page<Recipe> getMyRecipes(@AuthenticationPrincipal UserDetailsImpl currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recipeService.getRecipesByAuthor(currentUser.getId(), pageable);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@AuthenticationPrincipal UserDetailsImpl currentUser,
            @Valid @RequestBody CreateRecipeRequest request) {
        Recipe created = recipeService.createRecipe(request, currentUser.getId());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser,
            @Valid @RequestBody CreateRecipeRequest request) {
        Recipe existing = recipeService.getRecipeById(id);
        if (!canModifyRecipe(existing, currentUser)) {
            return ResponseEntity.status(403).build();
        }

        Recipe updated = recipeService.updateRecipe(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Recipe existing = recipeService.getRecipeById(id);
        if (!canModifyRecipe(existing, currentUser)) {
            return ResponseEntity.status(403).build();
        }

        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    private boolean canModifyRecipe(Recipe recipe, UserDetailsImpl currentUser) {
        if (recipe.getAuthor() == null) {
            return false;
        }

        boolean isOwner = recipe.getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        return isOwner || isAdmin;
    }
}

