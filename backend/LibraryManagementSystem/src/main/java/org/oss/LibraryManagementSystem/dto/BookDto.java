package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
public class BookDto {
    private UUID id;
    private String publisherName;
    private Timestamp yearOfPublishing;
    private String isbn;
    private String bookStatus;
    private UUID bookInfo;
}
