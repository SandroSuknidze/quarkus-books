package com.sandro.repository;

import com.sandro.DTO.CartWithBook;
import com.sandro.model.Cart;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CartRepository implements PanacheRepository<Cart> {

    @Inject
    EntityManager em;

    @SuppressWarnings("unchecked")
    public List<CartWithBook> getCartItemsWithBooksNative() {
        String nativeQuery = """
            SELECT sb.id AS book_id,
                   sb.title,
                   sb.author,
                   sb.price,
                   sb.quantity AS stock_quantity,
                   COALESCE(sc.quantity, 0) AS cart_quantity
            FROM books sb
            LEFT JOIN carts sc ON sb.id = sc.book_id
            """;

        var query = em.createNativeQuery(nativeQuery);
        List<Object[]> results = query.getResultList();
        Log.debug(results);

        return results.stream()
                .map(record -> new CartWithBook(
                        ((Number) record[0]).longValue(),    // book_id
                        (String) record[1],                  // title
                        (String) record[2],                  // author
                        (BigDecimal) record[3],             // price
                        ((Number) record[4]).intValue(),     // stock_quantity
                        ((Number) record[5]).intValue()      // cart_quantity
                ))
                .collect(Collectors.toList());
    }
}
