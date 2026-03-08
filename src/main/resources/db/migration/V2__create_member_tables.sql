CREATE TABLE member (
    id BINARY(16) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE member_wish (
    member_id BINARY(16) NOT NULL,
    product_id BINARY(16) NOT NULL,
    PRIMARY KEY (member_id, product_id),
    FOREIGN KEY (member_id) REFERENCES member(id)
);
