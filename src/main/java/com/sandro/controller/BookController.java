package com.sandro.controller;

import com.sandro.DTO.BookRequestDTO;
import com.sandro.model.Book;
import com.sandro.service.BookService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {

    @Inject
    BookService bookService;

    @GET
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") Long id) {
        return bookService.getBookById(id);
    }

    @POST
    @Transactional
    public Response createBook(@Valid BookRequestDTO bookRequestDTO) {
        Book createdBook = bookService.createBook(bookRequestDTO);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Book editBook(@PathParam("id") Long id, @Valid BookRequestDTO bookRequestDTO) {
        return bookService.editBook(id, bookRequestDTO);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteBook(@PathParam("id") Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/filtered")
    public List<Book> getFilteredBooks(
            @QueryParam("title") String title,
            @QueryParam("author") String author,
            @QueryParam("price") Integer price,
            @QueryParam("quantity") Integer quantity
    ) {
        return bookService.getFilteredBooks(title, author, price,quantity);
    }



}
