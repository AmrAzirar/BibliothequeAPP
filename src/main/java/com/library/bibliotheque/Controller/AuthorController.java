package com.library.bibliotheque.Controller;

import com.library.bibliotheque.model.Author;
import com.library.bibliotheque.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // CREATE
    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    // READ - Tous
    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // READ - Par ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH - Par nom
    @GetMapping("/search/lastname")
    public ResponseEntity<List<Author>> searchByLastName(@RequestParam String lastName) {
        List<Author> authors = authorService.searchAuthorsByLastName(lastName);
        return ResponseEntity.ok(authors);
    }



    // SEARCH - Par nom complet



    // STATISTICS - Auteurs avec minimum de livres
    @GetMapping("/with-books")
    public ResponseEntity<List<Author>> getAuthorsWithMinBooks(@RequestParam int minBooks) {
        List<Author> authors = authorService.getAuthorsWithMinimumBooks(minBooks);
        return ResponseEntity.ok(authors);
    }
}