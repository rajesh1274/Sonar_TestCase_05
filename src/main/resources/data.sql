insert into Accounts (ID, USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE, CUSTOMER_ADDRESS, CUSTOMER_EMAIL, CUSTOMER_PHONE, default_store, default_store_name)
values (1, 'Sudha', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_MANAGER','kondapur', 'Sudha.v@mail.com','1234567890', 1, 'San Francisco');
 
insert into Accounts (ID, USER_NAME, ACTIVE, ENCRYTED_PASSWORD, USER_ROLE, CUSTOMER_ADDRESS, CUSTOMER_EMAIL, CUSTOMER_PHONE, default_store, default_store_name)
values (2, 'Vamshi', 1,
'$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 'ROLE_MANAGER','KPHB', 'vamshi.m@mail.com','1234567890', 1, 'San Francisco');

insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO1', 'Armani Perfume', 100, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/armani+perfume.jpg'  );
  
insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO2', 'Origins Mascara', 50, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/origins+mascara.jpg'  );
  
insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO3', 'Chanel Body Lotion', 120, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/chanel+body+lotion.jpg'  );
  
insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO4', 'Chanel Hand Cream', 120, CURRENT_TIMESTAMP,'https://s3.amazonaws.com/irisideathon/chanel+hand+cream.jpg'  );
  
insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO5', 'Mac Concealer', 170, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/mac+concealer.jpg'  );

insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO6', 'Mac Eye Cream', 200, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/mac+eye+cream.jpg'  );

insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO7', 'Origins Body Wash', 250, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/origins+body+wash.jpg'  );

insert into products (CODE, NAME, PRICE, CREATE_DATE, IMG_SRC)
values ('PRO8', 'Burberry Perfume', 190, CURRENT_TIMESTAMP, 'https://s3.amazonaws.com/irisideathon/burberry+perfume.jpg'  );



insert into store values (1,'San Francisco', 'San Francisco'), (2,'Pleasanton', 'Pleasanton'), (3,'Fremont', 'Fremont');

insert into store_product values (1, 500, 'PRO1', 1), (2, 550, 'PRO2', 1), (3, 550, 'PRO3', 1), (4, 550, 'PRO4', 1), (5, 550, 'PRO5', 1),
									(6, 500, 'PRO1', 2), (7, 550, 'PRO2', 2), (8, 550, 'PRO3', 2), (9, 550, 'PRO4', 2), (10, 550, 'PRO5', 2)
									, (11, 550, 'PRO6', 2), (12, 550, 'PRO7', 2), (13, 550, 'PRO8', 2), (14, 550, 'PRO1', 3), (15, 550, 'PRO2', 3), (16, 550, 'PRO3', 3);
									
insert into orders values('a64943a2-a1f4-4c6e-abf0-ca1135fb16ce', 5450, {ts '2019-02-01 20:39:08.999'}, 1, 'Delivered', 1),('a64943a2-a1f4-4c6e-edff-ca1135fb16cs', 1500, {ts '2019-02-17 20:39:08.999'}, 2, 'Shipped', 1);


insert into promotions (Id, promotion_level, promotion_Level_Id, promortion_Description, name) values(1, 'S', 'San Francisco', '20% off on body lotions at San Francisco store', 'body lotions sale'),(2, 'P', 'Perfumes', 'Buy one get one on perfumes', 'Perfume fest'),(3, 'A', 'All', 'Shop for 200$ and get 50$ gift card', 'Shop more');