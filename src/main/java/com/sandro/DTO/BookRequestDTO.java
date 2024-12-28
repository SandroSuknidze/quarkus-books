package com.sandro.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class BookRequestDTO {

    @NotNull(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    public String title;

    @NotNull(message = "Author is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    public String author;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Minimum price should be 0")
    public BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Minimum price should be 0")
    public Integer quantity;
}
