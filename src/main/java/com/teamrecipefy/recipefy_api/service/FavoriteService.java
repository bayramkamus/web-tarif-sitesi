package com.teamrecipefy.recipefy_api.service;

import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.model.User;
import com.teamrecipefy.recipefy_api.repository.RecipeRepository;
import com.teamrecipefy.recipefy_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.Set;

@Service
public class FavoriteService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    // "Tarif Defterim": Kullanıcının favori tariflerini listele [cite: 93]
    @Transactional(readOnly = true) // Sadece okuma yapacağı için
    public Set<Recipe> getFavoriteRecipes(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        
        // User entity'mizdeki 'favoriteRecipes' set'ini döndürür.
        // @ManyToMany ve FetchType.LAZY kullandığımız için bu işlem 
        // veritabanından favorileri çekecektir.
        return user.getFavoriteRecipes();
    }

    // Kullanıcı: Favorilere Ekle [cite: 27, 73]
    @Transactional
    public void addRecipeToFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        // User entity'sindeki Set'e tarifi ekle
        user.getFavoriteRecipes().add(recipe);
        
        userRepository.save(user); // Kullanıcıyı kaydet
    }

    // Kullanıcı: Favorilerden Çıkar
    @Transactional
    public void removeRecipeFromFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        // User entity'sindeki Set'ten tarifi çıkar
        user.getFavoriteRecipes().remove(recipe);

        userRepository.save(user); // Kullanıcıyı kaydet
    }
}