package org.shopping.cart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.Arrays;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CheckoutTest {
    private final Checkout checkout = new Checkout();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void checkout_returnsZeroAmount_givenEmptyBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(emptyList());
        assertThat(actualAmount, is(value(0.00)));
    }

    @Test
    public void checkout_throwsInvalidItemInBasketException_givenBasketContainsOtherThanAppleOrOrange() throws Exception {
        exception.expect(InvalidItemInBasketException.class);
        exception.expectMessage("Invalid Item in the basket.");
        checkout.evaluateBasket(singletonList("Banana"));
    }

    @Test
    public void checkout_returns60p_givenOnlyOneAppleInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(singletonList("Apple"));
        assertThat(actualAmount, is(value(0.60)));
    }

    @Test
    public void checkout_returns25p_givenOnlyOneOrangeInShoppingCart() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(singletonList("Orange"));
        assertThat(actualAmount, is(value(0.25)));
    }

    @Test
    public void checkout_returns2PoundsAnd5Pence_givenThreeApplesAndOneOrangeInShoppingCart() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(Arrays.asList("Apple", "Apple", "Orange", "Apple"));
        assertThat(actualAmount, is(value(2.05)));
    }
    private BigDecimal value(double val) {
        return valueOf(val).setScale(2, ROUND_HALF_UP);
    }
}
