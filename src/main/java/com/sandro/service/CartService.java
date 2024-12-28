package com.sandro.service;

import com.sandro.DTO.CartDTO;
import com.sandro.DTO.CartWithBook;
import com.sandro.model.Book;
import com.sandro.model.Cart;
import com.sandro.repository.BookRepository;
import com.sandro.repository.CartRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;


@ApplicationScoped
public class CartService {

    @Inject
    CartRepository cartRepository;

    @Inject
    BookRepository bookRepository;

    public List<Cart> getAllCartItems() {
        return cartRepository.listAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cart item with ID " + id + " not found."));
    }

    @Transactional
    public void createCart(CartDTO cartDTO) {

        Book book = bookRepository.findByIdOptional(cartDTO.getBookId())
                .orElseThrow(() -> new WebApplicationException("Book not found", 404));

        if (book.getQuantity() < cartDTO.getQuantity()) {
            throw new WebApplicationException("Insufficient stock", 400);
        }

        Cart cart = new Cart();
        cart.setBook(book);
        cart.setQuantity(cartDTO.getQuantity());
        cartRepository.persist(cart);

    }

    @Transactional
    public void updateCart(Long id, CartDTO cartDTO) {
        if (!id.equals(cartDTO.getId())) {
            throw new BadRequestException("Cart ID mismatch.");
        }

        Cart existingCart = cartRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Cart item with ID " + id + " not found."));

        Book book = bookRepository.findById(cartDTO.getBookId());
        if (book == null || book.getQuantity() < cartDTO.getQuantity()) {
            throw new BadRequestException("Invalid book or insufficient stock.");
        }

        existingCart.setQuantity(cartDTO.getQuantity());
    }

    @Transactional
    public void deleteCart(Long id) {
        boolean deleted = cartRepository.deleteById(id);
        if (!deleted) {
            throw new NotFoundException("Cart item with ID " + id + " not found.");
        }
    }

    public List<CartWithBook> getCartItemsWithBooks() {
        return cartRepository.getCartItemsWithBooksNative();
    }
}
