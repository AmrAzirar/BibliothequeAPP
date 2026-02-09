package com.library.bibliotheque.dto.mapper;

import com.library.bibliotheque.dto.BookDto;
import com.library.bibliotheque.dto.BookRequestDto;
import com.library.bibliotheque.dto.BookSummaryDto;
import com.library.bibliotheque.model.Author;
import com.library.bibliotheque.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    // Entity → DTO complet
    public BookDto toDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAvailable(book.getAvailable());

        // Mapper l'auteur en version simplifiée
        dto.setAuthor(authorMapper.toSummaryDto(book.getAuthor()));

        return dto;
    }

    // Entity → DTO simplifié
    public BookSummaryDto toSummaryDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookSummaryDto dto = new BookSummaryDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());

        // Nom complet de l'auteur
        if (book.getAuthor() != null) {
            String authorName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
            dto.setAuthorName(authorName);
        }

        return dto;
    }

    // RequestDTO → Entity (pour création)
    public Book toEntity(BookRequestDto requestDTO, Author author) {
        if (requestDTO == null) {
            return null;
        }

        Book book = new Book();
        book.setTitle(requestDTO.getTitle());
        book.setIsbn(requestDTO.getIsbn());
        book.setPublicationYear(requestDTO.getPublicationYear());
        book.setAvailable(true);  // Par défaut disponible
        book.setAuthor(author);

        return book;
    }

    // RequestDTO → Entity existant (pour modification)
    public void updateEntity(BookRequestDto requestDTO, Book book, Author author) {
        if (requestDTO == null || book == null) {
            return;
        }

        book.setTitle(requestDTO.getTitle());
        book.setIsbn(requestDTO.getIsbn());
        book.setPublicationYear(requestDTO.getPublicationYear());
        book.setAuthor(author);
    }
}