INSERT INTO customerentity (id, "name", email, address) VALUES ('b670c869-2b83-4455-b6a2-9ec21b002e3a', 'JohnDoe', 'john.doe@example.com', '123 Main St');
INSERT INTO customerentity (id, "name", email, address) VALUES ('b670c869-2b83-4455-b6a2-9ec21b002e3b', 'Jane Smith', 'jone.smith@example.com', '456 Elm St');
INSERT INTO productentity (id, "category", "name", price) VALUES ('b670c869-2b83-4455-b6a2-9ec21b002e3c', 'APPLIANCES', 'Chair', 49.99);
INSERT INTO productentity (id, "category", "name", price) VALUES ('b670c869-2b83-4455-b6a2-9ec21b002e3d', 'SMARTPHONES', 'Samsung', 9999.99);

INSERT INTO orderentity (id, status, customer_id, "shipping-address", discountPercentage) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'SHIPPED', 'b670c869-2b83-4455-b6a2-9ec21b002e3a', '456 Elm St', 5.00);
INSERT INTO orderentity (id, status, customer_id, "shipping-address", discountPercentage) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'PENDING', 'b670c869-2b83-4455-b6a2-9ec21b002e3a', '123 Main St', 10.00);
INSERT INTO orderitementity (id, order_id, product_id, "name", price, quantity, discounted) VALUES ('a8098c1a-f86e-11da-bd1a-00112444be1e', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'b670c869-2b83-4455-b6a2-9ec21b002e3c', 'Chair', 49.99, 2, false);
INSERT INTO orderitementity (id, order_id, product_id, "name", price, quantity, discounted) VALUES ('c56a4180-65aa-42ec-a945-5fd21dec0538', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'b670c869-2b83-4455-b6a2-9ec21b002e3d', 'Samsung', 9999.99, 1, false);
INSERT INTO discountentity (id, code, percentage) VALUES ('3fa85f64-5717-4562-b3fc-2c963f66afa6', 'DISCOUNT5', 5.00);
INSERT INTO discountentity (id, code, percentage) VALUES ('d94f3f01-70ee-4d74-b6c0-9dd6cd9bc7e9', 'DISCOUNT10', 10.00);
INSERT INTO discountrestrictionentity (id, discount_id, type, restriction) VALUES ('72d5b744-6f26-4464-8e0d-e755b9eabc12', '3fa85f64-5717-4562-b3fc-2c963f66afa6', 'CATEGORY', 'APPLIANCES');
INSERT INTO discountrestrictionentity (id, discount_id, type, restriction) VALUES ('5a7164df-ac18-4cdc-8e55-3ee22e9f0abc', 'd94f3f01-70ee-4d74-b6c0-9dd6cd9bc7e9', 'PRICE', '1000');
