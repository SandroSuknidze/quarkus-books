package com.sandro.service;

import com.sandro.DTO.OrderDTO;
import com.sandro.model.Book;
import com.sandro.model.Order;
import com.sandro.repository.BookRepository;
import com.sandro.repository.CartRepository;
import com.sandro.repository.OrderRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class OrderService {

    @Inject
    OrderRepository orderRepository;

    @Inject
    BookRepository bookRepository;
    @Inject
    CartRepository cartRepository;

    public List<Order> getOrders() {
        return orderRepository.listAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Order item with ID " + id + " not found."));
    }

    public void placeOrder(List<OrderDTO> orderDTOS) {
        if (orderDTOS == null || orderDTOS.isEmpty()) {
            throw new BadRequestException("Order info required");
        }

        for (OrderDTO orderDTO : orderDTOS) {
            createOrder(orderDTO);
        }

    }

    private void createOrder(OrderDTO orderDTO) {
        Book book = bookRepository.findById(orderDTO.getBookId());
        if (book == null) {
            throw new BadRequestException("Book not found with ID: " + orderDTO.getBookId());
        }

        if (book.getQuantity() < orderDTO.getQuantity()) {
            throw new BadRequestException("Not enough stock for book ID: " + orderDTO.getBookId());
        }

        Order order = new Order();
        order.setBook(book);
        order.setQuantity(orderDTO.getQuantity());
        order.setUsername(orderDTO.getUsername());
        orderRepository.persist(order);

        cartRepository.deleteAll();
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.delete(order);
    }

    @Transactional
    public void acceptOrder(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        Book book = order.getBook();
        Log.debug(order);
        Log.debug(book);
        if (book.getQuantity() < order.getQuantity()) {
            throw new BadRequestException("Insufficient stock for book: " + book.getTitle());
        }
        book.setQuantity(book.getQuantity() - order.getQuantity());
        bookRepository.persist(book);

        orderRepository.delete(order);
    }


}
