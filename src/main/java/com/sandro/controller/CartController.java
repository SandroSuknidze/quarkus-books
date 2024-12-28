package com.sandro.controller;

import com.sandro.DTO.CartDTO;
import com.sandro.DTO.CartWithBook;
import com.sandro.model.Cart;
import com.sandro.service.CartService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/carts")
@Produces("application/json")
@Consumes("application/json")
public class CartController {

    @Inject
    CartService cartService;


    @GET
    public Response getAllCarts() {
        List<Cart> cartItems = cartService.getAllCartItems();
        return Response.ok(cartItems).build();
    }

    @GET
    @Path("/{id}")
    public Response getCartById(@PathParam("id") Long id) {
        try {
            Cart cart = cartService.getCartById(id);
            return Response.ok(cart).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @POST
    @Transactional
    public Response createCart(CartDTO cartDTO) {
        try {
            cartService.createCart(cartDTO);
            return Response.status(Response.Status.CREATED).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCart(@PathParam("id") Long id, CartDTO cartDTO) {
        try {
            cartService.updateCart(id, cartDTO);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCart(@PathParam("id") Long id) {
        try {
            cartService.deleteCart(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/with-books")
    public Response getCartItemsWithBooks() {
        try {
            List<CartWithBook> cartWithBooks = cartService.getCartItemsWithBooks();
            return Response.ok(cartWithBooks).build();
        } catch (Exception e) {
            return Response.serverError().entity("An error occurred: " + e.getMessage()).build();
        }
    }

}
