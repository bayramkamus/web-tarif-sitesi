package com.teamrecipefy.recipefy_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamrecipefy.recipefy_api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    // Spring Security için: "Verilen 'name' (isim) ile rolü bul" (Örn: "ROLE_USER")
    Optional<Role> findByName(String name);
}