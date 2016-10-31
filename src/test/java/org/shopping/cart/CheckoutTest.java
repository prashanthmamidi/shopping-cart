package org.shopping.cart;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
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
    public void checkout_returns25p_givenOnlyOneOrangeInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(singletonList("Orange"));
        assertThat(actualAmount, is(value(0.25)));
    }

    @Test
    public void checkout_returns2PoundsAnd5Pence_givenThreeApplesAndOneOrangeInShoppingCart() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Apple", "Apple", "Orange", "Apple"));
        assertThat(actualAmount, is(value(1.45)));
    }
    
    @Test
    public void checkoutWithBuyOneGetOneOffer_returns60p_givenOnlyTwoApplesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Apple", "Apple"));
        assertThat(actualAmount, is(value(0.60)));
    }

    @Test
    public void checkoutWithBuyOneGetOneOffer_returnsOnePoundTwentyPence_givenOnlyThreeApplesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Apple", "Apple", "Apple"));
        assertThat(actualAmount, is(value(1.20)));
    }

    @Test
    public void checkoutWithThreeForTwoOffer_returns25p_givenOnlyTwoOrangesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Orange", "Orange"));
        assertThat(actualAmount, is(value(0.50)));
    }

    @Test
    public void checkoutWithThreeForTwoOffer_returns50p_givenOnlyThreeOrangesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Orange", "Orange", "Orange"));
        assertThat(actualAmount, is(value(0.50)));
    }

    @Test
    public void checkoutWithMultipleOffers_returnsOnePoundTenPence_givenTwoApplesAndThreeOrangesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Apple", "Apple", "Orange", "Orange", "Orange"));
        assertThat(actualAmount, is(value(1.10)));
    }

    @Test
    public void checkoutWithOffersAndWithoutOffers_returns85p_givenTwoApplesAndOneOrangesInBasket() throws Exception {
        BigDecimal actualAmount = checkout.evaluateBasket(asList("Apple", "Apple", "Orange"));
        assertThat(actualAmount, is(value(0.85)));
    }

    private BigDecimal value(double val) {
        return valueOf(val).setScale(2, ROUND_HALF_UP);
    }
}
