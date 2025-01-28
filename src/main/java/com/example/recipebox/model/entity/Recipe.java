package com.example.recipebox.model.entity;

import com.example.recipebox.model.enums.CategoryType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToMany(mappedBy = "favorites")
    private List<User> likedByUsers;





}
