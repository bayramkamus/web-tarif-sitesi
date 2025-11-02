package com.teamrecipefy.recipefy_api.repository;

import java.util.List; // Bunu import et
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamrecipefy.recipefy_api.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    // Bir tarife ait tüm yorumları bul
    List<Comment> findAllByRecipeId(Long recipeId);
}