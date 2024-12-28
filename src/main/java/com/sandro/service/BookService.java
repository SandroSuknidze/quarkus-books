package com.sandro.service;

import com.sandro.DTO.BookRequestDTO;
import com.sandro.model.Book;
import com.sandro.repository.BookRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.listAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(BookRequestDTO bookRequestDTO) {
        Book book = new Book();
        book.setTitle(bookRequestDTO.title);
        book.setAuthor(bookRequestDTO.author);
        book.setPrice(bookRequestDTO.price);
        book.setQuantity(bookRequestDTO.quantity);

        bookRepository.persist(book);

        return book;
    }

    public Book editBook(Long id, @Valid BookRequestDTO bookRequestDTO) {
        Book existingBook = bookRepository.findById(id);
        if (existingBook == null) {
            throw new NotFoundException("Book with id " + id + " not found");
        }

        existingBook.setTitle(bookRequestDTO.title);
        existingBook.setAuthor(bookRequestDTO.author);
        existingBook.setPrice(bookRequestDTO.price);
        existingBook.setQuantity(bookRequestDTO.quantity);

        return existingBook;
    }


    public boolean deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }

    public List<Book> getFilteredBooks(String title, String author, Integer price, Integer quantity) {
        return bookRepository.findFilteredBooks(title, author, price, quantity);
    }
}
