-- V3__create_vehicles_and_accessories.sql
-- Description: Create vehicles and accessories catalog tables with version columns for optimistic locking

CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    brand_id BIGINT NOT NULL REFERENCES brands(id),
    category_id BIGINT NOT NULL REFERENCES vehicle_categories(id),
    price DECIMAL(12,2) NOT NULL CHECK (price >= 0),
    year INT NOT NULL CHECK (year >= 1900 AND year <= 2100),
    fuel_type VARCHAR(50) NOT NULL,
    transmission VARCHAR(50) NOT NULL,
    description TEXT,
    main_image_url VARCHAR(512),
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'RESERVED', 'SOLD', 'INACTIVE')),
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE accessories (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL REFERENCES accessory_categories(id),
    price DECIMAL(12,2) NOT NULL CHECK (price >= 0),
    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    image_url VARCHAR(512),
    description TEXT,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_vehicles_slug ON vehicles(slug);
CREATE INDEX idx_vehicles_brand_id ON vehicles(brand_id);
CREATE INDEX idx_vehicles_category_id ON vehicles(category_id);
CREATE INDEX idx_vehicles_price ON vehicles(price);
CREATE INDEX idx_vehicles_status ON vehicles(status);

CREATE INDEX idx_accessories_slug ON accessories(slug);
CREATE INDEX idx_accessories_category_id ON accessories(category_id);
CREATE INDEX idx_accessories_price ON accessories(price);
CREATE INDEX idx_accessories_stock_active ON accessories(stock_quantity, is_active);

CREATE TRIGGER update_vehicles_updated_at BEFORE UPDATE ON vehicles FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_accessories_updated_at BEFORE UPDATE ON accessories FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
