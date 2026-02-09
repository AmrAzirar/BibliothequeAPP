package com.library.bibliotheque.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private Boolean available;

    private AuthorSummaryDto author;
}
