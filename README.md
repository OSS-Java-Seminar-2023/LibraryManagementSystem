# Library Management System

## List Of Contents

- [Screenshots](#screenshots)
- [Developers](#developers)
- [Introduction](#introduction)
- [Features](#features)
  - [User management](#user)
  - [Library management](#library)
  - [Email notifications](#email)
- [Database](#database)
- [Technologies](#technologies)

<h2 id="screenshots">
  Screenshots of Project 📸
</h2>

<h3>Login</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/4480746e-3a94-44dd-8739-45886e4d9d6a"/>
<br>

<h3>Signup</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/050e0dc8-e41b-4838-9207-753e5fb60fed"/>
<br>

<h3>Home</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/f5e13a20-f68d-4545-a6d9-99e83b2db99d"/>
<br>

<h3>Loans</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/006fd7dd-a435-413c-bf03-6abbd76e0814"/>
<br>

<h3>Start new Loan</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/3451fc4f-38e5-4507-bf5c-a45624a7a541"/>
<br>

<h3>Books</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/b4141c8d-089e-47fc-836d-87d29f58b147"/>
<br>

<h3>Add new Book</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/493fed22-ff65-4dc6-8008-09bcdc81aef8"/>
<br>

<h3>Book details</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/77176e35-8b2a-4daa-b86b-e2580c8ab441"/>
<br>

<h3>Book informations</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/308587cc-135b-4f7c-9cbf-8d628e9b12eb"/>
<br>

<h3>Add new Book information</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/0f15902f-e21a-4828-889f-3578eac3dc43"/>
<br>

<h3>Authors</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/cf91cd7b-3216-43ee-92ff-75e0eebf2b1d"/>
<br>

<h3>Add new Author</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/61c2c36e-f6f0-4193-8fd2-693fc1da7ad1"/>
<br>

<h3>Categories</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/865ebe50-3589-47a4-a045-879f53e1e16a"/>
<br>

<h3>Add new Category</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/4b35fa86-3ecd-4386-a18c-327ced2635cf"/>
<br>

<h3>Users</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/a46164af-e9d9-46de-a41b-c6605422418f"/>
<br>

<h3>User details</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/ecf5e221-4ed2-4fb7-83ce-fb4f67ce4352"/>
<br>

<h3>Add new User</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/79512b33-84a2-4152-a2a2-c9c49c4734c5"/>
<br>

<h3>404 Page</h3>
<img src="https://github.com/OSS-Java-Seminar-2023/LibraryManagementSystem/assets/45322112/9038eab4-a8d5-4d22-8be5-4fd7fd06641d"/>
<br>

<br>
<br>

<h2 id="developers">
Developers
</h2>

Petar Topić <br>
Martina Hrgovič

<h2 id="introduction">
Introduction
</h2>
Library management system is built using Spring boot framework. It allows users to easily view available books in our library and borrow or return them. It also allows for system admins and librarians to manage books in our system.
<br>

<h2 id="features">
Features
</h2>

<h2 id="user">
User management
</h2>
<ul>
  <li>Roles</li>
  <ul>
    <li>Admin</li>
    <li>Librarian</li>
    <li>Member</li>
  </ul>
  <li>Adding a new user</li>
  <li>Edit and update user data</li>
  <li>Delete user</li>
  <li>Authentication (login, logout)</li>
  <li>Show all users</li>
  <li>Show user details for specific user</li>
  <li>View account details (My details)</li>
</ul>

<h2 id="library">
Library management
</h2>
<h3>Authors</h3>
<ul>
    <li>Add new author</li>
    <li>Edit and update author</li>
    <li>Delete author</li>
    <li>View all authors</li>
</ul>

<h3>Categories</h3>
<ul>
  <li>Add new category</li>
  <li>Edit and update category</li>
  <li>Delete category</li>
  <li>View all categories</li>
  <li>View all books in certain category</li>
</ul>

<h3>Books</h3>
<ul>
  <li>Add new book</li>
  <li>Edit and update book</li>
  <li>Delete book</li>
  <li>View all books</li>
</ul>

<h3>Loans</h3>
<ul>
  <li>Start loan</li>
      <ul>
        <li>Max 3 books per member borrowed at the same time</li>
      </ul>
  <li>End loan</li>
  <li>View all loans</li>
  <li>View all loans of a specific book</li>
  <li>View all loans of member</li>
</ul>

<h2 id="email">
Email notifications
</h2>
<ul>
  <li>Send Welcome email when user creates an account</li>
  <li>Send Loan started email when library member borrows a book</li>
  <li>Send Loan ended email when library member returns a book</li>
</ul>

<h2 id="database">
Database
</h2>

<img src="https://github.com/Ptopic/Java-seminar/assets/45322112/8e90c85c-2a44-463e-be8f-c24e32505c34"/>

<h2 id="technologies">
Technologies
</h2>

  <p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original-wordmark.svg" height="70"/>  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original-wordmark.svg" height="70"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="70" />
  <img src="https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original-wordmark.svg" height="70"/>

  ![Flyway](https://img.shields.io/badge/flyway-flyway?color=red)
  </p>
