package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class BookDto {
    private UUID id;
    private String publisherName;
    private Date dateOfPublishing;
    private String isbn;
    private String bookStatus;
    private UUID bookInfo;
    private String image;
    private UUID fileId;
    private boolean available;

    public boolean getAvailable() {
        return this.available;
    }
}
