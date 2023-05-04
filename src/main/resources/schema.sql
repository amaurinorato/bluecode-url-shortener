DROP TABLE url_model IF EXISTS;
CREATE TABLE url_model (shorten VARCHAR(255) PRIMARY KEY, long_version VARCHAR(255), access INTEGER);