package com.library.bibliotheque.service;

import com.library.bibliotheque.Service.ILoanService;
import com.library.bibliotheque.model.Book;
import com.library.bibliotheque.model.Loan;
import com.library.bibliotheque.model.User;
import com.library.bibliotheque.Repository.BookRepository;
import com.library.bibliotheque.Repository.LoanRepository;
import com.library.bibliotheque.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanService implements ILoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private static final double FINE_PER_DAY = 5.0; // 5 MAD par jour de retard

    @Override
    public Loan createLoan(Long userId, Long bookId, int loanDurationDays) {
        // Récupérer l'utilisateur
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));

        // Vérifier si l'utilisateur peut emprunter
        if (!user.canBorrow()) {
            throw new RuntimeException("L'utilisateur ne peut pas emprunter (inactif ou limite atteinte)");
        }

        // Récupérer le livre
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livre non trouvé avec l'ID : " + bookId));

        // Vérifier si le livre est disponible
        if (!book.getAvailable()) {
            throw new RuntimeException("Le livre n'est pas disponible");
        }

        // Créer l'emprunt
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(loanDurationDays));
        loan.setStatus("ACTIVE");

        // Marquer le livre comme non disponible
        book.setAvailable(false);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    @Override
    public Loan updateLoan(Long id, Loan loan) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID : " + id));

        existingLoan.setDueDate(loan.getDueDate());
        existingLoan.setReturnDate(loan.getReturnDate());
        existingLoan.setStatus(loan.getStatus());
        existingLoan.setFineAmount(loan.getFineAmount());

        return loanRepository.save(existingLoan);
    }

    @Override
    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Emprunt non trouvé avec l'ID : " + id);
        }
        loanRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByBookId(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansByStatus(String status) {
        return loanRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansDueSoon(int days) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(days);
        return loanRepository.findLoansDueSoon(today, futureDate);
    }

    @Override
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID : " + loanId));

        // Vérifier si le livre n'est pas déjà rendu
        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Ce livre a déjà été rendu");
        }

        // Marquer comme rendu
        loan.setReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");

        // Calculer l'amende si en retard
        if (loan.isOverdue()) {
            double fine = calculateFine(loanId);
            loan.setFineAmount(fine);
        }

        // Marquer le livre comme disponible
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    @Override
    public void renewLoan(Long loanId, int additionalDays) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID : " + loanId));

        // Vérifier si le prêt est actif
        if (!loan.getStatus().equals("ACTIVE")) {
            throw new RuntimeException("Seuls les emprunts actifs peuvent être renouvelés");
        }

        // Prolonger la date de retour
        loan.setDueDate(loan.getDueDate().plusDays(additionalDays));
        loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public double calculateFine(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Emprunt non trouvé avec l'ID : " + loanId));

        if (!loan.isOverdue()) {
            return 0.0;
        }

        long daysOverdue = loan.getDaysOverdue();
        return daysOverdue * FINE_PER_DAY;
    }

    @Override
    public void updateOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findOverdueLoans();

        for (Loan loan : overdueLoans) {
            loan.setStatus("OVERDUE");
            double fine = calculateFine(loan.getId());
            loan.setFineAmount(fine);
            loanRepository.save(loan);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countActiveLoansForUser(Long userId) {
        return loanRepository.countActiveByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isBookCurrentlyLoaned(Long bookId) {
        return loanRepository.isBookCurrentlyLoaned(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getLoansWithFines() {
        return loanRepository.findLoansWithFines();
    }
}