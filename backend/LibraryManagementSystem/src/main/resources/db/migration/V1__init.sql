CREATE TABLE "user" (
    id UUID NOT NULL PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    contactNumber VARCHAR(255) NOT NULL,
    dateOfBirth DATE NOT NULL,
    role VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE BookInfo (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE Author (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE BookInfoAuthor (
    bookInfoId UUID NOT NULL PRIMARY KEY REFERENCES BookInfo(id),
    authorId UUID NOT NULL REFERENCES Author(id)
);

CREATE TABLE Category (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE BoookInfoCategory (
    bookInfoId UUID NOT NULL PRIMARY KEY REFERENCES BookInfo(id),
    categoryId UUID NOT NULL REFERENCES Category(id)
);

CREATE TABLE Book (
    id UUID NOT NULL PRIMARY KEY,
    publisherName VARCHAR(255) NOT NULL,
    yearOfPublishing DATE NOT NULL,
    ISBN VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    bookInfoId UUID NOT NULL REFERENCES BookInfo(id)
);

CREATE TABLE Loan (
    id UUID NOT NULL PRIMARY KEY,
    memberId UUID NOT NULL REFERENCES "user"(id),
    librarianId UUID NOT NULL REFERENCES "user"(id),
    bookId UUID NOT NULL REFERENCES Book(id),
    dateIssued DATE,
    dateReturned DATE
);