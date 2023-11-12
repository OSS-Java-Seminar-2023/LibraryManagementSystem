CREATE TABLE User (
    id uuid NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    contactNumber VARCHAR(255) NOT NULL,
    dateOfBirth DATE NOT NULL,
    role VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Loan (
    id uuid NOT NULL,
    memberId uuid NOT NULL,
    librarianId uuid NOT NULL,
    bookId uuid NOT NULL,
    dateIssued DATE,
    dateReturned DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES User(id),
    FOREIGN KEY (librarianId) REFERENCES User(id),
    FOREIGN KEY (bookId) REFERENCES Book(id),
);

CREATE TABLE Book (
    id uuid NOT NULL,
    publisherName VARCHAR(255) NOT NULL,
    yearOfPublishing DATE NOT NULL,
    ISBN VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    bookInfoId uuid NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id)
);

CREATE TABLE BookInfo (
    id uuid NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE BookInfoAuthor (
    bookInfoId uuid NOT NULL,
    authorId uuid NOT NULL,
    PRIMARY KEY (bookInfoId, authorId),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id),
    FOREIGN KEY (authorId) REFERENCES Author(id)
);

CREATE TABLE Author (
    id uuid NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE BoookInfoCategory (
    bookInfoId uuid NOT NULL,
    categoryId uuid NOT NULL,
    PRIMARY KEY (bookInfoId, categoryId),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id),
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);

CREATE TABLE Category (
    id uuid NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);