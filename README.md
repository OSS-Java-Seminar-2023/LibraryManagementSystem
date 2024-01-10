# Library Management System

## List Of Contents

- [Developers](#developers)
- [Introduction](#introduction)
- [Features](#features)
  - [User management](#user)
  - [Library management](#library)
  - [Email notifications](#email)
- [Database](#database)
- [Technologies](#technologies)

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
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/bootstrap/bootstrap-original-wordmark.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg" height="70" />
  <img src="https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original-wordmark.svg" height="70" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original-wordmark.svg" height="70"/>

  ![Flyway](https://img.shields.io/badge/flyway-flyway?color=red)
  </p>
