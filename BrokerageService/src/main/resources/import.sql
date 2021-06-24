INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (1, 'Иван', 'Иванович', 'Иванов', '1981-01-01', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (2, 'Петр', 'Пантелеевич', 'Костромской', '1956-02-01', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (3, 'Захар', 'Ксилофентович', 'Артемкин', '1961-07-21', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (4, 'Даниил', 'Гордеевич', 'Полянский', '1972-11-05', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (5, 'Дана', 'Игоревна', 'Тимофеева', '1976-03-31', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (6, 'Валерий', 'Савельевич', 'Красивейший', '1983-05-03', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES  (7, 'Тимофей', 'Петрович', 'Прядинский', '1953-11-12', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES  (8, 'Гордей', 'Иванович', 'Шукшинков', '2000-01-21', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES  (9, 'Дмитрий', 'Георгиевич', 'Паутинкин', '1993-02-14', '2020-04-28');
INSERT INTO CLIENT (id, first_name, second_name, last_name, birth_date, date_add) VALUES (10, 'Никита', 'Яковлевич', 'Липатов', '1987-06-17', '2020-04-28');


INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (1, 'POST', 'г.Москва,Леонтьевский переулок, 24, кв.11', 1);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (2, 'REGISTRATION', 'г.Москва, ул.Никольская улица, 5/1с1, кв.21', 2);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (3, 'POST', 'г.Москва, улица Воронцово Поле, 4с1, кв.115', 3);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (4, 'REGISTRATION', 'г.Москва, Большой Дровяной переулок, 18, кв.55', 4);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (5, 'POST', 'г.Москва, Брошевский переулок, 6с1, кв.322', 5);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (6, 'REGISTRATION', 'г.Москва, улица Гвоздева, 7/4с1, кв.501', 6);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (7, 'REGISTRATION', 'г.Москва, улица Мельникова, 27, кв.4', 7);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (8, 'POST', 'г.Москва, Угрешская улица, 2с147, кв.81', 8);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (9, 'REGISTRATION', 'г.Москва, улица Новинки, 27к1, кв.78', 9);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (10, 'POST', 'г.Москва, улица Москворечье, 17, кв.96', 10);
INSERT INTO ADDRESS(id, address_type, value, client_id) VALUES (11, 'REGISTRATION', 'г.Москва, 1-я улица Машиностроения, 4к2, кв.67', 8);

INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES(1,1, '79161234567', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES      (2,1, 'my@mail.ru', 'EMAIL' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (3,2, 'ers@yandex.ru', 'EMAIL' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (4,3, '79163334455', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (5,4, 'ttr11@mail.ru', 'EMAIL' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (6,5, '79268172299', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (7,6, '79189991199', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (8,7, 'gfdss@mail.ru', 'EMAIL' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (9,8, '79163332211', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (10,9, '79167776655', 'MOBILE' );
INSERT INTO  CONTACT (id, client_id, value, contact_type) VALUES (11,10, 'qwe12@mail.ru', 'EMAIL' );


INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (1, 'VSP', '02.02.2020', 1);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (2, 'VSP', '01.03.2020', 2);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (3, 'VSP', '22.04.2020', 3);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (4, 'VSP', '30.12.2019', 4);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (5, 'ONLINE', '10.11.2019', 5);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (6, 'ONLINE', '15.12.2019', 6);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (7, 'VSP', '05.09.2019', 7);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (8, 'ONLINE', '14.01.2020', 8);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (9, 'VSP', '21.02.2020', 9);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (10, 'VSP', '25.12.2019', 10);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (11, 'ONLINE', '16.11.2019', 1);
INSERT INTO CONTRACT (id, channel_type, date_add,client_id) VALUES (12, 'ONLINE', '01.03.2020', 2);

INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (1 ,1 , 'FOND', '2020-02-03');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (2 ,1 , 'TERM', '2020-02-03');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (3 ,1 , 'CURR', '2020-02-03');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (4 ,2 , 'TERM', '2020-05-03');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (5 ,2 , 'FOND', '2020-02-06');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (6 ,3 , 'FOND', '2020-04-23');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (7 ,4 , 'FOND', '2019-12-31');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (8 ,4 , 'TERM', '2019-12-31');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (9 ,4 , 'CURR', '2019-12-31');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (10 ,5 , 'FOND', '2019-11-13');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (11 ,5 , 'TERM', '2019-11-14');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (12 ,6 , 'FOND', '2019-12-16');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (13 ,6 , 'TERM', '2019-12-16');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (14 ,6 , 'CURR', '2019-12-16');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (15 ,7 , 'FOND', '2019-09-07');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (16 ,7 , 'TERM', '2019-09-07');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (17 ,7 , 'CURR', '2019-09-07');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (18 ,8 , 'CURR', '2020-01-15');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (19 ,8 , 'TERM', '2020-01-15');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (20 ,9 , 'TERM', '2020-02-22');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (21 ,10 , 'CURR', '2019-12-26');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (22 ,11 , 'CURR', '2019-12-26');
INSERT  INTO contract_market_place (id, contract_id, market_place_type, date_add) VALUES (23 ,12 , 'TERM', '2019-12-26');


insert into USER(  username, password, accountnonexpired,accountnonlocked,credentialsnonexpired,enabled  ) values ('admin','admin', true,true,true,true );
insert into USER(  username, password, accountnonexpired,accountnonlocked,credentialsnonexpired,enabled  )  values ('user','1', true,true,true,true  );



