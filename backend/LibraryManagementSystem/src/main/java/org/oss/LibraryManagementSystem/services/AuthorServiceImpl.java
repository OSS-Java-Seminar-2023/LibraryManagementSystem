package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.dto.AuthorDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author createAuthor(AuthorDto authorDto) {
        Author author = new Author();

        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());
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

        author.setId(authorDto.getId());
        author.setFirstName(authorDto.getFirstName());
        author.setLastName(authorDto.getLastName());

        return authorRepository.save(author);
    }

    @Override
    public Long getAuthorCount() {
        return authorRepository.count();
    }
}
