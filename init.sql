-- Create databases
CREATE DATABASE balancedb;
CREATE DATABASE tenantdb;

-- Switch to balancedb
\c balancedb

-- Create user and schema for balancedb
CREATE USER balance_app WITH PASSWORD 'balance_app';
CREATE SCHEMA balance_app AUTHORIZATION balance_app;
GRANT ALL PRIVILEGES ON SCHEMA balance_app TO balance_app;
ALTER ROLE balance_app in database balancedb SET search_path TO balance_app;

-- Switch to tenantdb
\c tenantdb

-- Create user and schema for tenantdb
CREATE USER tenant_app WITH PASSWORD 'tenant_app';
CREATE SCHEMA tenant_app AUTHORIZATION tenant_app;
GRANT ALL PRIVILEGES ON SCHEMA tenant_app TO tenant_app;
ALTER ROLE tenant_app in database tenantdb SET search_path TO tenant_app;

