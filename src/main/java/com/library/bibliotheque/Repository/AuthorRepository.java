package com.library.bibliotheque.Repository;

import com.library.bibliotheque.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByLastnameContainingIgnoreCase(String lastname);

    List<Author> findByFirstnameContainingIgnoreCase(String firstname);

    Optional<Author> findByNationality(String nationality);

    @Query("SELECT a FROM Author a WHERE SIZE(a.books) >= :minBooks")
    List<Author> findAuthorsWithMinimumBooks(int minBooks);

    boolean existsByFirstnameAndLastname(String firstname, String lastname);
}

