INSERT INTO customerentity (id, "name", email, address)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'JohnDoe', 'john.doe@example.com', '123 Main St');
--    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'Jane Smith', 'jone.smith@example.com', '456 Elm St');

INSERT INTO productentity (id, "category", "name", price)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'SMARTPHONES', 'Samsung', 999.99),
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'APPLIANCES', 'Chair', 49.99);

INSERT INTO orderentity (id, status, customer_id, "shipping-address", discountPercentage)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'PENDING', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', '123 Main St', 10.00),
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'SHIPPED', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', '456 Elm St', 5.00);

INSERT INTO orderitementity (id, order_id, product_id, "name", price, quantity, discounted)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'Laptop', 999.99, 1, 0),
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'Chair', 49.99, 2, 1);

INSERT INTO discountentity (id, code, percentage)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'DISCOUNT10', 10.00),
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'DISCOUNT5', 5.00);

INSERT INTO discount_restriction (id, discount_id, restriction_type)
VALUES
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'PRICE'),
    ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'CATEGORY');
