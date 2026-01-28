package com.library.bibliotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name", nullable = false)
    public String firstName;
    @NotBlank(message = "Last name is mandatory")
    @Column(name="last_name", nullable = false)
    public String lastName;
    @Column(columnDefinition = "TEXT")
    public String Biography;
    @Column(nullable = false)
    private String nationality;
    //UN auteur peut avoir plusieurs livres
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<Book> books=new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this );
    }


    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(this);
    }

}
