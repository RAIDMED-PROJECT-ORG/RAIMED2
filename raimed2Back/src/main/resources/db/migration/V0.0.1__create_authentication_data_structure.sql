CREATE TABLE raimed_user
(
    id        UUID         NOT NULL,
    username  VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname  VARCHAR(255) NOT NULL,
    email     VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    role      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_raimed_user PRIMARY KEY (id)
);

ALTER TABLE raimed_user
    ADD CONSTRAINT uc_raimed_user_email UNIQUE (email);

ALTER TABLE raimed_user
    ADD CONSTRAINT uc_raimed_user_username UNIQUE (username);

CREATE TABLE raimed_refresh_token
(
    id      UUID         NOT NULL,
    token   VARCHAR(255) NOT NULL,
    user_id UUID         NOT NULL,
    CONSTRAINT pk_raimed_refresh_token PRIMARY KEY (id)
);

ALTER table raimed_refresh_token
    ADD CONSTRAINT uc_raimed_refresh_token_user UNIQUE (user_id);

ALTER TABLE raimed_refresh_token
    ADD CONSTRAINT FK_RAIMED_REFRESH_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES raimed_user (id);