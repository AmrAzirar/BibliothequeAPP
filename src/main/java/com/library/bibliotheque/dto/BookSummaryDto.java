package com.library.bibliotheque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSummaryDto {
    private Long id;
    private String title;
    private String authorName;
}