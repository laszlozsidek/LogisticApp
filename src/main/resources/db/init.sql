insert into address
(id, city, country_code, latitude, longitude, number, postal_code, street)
values
(1,'Tata','hu','42.543543','12.3234','15','2890','Majus 1'),
(2,'Budakalasz','hu','41.543543','13.3234','22','2011','Csapas'),
(3,'Budakalasz','hu','41.543543','13.3234','10','2011','Mezo'),
(4,'Komarom','hu','40.543543','11.3234','1','2900','Furdo'),
(5,'Tatabanya','hu','40.543543','10.3234','34','2800','Fo'),
(6,'Wien','at','36.543543','10.3234','34','1110','Haupt'),
(7,'Passau','de','33.543543','10.3234','114','1110','König')

insert into milestone(id,planned_time,address_id)
values
(1,'2022-01-01 00:00:00.0000000',1),
(2,'2022-01-02 00:00:00.0000000',2),
(3,'2022-01-03 00:00:00.0000000',3),
(4,'2022-01-04 00:00:00.0000000',4),
(5,'2022-01-05 00:00:00.0000000',5),
(6,'2022-01-06 00:00:00.0000000',6),
(7,'2022-01-07 00:00:00.0000000',7)

insert into transport_plan(id,planned_income)
values
(1,1000),
(2,2000),
(3,3000)

insert into section(id,number,from_milestone_id,to_milestone_id,transport_plan_id)
values
(1,0,1,2,1),
(2,0,2,3,2),
(3,0,2,5,3),
(4,1,3,4,1),
(5,1,3,4,2),
(6,2,5,6,1),
(7,2,4,5,2)