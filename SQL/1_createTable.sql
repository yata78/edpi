CREATE TABLE m_user (
    user_id serial NOT NULL,
    user_name VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE users_dpi (
    dpi_id serial NOT NULL,
    user_id integer NOT NULL,
    dpi integer NOT NULL,
    sensitivity numeric(5,3) NOT NULL,
    PRIMARY KEY (dpi_id),
    FOREIGN KEY (user_id) references m_user(user_id)
);

CREATE TABLE m_match (
    match_id serial NOT NULL,
    dpi_id integer NOT NULL,
    user_id integer NOT NULL,
    isWin boolean NOT NULL,
    hs_rate smallint NOT NULL,
    PRIMARY KEY (match_id)
);