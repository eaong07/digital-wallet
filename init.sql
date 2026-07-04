-- Create Enum for Account Types
CREATE TYPE account_types AS ENUM ('WALLET');

-- 1. Create User Table
CREATE TABLE "user" (
    user_id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dob DATE,
    address VARCHAR(255),
    contact_numbers TEXT[]
);

-- 2. Create Account Table
CREATE TABLE account (
    account_id UUID PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    type account_types NOT NULL,
    amount DOUBLE PRECISION NOT NULL
);

-- 3. Create Transaction Table
CREATE TABLE transaction (
    transaction_id VARCHAR(36) PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    sender VARCHAR(255),
    receiver VARCHAR(255),
    account_id VARCHAR(36),
    user_id VARCHAR(36),
    amount DOUBLE PRECISION NOT NULL
);

-- --- INSERT SAMPLE DATA ---

-- Insert Users (3 rows)
INSERT INTO "user" (user_id, name, dob, address, contact_numbers) VALUES
('u1111111-1111-1111-1111-111111111111', 'John Doe', '1990-05-15', '123 Main St, New York', ARRAY['+1234567890', '+1098765432']),
('u2222222-2222-2222-2222-222222222222', 'Jane Smith', '1985-11-23', '456 Oak Ave, California', ARRAY['+14155552671']),
('u3333333-3333-3333-3333-333333333333', 'Alice Johnson', '1995-08-30', '789 Pine Rd, Texas', ARRAY['+15125558899', '+15125557711']);

-- Insert Accounts (3 rows using valid UUIDs)
INSERT INTO account (account_id, user_id, type, amount) VALUES
('a1111111-1111-4111-a111-111111111111', 'u1111111-1111-1111-1111-111111111111', 'WALLET', 5000.50),
('a2222222-2222-4222-a222-222222222222', 'u2222222-2222-2222-2222-222222222222', 'WALLET', 1200.75),
('a3333333-3333-4333-a333-333333333333', 'u3333333-3333-3333-3333-333333333333', 'WALLET', 350000.00);
