package com.library.bibliotheque.Controller;

import com.library.bibliotheque.model.User;
import com.library.bibliotheque.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // READ - Tous
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // READ - Par ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH - Par email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }











    // CHECK - Peut emprunter ?
    @GetMapping("/{id}/can-borrow")
    public ResponseEntity<Boolean> canUserBorrow(@PathVariable Long id) {
        boolean canBorrow = userService.canUserBorrow(id);
        return ResponseEntity.ok(canBorrow);
    }

    // STATISTICS - Utilisateurs avec retards
    @GetMapping("/with-overdue")
    public ResponseEntity<List<User>> getUsersWithOverdue() {
        List<User> users = userService.getUsersWithOverdueLoans();
        return ResponseEntity.ok(users);
    }

    // STATISTICS - Utilisateurs avec amendes
    @GetMapping("/with-fines")
    public ResponseEntity<List<User>> getUsersWithFines() {
        List<User> users = userService.getUsersWithFines();
        return ResponseEntity.ok(users);
    }

    // STATISTICS - Total amendes d'un utilisateur
    @GetMapping("/{id}/total-fines")
    public ResponseEntity<Double> getUserTotalFines(@PathVariable Long id) {
        double totalFines = userService.getUserTotalFines(id);
        return ResponseEntity.ok(totalFines);
    }
}