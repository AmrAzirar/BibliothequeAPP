package com.library.bibliotheque.Service;
import com.library.bibliotheque.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


public interface IBookService {
    Book saveBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    Optional<Book> getBookByIsbn(String isbn);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);


    Optional<Book> findByIsbn(String isbn);
}
