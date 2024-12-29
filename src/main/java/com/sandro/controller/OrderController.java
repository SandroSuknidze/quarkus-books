package com.sandro.controller;

import com.sandro.DTO.OrderDTO;
import com.sandro.model.Order;
import com.sandro.service.OrderService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@Path("/orders")
@Produces("application/json")
@Consumes("application/json")
public class OrderController {

    @Inject
    OrderService orderService;

    @GET
    public Response getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrder(@PathParam("id") Long id) {
        Order order = orderService.getOrder(id);
        return Response.ok(order).build();
    }

    @POST
    @Transactional
    public Response placeOrder(@RequestBody List<OrderDTO> orderDTOS) {
        orderService.placeOrder(orderDTOS);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteOrder(@PathParam("id") Long id) {
        try {
            orderService.deleteOrder(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order with ID " + id + " not found.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/accept/{id}")
    @Transactional
    public Response acceptOrder(@PathParam("id") Long id) {
        try {
            orderService.acceptOrder(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order with ID " + id + " not found.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage()).build();
        }
    }


}
