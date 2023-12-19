package org.oss.LibraryManagementSystem.dto;

import lombok.Data;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.Category;

import java.util.Set;
import java.util.UUID;

@Data
public class BookInfoDto {
    private UUID id;
    private String title;
    private String description;
    private Set<UUID> authors;
    private Set<UUID> categories;
}
