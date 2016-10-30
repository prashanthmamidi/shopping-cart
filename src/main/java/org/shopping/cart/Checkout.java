package org.shopping.cart;

import java.math.BigDecimal;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;

public class Checkout {
    public BigDecimal evaluateBasket(List<String> fruitBasket) {
        validateBasket(fruitBasket);
        return fruitBasket.stream()
                .map(basketWithPrice()::get)
                .reduce(value(0.00), BigDecimal::add);
    }

    private boolean isValidFruit(List<String> fruitBasket) {
        HashSet<String> validFruits = new HashSet<>(asList("Apple", "Orange"));
        return validFruits.containsAll(fruitBasket);
    }
    private Map<String, BigDecimal> basketWithPrice() {
        Map<String, BigDecimal> basketPrice = new HashMap<>();
        basketPrice.put("Apple", value(0.60));
        basketPrice.put("Orange", value(0.25));
        return basketPrice;
    }
    private BigDecimal value(double price) {
        return valueOf(price).setScale(2, ROUND_HALF_UP);
    }
    private void validateBasket(List<String> fruitBasket) {
        if (!isValidFruit(fruitBasket)) {
            throw new InvalidItemInBasketException("Invalid Item in the basket.");
        }
    }
}
