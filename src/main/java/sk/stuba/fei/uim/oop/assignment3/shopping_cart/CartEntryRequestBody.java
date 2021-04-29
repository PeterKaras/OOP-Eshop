package sk.stuba.fei.uim.oop.assignment3.shopping_cart;

import lombok.Data;

@Data
public class CartEntryRequestBody {

    private Long productId;

    private Long amount;
}
