DELETE FROM cars;
DELETE FROM car_models;

insert into car_models(id, name)
values (1, 'audi'),
       (2, 'volvo');

insert into cars(id, number, id_model)
values (1, '123', 1),
       (2, '2312', 2);