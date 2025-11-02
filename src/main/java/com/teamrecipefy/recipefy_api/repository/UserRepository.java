package com.teamrecipefy.recipefy_api.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teamrecipefy.recipefy_api.model.User; // model paketindeki User sınıfını import ediyoruz

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Spring Data JPA bu metot isimlerinden ne yapması gerektiğini anlar:
    
    // Spring Security için: "Verilen 'username' (kullanıcı adı) ile kullanıcıyı bul"
    Optional<User> findByUsername(String username);

    // Kayıt (register) olurken kontrol için: "Bu 'username' veritabanında var mı?"
    Boolean existsByUsername(String username);

    // Kayıt (register) olurken kontrol için: "Bu 'email' veritabanında var mı?"
    Boolean existsByEmail(String email);
}