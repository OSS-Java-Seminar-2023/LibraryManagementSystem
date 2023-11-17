CREATE TABLE User (
    id VARCHAR(255) NOT NULL,
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

CREATE TABLE BookInfo (
    id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Author (
    id VARCHAR(255) NOT NULL,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE BookInfoAuthor (
    bookInfoId VARCHAR(255) NOT NULL,
    authorId VARCHAR(255) NOT NULL,
    PRIMARY KEY (bookInfoId, authorId),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id),
    FOREIGN KEY (authorId) REFERENCES Author(id)
);

CREATE TABLE Category (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE BoookInfoCategory (
    bookInfoId VARCHAR(255) NOT NULL,
    categoryId VARCHAR(255) NOT NULL,
    PRIMARY KEY (bookInfoId, categoryId),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id),
    FOREIGN KEY (categoryId) REFERENCES Category(id)
);

CREATE TABLE Book (
    id VARCHAR(255) NOT NULL,
    publisherName VARCHAR(255) NOT NULL,
    yearOfPublishing DATE NOT NULL,
    ISBN VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    bookInfoId VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (bookInfoId) REFERENCES BookInfo(id)
);

CREATE TABLE Loan (
    id VARCHAR(255) NOT NULL,
    memberId VARCHAR(255) NOT NULL,
    librarianId VARCHAR(255) NOT NULL,
    bookId VARCHAR(255) NOT NULL,
    dateIssued DATE,
    dateReturned DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (memberId) REFERENCES User(id),
    FOREIGN KEY (librarianId) REFERENCES User(id),
    FOREIGN KEY (bookId) REFERENCES Book(id)
);