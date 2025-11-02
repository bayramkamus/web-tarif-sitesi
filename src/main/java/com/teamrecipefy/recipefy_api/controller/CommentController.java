package com.teamrecipefy.recipefy_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamrecipefy.recipefy_api.dto.CreateCommentRequest;
import com.teamrecipefy.recipefy_api.model.Comment;
import com.teamrecipefy.recipefy_api.security.services.UserDetailsImpl;
import com.teamrecipefy.recipefy_api.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/recipes/{recipeId}/comments")
    public List<Comment> getComments(@PathVariable Long recipeId) {
        return commentService.getCommentsByRecipeId(recipeId);
    }

    @PostMapping("/recipes/{recipeId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long recipeId,
            @AuthenticationPrincipal UserDetailsImpl currentUser,
            @Valid @RequestBody CreateCommentRequest request) {
        Comment created = commentService.addCommentToRecipe(recipeId, currentUser.getId(), request);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        Comment comment = commentService.getCommentById(commentId);

        boolean isOwner = comment.getAuthor() != null && comment.getAuthor().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            return ResponseEntity.status(403).build();
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

