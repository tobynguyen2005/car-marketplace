-- V8__seed_initial_data.sql
-- Description: Seed realistic initial production data for testing and immediate application usage

-- 1. Users (Passwords hashed with BCrypt for 'Password123!')
INSERT INTO users (id, email, password_hash, full_name, phone, role, enabled) VALUES
(1, 'admin@carmarketplace.com', '$2a$12$e8P63UCDpnraw9vpnVNm6eis9bHgTY0hWnPJlOD57l5SHJ.TM17ky', 'System Administrator', '+1-800-555-0101', 'ADMIN', true),
(2, 'staff@carmarketplace.com', '$2a$12$e8P63UCDpnraw9vpnVNm6eis9bHgTY0hWnPJlOD57l5SHJ.TM17ky', 'Sarah Connor (Staff)', '+1-800-555-0102', 'STAFF', true),
(3, 'john.doe@example.com', '$2a$12$e8P63UCDpnraw9vpnVNm6eis9bHgTY0hWnPJlOD57l5SHJ.TM17ky', 'John Doe', '+1-555-234-5678', 'USER', true),
(4, 'jane.smith@example.com', '$2a$12$e8P63UCDpnraw9vpnVNm6eis9bHgTY0hWnPJlOD57l5SHJ.TM17ky', 'Jane Smith', '+1-555-987-6543', 'USER', true);

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

-- 2. OAuth2 Accounts
INSERT INTO oauth2_accounts (id, user_id, provider, provider_user_id) VALUES
(1, 4, 'GOOGLE', 'google-oauth2-10987654321');

SELECT setval('oauth2_accounts_id_seq', (SELECT MAX(id) FROM oauth2_accounts));

-- 3. Vehicle Categories
INSERT INTO vehicle_categories (id, name, slug, description) VALUES
(1, 'Sedan', 'sedan', 'Elegant four-door passenger cars emphasizing comfort and fuel efficiency.'),
(2, 'SUV', 'suv', 'Sports Utility Vehicles with elevated seating, off-road capabilities, and room for families.'),
(3, 'Hatchback', 'hatchback', 'Compact passenger cars with rear flip-up doors for versatile cargo space.'),
(4, 'Pickup', 'pickup', 'Rugged light-duty trucks equipped with open cargo beds and towing strength.'),
(5, 'Coupe', 'coupe', 'Sporty two-door automobiles optimized for aerodynamics and dynamic performance.'),
(6, 'MPV', 'mpv', 'Multi-Purpose Vehicles designed for spacious multi-passenger transportation.'),
(7, 'Electric Vehicle', 'electric-vehicle', 'Zero-emission automobiles powered strictly by battery packs and electric powertrains.'),
(8, 'Hybrid', 'hybrid', 'Environmentally conscious vehicles pairing internal combustion with electric motors.');

SELECT setval('vehicle_categories_id_seq', (SELECT MAX(id) FROM vehicle_categories));

