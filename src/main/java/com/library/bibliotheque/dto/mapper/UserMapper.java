package com.library.bibliotheque.dto.mapper;

import com.library.bibliotheque.dto.UserDto;
import com.library.bibliotheque.dto.UserRequestDto;
import com.library.bibliotheque.dto.UserSummaryDto;
import com.library.bibliotheque.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Entity → DTO complet
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRegistrationDate(user.getRegistrationDate());
        dto.setActive(user.getActive());

        return dto;
    }

    // Entity → DTO simplifié
    public UserSummaryDto toSummaryDTO(User user) {
        if (user == null) {
            return null;
        }

        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());

        return dto;
    }

    // RequestDTO → Entity (pour création)
    public User toEntity(UserRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setActive(true);  // Par défaut actif

        return user;
    }

    // RequestDTO → Entity existant (pour modification)
    public void updateEntity(UserRequestDto requestDTO, User user) {
        if (requestDTO == null || user == null) {
            return;
        }

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
    }
}