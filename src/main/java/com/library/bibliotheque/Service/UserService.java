package com.library.bibliotheque.Service;

import com.library.bibliotheque.Repository.UserRepository;
import com.library.bibliotheque.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.library.bibliotheque.service.IUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà" + user.getEmail());
        }
        return userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id){
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    @Transactional(readOnly = true)
    public java.util.List<User> getAllUsers() {
        return userRepository.findAll();

    }
    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    @Override
    @Transactional(readOnly = true)
    public boolean canUserBorrow(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        return user.canBorrow();
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersWithOverdueLoans() {
        return userRepository.findUsersWithOverdueLoans();
    }
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersWithFines() {
        return userRepository.findUsersWithFines();
    }

    @Override
    @Transactional(readOnly = true)
    public double getUserTotalFines(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + id));
        return user.getTotalFines();
    }




}
