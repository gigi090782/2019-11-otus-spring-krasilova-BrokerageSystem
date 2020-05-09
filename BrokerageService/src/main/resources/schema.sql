DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS contract_market_place;
DROP TABLE IF EXISTS contract;
DROP TABLE IF EXISTS client;





CREATE table client  (ID BIGSERIAL PRIMARY KEY, date_add varchar(255), first_name varchar(255), last_name varchar(255), second_name varchar(255), birth_date varchar(255));
CREATE table address (ID BIGSERIAL PRIMARY KEY, address_type varchar(255), value varchar(255), client_id bigint, FOREIGN KEY  (client_id) REFERENCES client(ID));
CREATE table contact (ID BIGSERIAL PRIMARY KEY, contact_type varchar(255), value varchar(255), client_id bigint, FOREIGN KEY  (client_id) REFERENCES client(ID));
CREATE TABLE contract(ID BIGSERIAL PRIMARY KEY, channel_type varchar(255), date_add varchar(255), client_id bigint, FOREIGN KEY  (client_id) REFERENCES client(ID));
CREATE TABLE contract_market_place (ID BIGSERIAL PRIMARY KEY,date_add varchar(255), market_place_type varchar(255), contract_id bigint, FOREIGN KEY  (contract_id) REFERENCES contract(ID) );

