package com.library.bibliotheque.Service;
import com.library.bibliotheque.model.Book;
import com.library.bibliotheque.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {
    private BookRepository bookRepository;
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();

    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return Optional.empty();
    }

    @Override
    public Book updateBook(Long id, Book book) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);

    }
    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return BookRepository.findByTitleContainingIgnoreCase(title);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);

    }
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(Book::getAvailable)
                .toList();
    }
    @Override
    @Transactional(readOnly = true)
    public List<Book> getBooksByPublicationYear(Integer year) {
        return bookRepository.findByPublicationYear(year);
    }
    @Override
    public void markAsAvailable(Long id) {
        Book Book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("livre non trouve avec l'id: " + id));
        Book.setAvailable(true);
        bookRepository.save(Book);

        }

    @Override
    public void markAsUnavailable(Long id) {
        Book Book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("livre non trouve avec l'id: " + id));
        Book.setAvailable(false);
        bookRepository.save(Book);

    }
    @Override
    @Transactional(readOnly = true)
    public boolean isBookAvailable(Long id) {
        Book Book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("livre non trouve avec l'id: " + id));
        return Book.getAvailable();
    }






}
