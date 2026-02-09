package com.library.bibliotheque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
}
