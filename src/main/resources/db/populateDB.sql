DELETE
FROM user_role;
DELETE
FROM meals;
DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES
    (100000, '2026-08-29 08:00:00', 'Завтрак', 500),
    (100000, '2026-08-29 13:00:00', 'Обед', 700),
    (100000, '2026-08-29 19:00:00', 'Ужин', 600),

    (100000, '2026-08-30 08:30:00', 'Завтрак', 700),
    (100000, '2026-08-30 13:30:00', 'Обед', 900),
    (100000, '2026-08-30 20:00:00', 'Ужин', 800),

    (100000, '2026-08-31 09:00:00', 'Завтрак', 400),
    (100000, '2026-08-31 14:00:00', 'Обед', 750),
    (100000, '2026-08-31 20:00:00', 'Ужин', 650),

    (100001, '2026-08-30 09:00:00', 'Завтрак', 500),
    (100001, '2026-08-30 14:00:00', 'Обед', 800);