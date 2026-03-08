CREATE TABLE member (
    id BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE wish (
    id BINARY(16) PRIMARY KEY,
    member_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    FOREIGN KEY (member_id) REFERENCES member(id),
    UNIQUE (member_id, product_id)
);
