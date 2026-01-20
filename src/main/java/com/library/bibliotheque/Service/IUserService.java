package com.library.bibliotheque.service;

import com.library.bibliotheque.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    // CRUD basique
    User createUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();

    // Recherches
    Optional<User> getUserByEmail(String email);
    List<User> searchUsersByLastName(String lastName);
    List<User> getActiveUsers();
    List<User> getInactiveUsers();

    // Opérations métier
    void activateUser(Long id);
    void deactivateUser(Long id);
    boolean canUserBorrow(Long id);
    List<User> getUsersWithOverdueLoans();
    List<User> getUsersWithFines();
    double getUserTotalFines(Long id);

    // Validations
    boolean emailExists(String email);
}