package com.example.bai2.services;

import com.example.bai2.models.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
    public class BookService {


        private List<Book> books = new ArrayList<>(Arrays.asList(
                new Book(1, "Clean Code", "Robert C. Martin"),
                new Book(2, "Effective Java", "Joshua Bloch"),
                new Book(3, "Java: The Complete Reference", "Herbert Schildt"),
                new Book(4, "Head First Java", "Kathy Sierra"),
                new Book(5, "Spring in Action", "Craig Walls")
        ));
    public List<Book> getAllBooks() {
        return books;
    }

        public Book getBookById(int id) {
            return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
        }

        public void addBook(Book book) {
            books.add(book);
        }
        public void updateBook(int id, Book updatedBook) {
            books.stream()
                    .filter(book -> book.getId() == id)
                    .findFirst()
                    .ifPresent(book -> {
                        book.setTitle(updatedBook.getTitle());
                        book.setAuthor(updatedBook.getAuthor());
                    });
        }

        public void deleteBook(int id) {
            books.removeIf(book -> book.getId() == id);
        }
    }
