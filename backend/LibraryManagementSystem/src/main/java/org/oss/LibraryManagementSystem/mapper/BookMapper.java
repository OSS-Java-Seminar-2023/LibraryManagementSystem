package org.oss.LibraryManagementSystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.oss.LibraryManagementSystem.dto.BookDto;
import org.oss.LibraryManagementSystem.models.Book;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "book.bookInfo.id", target = "bookInfo")
    @Mapping(source = "book.file.id", target = "fileId")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "bookInfo", target = "bookInfo.id")
    @Mapping(source = "fileId", target = "file.id")
    Book bookDtoToBook(BookDto bookDto);
}
