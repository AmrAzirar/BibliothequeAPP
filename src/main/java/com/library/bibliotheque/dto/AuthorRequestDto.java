package com.library.bibliotheque.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorRequestDto {
    @NotBlank(message="Le prénom est obligatoire")
    private String firstName;
    @NotBlank(message="Le nom est obligatoire")
    private String lastName;
    @NotBlank(message="La nationalité est obligatoire")
    private String nationality;
    private String biography;
}
