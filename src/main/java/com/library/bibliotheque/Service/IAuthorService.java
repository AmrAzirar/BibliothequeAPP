package com.library.bibliotheque.Service;

import com.library.bibliotheque.model.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorService {
    // CRUD basique
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);
    Optional<Author> getAuthorById(Long id);
    List<Author> getAllAuthors();

    // Recherches
    List<Author> searchAuthorsByLastName(String lastName);


    // Opérations métier
    List<Author> getAuthorsWithMinimumBooks(int minBooks);

}
