
create table material
(
mt_id char(9) not null,
mt_title varchar(50) not null,
mt_isbn varchar(20),
mt_publisher varchar(100) not null,
mt_publish_date date not null,
mt_pageno int(5),
mt_sh_id char(4) not null,
mt_price decimal(8, 2) not null,
mt_type varchar(15),
primary key (mt_id),
foreign key (mt_sh_id) references shelf
);

create table material_copy
(
mc_id char(10) not null,
mc_mt_id char(10) not null,
mc_status varchar(8) not null
primary key (mc_id, mc_mt_id),
foreign key (mc_mt_id) references material
);

create table shelf
(
sh_id char(4) not null,
sh_no int(3) not null,
sh_row int(2) not null,
primary key (sh_id),
);

create table usertype(ut_id char(4) not null, ut_role varchar(20) not null, primary key (ut_id))

insert into usertype('ut01', 'student')
insert into usertype('ut01', 'academic staff')
insert into usertype('ut01', 'administrative staff')
insert into usertype('ut01', 'librarian')

create table issue
(
is_id char(10) not null,
is_mc_id char(10) not null,
is_mb_id char(10) not null,
is_issue_date date not null,
is_due_date date not null,
is_return_date date,
is_fine decimal(8, 2),
primary key(ts_id),
foreign key (is_mc_id) references material_copy,
foreign key (is_mb_id) references member
);

create table author(au_id char(7) not null, au_name varchar(100) not null, primary key (au_id))

insert into author values('AU00001', 'Abraham Silberschatz')
insert into author values('AU00002', 'Greg Gagne')
insert into author values('AU00003', 'Peter Baer Galvin')
insert into author values('AU00004', 'Roger S. Pressman')
insert into author values('AU00005', 'Walter Savitch')
insert into author values('AU00006', 'Y. Daniel Liang')
insert into author values('AU00007', 'Zed Shaw')

create table link_author_material
(
lnk_mt_id char(10) not null,
lnk_au_id char(7) not null,
primary key (lnk_mt_id, lnk_au_id),
foreign key (lnk_mt_id) references material,
foreign key (lnk_au_id) references authhor
);

create table member(mb_id char(10) not null, mb_name varchar(80) not null, mb_gender char(1) not null, mb_email varchar(100) not null, mb_contact_no varchar(11) not null, mb_password varchar(20) not null, mb_dob date not null, mb_pin int(6) not null, mb_material_borrowed int(2) not null, mb_type_id varhcar(2) not null, primary key (mb_id))

insert into member values('1000000001', 'Arul', 'M', 'arul@e97a.com', '01010001000', '1234_abcd', '1997-01-01', 123456, 0, 'S')
insert into member values('1000000002', 'Bear', 'M', 'bear@e97a.com', '01012001200', '1234_abcd', '1997-02-01', 123456, 0, 'S')
insert into member values('1000000003', 'Chia', 'F', 'chia@e97a.com', '01010301030', '1234_abcd', '1997-03-01', 123456, 0, 'S')
insert into member values('1000000004', 'Dana', 'F', 'dana@e97a.com', '01040404040', '1234_abcd', '1997-04-01', 123456, 0, 'S')
insert into member values('1000000005', 'Ezra', 'M', 'ezra@e97a.com', '01055005500', '1234_abcd', '1997-05-01', 123456, 0, 'S')