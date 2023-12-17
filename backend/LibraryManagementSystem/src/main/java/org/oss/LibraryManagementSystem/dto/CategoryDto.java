package org.oss.LibraryManagementSystem.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    private UUID id;
    private String name;
}
