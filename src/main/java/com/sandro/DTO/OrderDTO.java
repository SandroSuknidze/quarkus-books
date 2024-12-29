package com.sandro.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    public Long bookId;
    public Integer quantity;
    public String username;
}
