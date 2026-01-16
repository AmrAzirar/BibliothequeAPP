package com.library.bibliotheque.Controller;
import com.library.bibliotheque.model.Book;

import com.library.bibliotheque.Service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }
    @GetMapping
    public ResponseEntity<java.util.List<Book>> getAllBooks() {
        java.util.List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@org.springframework.web.bind.annotation.PathVariable Long id) {
        java.util.Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@org.springframework.web.bind.annotation.PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }



}
