package org.oss.LibraryManagementSystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.oss.LibraryManagementSystem.dto.BookInfoDto;
import org.oss.LibraryManagementSystem.models.Author;
import org.oss.LibraryManagementSystem.models.BookInfo;
import org.oss.LibraryManagementSystem.models.Category;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Mapper(componentModel = "spring")
@Component
public interface BookInfoMapper {
    BookInfoMapper INSTANCE = Mappers.getMapper(BookInfoMapper.class);

    @Mapping(source = "authors", target = "authors", qualifiedByName = "authorToAuthorId")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoryToCategoryId")
    BookInfoDto bookInfoToBookInfoDto(BookInfo bookInfo);

    @Mapping(source = "authors", target = "authors", qualifiedByName = "authorIdToAuthor")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "categoryIdToCategory")
    BookInfo bookInfoDtoToBookInfo(BookInfoDto bookInfoDto);

    @Named("authorToAuthorId")
    default UUID authorToAuthorId(Author author) {
        return author.getId();
    }

    @Named("categoryToCategoryId")
    default UUID categoryToCategoryId(Category category) {
        return category.getId();
    }

    @Named("authorIdToAuthor")
    default Author authorIdToAuthor(UUID authorId) {
        Author author = new Author();
        author.setId(authorId);
        return author;
    }

    @Named("categoryIdToCategory")
    default Category categoryIdToCategory(UUID categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }
}
