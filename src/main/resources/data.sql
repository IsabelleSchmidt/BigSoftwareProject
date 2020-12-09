
/*Products*/

/*Plants*/
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Epipremnum Trebie', 'Pflanze',10.99, '/plants/EpipremnumTrebie.jpg', 3,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Marante Leuconeura', 'Pflanze',15.99, '/plants/MarantaLeuconeura.jpg', 10,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Monstera', 'Pflanze',29.99, '/plants/Monstera.jpg', 6,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Philodendron Pink Princess', 'Pflanze',64.99, '/plants/PhilodendronPinkPrincess.jpg', 2,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Pilea Peperomioides', 'Pflanze',20.99, '/plants/PileaPeperomioides.jpg', 17,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Senecio Rowleyanus', 'Pflanze',15.99, '/plants/SenecioRowleyanus.jpg', 11,0,0,0,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Xerosicyos Danguyi', 'Pflanze',5.99, '/plants/XerosicyosDanguyi.jpg', 10,0,0,0,1);

/*Chair*/
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Joerg', 'Stuhl','Esszimmer',95.99, '/chairs/chair1.jpg', 25,34,55,40,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Steven', 'Stuhl','Esszimmer',64.99, '/chairs/chair2.jpg', 17,16,33,14.5,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Bob', 'Stuhl','Esszimmer',110.99, '/chairs/chair3.jpg', 16,16,33,14.5,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Teddy', 'Stuhl','Esszimmer',139.99, '/chairs/chair4.jpg', 6,34,55,40,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Bulli', 'Stuhl','Esszimmer',50.99, '/chairs/chair5.jpg', 80,16,33,14.5,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Shelli', 'Stuhl','Esszimmer',210.99, '/chairs/chair6.jpg', 10,34,55,40,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Stuhl Chris', 'Stuhl','Esszimmer',35.99, '/chairs/chair7.jpg', 10,16,33,14.5,1);

/*Decorations*/
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Bilderrahmen Blacki', 'Dekoration',7.99, '/deco/deco1.jpg', 35,4,15,8,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Bilderrahmen Bloom', 'Dekoration',9.99, '/deco/deco2.jpg', 25,3,6,6,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Vase Abstrakt', 'Dekoration',9.99, '/deco/deco3.jpg', 55,4,7,3.5,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Vase Freedom', 'Dekoration',6.50, '/deco/deco4.png', 60,4,8,4,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Bilderrahmen Hands', 'Dekoration',11.99, '/deco/deco5.png', 25,4,15,4,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Bilderrahmen HandsII', 'Dekoration',7.99, '/deco/deco6.jpg', 45,4,8,4,1);
INSERT INTO product(name, productType, price, picture, available, depth, height, width, version) values ('Bilderrahmen Spring', 'Dekoration',15.99, '/deco/deco7.jpg', 67,0,15,4,1);

/*Couches*/
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Herbert', 'Sofa/Couch','Wohnzimmer',1200.99, '/sofas/sofa1.jpg', 25,60,45,250,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Valentina', 'Sofa/Couch','Wohnzimmer',1550.99, '/sofas/sofa2.jpg', 45,90,45,250,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Fluff', 'Sofa/Couch','Wohnzimmer',1671.99, '/sofas/sofa3.jpg', 90,60,45,450,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Otto', 'Sofa/Couch','Wohnzimmer',1200.99, '/sofas/sofa4.jpg', 45,60,45,250,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Stella', 'Sofa/Couch','Wohnzimmer',1860.99, '/sofas/sofa5.jpg', 25,55,45,345,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Ulli', 'Sofa/Couch','Wohnzimmer',870.99, '/sofas/sofa6.jpg', 55,65,45,220,1);
INSERT INTO product(name, productType, room, price, picture, available, depth, height, width, version) values ('Sofa Bernd', 'Sofa/Couch','Wohnzimmer',1100.99, '/sofas/sofa7.jpg', 33,60,45,346,1);


