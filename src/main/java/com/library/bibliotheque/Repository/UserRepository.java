package com.library.bibliotheque.Repository;

import com.library.bibliotheque.model.Loan;
import com.library.bibliotheque.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
interface UserRepository extends JpaRepository<Loan, Long> {
    //find user by email
    Optional<User> findByEmail(String email);
    //find users by last name containing ignore case
    List<User> findLastNameContainingIgnoreCase(String lastName);

    List<User> FindByActiveTrue(Boolean active);
    //find user by phone
    Optional<User> FindByphoneNumber(String phoneNumber);

    // Utilisateurs avec emprunts en retard
    @Query("SELECT DISTINCT u FROM User u JOIN u.loans l WHERE l.status = 'OVERDUE'")
    List<User> findUsersWithOverdueLoans();

    // Utilisateurs avec amendes
    @Query("SELECT u FROM User u JOIN u.loans l WHERE l.fineAmount > 0 GROUP BY u")
    List<User> findUsersWithFines();

    // Utilisateurs actifs avec moins de X emprunts
    @Query("SELECT u FROM User u WHERE u.active = true AND SIZE(u.loans) < :maxLoans")
    List<User> findActiveUsersWithLessThanLoans(int maxLoans);
}
