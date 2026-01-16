package com.library.bibliotheque.Repository;

import com.library.bibliotheque.model.Book;
import com.library.bibliotheque.model.Loan;
import com.library.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    List<Loan> findByUser(Long userId);

    List<Loan> findByStatus(String status);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByUserIdAndStatus(Long userId, String status);

    List<Loan> findByBook(Book book);

    @Query("SELECT l FROM Loan l WHERE l.status = 'OVERDUE'")
    List<Loan> findOverdueLoans();

    // Emprunts à rendre bientôt (dans les N jours)
    @Query("SELECT l FROM Loan l WHERE l.dueDate BETWEEN :today AND :futureDate AND l.returnDate IS NULL")
    List<Loan> findLoansDueSoon(LocalDate today, LocalDate futureDate);

    // Emprunts rendus entre deux dates
    List<Loan> findByReturnDateBetween(LocalDate startDate, LocalDate endDate);

    // Compter les emprunts actifs d'un utilisateur
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.user.id = :userId AND l.status IN ('ACTIVE', 'OVERDUE')")
    long countActiveByUserId(Long userId);

    // Vérifier si un livre est actuellement emprunté
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Loan l WHERE l.book.id = :bookId AND l.status IN ('ACTIVE', 'OVERDUE')")
    boolean isBookCurrentlyLoaned(Long bookId);

    // Emprunts avec amendes
    @Query("SELECT l FROM Loan l WHERE l.fineAmount > 0")
    List<Loan> findLoansWithFines();

    // Total des amendes d'un utilisateur
    @Query("SELECT SUM(l.fineAmount) FROM Loan l WHERE l.user.id = :userId")
    Double getTotalFinesByUserId(Long userId);
}

