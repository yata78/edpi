CREATE TABLE m_user (
    user_id serial NOT NULL,
    user_name VARCHAR(20),
    email VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE users_dpi (
    dpi_id serial NOT NULL,
    user_id integer,
    dpi integer,
    sensitivity numeric(5,3),
    PRIMARY KEY (dpi_id),
    FOREIGN KEY (user_id) references m_user(user_id)
);

CREATE TABLE m_match (
    match_id serial NOT NULL,
    dpi_id integer,
    user_id integer,
    isWin boolean,
    hs_rate smallint,
    PRIMARY KEY (match_id)
);