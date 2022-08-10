ALTER TABLE orders
    ADD id_of_status       INTEGER,
    ADD CONSTRAINT fk_order_status
        FOREIGN KEY (id_of_status)
            REFERENCES order_status (status_id),

    ADD id_of_user         INTEGER,
    ADD CONSTRAINT fk_user
        FOREIGN KEY (id_of_user)
            REFERENCES users (user_id),

    ADD id_of_service_type INTEGER,
    ADD CONSTRAINT fk_service_type
        FOREIGN KEY (id_of_service_type)
            REFERENCES service_type (service_type_id),

    ADD id_of_box          INTEGER,
    ADD CONSTRAINT fk_box
        FOREIGN KEY (id_of_box)
            REFERENCES boxes (box_id);


ALTER TABLE confirmations
    ADD id_of_order INTEGER,
    ADD CONSTRAINT fk_order
        FOREIGN KEY (id_of_order)
            REFERENCES orders (order_id);