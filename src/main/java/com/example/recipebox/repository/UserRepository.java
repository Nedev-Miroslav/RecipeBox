package com.example.recipebox.repository;

import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u JOIN u.favorites f WHERE f = :recipe")
    List<User> findAllByFavoritesContaining(@Param("recipe") Recipe recipe);

}
