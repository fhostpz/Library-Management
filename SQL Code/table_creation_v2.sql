create table shelf
(
  sh_id char(4) not null,
  sh_no int not null,
  sh_row int not null,
  primary key (sh_id)
);

create table usertype
(
  ut_id char(4) not null, 
  ut_role varchar(20) not null, 
  primary key (ut_id)
);

create table author
(
  au_id char(7) not null, 
  au_name varchar(100) not null, 
  primary key (au_id)
);

create table material 
(
  mt_id char(9) not null,
  mt_title varchar(200) not null,
  mt_isbn varchar(20),
  mt_publisher varchar(100) not null,
  mt_publish_date date not null, 
  mt_edition int ,
  mt_pageno int,
  mt_sh_id char(4) not null,
  mt_price decimal(8, 2) not null,
  mt_type varchar(15),
  primary key (mt_id),
  foreign key (mt_sh_id) references shelf(sh_id)
);

create table material_copy
(
  mc_id char(10) not null,
  mc_mt_id char(10) not null,
  mc_status varchar(9) not null check(mc_status in ('Borrowed','Lost','Available')), 
  primary key (mc_id),
  foreign key (mc_mt_id) references material(mt_id)
);

create table member
(
  mb_id char(10) not null, 
  mb_name varchar(80) not null, 
  mb_gender char(1) not null check(mb_gender in ('M','F')), 
  mb_email varchar(100) not null, 
  mb_contact_no varchar(11) not null, 
  mb_password varchar(20) not null, 
  mb_dob date not null, 
  mb_pin int not null, 
  mb_material_borrowed int not null, 
  mb_type_id char(4) not null, 
  mb_availability varchar(7) check (mb_availability  in ('Offline','Online')) DEFAULT 'Offline', /* new added, new attributes */
  primary key (mb_id),
  foreign key (mb_type_id) references usertype(ut_id)
);

create table issue
(
  is_id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), /* new changes, datatype to (int) and autoincrement */
  is_mc_id char(10) not null,
  is_mb_id char(10) not null,
  is_issue_date date not null,
  is_due_date date not null,
  is_return_date date,
  is_fine decimal(8, 2),
  primary key(is_id),
  foreign key (is_mc_id) references material_copy(mc_id),
  foreign key (is_mb_id) references member(mb_id)
);

create table link_author_material
(
  lnk_mt_id char(10) not null,
  lnk_au_id char(7) not null,
  primary key (lnk_mt_id, lnk_au_id),
  foreign key (lnk_mt_id) references material(mt_id),
  foreign key (lnk_au_id) references author(au_id)
);
