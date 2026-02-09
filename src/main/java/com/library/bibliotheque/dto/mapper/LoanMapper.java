package com.library.bibliotheque.dto.mapper;

import com.library.bibliotheque.dto.LoanDto;
import com.library.bibliotheque.model.Loan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanMapper {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    // Entity → DTO
    public LoanDto toDTO(Loan loan) {
        if (loan == null) {
            return null;
        }

        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());
        dto.setFineAmount(loan.getFineAmount());

        // Mapper les relations en version simplifiée
        dto.setUser(userMapper.toSummaryDTO(loan.getUser()));
        dto.setBook(bookMapper.toSummaryDTO(loan.getBook()));

        return dto;
    }
}
