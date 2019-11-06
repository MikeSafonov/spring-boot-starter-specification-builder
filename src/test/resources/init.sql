DELETE FROM cars;
DELETE FROM car_models;
DELETE FROM students_classes;
DELETE FROM students;
DELETE FROM classes;

insert into car_models(id, name)
values (1, 'audi'),
       (2, 'volvo'),
       (3, null);

insert into cars(id, number, id_model)
values (1, '123', 1),
       (2, '2312', 2);


insert into students(id, name)
values (1, 'Student 1'),
       (2, 'Student 2'),
       (3, 'Student 3'),
       (4, 'Student 4'),
       (5, 'Student 5');

insert into classes(id, name)
values (1, 'Class 1'),
       (2, 'Class 2'),
       (3, 'Class 3');

insert into students_classes(id_student, id_class)
values(1,1), (1,2), (2,1), (3,2), (4,1), (4,2), (4,3), (5,2), (5,3);




