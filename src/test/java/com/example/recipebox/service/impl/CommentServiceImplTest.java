package com.example.recipebox.service.impl;

import com.example.recipebox.model.dto.CommentDTO;
import com.example.recipebox.model.entity.Comments;
import com.example.recipebox.model.entity.Recipe;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.repository.CommentRepository;
import com.example.recipebox.repository.RecipeRepository;
import com.example.recipebox.service.LoggedUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentServiceToTest;

    @Mock
    private CommentRepository mockCommentRepository;

    @Mock
    private RecipeRepository mockRecipeRepository;

    @Mock
    private LoggedUserService mockLoggedUserService;

    @BeforeEach
    public void setUp() {
        commentServiceToTest = new CommentServiceImpl(mockCommentRepository, mockRecipeRepository, mockLoggedUserService);
    }

    @Test
    public void testAddCommentShouldSaveCommentSuccessfully(){
        // Arrange
        CommentDTO mockCommentDTO = new CommentDTO();
        mockCommentDTO.setRecipeId(1L);
        mockCommentDTO.setContent("Great recipe!");

        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(1L);

        User mockUser = new User();
        mockUser.setId(2L);
        mockUser.setUsername("testUser");

        when(mockRecipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));
        when(mockLoggedUserService.getUser()).thenReturn(mockUser);

        // Act
        commentServiceToTest.addComment(mockCommentDTO);

        // Assert
        verify(mockCommentRepository, times(1)).save(any(Comments.class));

    }

    @Test
    public void testAddCommentShouldThrowExceptionWhenRecipeNotFound(){
        // Arrange
        CommentDTO mockCommentDTO = new CommentDTO();
        mockCommentDTO.setRecipeId(6L);
        mockCommentDTO.setContent("Nice recipe!");

        when(mockRecipeRepository.findById(6L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> commentServiceToTest.addComment(mockCommentDTO));
        assertEquals("Recipe not found", exception.getMessage());

        verify(mockCommentRepository, never()).save(any(Comments.class));
    }

    @Test
    void testGetCommentsByRecipeShouldReturnComments() {
        // Arrange
        Long recipeId = 1L;
        Recipe mockRecipe = new Recipe();
        mockRecipe.setId(recipeId);

        Comments comment1 = new Comments();
        comment1.setId(1L);
        comment1.setRecipe(mockRecipe);
        comment1.setContent("Tasty!");
        comment1.setTimestamp(LocalDateTime.now());

        Comments comment2 = new Comments();
        comment2.setId(2L);
        comment2.setRecipe(mockRecipe);
        comment2.setContent("Delicious!");
        comment2.setTimestamp(LocalDateTime.now().minusHours(1));

        List<Comments> mockComments = List.of(comment1, comment2);

        when(mockCommentRepository.findByRecipeIdOrderByTimestampDesc(recipeId)).thenReturn(mockComments);

        // Act
        List<Comments> result = commentServiceToTest.getCommentsByRecipe(recipeId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Tasty!", result.get(0).getContent());
        assertEquals("Delicious!", result.get(1).getContent());

        verify(mockCommentRepository, times(1)).findByRecipeIdOrderByTimestampDesc(recipeId);
    }

    @Test
    void testGetCommentsByRecipeShouldReturnEmptyListWhenNoComments() {
        // Arrange
        Long recipeId = 2L;
        when(mockCommentRepository.findByRecipeIdOrderByTimestampDesc(recipeId)).thenReturn(Collections.emptyList());

        // Act
        List<Comments> result = commentServiceToTest.getCommentsByRecipe(recipeId);

        // Assert
        assertTrue(result.isEmpty());

        verify(mockCommentRepository, times(1)).findByRecipeIdOrderByTimestampDesc(recipeId);
    }

}
