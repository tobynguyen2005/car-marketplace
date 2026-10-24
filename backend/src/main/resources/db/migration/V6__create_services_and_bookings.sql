-- V6__create_services_and_bookings.sql
-- Description: Create services and service_bookings tables

CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(12,2) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE service_bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    service_id BIGINT NOT NULL REFERENCES services(id),
    customer_name VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    booking_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED')),
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX idx_services_slug ON services(slug);
CREATE INDEX idx_services_category ON services(category);

CREATE INDEX idx_service_bookings_user_id ON service_bookings(user_id);
CREATE INDEX idx_service_bookings_service_id ON service_bookings(service_id);
CREATE INDEX idx_service_bookings_status ON service_bookings(status);
CREATE INDEX idx_service_bookings_date ON service_bookings(booking_date);

CREATE TRIGGER update_services_updated_at BEFORE UPDATE ON services FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_service_bookings_updated_at BEFORE UPDATE ON service_bookings FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
