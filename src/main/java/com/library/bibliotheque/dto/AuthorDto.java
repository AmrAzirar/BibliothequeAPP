package com.library.bibliotheque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorDto {
    private long id;
    private String firstName;
    private String lastName;
    private String biography;
    private String Biography;
}
