INSERT IGNORE INTO regions (id, code, name)
VALUES (1, '01', 'ANDALUCÍA'),
       (2, '02', 'ARAGÓN'),
       (3, '03', 'ASTURIAS'),
       (4, '04', 'BALEARES'),
       (5, '05', 'CANARIAS'),
       (6, '06', 'CANTABRIA'),
       (7, '07', 'CASTILLA Y LEÓN'),
       (8, '08', 'CASTILLA-LA MANCHA'),
       (9, '09', 'CATALUÑA'),
       (10, '10', 'COMUNIDAD VALENCIANA'),
       (11, '11', 'EXTREMADURA'),
       (12, '12', 'GALICIA'),
       (13, '13', 'MADRID'),
       (14, '14', 'MURCIA'),
       (15, '15', 'NAVARRA'),
       (16, '16', 'PAÍS VASCO'),
       (17, '17', 'LA RIOJA'),
       (18, '18', 'CEUTA Y MELILLA');

INSERT IGNORE INTO provinces (id, code, name, region_id)
VALUES
-- ANDALUCÍA (1)
(1, '01', 'Almería', 1),
(2, '02', 'Cádiz', 1),
(3, '03', 'Córdoba', 1),
(4, '04', 'Granada', 1),
(5, '05', 'Huelva', 1),
(6, '06', 'Jaén', 1),
(7, '07', 'Málaga', 1),
(8, '08', 'Sevilla', 1),

-- ARAGÓN (2)
(9, '09', 'Huesca', 2),
(10, '10', 'Teruel', 2),
(11, '11', 'Zaragoza', 2),

-- ASTURIAS (3)
(12, '12', 'Asturias', 3),

-- BALEARES (4)
(13, '13', 'Islas Baleares', 4),

-- CANARIAS (5)
(14, '14', 'Las Palmas', 5),
(15, '15', 'Santa Cruz de Tenerife', 5),

-- CANTABRIA (6)
(16, '16', 'Cantabria', 6),

-- CASTILLA Y LEÓN (7)
(17, '17', 'Ávila', 7),
(18, '18', 'Burgos', 7),
(19, '19', 'León', 7),
(20, '20', 'Palencia', 7),
(21, '21', 'Salamanca', 7),
(22, '22', 'Segovia', 7),
(23, '23', 'Soria', 7),
(24, '24', 'Valladolid', 7),
(25, '25', 'Zamora', 7),

-- CASTILLA-LA MANCHA (8)
(26, '26', 'Albacete', 8),
(27, '27', 'Ciudad Real', 8),
(28, '28', 'Cuenca', 8),
(29, '29', 'Guadalajara', 8),
(30, '30', 'Toledo', 8),

-- CATALUÑA (9)
(31, '31', 'Barcelona', 9),
(32, '32', 'Girona', 9),
(33, '33', 'Lleida', 9),
(34, '34', 'Tarragona', 9),

-- COMUNIDAD VALENCIANA (10)
(35, '35', 'Alicante', 10),
(36, '36', 'Castellón', 10),
(37, '37', 'Valencia', 10),

-- EXTREMADURA (11)
(38, '38', 'Badajoz', 11),
(39, '39', 'Cáceres', 11),

-- GALICIA (12)
(40, '40', 'A Coruña', 12),
(41, '41', 'Lugo', 12),
(42, '42', 'Ourense', 12),
(43, '43', 'Pontevedra', 12),

-- MADRID (13)
(44, '44', 'Madrid', 13),

-- MURCIA (14)
(45, '45', 'Murcia', 14),

-- NAVARRA (15)
(46, '46', 'Navarra', 15),

-- PAÍS VASCO (16)
(47, '47', 'Álava', 16),
(48, '48', 'Gipuzkoa', 16),
(49, '49', 'Bizkaia', 16),

-- LA RIOJA (17)
(50, '50', 'La Rioja', 17),

-- CEUTA Y MELILLA (18)
(51, '51', 'Ceuta', 18),
(52, '52', 'Melilla', 18);

-- =========================
-- USERS
-- =========================
INSERT INTO users (name, email, password)
VALUES ('Garik Asatryan', 'garik@email.com', '$2a$12$yKhnvhrGWtqF8fVTGPeKm.TXUdjd9XUjRdRyRUMpSMQ4cO8odkD8W'),
       ('Paula Martín', 'paula@email.com', '$2a$12$5huTDxAWZeYqynV2P.IpFuXw8EOBQ.9JEJdcZAhkNVc8pRovicvMS');

-- =========================
-- ROLES
-- =========================
INSERT IGNORE INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_MANAGER'),
       (3, 'ROLE_USER');

-- =========================
-- USERS_ROLES
-- =========================
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 3);

-- =========================
-- CATEGORIES
-- =========================
INSERT INTO categories (name, description)
VALUES ('Electronics', 'Electronic devices'),
       ('Books', 'Books and educational material');

-- =========================
-- PRODUCTS
-- =========================
INSERT INTO products (name, description, price, category_id)
VALUES
-- Electrónica (1)
('Lenovo Laptop', '15 inch laptop with Intel i5 processor', 899.99, 1),
('HP Laptop', '14 inch laptop with AMD Ryzen 5', 749.00, 1),
('Bluetooth Headphones', 'Wireless over-ear headphones', 59.90, 1),
('Wireless Earbuds', 'True wireless earbuds with charging case', 39.99, 1),
('Mechanical Keyboard', 'RGB mechanical keyboard with blue switches', 79.99, 1),
('Wireless Mouse', 'Ergonomic wireless mouse', 24.50, 1),
('Gaming Mouse', 'High precision gaming mouse', 49.99, 1),
('27 Inch Monitor', 'Full HD LED monitor', 189.00, 1),
('USB-C Hub', 'Multiport adapter with HDMI and USB ports', 34.99, 1),
('External Hard Drive', '1TB USB 3.0 external hard drive', 64.90, 1),
('SSD 500GB', '500GB solid state drive', 59.00, 1),
('Smartphone Android', '6.5 inch Android smartphone', 299.99, 1),
('Tablet 10 Inch', '10 inch tablet with 64GB storage', 219.00, 1),
('Spring Boot Book', 'Practical guide to Spring Boot', 39.95, 2),
('Java Programming Book', 'Complete guide to Java programming', 45.00, 2),
('Clean Code', 'A Handbook of Agile Software Craftsmanship', 42.95, 2),
('Effective Java', 'Best practices for the Java platform', 47.50, 2),
('Microservices with Spring', 'Building microservices using Spring', 44.90, 2),
('Design Patterns Book', 'Elements of Reusable Object-Oriented Software', 49.99, 2),
('Hibernate in Action', 'Mastering Hibernate ORM', 41.00, 2),
('Docker for Developers', 'Containerization for modern applications', 38.90, 2),
('REST APIs with Spring', 'Developing RESTful APIs using Spring', 36.50, 2),
('Learning SQL', 'Beginner to advanced SQL guide', 29.95, 2),
('Web Development Basics', 'HTML, CSS and JavaScript fundamentals', 27.99, 2);

-- =========================
-- ORDERS
-- =========================
INSERT INTO orders (user_id)
VALUES (1),
       (2);

-- =========================
-- ORDER_PRODUCTS
-- =========================
INSERT INTO order_products (order_id, product_id, quantity)
VALUES (1, 1, 1), -- Order 1 → Laptop
       (1, 2, 1), -- Order 1 → Headphones
       (2, 3, 1); -- Order 2 → Book
