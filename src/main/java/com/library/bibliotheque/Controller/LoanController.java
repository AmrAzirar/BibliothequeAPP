package com.library.bibliotheque.Controller;

import com.library.bibliotheque.model.Loan;
import com.library.bibliotheque.Service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // CREATE - Créer un emprunt
    @PostMapping
    public ResponseEntity<Loan> createLoan(
            @RequestParam Long userId,
            @RequestParam Long bookId,
            @RequestParam(defaultValue = "14") int loanDurationDays) {
        Loan createdLoan = loanService.createLoan(userId, bookId, loanDurationDays);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    // READ - Tous les emprunts
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // READ - Par ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH - Par utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUser(@PathVariable Long userId) {
        List<Loan> loans = loanService.getLoansByUserId(userId);
        return ResponseEntity.ok(loans);
    }

    // SEARCH - Par livre
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Loan>> getLoansByBook(@PathVariable Long bookId) {
        List<Loan> loans = loanService.getLoansByBookId(bookId);
        return ResponseEntity.ok(loans);
    }

    // SEARCH - Par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable String status) {
        List<Loan> loans = loanService.getLoansByStatus(status);
        return ResponseEntity.ok(loans);
    }

    // SEARCH - Emprunts en retard
    @GetMapping("/overdue")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        List<Loan> loans = loanService.getOverdueLoans();
        return ResponseEntity.ok(loans);
    }

    // SEARCH - Emprunts à rendre bientôt
    @GetMapping("/due-soon")
    public ResponseEntity<List<Loan>> getLoansDueSoon(@RequestParam(defaultValue = "7") int days) {
        List<Loan> loans = loanService.getLoansDueSoon(days);
        return ResponseEntity.ok(loans);
    }

    // OPERATIONS - Retourner un livre
    @PatchMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        Loan returnedLoan = loanService.returnBook(id);
        return ResponseEntity.ok(returnedLoan);
    }

    // OPERATIONS - Renouveler un emprunt
    @PatchMapping("/{id}/renew")
    public ResponseEntity<Void> renewLoan(
            @PathVariable Long id,
            @RequestParam(defaultValue = "14") int additionalDays) {
        loanService.renewLoan(id, additionalDays);
        return ResponseEntity.ok().build();
    }

    // OPERATIONS - Calculer l'amende
    @GetMapping("/{id}/fine")
    public ResponseEntity<Double> calculateFine(@PathVariable Long id) {
        double fine = loanService.calculateFine(id);
        return ResponseEntity.ok(fine);
    }

    // OPERATIONS - Mettre à jour les emprunts en retard
    @PostMapping("/update-overdue")
    public ResponseEntity<Void> updateOverdueLoans() {
        loanService.updateOverdueLoans();
        return ResponseEntity.ok().build();
    }

    // STATISTICS - Compter emprunts actifs d'un utilisateur
    @GetMapping("/user/{userId}/active-count")
    public ResponseEntity<Long> countActiveLoansForUser(@PathVariable Long userId) {
        long count = loanService.countActiveLoansForUser(userId);
        return ResponseEntity.ok(count);
    }

    // STATISTICS - Vérifier si livre est emprunté
    @GetMapping("/book/{bookId}/is-loaned")
    public ResponseEntity<Boolean> isBookCurrentlyLoaned(@PathVariable Long bookId) {
        boolean isLoaned = loanService.isBookCurrentlyLoaned(bookId);
        return ResponseEntity.ok(isLoaned);
    }

    // STATISTICS - Emprunts avec amendes
    @GetMapping("/with-fines")
    public ResponseEntity<List<Loan>> getLoansWithFines() {
        List<Loan> loans = loanService.getLoansWithFines();
        return ResponseEntity.ok(loans);
    }
}