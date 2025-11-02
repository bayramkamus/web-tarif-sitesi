package com.teamrecipefy.recipefy_api.repository;

import java.util.List; // Bunu import et
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamrecipefy.recipefy_api.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    // Bir tarife verilen tüm puanları bul
    List<Rating> findAllByRecipeId(Long recipeId);
}