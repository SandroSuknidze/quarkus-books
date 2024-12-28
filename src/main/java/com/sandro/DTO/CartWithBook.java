package com.sandro.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartWithBook {
    public Long bookId;
    public String title;
    public String author;
    public BigDecimal price;
    public Integer stockQuantity;
    public Integer cartQuantity;
}
