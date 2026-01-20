package com.library.bibliotheque.Service;

import com.library.bibliotheque.model.Loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ILoanService {

    // CRUD basique
    Loan createLoan(Long userId, Long bookId, int loanDurationDays);
    Loan updateLoan(Long id, Loan loan);
    void deleteLoan(Long id);
    Optional<Loan> getLoanById(Long id);
    List<Loan> getAllLoans();

    // Recherches
    List<Loan> getLoansByUserId(Long userId);
    List<Loan> getLoansByBookId(Long bookId);
    List<Loan> getLoansByStatus(String status);
    List<Loan> getOverdueLoans();
    List<Loan> getLoansDueSoon(int days);

    // Opérations métier
    Loan returnBook(Long loanId);
    void renewLoan(Long loanId, int additionalDays);
    double calculateFine(Long loanId);
    void updateOverdueLoans();

    // Statistiques
    long countActiveLoansForUser(Long userId);
    boolean isBookCurrentlyLoaned(Long bookId);
    List<Loan> getLoansWithFines();
}