package com.library.bibliotheque.dto.mapper;

import com.library.bibliotheque.dto.AuthorDto;
import com.library.bibliotheque.dto.AuthorRequestDto;
import com.library.bibliotheque.dto.AuthorSummaryDto;
import com.library.bibliotheque.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }


    AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());


        return dto;
    }
    public AuthorSummaryDto toSummaryDto(Author author) {
        if (author == null) {
            return null;
        }

        AuthorSummaryDto dto = new AuthorSummaryDto();
        dto.setId(author.getId());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());

        return dto;
    }

    public Author toEntity(AuthorRequestDto requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        Author author = new Author();
        author.setFirstName(requestDTO.getFirstName());
        author.setLastName(requestDTO.getLastName());
        author.setBiography(requestDTO.getBiography());

        return author;

    }
    public void updateEntity(AuthorRequestDto requestDTO, Author author) {
        if (requestDTO == null || author == null) {
            return;
        }

        author.setFirstName(requestDTO.getFirstName());
        author.setLastName(requestDTO.getLastName());
        author.setNationality(requestDTO.getNationality());
        author.setBiography(requestDTO.getBiography());
    }

}

