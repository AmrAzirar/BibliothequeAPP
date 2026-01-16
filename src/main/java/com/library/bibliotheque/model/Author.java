package com.library.bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name", nullable = false)
    public String firstname;
    @NotBlank(message = "Last name is mandatory")
    @Column(name="last_name", nullable = false)
    public String lastname;
    @Column(columnDefinition = "TEXT")
    public String Biography;
    @Column(nullable = false)
    private String nationality;
    //UN auteur peut avoir plusieurs livres
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Book> books=new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(null);
    }

}
