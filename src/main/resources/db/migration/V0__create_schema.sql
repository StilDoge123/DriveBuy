-- Migration: Create Database Schema
-- This migration creates all tables needed for the DriveBuy application

-- Ensure public schema exists and is accessible
CREATE SCHEMA IF NOT EXISTS public;
SET search_path TO public;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    firebase_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

-- Car brands table
CREATE TABLE IF NOT EXISTS car_brand (
    id BIGSERIAL PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL UNIQUE
);

-- Car models table
CREATE TABLE IF NOT EXISTS car_model (
    id BIGSERIAL PRIMARY KEY,
    brand_id BIGINT NOT NULL REFERENCES car_brand(id),
    model_name VARCHAR(255) NOT NULL,
    UNIQUE (brand_id, model_name)
);

-- Body types table
CREATE TABLE IF NOT EXISTS body_types (
    id BIGSERIAL PRIMARY KEY,
    body_type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Car conditions table
CREATE TABLE IF NOT EXISTS car_conditions (
    id BIGSERIAL PRIMARY KEY,
    condition_name VARCHAR(255) NOT NULL UNIQUE
);

-- Car features table
CREATE TABLE IF NOT EXISTS car_features (
    id BIGSERIAL PRIMARY KEY,
    feature_name VARCHAR(255) NOT NULL UNIQUE
);

-- Colors table
CREATE TABLE IF NOT EXISTS colors (
    id BIGSERIAL PRIMARY KEY,
    color_name VARCHAR(255) NOT NULL UNIQUE
);

-- Cylinder counts table
CREATE TABLE IF NOT EXISTS cylinder_counts (
    id BIGSERIAL PRIMARY KEY,
    cylinder_count VARCHAR(255) NOT NULL UNIQUE
);

-- Door counts table
CREATE TABLE IF NOT EXISTS door_counts (
    id BIGSERIAL PRIMARY KEY,
    door_count VARCHAR(255) NOT NULL UNIQUE
);

-- Drive types table
CREATE TABLE IF NOT EXISTS drive_types (
    id BIGSERIAL PRIMARY KEY,
    drive_type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Fuel types table
CREATE TABLE IF NOT EXISTS fuel_types (
    id BIGSERIAL PRIMARY KEY,
    fuel_type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Steering positions table
CREATE TABLE IF NOT EXISTS steering_positions (
    id BIGSERIAL PRIMARY KEY,
    steering_position_name VARCHAR(255) NOT NULL UNIQUE
);

-- Transmission types table
CREATE TABLE IF NOT EXISTS transmission_types (
    id BIGSERIAL PRIMARY KEY,
    transmission_type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Regions table
CREATE TABLE IF NOT EXISTS regions (
    id BIGSERIAL PRIMARY KEY,
    region_name VARCHAR(255) NOT NULL UNIQUE
);

-- Cities table
CREATE TABLE IF NOT EXISTS cities (
    id BIGSERIAL PRIMARY KEY,
    region_id BIGINT NOT NULL REFERENCES regions(id),
    city_name VARCHAR(255) NOT NULL,
    UNIQUE (region_id, city_name)
);

-- Countries table (if needed)
CREATE TABLE IF NOT EXISTS countries (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Ads table
CREATE TABLE IF NOT EXISTS ads (
    id BIGSERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    description TEXT,
    year INTEGER NOT NULL,
    color VARCHAR(255),
    hp INTEGER NOT NULL,
    displacement INTEGER NOT NULL,
    mileage INTEGER NOT NULL,
    price INTEGER NOT NULL,
    body_type VARCHAR(255),
    condition VARCHAR(255),
    door_count VARCHAR(255),
    cylinder_count VARCHAR(255),
    transmission_type VARCHAR(255),
    fuel_type VARCHAR(255),
    steering_position VARCHAR(255),
    drive_type VARCHAR(255),
    owner_count INTEGER NOT NULL,
    phone VARCHAR(255),
    region VARCHAR(255),
    city VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Ad images table (for ElementCollection)
CREATE TABLE IF NOT EXISTS ad_images (
    ad_id BIGINT NOT NULL REFERENCES ads(id) ON DELETE CASCADE,
    image_url VARCHAR(1024) NOT NULL
);

-- Ad features table (for ElementCollection)
CREATE TABLE IF NOT EXISTS ad_features (
    ad_id BIGINT NOT NULL REFERENCES ads(id) ON DELETE CASCADE,
    feature VARCHAR(255) NOT NULL
);

-- User saved ads join table
CREATE TABLE IF NOT EXISTS user_saved_ads (
    user_id VARCHAR(255) NOT NULL REFERENCES users(firebase_id),
    ad_id BIGINT NOT NULL REFERENCES ads(id),
    PRIMARY KEY (user_id, ad_id)
);

-- Chats table
CREATE TABLE IF NOT EXISTS chats (
    id BIGSERIAL PRIMARY KEY,
    ad_id BIGINT NOT NULL REFERENCES ads(id),
    buyer_id VARCHAR(255) NOT NULL,
    seller_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_message_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Messages table
CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL REFERENCES chats(id),
    sender_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_car_model_brand_id ON car_model(brand_id);
CREATE INDEX IF NOT EXISTS idx_cities_region_id ON cities(region_id);
CREATE INDEX IF NOT EXISTS idx_ads_user_id ON ads(user_id);
CREATE INDEX IF NOT EXISTS idx_chats_ad_id ON chats(ad_id);
CREATE INDEX IF NOT EXISTS idx_chats_buyer_id ON chats(buyer_id);
CREATE INDEX IF NOT EXISTS idx_chats_seller_id ON chats(seller_id);
CREATE INDEX IF NOT EXISTS idx_messages_chat_id ON messages(chat_id);
