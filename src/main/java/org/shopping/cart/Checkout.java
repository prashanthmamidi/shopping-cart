package org.shopping.cart;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

public class Checkout {
    private final Function<String, Predicate<String>> fruitFunc = fruitName -> fruit -> fruit.equals(fruitName);

    public BigDecimal evaluateBasket(List<String> fruitBasket) {
        validateBasket(fruitBasket);

        return buyOneGetOneOffer(getNumberOf("Apple", fruitBasket))
                .add(threeForTwoOffer(getNumberOf("Orange", fruitBasket)));
    }

    private long getNumberOf(String fruitName, List<String> fruitBasket) {
        return fruitBasket.stream().filter(fruitFunc.apply(fruitName)).count();
    }

    private BigDecimal buyOneGetOneOffer(long numberOfApples) {
        return valueOf(numberOfApples / 2 + numberOfApples % 2).multiply(basketWithPrice().get("Apple"));
    }

    private BigDecimal threeForTwoOffer(long numberOfOranges) {
        return valueOf((numberOfOranges / 3) * 2 + numberOfOranges % 3).multiply(basketWithPrice().get("Orange"));
    }

    private Map<String, BigDecimal> basketWithPrice() {
        Map<String, BigDecimal> basketPrice = new HashMap<>();
        basketPrice.put("Apple", value(0.60));
        basketPrice.put("Orange", value(0.25));
        return basketPrice;
    }

    private void validateBasket(List<String> fruitBasket) {
        if (!isValidFruit(fruitBasket)) {
            throw new InvalidItemInBasketException("Invalid Item in the basket.");
        }
    }

    private boolean isValidFruit(List<String> fruitBasket) {
        Set<String> validFruits = basketWithPrice().keySet();
        return validFruits.containsAll(fruitBasket);
    }

    private BigDecimal value(double price) {
        return valueOf(price).setScale(2, ROUND_HALF_UP);
    }
}
