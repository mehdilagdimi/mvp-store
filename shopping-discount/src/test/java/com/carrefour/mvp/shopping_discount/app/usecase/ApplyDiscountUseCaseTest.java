package com.carrefour.mvp.shopping_discount.app.usecase;

import com.carrefour.mvp.shopping_discount.domain.Order.OrderAggregate;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderId;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderItemEntity;
import com.carrefour.mvp.shopping_discount.domain.Order.OrderRepository;
import com.carrefour.mvp.shopping_discount.domain.customer.CustomerEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountAggregate;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRepository;
import com.carrefour.mvp.shopping_discount.domain.discount.DiscountRestrictionEntity;
import com.carrefour.mvp.shopping_discount.domain.discount.Restriction;
import com.carrefour.mvp.shopping_discount.domain.discount.RestrictionType;
import com.carrefour.mvp.shopping_discount.domain.discount.rulesengine.DiscountRulesEngine;
import com.carrefour.mvp.shopping_discount.domain.product.Category;
import com.carrefour.mvp.shopping_discount.domain.product.CategoryType;
import com.carrefour.mvp.shopping_discount.domain.product.ProductEntity;
import com.carrefour.mvp.shopping_discount.domain.product.ProductName;
import com.carrefour.mvp.shopping_discount.domain.product.ShippingAdress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashSet;
import java.util.SequencedSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyDiscountUseCaseTest {

    ApplyDiscountOnOrderUseCase applyDiscountOnOrderUseCase;

    @Mock
    DiscountEntity discountEntity;

    @Mock
    DiscountRestrictionEntity discountRestrictionEntity;

    @Mock
    OrderRepository orderRepository;

    @Mock
    DiscountRepository discountRepository;

    String discountCode;
    BigDecimal percentage ;


    OrderItemEntity orderItem1;
    OrderItemEntity orderItem2;
    BigDecimal price1;
    BigDecimal price2;
    OrderItemEntity discountedOrderItem1;
    OrderItemEntity discountedOrderItem2;
    SequencedSet<OrderItemEntity> items;

    ProductEntity product1;
    ProductEntity product2;

    OrderEntity orderEntity;
    DiscountAggregate discountAggregate;

    @BeforeEach
    void setup (){
        discountCode = "DSTCODE10%";
        percentage = BigDecimal.valueOf(0.1);

        ProductName productName1 = new ProductName("Prdt1");
        ProductName productName2 = new ProductName("Prdt2");
        price1 = BigDecimal.valueOf( 10000.99f ).setScale(2, RoundingMode.HALF_UP);
        price2 = BigDecimal.valueOf( 5000f ).setScale(2, RoundingMode.HALF_UP);
        Integer quantity1 = 3;
        Integer quantity2 = 2;

        product1  = new ProductEntity( new Category(CategoryType.APPLIANCES.toString()), price1 , productName1);
        product2  = new ProductEntity( new Category(CategoryType.SMARTPHONES.toString()), price2, productName2);

        orderItem1 = new OrderItemEntity(product1, price1, quantity1, productName1);
        orderItem2 = new OrderItemEntity(product2, price2, quantity2, productName2);

        discountedOrderItem1 =
                new OrderItemEntity(
                        mock(ProductEntity.class),
                        price1.subtract( orderItem1.getPrice().multiply( percentage ).setScale(2, RoundingMode.HALF_UP) ),
                        quantity1,
                        productName1);
        discountedOrderItem1.flagAsDiscounted();

        discountedOrderItem2 =
                new OrderItemEntity(
                        mock(ProductEntity.class),
                        price2.subtract( orderItem2.getPrice().multiply( percentage ).setScale(2, RoundingMode.HALF_UP) ),
                        quantity2,
                        productName2);
        discountedOrderItem2.flagAsDiscounted();

        items = new LinkedHashSet<>(Set.of(orderItem1, orderItem2));

        orderEntity = new OrderEntity(mock(CustomerEntity.class), mock(ShippingAdress.class), items);

        discountAggregate = new DiscountAggregate(discountEntity);

        SequencedSet<OrderItemEntity> orderItems = new LinkedHashSet<>();
    }

    @Test
    void shouldReturnProductsDiscounted() {
        DiscountAggregate discountAggregate = new DiscountAggregate(discountEntity);

        discountAggregate.apply(orderItem2, percentage);

        assertTrue(orderItem2.getIsDiscounted());
        assertEquals(BigDecimal.valueOf(4500f).setScale(2, RoundingMode.HALF_UP), orderItem2.getPrice());
    }

    @Test
    void shouldReturnProductNonDiscountedBasedOnRulesRestriction() {
        when(discountEntity.getDiscountRestrictions()).thenReturn(new LinkedHashSet<>(Set.of(discountRestrictionEntity)));

        try (MockedStatic<DiscountRulesEngine> mockedStatic = mockStatic(DiscountRulesEngine.class)){
            mockedStatic
                    .when(() -> DiscountRulesEngine.applyRules(orderItem1, discountRestrictionEntity))
                    .thenReturn(true);
            mockedStatic
                    .when(() -> DiscountRulesEngine.applyRules(orderItem2, discountRestrictionEntity))
                    .thenReturn(true);

            Boolean res = discountAggregate.applyDiscount(items);

            assertFalse(res);
            assertEquals(price1, orderItem1.getPrice());
            assertEquals(price2, orderItem2.getPrice());
            assertFalse(orderItem1.getIsDiscounted());
            assertFalse(orderItem2.getIsDiscounted());
        }
    }

     @Test
    void shouldReturnProductDiscountedBasedOnRulesRestriction() {
        DiscountRestrictionEntity restriction = mock(DiscountRestrictionEntity.class);

        when(discountEntity.getDiscountPercentage()).thenReturn(this.percentage);
        when(discountEntity.getDiscountRestrictions()).thenReturn(new LinkedHashSet<>(Set.of(restriction)));

        try (MockedStatic<DiscountRulesEngine> mockedStatic = mockStatic(DiscountRulesEngine.class)){
            mockedStatic
                    .when(() -> DiscountRulesEngine.applyRules(orderItem1, restriction))
                    .thenReturn(false);
            mockedStatic
                    .when(() -> DiscountRulesEngine.applyRules(orderItem2, restriction))
                    .thenReturn(true);

            Boolean res = discountAggregate.applyDiscount(items);

            assertTrue(res);
            assertTrue(orderItem1.getIsDiscounted());
            assertFalse(orderItem2.getIsDiscounted());
            assertEquals(discountedOrderItem1.getPrice(), orderItem1.getPrice());
            assertEquals(price2, orderItem2.getPrice());
        }
    }

    @Test
    void shouldReturnTrueOrFalseForProductsDiscountRestrictedBasedOnRulesRestriction() {
        DiscountEntity discount = mock(DiscountEntity.class);
        Restriction restriction1 = new Restriction("1000.00");
        Restriction restriction2 = new Restriction("SMARTPHONES");
        DiscountRestrictionEntity discountRestriction1 = new DiscountRestrictionEntity(RestrictionType.PRICE, discount, restriction1);
        DiscountRestrictionEntity discountRestriction2 = new DiscountRestrictionEntity(RestrictionType.CATEGORY, discount, restriction2);

        boolean res1 = DiscountRulesEngine.applyRules(orderItem1, discountRestriction1);
        boolean res2 = DiscountRulesEngine.applyRules(orderItem2, discountRestriction2);

        boolean res3 = DiscountRulesEngine.applyRules(orderItem2, discountRestriction1);
        boolean res4 = DiscountRulesEngine.applyRules(orderItem1, discountRestriction2);

        assertFalse(res1);
        assertTrue(res2);

        assertFalse(res3);
        assertFalse(res4);
    }


    @Test
    void shouldApplyDiscountOnOrderWithSomeItemsRestricted() {
        OrderId orderId = new OrderId();

        SequencedSet<DiscountRestrictionEntity> discountRestrictions = new LinkedHashSet<>();
        DiscountEntity discountEntity2 = new DiscountEntity(percentage, discountRestrictions);
        Restriction restriction1 = new Restriction("1000.00");
        Restriction restriction2 = new Restriction("SMARTPHONES");
        DiscountRestrictionEntity discountRestriction1 = new DiscountRestrictionEntity(RestrictionType.PRICE, discountEntity2, restriction1);
        DiscountRestrictionEntity discountRestriction2 = new DiscountRestrictionEntity(RestrictionType.CATEGORY, discountEntity2, restriction2);

        discountRestrictions.add(discountRestriction1);
        discountRestrictions.add(discountRestriction2);

        applyDiscountOnOrderUseCase = new ApplyDiscountOnOrderUseCase(orderRepository, discountRepository);

        BigDecimal discountedTotal =
                orderItem1
                        .getPrice()
                        .subtract( orderItem1.getPrice().multiply( percentage ) )
                        .multiply(BigDecimal.valueOf( orderItem1.getQuantity()) )
                        .add(orderItem2
                                .getPrice()
                                .multiply( BigDecimal.valueOf( orderItem2.getQuantity()) ))
                        .setScale(2, RoundingMode.HALF_UP);

        when(orderRepository.findById(orderId)).thenReturn(orderEntity);
        when(discountRepository.findByCode(discountCode)).thenReturn(discountEntity2);


        OrderAggregate orderAggregateRes = applyDiscountOnOrderUseCase.apply(orderId.id(), discountCode);

        BigDecimal totalRes =
                orderAggregateRes.getItems()
                        .stream()
                        .map(item -> item.price().multiply( BigDecimal.valueOf( item.quantity())) )
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertTrue(totalRes.compareTo(discountedTotal) == 0);
    }

}