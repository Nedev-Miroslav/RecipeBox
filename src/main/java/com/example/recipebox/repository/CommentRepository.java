package com.example.recipebox.repository;

import com.example.recipebox.model.entity.Comments;
import com.example.recipebox.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comments, Long> {
    @Query("SELECT c FROM Comments c JOIN FETCH c.user WHERE c.recipe.id = :recipeId ORDER BY c.timestamp DESC")
    List<Comments> findByRecipeIdOrderByTimestampDesc(@Param("recipeId") Long recipeId);

    List<Comments> findByRecipeId(Long recipeId);
    void deleteByRecipe(Recipe recipe);

}
