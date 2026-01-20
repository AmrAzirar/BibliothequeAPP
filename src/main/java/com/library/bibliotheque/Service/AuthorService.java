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
class AuthorService implements IAuthorService {
    private AuthorRepository authorRepository;
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author createAuthor(Author author) {
        if (authorRepository.existsByFirstnameContainingIgnoreCase(
                author.getFirstname()
        )) {
            throw new RuntimeException("Author already exists with name: " + author.getFirstname());
        }
        return authorRepository.save(author);

    }
    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        existingAuthor.setFirstname(author.getFirstname());
        existingAuthor.setLastname(author.getLastname());
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
        return authorRepository.findByLastnameContainingIgnoreCase(lastName);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Author> getAuthorsWithMinimumBooks(int minBooks) {
        return authorRepository.findAuthorsWithMinimumBooks(minBooks);

    }
    @Override
    @Transactional(readOnly = true)
    public boolean authorExists(String firstName, String lastName) {
        return authorRepository.existsByFirstnameContainingIgnoreCase(firstName) &&
                authorRepository.existsByFirstNameAndLastnameIgnoreCase(lastName);
    }


}
