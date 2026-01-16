package com.library.bibliotheque.Repository;



import com.library.bibliotheque.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //tu cherche un livre par son isbn
    Optional<Book> findByIsbn(String isbn);
    //tu verifie si un livre existe par son isbn
    boolean existsByIsbn(String isbn);

}
