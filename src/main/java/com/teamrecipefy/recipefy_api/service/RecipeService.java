package com.teamrecipefy.recipefy_api.service;

import com.teamrecipefy.recipefy_api.dto.CreateRecipeRequest;
import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.model.User;
import com.teamrecipefy.recipefy_api.repository.RecipeRepository;
import com.teamrecipefy.recipefy_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Hata yönetimi için (Kendi özel exception'larınızı da oluşturabilirsiniz)
import jakarta.persistence.EntityNotFoundException;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository; // DB tarif sorguları

    @Autowired
    private UserRepository userRepository; // Kullanıcıyı bulmak için

    // Herkese açık: Tüm tarifleri sayfalı getir
    public Page<Recipe> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    // Herkese açık: Arama çubuğu için başlığa göre tarifleri sayfalı getir 
    public Page<Recipe> searchRecipesByTitle(String title, Pageable pageable) {
        return recipeRepository.findByTitleContainingIgnoreCase(title, pageable);
    }
    
    // Herkese açık: Tek bir tarifin detayını getir
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + id));
    }

    // "Tariflerim": Belli bir kullanıcının tariflerini sayfalı getir 
    public Page<Recipe> getRecipesByAuthor(Long userId, Pageable pageable) {
        return recipeRepository.findByAuthorId(userId, pageable);
    }

    // Kullanıcı: Yeni tarif ekle 
    @Transactional
    public Recipe createRecipe(CreateRecipeRequest request, Long authorId) {
        // Tarifi oluşturan kullanıcıyı ID ile bul
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + authorId));

        Recipe newRecipe = new Recipe();
        newRecipe.setTitle(request.getTitle());
        newRecipe.setDescription(request.getDescription());
        newRecipe.setIngredients(request.getIngredients());
        newRecipe.setInstructions(request.getInstructions());
        newRecipe.setImageUrl(request.getImageUrl());
        
        // Tarifin sahibini (ilişkiyi) ata
        newRecipe.setAuthor(author);
        
        return recipeRepository.save(newRecipe);
    }
    
    // Kullanıcı/Admin: Tarif güncelleme 
    // (Güvenlik: "Bu tarifi güncelleyebilir mi?" kontrolü Security/Controller katmanında yapılacak)
    @Transactional
    public Recipe updateRecipe(Long recipeId, CreateRecipeRequest request) {
        Recipe existingRecipe = getRecipeById(recipeId); // Önce tarifi bul

        // Alanları güncelle
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setIngredients(request.getIngredients());
        existingRecipe.setInstructions(request.getInstructions());
        existingRecipe.setImageUrl(request.getImageUrl());

        return recipeRepository.save(existingRecipe);
    }

    // Kullanıcı/Admin: Tarif silme 
    // (Güvenlik: "Bu tarifi silebilir mi?" kontrolü Security/Controller katmanında yapılacak)
    @Transactional
    public void deleteRecipe(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new EntityNotFoundException("Recipe not found with id: " + recipeId);
        }
        recipeRepository.deleteById(recipeId);
    }
}