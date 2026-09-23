CREATE TABLE IF NOT EXISTS reserves (
    id VARCHAR(50) PRIMARY KEY,
    id_client VARCHAR(50) NOT NULL,
    id_room VARCHAR(50) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    state VARCHAR(20) NOT NULL
);