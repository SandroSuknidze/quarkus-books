package com.sandro.repository;

import com.sandro.model.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class BookRepository implements PanacheRepository<Book> {
    public List<Book> findFilteredBooks(String title, String author, Integer price, Integer quantity) {
        String query = "";

        Map<String, Object> params = new HashMap<>();

        if (title != null) {
            query += "LOWER(title) LIKE CONCAT('%', LOWER(:title), '%')";
            params.put("title", title);
        }
        if (author != null) {
            query += (query.isEmpty() ? "" : " AND ") + "LOWER(author) LIKE CONCAT('%', LOWER(:author), '%')";
            params.put("author", author);
        }
        if (price != null) {
            query += (query.isEmpty() ? "" : " AND ") + "price = :price";
            params.put("price", price);
        }
        if (quantity != null) {
            query += (query.isEmpty() ? "" : " AND ") + "quantity = :quantity";
            params.put("quantity", quantity);
        }

        return find(query, params).list();

    }
}
