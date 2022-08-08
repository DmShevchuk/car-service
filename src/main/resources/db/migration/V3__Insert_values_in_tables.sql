INSERT INTO roles (role_id, role_name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_OPERATOR'),
       (3, 'ROLE_USER');


INSERT INTO order_status (status_id, status_name)
VALUES (1, 'AWAITING_CONFIRMATION'),
       (2, 'CONFIRMED'),
       (3, 'CLIENT_CHECKED_IN'),
       (4, 'IN_PROGRESS'),
       (5, 'CANCELED'),
       (6, 'FINISHED')