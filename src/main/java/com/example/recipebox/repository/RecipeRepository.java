package com.example.recipebox.repository;

import com.example.recipebox.model.entity.Recipe;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameContainingIgnoreCase(String name);

    List<Recipe> findAllByAuthorId(long id);

    Page<Recipe> findAll(@NonNull Pageable pageable);
}