-- 4. Brands
INSERT INTO brands (id, name, slug, logo_url, description) VALUES
(1, 'Toyota', 'toyota', 'https://images.unsplash.com/photo-1619682817481-e994891cd1f5', 'Japanese automotive giant renowned for reliability, safety, and hybrid technology.'),
(2, 'Honda', 'honda', 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8', 'Leading manufacturer of efficient, high-performing sedans, crossovers, and power units.'),
(3, 'BMW', 'bmw', 'https://images.unsplash.com/photo-1555215695-3004980ad54e', 'Bavarian luxury automaker producing dynamic driving machines and executive sports cars.'),
(4, 'Mercedes-Benz', 'mercedes-benz', 'https://images.unsplash.com/photo-1617814076367-b759c7d7e738', 'Global symbol of automotive luxury, cutting-edge innovation, and refined craftsmanship.'),
(5, 'Ford', 'ford', 'https://images.unsplash.com/photo-1551830820-330a71b99659', 'American heritage brand famed for muscle cars, durable pickup trucks, and modern electric SUVs.'),
(6, 'Hyundai', 'hyundai', 'https://images.unsplash.com/photo-1617814076668-8dfc6ba13133', 'Innovative Korean automaker producing award-winning electric vehicles and feature-packed sedans.'),
(7, 'Kia', 'kia', 'https://images.unsplash.com/photo-1606016159991-dfe4f2746ad5', 'Modern manufacturer delivering bold design language, extended warranties, and high value.'),
(8, 'Tesla', 'tesla', 'https://images.unsplash.com/photo-1560958089-b8a1929cea89', 'Pioneer in battery electric vehicles, autonomous software, and sustainable energy ecosystems.'),
(9, 'Audi', 'audi', 'https://images.unsplash.com/photo-1603584173870-7f23fdae1b7a', 'German premium brand engineering Quattro all-wheel drive and sophisticated technological cockpits.'),
(10, 'Lexus', 'lexus', 'https://images.unsplash.com/photo-1549399542-7e3f8b79c341', 'Luxury division of Toyota offering serene ride quality, hybrid refinement, and exceptional durability.');

SELECT setval('brands_id_seq', (SELECT MAX(id) FROM brands));

-- 5. Vehicles
INSERT INTO vehicles (id, title, slug, brand_id, category_id, price, year, fuel_type, transmission, description, main_image_url, status) VALUES
(1, '2024 Toyota Camry Hybrid XSE', '2024-toyota-camry-hybrid-xse', 1, 8, 34250.00, 2024, 'Hybrid', 'Automatic', 'Midsize sedan featuring 208 hp hybrid powertrain, sport-tuned suspension, and SofTex interior.', 'https://images.unsplash.com/photo-1621007947382-bb3c3994e3fb', 'AVAILABLE'),
(2, '2024 BMW M4 Competition Coupe', '2024-bmw-m4-competition-coupe', 3, 5, 82200.00, 2024, 'Gasoline', 'Automatic', 'High-performance coupe delivering 503 hp twin-turbo inline 6-cylinder engine and M xDrive.', 'https://images.unsplash.com/photo-1580273916550-e323be2ae537', 'AVAILABLE'),
(3, '2024 Tesla Model Y Long Range', '2024-tesla-model-y-long-range', 8, 7, 48990.00, 2024, 'Electric', 'Automatic', 'All-electric crossover with 310 miles EPA range, dual-motor AWD, and Autopilot safety suites.', 'https://images.unsplash.com/photo-1560958089-b8a1929cea89', 'AVAILABLE'),
(4, '2024 Ford F-150 Lariat 4x4', '2024-ford-f-150-lariat-4x4', 5, 4, 65300.00, 2024, 'Gasoline', 'Automatic', 'Full-size pickup truck boasting 3.5L EcoBoost V6, Pro Power Onboard generator, and 14,000 lbs towing capability.', 'https://images.unsplash.com/photo-1583121274602-3e2820c69888', 'AVAILABLE'),
(5, '2024 Mercedes-Benz GLE 450 4MATIC', '2024-mercedes-benz-gle-450-4matic', 4, 2, 69500.00, 2024, 'Hybrid', 'Automatic', 'Luxury midsize SUV with mild-hybrid turbo inline 6, MBUX hyperscreen navigation, and executive seating.', 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8', 'AVAILABLE');

SELECT setval('vehicles_id_seq', (SELECT MAX(id) FROM vehicles));

-- 6. Accessory Categories
INSERT INTO accessory_categories (id, name, slug, description) VALUES
(1, 'Electronics & Tech', 'electronics-tech', 'Dash cams, wireless chargers, GPS units, and audio upgrades.'),
(2, 'Interior Protection', 'interior-protection', 'All-weather floor mats, seat covers, and trunk liners.'),
(3, 'Exterior Styling', 'exterior-styling', 'Spoilers, body kits, chrome accents, and custom grilles.'),
(4, 'Wheels & Tires', 'wheels-tires', 'Alloy rims, performance tires, and tire pressure monitoring systems.'),
(5, 'Car Care & Cleaning', 'car-care-cleaning', 'Ceramic coatings, microfiber towels, and interior detailers.');

SELECT setval('accessory_categories_id_seq', (SELECT MAX(id) FROM accessory_categories));

-- 7. Accessories
INSERT INTO accessories (id, title, slug, category_id, price, stock_quantity, is_active, image_url, description) VALUES
(1, '4K Ultra HD Dual Dash Camera', '4k-ultra-hd-dual-dash-camera', 1, 189.99, 45, true, 'https://images.unsplash.com/photo-1508974239320-0a029497e820', 'Front and rear 4K recording dash cam with night vision, GPS tracking, and Wi-Fi sync.'),
(2, '3D All-Weather Heavy Duty Floor Mats', '3d-all-weather-heavy-duty-floor-mats', 2, 129.50, 100, true, 'https://images.unsplash.com/photo-1549399542-7e3f8b79c341', 'Custom-fit laser measured thermoplastic floor liners engineered for ultimate spill protection.'),
(3, 'Wireless Fast Charging Car Phone Mount', 'wireless-fast-charging-car-phone-mount', 1, 49.99, 150, true, 'https://images.unsplash.com/photo-1511707171634-5f897ff02aa9', 'Automatic clamping air vent mount providing 15W Qi wireless fast charging for iPhone & Android.'),
(4, 'Professional Ceramic Coating Protection Kit', 'professional-ceramic-coating-protection-kit', 5, 89.00, 30, true, 'https://images.unsplash.com/photo-1607860108855-64acf2078ed9', 'SiO2-based hydrophobic coat giving 9H diamond hardness and high-gloss weather protection.'),
(5, '12V Portable Digital Tire Inflator Air Compressor', '12v-portable-digital-tire-inflator-air-compressor', 4, 59.95, 1, true, 'https://images.unsplash.com/photo-1578637387939-43c525550085', 'Fast electric pump with auto-shutoff preset pressure digital gauge.');

SELECT setval('accessories_id_seq', (SELECT MAX(id) FROM accessories));

-- 8. Carts
INSERT INTO carts (id, user_id) VALUES
(1, 3);

SELECT setval('carts_id_seq', (SELECT MAX(id) FROM carts));

-- 9. Cart Items
INSERT INTO cart_items (id, cart_id, accessory_id, quantity) VALUES
(1, 1, 1, 1),
(2, 1, 3, 2);

SELECT setval('cart_items_id_seq', (SELECT MAX(id) FROM cart_items));

-- 10. Orders
INSERT INTO orders (id, user_id, total_amount, status, shipping_address, phone) VALUES
(1, 3, 309.49, 'DELIVERED', '123 Market Street, San Francisco, CA 94105', '+1-555-234-5678');

SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));

