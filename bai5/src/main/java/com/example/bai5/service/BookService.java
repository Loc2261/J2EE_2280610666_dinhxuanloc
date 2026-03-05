package com.example.bai5.service;

import com.example.bai5.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    private List<Book> books = new ArrayList<>();
    private Long nextId = 1L;

    public BookService() {
        addBook(new Book(null, "Lập trình Java nâng cao", "Nguyễn Văn A"));
        addBook(new Book(null, "Học Spring Boot trong 24h", "Trần Thị B"));
        addBook(new Book(null, "Cấu trúc dữ liệu và Giải thuật", "Lê Văn C"));
        addBook(new Book(null, "Thiết kế Web với Thymeleaf", "Phạm Minh D"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void addBook(Book book) {
        book.setId(nextId++);
        books.add(book);
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public void updateBook(Book updatedBook) {
        books.stream()
                .filter(book -> book.getId().equals(updatedBook.getId()))
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}