package com.teamrecipefy.recipefy_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Bu ikisini import et
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamrecipefy.recipefy_api.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    // Ana sayfada tarifleri sayfalı göstermek için
    Page<Recipe> findAll(Pageable pageable);
    
    // "Tariflerim" sayfası için: Belirli bir kullanıcının tariflerini sayfalı getir. 
    Page<Recipe> findByAuthorId(Long userId, Pageable pageable);
    
    // "Arama" özelliği için: Başlığında aranan kelime geçen tarifleri sayfalı getir. 
    Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}