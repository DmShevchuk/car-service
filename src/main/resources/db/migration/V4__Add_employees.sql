CREATE TABLE IF NOT EXISTS employees
(
    employee_id    INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,

    id_of_user     INTEGER,
    constraint fk_user
        FOREIGN KEY (id_of_user)
            REFERENCES users (user_id),

    id_of_box      INTEGER,
    CONSTRAINT fk_box
        FOREIGN KEY (id_of_box)
            REFERENCES boxes (box_id)
                ON DELETE CASCADE,

    id_of_discount INTEGER,
    CONSTRAINT fk_discount
        FOREIGN KEY (id_of_discount)
            REFERENCES discounts (discount_id)
);