package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.dto.AuthorDto;
import org.oss.LibraryManagementSystem.mapper.AuthorMapper;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Page<Author> getAllAuthors(String searchQuery, int page, int size, String sortField, String sortDirection) {
        var direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        var order = new Sort.Order(direction, sortField);

        Pageable paging = PageRequest.of(page - 1, size, Sort.by(order));

        if(searchQuery == null) {
            return authorRepository.findAll(paging);
        } else {
            return authorRepository.findByFirstNameContainingOrLastNameContainingAllIgnoreCase(searchQuery, searchQuery, paging);
        }
    }

    @Override
    public Author createAuthor(AuthorDto authorDto) {
        Author author = authorMapper.authorDtoToAuthor(authorDto);
        return authorRepository.save(author);
    }

    @Override
    public String deleteAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

        authorRepository.delete(author);
        return "Author with id " + id + " has been deleted.";
    }

    @Override
    public Author editAuthor(AuthorDto authorDto) {
        Author author = authorRepository.findById(authorDto.getId()).orElseThrow(() -> new RuntimeException("Author not found"));

        Author editedAuthor = authorMapper.authorDtoToAuthor(authorDto);

        editedAuthor.setId(author.getId()); // preserve the id of original author

        return authorRepository.save(editedAuthor);
    }

    @Override
    public Long getAuthorCount() {
        return authorRepository.count();
    }
}