/*Beds*/
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(175.50,'Elfriede','Bett',1,140,210,60,10,'Schlafzimmer','/beds/bed1.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(195.90,'Cozy','Bett',1,180,220,50,170,'Schlafzimmer','/beds/bed2.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(255.0,'White','Bett',1,210,210,80,5,'Schlafzimmer','/beds/bed3.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(1290.99,'Luxury','Bett',1,140,210,60,80,'Schlafzimmer','/beds/bed4.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(690.00,'Bernhard','Bett',1,140,210,60,77,'Schlafzimmer','/beds/bed5.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(117.17,'Wild Life','Bett',1,140.5,215.9,60,17,'Schlafzimmer','/beds/bed6.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(700.00,'Rustikal','Bett',1,170,220.10,55.89,10,'Schlafzimmer','/beds/bed7.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(99.99,'Allround','Bett',1,120,180,40,3,'Schlafzimmer','/beds/bed8.jpg');

/*Closets*/
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(999.99,'Millenial','Schrank/Kommode',1,70,176.5,196.5,13,'Schlafzimmer','/closets/closet1.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(245.95,'Bjoern','Schrank/Kommode',1,60.3,120.5,96.5,1312,'Schlafzimmer','/closets/closet2.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(2995.90,'Minimal','Schrank/Kommode',1,70,176.5,196.5,33,'Schlafzimmer','/closets/closet3.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(500,'Mint','Schrank/Kommode',1,50,120,110,117,'Schlafzimmer','/closets/closet4.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(1900.99,'Schwerlastregal','Schrank/Kommode',1,45,210.3,200,1300,'Schlafzimmer','/closets/closet5.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(9999.99,'Justus Business','Schrank/Kommode',1,83.5,180,213.6,5,'Schlafzimmer','/closets/closet6.png');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Bestimmt nicht MALM','Schrank/Kommode',1,50,120,110,11,'Schlafzimmer','/closets/closet7.jpg');

/*Tables*/

insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Rund','Tisch',1,80,80,90,111,'Wohnzimmer','/tables/table1.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(15010.95,'Vollholz','Tisch',1,80,235.5,93,550,'Wohnzimmer','/tables/table2.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Kaeren','Tisch',1,95,180,90,3,'Wohnzimmer','/tables/table3.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Lifestail','Tisch',1,95,180,90,25,'Wohnzimmer','/tables/table4.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Alfrede','Tisch',1,89.9,112.8,96,89,'Wohnzimmer','/tables/table5.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Modern chic','Tisch',1,95,180,90,2,'Wohnzimmer','/tables/table6.jpg');
insert into product(price,name,producttype,version,depth,width,height,available,room,picture) values(110.99,'Classic','Tisch',1,80,80,90,99,'Wohnzimmer','/tables/table7.jpg');

/*Tags*/
Insert into Tag(value,version) values('Braun',1);
Insert into Tag(value,version) values('Abendessen',1);
Insert into Tag(value,version) values('Holz',1);
Insert into Tag(value,version) values('Schlafen',1);
Insert into Tag(value,version) values('Rustikal',1);
Insert into Tag(value,version) values('Eiche',1);
Insert into Tag(value,version) values('Mahagoni',1);
Insert into Tag(value,version) values('gelb',1);
Insert into Tag(value,version) values('Prinzessin',1);
Insert into Tag(value,version) values('Business',1);
Insert into Tag(value,version) values('guenstig',1);
Insert into Tag(value,version) values('Luxus',1);
Insert into Tag(value,version) values('rosa',1);
Insert into Tag(value,version) values('bunt',1);
Insert into Tag(value,version) values('bluete',1);
Insert into Tag(value,version) values('bequem',1);


/* Pictures */ 
Insert into Picture(path, version) values('/plants/Epipremnum Trebie.jpg', 1);
Insert into Picture(path, version) values('/plants/Marante Leuconeura.jpg', 1);
Insert into Picture(path, version) values('/plants/Monstera',1);
Insert into Picture(path, version) values('/plants/Philodendron Pink Princess.jpg', 1);
Insert into Picture(path, version) values('/plants/Seneio Rowleyanus.jpg', 1);
Insert into Picture(path, version) values('/plants/Xerosicyos Danguyi.jpg', 1);
Insert into Picture(path, version) values('/chairs/chair2.jpg', 1);
Insert into Picture(path, version) values('/chairs/chair4.jpg', 1);
Insert into Picture(path, version) values('/chairs/chair5.jpg', 1);
Insert into Picture(path, version) values('/chairs/chair6.jpg', 1);
Insert into Picture(path, version) values('/chairs/chair7.jpg', 1);
Insert into Picture(path, version) values('/deco/deco1.jpg', 1);
Insert into Picture(path, version) values('/deco/deco2.jpg', 1);
Insert into Picture(path, version) values('/deco/deco3.jpg', 1);
Insert into Picture(path, version) values('/deco/deco4.jpg', 1);
Insert into Picture(path, version) values('/deco/deco5.jpg', 1);
Insert into Picture(path, version) values('/deco/deco6.jpg', 1);
Insert into Picture(path, version) values('/deco/deco7.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa1.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa2.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa3.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa4.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa5.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa6.jpg', 1);
Insert into Picture(path, version) values('/sofas/sofa7.jpg', 1);
Insert into Picture(path, version) values('/beds/bed1.jpg', 1);
Insert into Picture(path, version) values('/beds/bed2.jpg', 1);
Insert into Picture(path, version) values('/beds/bed3.jpg', 1);
Insert into Picture(path, version) values('/beds/bed4.jpg', 1);
Insert into Picture(path, version) values('/beds/bed5.jpg', 1);
Insert into Picture(path, version) values('/beds/bed6.jpg', 1);
Insert into Picture(path, version) values('/beds/bed7.jpg', 1);
Insert into Picture(path, version) values('/beds/bed8.jpg', 1);
Insert into Picture(path, version) values('/closets/closet1.jpg', 1);
Insert into Picture(path, version) values('/closets/closet2.jpg', 1);
Insert into Picture(path, version) values('/closets/closet3.jpg', 1);
Insert into Picture(path, version) values('/closets/closet4.jpg', 1);
Insert into Picture(path, version) values('/closets/closet5.jpg', 1);
Insert into Picture(path, version) values('/closets/closet6.jpg', 1);
Insert into Picture(path, version) values('/closets/closet7.jpg', 1);
Insert into Picture(path, version) values('/tables/table1.jpg', 1);
Insert into Picture(path, version) values('/tables/table2.jpg', 1);
Insert into Picture(path, version) values('/tables/table3.jpg', 1);
Insert into Picture(path, version) values('/tables/table4.jpg', 1);
Insert into Picture(path, version) values('/tables/table5.jpg', 1);
Insert into Picture(path, version) values('/tables/table6.jpg', 1);
Insert into Picture(path, version) values('/tables/table7.jpg', 1);