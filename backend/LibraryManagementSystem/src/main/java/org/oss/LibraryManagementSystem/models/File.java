package org.oss.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    private String type;

    @Column(name = "data", columnDefinition="bytea")
    private byte[] data;

    public File(String name, String type, byte[]data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
