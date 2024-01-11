package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class LoanDto {
    private UUID id;
    private UUID memberId;
    private UUID librarianId;
    private Date dateIssued;
    private Date dateReturned;
    private UUID book;
}
