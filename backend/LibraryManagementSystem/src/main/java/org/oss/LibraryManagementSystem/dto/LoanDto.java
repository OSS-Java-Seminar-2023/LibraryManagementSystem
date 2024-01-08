package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class LoanDto {
    private UUID id;
    private UUID member;
    private UUID librarian;
    private Date dateIssued;
    private Date dateReturned;
    private UUID book;
}
