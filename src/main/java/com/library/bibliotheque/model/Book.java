package com.library.bibliotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @NotNull(message = "Publication year is mandatory")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @NotBlank(message = "ISBN is mandatory")
    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(columnDefinition = "boolean default true")
    private Boolean available = true;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setBook(this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setBook(this);
    }




    }
