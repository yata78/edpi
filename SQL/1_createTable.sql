CREATE TABLE m_user (
    user_id integer,
    user_name VARCHAR(20),
    email VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE users_dpi (
    dpi_id integer,
    user_id integer,
    dpi numeric(5,3),
    sensitivity integer,
    PRIMARY KEY (dpi_id),
    FOREIGN KEY (user_id) references m_user(user_id)
);