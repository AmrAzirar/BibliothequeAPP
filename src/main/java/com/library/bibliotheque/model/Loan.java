package com.library.bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Data
@Table(name = "loans")
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long loanid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is mandatory")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull(message = "La date d'emprunt est obligatoire")
    @Column(name = "loan_date", nullable = false)
    private java.time.LocalDate loanDate;
    private LocalDate loandate;

    @NotNull(message = "La date de retour prévue est obligatoire")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(name =" fine_amount", nullable = false) //amende pour retard
    private double fineAmount = 0.0;
    //kan choufou wach teetel wala la
    public boolean isOverdue() {
        if (returnDate != null) {
            return false;
        }
        return LocalDate.now().isAfter(dueDate);


    }
    //kan hssbo chhal teetel
    public long getDaysOverdue() {
        if(!isOverdue()) {
            return 0;
        }
        return LocalDate.now().toEpochDay()-dueDate.toEpochDay();

    }
    //maj du status qbal ma yetsajel wala yethaddath
    @PrePersist
    @PreUpdate
    protected void updateStatus() {
        if (returnDate != null) {
            status = "RETURNED";
        } else if (isOverdue()) {
            status = "OVERDUE";
        } else {
            status = "ACTIVE";
        }
    }


    public void setBook(Book book) {
    }

    public Long getId() {
        return loanid;
    }
}
