package com.sandro.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {


    public Long id;
    @NotNull
    @Min(value = 1)
    public Long bookId;
    @NotNull
    @Min(value = 0)
    public Integer quantity;
}
