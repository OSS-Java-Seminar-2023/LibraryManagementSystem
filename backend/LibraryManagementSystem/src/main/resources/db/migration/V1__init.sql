CREATE TABLE "user" (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    contact_number VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE role (
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

CREATE TABLE user_role (
    user_id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    role_id INT NOT NULL REFERENCES role(id) ON DELETE CASCADE
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

CREATE TABLE book_info_author (
    book_info_id UUID NOT NULL REFERENCES BookInfo(id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES Author(id) ON DELETE CASCADE
);

CREATE TABLE Category (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE book_info_category (
    book_info_id UUID NOT NULL REFERENCES BookInfo(id) ON DELETE CASCADE,
    category_id UUID NOT NULL REFERENCES Category(id) ON DELETE CASCADE
);

CREATE TABLE File (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL
--     data bytea NOT NULL
);

CREATE TABLE Book (
    id UUID NOT NULL PRIMARY KEY,
    publisher_name VARCHAR(255) NOT NULL,
    date_of_publishing DATE NOT NULL,
    ISBN VARCHAR(255) NOT NULL,
    book_status VARCHAR(255) NOT NULL,
    available BOOLEAN NOT NULL,
    book_info_id UUID NOT NULL REFERENCES BookInfo(id) ON DELETE CASCADE,
    image VARCHAR(255),
    file_id UUID REFERENCES File(id)
);

CREATE TABLE Loan (
    id UUID NOT NULL PRIMARY KEY,
    member_Id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    librarian_Id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    book_Id UUID NOT NULL REFERENCES Book(id) ON DELETE CASCADE,
    date_issued DATE,
    date_returned DATE
);