package com.teamrecipefy.recipefy_api.service;

import com.teamrecipefy.recipefy_api.dto.CreateCommentRequest;
import com.teamrecipefy.recipefy_api.model.Comment;
import com.teamrecipefy.recipefy_api.model.Recipe;
import com.teamrecipefy.recipefy_api.model.User;
import com.teamrecipefy.recipefy_api.repository.CommentRepository;
import com.teamrecipefy.recipefy_api.repository.RecipeRepository;
import com.teamrecipefy.recipefy_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RecipeRepository recipeRepository; // Yorumun hangi tarife ait olduğunu bulmak için

    @Autowired
    private UserRepository userRepository; // Yorumu hangi kullanıcının yaptığını bulmak için

    // Herkese açık: Bir tarife ait tüm yorumları getir
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByRecipeId(Long recipeId) {
        return commentRepository.findAllByRecipeId(recipeId);
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
    }

    // Kullanıcı: Yeni yorum ekle 
    @Transactional
    public Comment addCommentToRecipe(Long recipeId, Long authorId, CreateCommentRequest request) {
        // Yorumu yapan kullanıcıyı bul
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + authorId));
        
        // Yorumun yapıldığı tarifi bul
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found with id: " + recipeId));

        Comment newComment = new Comment();
        newComment.setText(request.getText());
        newComment.setAuthor(author);
        newComment.setRecipe(recipe);

        return commentRepository.save(newComment);
    }
    
    // Admin: Yorum silme [cite: 79]
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}