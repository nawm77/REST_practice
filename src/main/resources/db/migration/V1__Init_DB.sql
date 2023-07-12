create table bike (cost_per_day float(53), cost_per_hour float(53), id bigserial not null, owner_id bigint, make varchar(255), model varchar(255), serial_number varchar(255), status varchar(255) check (status in ('AVAILABLE','RENTED','UNAVAILABLE')), primary key (id));
create table customer (rate float(53), id bigserial not null, status_id bigint default 1, cc_expiration varchar(255), cc_number varchar(255), cccvv varchar(255), email varchar(255), name varchar(255), phone_number varchar(255), surname varchar(255), primary key (id));
create table document_information (number integer, series integer, customer_id bigint unique, id bigserial not null, issue_date timestamp(6), primary key (id));
create table rent_request (bike_id bigint, customer_id bigint, id bigserial not null, time_start timestamp(6), rent_type varchar(255) check (rent_type in ('HOUR','DAY')), primary key (id));
create table role (id serial not null, name varchar(255), primary key (id));
create table users_roles (role_id integer not null, user_id bigint not null);
alter table if exists bike add constraint FKt9wuhid45wo6chotxfh35oikf foreign key (owner_id) references customer;
alter table if exists document_information add constraint FKq7lf68lw3q49pfot36avneoci foreign key (customer_id) references customer;
alter table if exists rent_request add constraint FK4jjromhyuer6cetw1llrc8fjl foreign key (bike_id) references bike;
alter table if exists rent_request add constraint FKnvj575giul8piirn5ehrkkf5r foreign key (customer_id) references customer;
alter table if exists users_roles add constraint FKt4v0rrweyk393bdgt107vdx0x foreign key (role_id) references role;
alter table if exists users_roles add constraint FKk38yo1t6hqkeo4pf46u0t8t0w foreign key (user_id) references customer;