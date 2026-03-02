INSERT INTO "City" (name) VALUES ('Диаспар'), ('Курск');
INSERT INTO "People" (name, surname, id_city, control_level) values ('Хедрон', '', 1, 10), ('noname', '', 2, 1);
INSERT INTO "Location" (name, id_city, latitude, longtitude) values ('Котельная', 1, 15.2, 32.3);
INSERT INTO "Powers" (name, level_accept) values ('Fireball', 8), ('Virus', 4), ('Armagedon', 9);
INSERT INTO "Powers_Location" ("Powers_id", "Location_id") values (3, 1);
INSERT INTO "Operations" (name, location_id, influence) values ('Overheating', 1, 10);
INSERT INTO "Machines" (name, city_id, location_id, operation_id, level_accept, is_it_infrastructure) values ('Котёл', 1, 1, 1, 5, true);
SELECT * FROM "People"