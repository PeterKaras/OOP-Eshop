package sk.stuba.fei.uim.oop.assignment3.cart.web;

import lombok.Data;

@Data
public class CartEntryRequestBody {

    private Long productId;

    private Long amount;
}
