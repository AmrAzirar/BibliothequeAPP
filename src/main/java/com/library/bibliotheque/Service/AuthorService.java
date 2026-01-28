package com.library.bibliotheque.Service;

import com.library.bibliotheque.Repository.AuthorRepository;
import com.library.bibliotheque.model.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService implements IAuthorService {
    private AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {
        if (authorRepository.existsByFirstNameContainingIgnoreCase(
                author.getFirstName()
        )) {
            throw new RuntimeException("Author already exists with name: " + author.getFirstName());
        }
        return authorRepository.save(author);

    }
    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());
        existingAuthor.setBiography(author.getBiography());
        existingAuthor.setNationality(author.getNationality());
        return authorRepository.save(existingAuthor);
    }
    @Override
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)){
            throw new RuntimeException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public java.util.List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    @Transactional(readOnly = true)
    public java.util.List<Author> searchAuthorsByLastName(String lastName) {
        return authorRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthorsWithMinimumBooks(int minBooks) {
        return authorRepository.findAuthorsWithMinimumBooks(minBooks);

    }




}
