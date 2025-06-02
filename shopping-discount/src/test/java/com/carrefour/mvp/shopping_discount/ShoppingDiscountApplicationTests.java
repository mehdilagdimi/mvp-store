package com.carrefour.mvp.shopping_discount;

import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRestrictionEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.Restriction;
import com.carrefour.mvp.shopping_discount.domain.discount.RestrictionType;
import com.carrefour.mvp.shopping_discount.domain.discount.rulesengine.DiscountRulesEngine;
import com.carrefour.mvp.shopping_discount.domain.order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ProductEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class ShoppingDiscountApplicationTests {

	private DiscountEntity discount10;
	private OrderItemEntity orderItempPrice1000;
	private OrderItemEntity orderItempPrice50;
	private DiscountAggregate aggregate;
	private DiscountRestrictionEntity discountRestrictionPrice;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	void setUp() {
		BigDecimal price1000 = new BigDecimal("1000.00");
		BigDecimal price50 = new BigDecimal("50.00");
		ProductEntity product = mock(ProductEntity.class);
		ProductName productName = new ProductName("Demo");
		orderItempPrice1000 = new OrderItemEntity(product, price1000, 1, productName);
		orderItempPrice50 = new OrderItemEntity(product, price50, 1, productName);

		Restriction restriction = new Restriction("900");
		discountRestrictionPrice = new DiscountRestrictionEntity(RestrictionType.PRICE, discount10, restriction);
		discount10 =
				new DiscountEntity(
					new BigDecimal("10"),
					Set.of(discountRestrictionPrice)
				);
		aggregate = new DiscountAggregate(discount10);
	}

	@Test
	void discount_is_applied_when_not_restricted() {

		try (MockedStatic<DiscountRulesEngine> mocked = mockStatic(DiscountRulesEngine.class)) {
			mocked.when(() -> DiscountRulesEngine.applyRules(orderItempPrice1000, discountRestrictionPrice)).thenReturn(false);

			StepVerifier.create(aggregate.applyDiscount(Set.of(orderItempPrice1000)))
					.expectNext(true)
					.verifyComplete();
		}

		assertThat(orderItempPrice1000.getPrice()).isEqualByComparingTo("900.00");
		assertThat(orderItempPrice1000.getIsDiscounted()).isTrue();
	}


	@Test
	void discount_not_applied_when_restricted() {
		try (MockedStatic<DiscountRulesEngine> mocked = mockStatic(DiscountRulesEngine.class)) {
			mocked.when(() -> DiscountRulesEngine.applyRules(orderItempPrice50, discountRestrictionPrice)).thenReturn(true);

			StepVerifier.create(aggregate.applyDiscount(Set.of(orderItempPrice50)))
					.expectNext(false)
					.verifyComplete();
		}

		assertThat(orderItempPrice50.getIsDiscounted()).isFalse();
		assertThat(orderItempPrice50.getPrice()).isEqualByComparingTo("50.00");
	}

	@Test
	void discount_applied_to_only_unrestricted_items() {
		OrderItemEntity unrestricted = orderItempPrice1000;
		OrderItemEntity restricted = orderItempPrice50;

		try (MockedStatic<DiscountRulesEngine> mocked = mockStatic(DiscountRulesEngine.class)) {
			mocked.when(() -> DiscountRulesEngine.applyRules(unrestricted, discountRestrictionPrice)).thenReturn(false);
			mocked.when(() -> DiscountRulesEngine.applyRules(restricted, discountRestrictionPrice)).thenReturn(true);

			StepVerifier.create(aggregate.applyDiscount(Set.of(unrestricted, restricted)))
					.expectNext(true)
					.verifyComplete();
		}

		assertThat(unrestricted.getPrice()).isEqualByComparingTo("900.00");
		assertThat(restricted.getPrice()).isEqualByComparingTo("50.00");
	}
}
