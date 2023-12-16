INSERT INTO "user" (
    id,
    first_name,
    last_name,
    username,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'c3410bd1-e82c-4b85-9584-7eb1e22a712d',
    'admin',
    'admin',
    'admin',
    '$2a$10$P5wYidqdhmnZZ15N1oObr.NsaIU/RvMzgSUHB6jPacam13SzEnjgu',
    'admin@gmail.com',
    '1980-01-10 00:00:00',
    '+1111111111',
    TRUE
);

INSERT INTO "user" (
    id,
    first_name,
    last_name,
    username,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
     '8461ea6b-2033-47db-9fe1-bdcb48feec81',
     'petar',
     'petar',
     'petar',
     '$2a$10$Ew1M0Up3BjzDKPjnPHWPz.Bcg49Pz1n58d/knrpsb7xO7sGhLsaau',
     'petar@gmail.com',
     '2002-10-15 00:00:00',
     '+2222222222',
     TRUE
);

INSERT INTO "user" (
    id,
    first_name,
    last_name,
    username,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
    'b7ed183e-fbf4-455b-9922-4ee26f386180',
    'user',
    'user',
    'user',
    '$2a$10$NC/BQILk/tq2eicWQu/G..c/f91lrSzVTkmp8KDDZWXuhrpgpUgsu',
    'user@gmail.com',
    '2000-12-12 00:00:00',
    '+3333333333',
    TRUE
);

INSERT INTO "user" (
    id,
    first_name,
    last_name,
    username,
    "password",
    email,
    date_of_birth,
    contact_number,
    "enabled"
) VALUES (
     'b23f7d2a-7243-4e32-9fe6-958159711947',
     'test',
     'test',
     'test',
     '$2a$10$NC/BQILk/tq2eicWQu/G..c/f91lrSzVTkmp8KDDZWXuhrpgpUgsu',
     'test@gmail.com',
     '2000-12-10 00:00:00',
     '+4444444444',
     TRUE
);

INSERT INTO user_role (user_id, role_id) VALUES ('c3410bd1-e82c-4b85-9584-7eb1e22a712d', 1); -- user admin has role ADMIN
INSERT INTO user_role (user_id, role_id) VALUES ('8461ea6b-2033-47db-9fe1-bdcb48feec81', 2); -- user petar has role LIBRARIAN
INSERT INTO user_role (user_id, role_id) VALUES ('b7ed183e-fbf4-455b-9922-4ee26f386180', 3); -- user user has role MEMBER
INSERT INTO user_role (user_id, role_id) VALUES ('b23f7d2a-7243-4e32-9fe6-958159711947', 3); -- user user has role MEMBER