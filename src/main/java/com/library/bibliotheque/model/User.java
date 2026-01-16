package com.library.bibliotheque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Size(min = 10, max = 15, message = "Le numéro de téléphone doit contenir entre 10 et 15 caractères")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(nullable = false)
    private Boolean active = true;

    // Relation OneToMany avec Loan (emprunts)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans = new ArrayList<>();

    // Méthode pour initialiser la date d'inscription automatiquement
    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
    }

    // Méthodes utilitaires pour gérer la relation avec Loan
    public void addLoan(Loan loan) {
        loans.add(loan);
        loan.setUser((User) this);
    }

    public void removeLoan(Loan loan) {
        loans.remove(loan);
        loan.setUser(null);
    }

    // Méthode pour obtenir les emprunts actifs
    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(loan -> loan.getStatus().equals("ACTIVE") || loan.getStatus().equals("OVERDUE"))
                .toList();
    }

    // Méthode pour vérifier si l'utilisateur peut emprunter
    public boolean canBorrow() {
        // Maximum 5 emprunts actifs
        return active && getActiveLoans().size() < 5;
    }

    // Méthode pour calculer le total des amendes
    public double getTotalFines() {
        return loans.stream()
                .mapToDouble(Loan::getFineAmount)
                .sum();
    }

}