package org.shopping.cart;

public class InvalidItemInBasketException extends RuntimeException {
    public InvalidItemInBasketException(String message) {
        super(message);
    }
}