-- 11. Order Items (Historical Snapshot)
INSERT INTO order_items (id, order_id, accessory_id, product_name, unit_price, quantity, subtotal) VALUES
(1, 1, 1, '4K Ultra HD Dual Dash Camera', 189.99, 1, 189.99),
(2, 1, 2, '3D All-Weather Heavy Duty Floor Mats', 119.50, 1, 119.50);

SELECT setval('order_items_id_seq', (SELECT MAX(id) FROM order_items));

-- 12. Services
INSERT INTO services (id, title, slug, category, description, price) VALUES
(1, 'Full Synthetic Engine Oil & Filter Change', 'full-synthetic-oil-change', 'Maintenance', 'Comprehensive oil replacement using Mobil 1 synthetic oil, OEM filter swap, and multi-point inspection.', 89.99),
(2, '4-Wheel Precision Laser Alignment', '4-wheel-laser-alignment', 'Tires & Suspension', 'Computerized laser calibration restoring wheel angles to exact manufacturer specifications.', 129.00),
(3, 'Brake Pad & Rotor Replacement (Front Axle)', 'brake-pad-rotor-replacement-front', 'Brakes', 'Installation of ceramic low-dust brake pads and vented steel disc rotors.', 289.50);

SELECT setval('services_id_seq', (SELECT MAX(id) FROM services));

-- 13. Service Bookings
INSERT INTO service_bookings (id, user_id, service_id, customer_name, phone, email, booking_date, status, notes) VALUES
(1, 3, 1, 'John Doe', '+1-555-234-5678', 'john.doe@example.com', '2026-08-05 10:00:00+00', 'CONFIRMED', 'Customer requested synthetic 5W-30 formulation.');

SELECT setval('service_bookings_id_seq', (SELECT MAX(id) FROM service_bookings));

-- 14. Blogs
INSERT INTO blogs (id, title, slug, content, author_id, status) VALUES
(1, 'Top 5 Hybrid Sedans for Fuel Efficiency in 2024', 'top-5-hybrid-sedans-2024', 'Hybrid technology has reached unprecedented peaks of power and thermal efficiency. In this comprehensive review, we examine the Toyota Camry, Honda Accord, and Hyundai Sonata hybrids...', 1, 'PUBLISHED'),
(2, 'Essential Maintenance Tips to Extend Engine Lifespan', 'essential-engine-maintenance-tips', 'Regular fluid inspection, timely synthetic oil changes, and air filter maintenance remain the bedrock of automotive longevity...', 2, 'PUBLISHED');

SELECT setval('blogs_id_seq', (SELECT MAX(id) FROM blogs));

-- 15. Contact Messages
INSERT INTO contact_messages (id, name, email, phone, subject, message, status) VALUES
(1, 'Robert Vance', 'robert.vance@example.com', '+1-555-444-3333', 'Inquiry regarding 2024 BMW M4 Financing', 'Hello, I would like to schedule a test drive for the 2024 BMW M4 Competition Coupe this weekend. Please contact me.', 'NEW');

SELECT setval('contact_messages_id_seq', (SELECT MAX(id) FROM contact_messages));
