package com.library.bibliotheque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "L'ISBN est obligatoire")
    private String isbn;

    @NotNull(message = "L'année de publication est obligatoire")
    private Integer publicationYear;

    @NotNull(message = "L'auteur est obligatoire")
    private Long authorId;  // Juste l'ID de l'auteur
}