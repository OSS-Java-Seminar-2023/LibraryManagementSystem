package org.oss.LibraryManagementSystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.oss.LibraryManagementSystem.dto.LoanDto;
import org.oss.LibraryManagementSystem.models.Loan;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface LoanMapper {
    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    @Mapping(source = "loan.member.id", target = "memberId")
    @Mapping(source = "loan.librarian.id", target = "librarianId")
    @Mapping(source = "loan.book.id", target = "book")
    LoanDto loanToLoanDto(Loan loan);

    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "librarianId", target = "librarian.id")
    @Mapping(source = "book", target = "book.id")
    Loan loanDtoToLoan(LoanDto loanDto);
}
