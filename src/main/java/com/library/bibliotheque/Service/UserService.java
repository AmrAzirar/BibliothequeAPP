package com.library.bibliotheque.Service;

import com.library.bibliotheque.Repository.UserRepository;
import com.library.bibliotheque.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.library.bibliotheque.service.IUserService;
import org.springframework.transaction.annotation.Transactional;

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




}
