package com.example.recipebox.repository;

import com.example.recipebox.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByIdAndAuthor_Id(Long id, long id1);
}
