package com.teamrecipefy.recipefy_api.service;

import com.teamrecipefy.recipefy_api.model.Rating;
import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.model.User;
import com.teamrecipefy.recipefy_api.repository.RatingRepository;
import com.teamrecipefy.recipefy_api.repository.RecipeRepository;
import com.teamrecipefy.recipefy_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;
    
    // Kullanıcı: Tarife Puan Verme [cite: 26, 72]
    @Transactional
    public Recipe rateRecipe(Long recipeId, Long userId, int score) {
        // Planda 1-5 arası isteniyor.
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Puan 1 ile 5 arasında olmalıdır.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        // Not: Burada "kullanıcı bu tarife daha önce puan vermiş mi?" diye
        // kontrol edip, vermişse eski puanı güncellemek daha iyi bir pratiktir.
        // Şimdilik basit tutuyoruz ve her puanı yeni bir kayıt olarak ekliyoruz.
        
        Rating newRating = new Rating();
        newRating.setScore(score);
        newRating.setUser(user);
        newRating.setRecipe(recipe);

        ratingRepository.save(newRating);

        // "Algoritmik Puanlama"  mantığı:
        // Yeni puan eklendikten sonra tarifin ortalama puanını GÜNCELLE.
        return updateRecipeAverageRating(recipeId);
    }

    // "Algoritmik Puanlama": Ortalama Hesaplama 
    @Transactional
    private Recipe updateRecipeAverageRating(Long recipeId) {
        // O tarife ait TÜM puanları veritabanından çek
        List<Rating> ratings = ratingRepository.findAllByRecipeId(recipeId);
        
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));

        if (ratings.isEmpty()) {
            recipe.setAverageRating(0.0);
        } else {
            // Aritmetik ortalama hesapla 
            double sum = 0;
            for (Rating r : ratings) {
                sum += r.getScore();
            }
            double average = sum / ratings.size();
            
            // Ortalamayı yuvarla (örn: 4.33)
            double roundedAverage = Math.round(average * 10.0) / 10.0;
            
            recipe.setAverageRating(roundedAverage);
        }

        // Güncellenmiş tarifi kaydet
        return recipeRepository.save(recipe);
    }
}