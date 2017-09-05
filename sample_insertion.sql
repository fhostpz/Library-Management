
create table material (mt_id char(9) not null,mt_title varchar(200) not null,mt_isbn varchar(20),mt_publisher varchar(100) not null,mt_publish_date date not null, mt_edition int ,mt_pageno int,mt_sh_id char(4) not null,mt_price decimal(8, 2) not null,mt_type varchar(15),primary key (mt_id),foreign key (mt_sh_id) references shelf(sh_id))
insert into material values ('MT0000001','An Introduction to General, Organic, and Biological Chemistry (12th Edition)','9780321908445','Pearson','2014-01-01',12,'','sh01',1036.88,'Chemistry')
insert into material values ('MT0000002','Basic Chemistry (5th Edition)','9780134138046','Pearson','2016-01-01',5,'','sh01',906.53,'Chemistry')
insert into material values ('MT0000003','Intro to Java Programming, Comprehensive Version (10th Edition)','9780133761313','Pearson','2014-01-01',10,'','sh01',745.24,'Programming')
insert into material values ('MT0000004','Operating System Concepts','9781118063330','Wiley','2012-12-01',NULL,'sh01',723.26,'')


             
create table material_copy(mc_id char(10) not null,mc_mt_id char(10) not null,mc_status varchar(9) not null check( mc_status in ('Borrowed','Lost','Available')), primary key (mc_id),foreign key (mc_mt_id) references material(mt_id))
insert into material_copy values ('MC00000001','MT0000001','Available')
insert into material_copy values ('MC00000002','MT0000002','Available')
insert into material_copy values ('MC00000003','MT0000003','Available')
insert into material_copy values ('MC00000004','MT0000004','Available')
insert into material_copy values ('MC00000005','MT0000001','Available')



create table shelf(sh_id char(4) not null,sh_no int not null,sh_row int not null,primary key (sh_id))
insert into shelf values ('SH01',1,1)
insert into shelf values ('SH02',1,2)
insert into shelf values ('SH03',1,3)
insert into shelf values ('SH04',2,1)
insert into shelf values ('SH05',2,2)
insert into shelf values ('SH06',2,3)

create table usertype(ut_id char(4) not null, ut_role varchar(20) not null, primary key (ut_id))

insert into usertype values ('UT01', 'student')
insert into usertype values ('UT02', 'academic staff')
insert into usertype values ('UT03', 'administrative staff')
insert into usertype values ('UT04', 'librarian')

create table issue(is_id char(10) not null,is_mc_id char(10) not null,is_mb_id char(10) not null,is_issue_date date not null,is_due_date date not null,is_return_date date,is_fine decimal(8, 2),primary key(is_id),foreign key (is_mc_id) references material_copy(mc_id),foreign key (is_mb_id) references member(mb_id))
insert into issue values ('IS00000001','MC00000001','1000000005','2017-01-01','2017-01-30','',NULL)
insert into issue values ('IS00000001','MC00000002','1000000004','2017-01-02','2017-01-30','',NULL)
insert into issue values ('IS00000001','MC00000004','1000000005','2017-01-03','2017-01-30','',NULL)


create table author(au_id char(7) not null, au_name varchar(100) not null, primary key (au_id))

insert into author values('AU00001', 'Karen C. Timberlake')
insert into author values('AU00002', 'Greg Gagne')
insert into author values('AU00003', 'Peter Baer Galvin')
insert into author values('AU00004', 'Abraham Silberschatzn')
insert into author values('AU00006', 'Y. Daniel Liang')
insert into author values('AU00007', 'Zed Shaw')

create table link_author_material(lnk_mt_id char(10) not null,lnk_au_id char(7) not null,primary key (lnk_mt_id, lnk_au_id),foreign key (lnk_mt_id) references material(mt_id),foreign key (lnk_au_id) references author(au_id))

insert into link_author_material values ('MT0000001','AU00001')
insert into link_author_material values ('MT0000002','AU00001')
insert into link_author_material values ('MT0000003','AU00006')
insert into link_author_material values ('MT0000004','AU00002')
insert into link_author_material values ('MT0000004','AU00003')
insert into link_author_material values ('MT0000004','AU00004')

                                 
create table member(mb_id char(10) not null, mb_name varchar(80) not null, mb_gender char(1) not null check(mb_gender in ('M','F')), mb_email varchar(100) not null, mb_contact_no varchar(11) not null, mb_password varchar(20) not null, mb_dob date not null, mb_pin int not null, mb_material_borrowed int not null, mb_type_id char(4) not null, primary key (mb_id),foreign key (mb_type_id) references usertype(ut_id))

insert into member values('1000000001', 'Arul', 'M', 'arul@e97a.com', '01010001000', '1234_abcd', '1997-01-01', 123456, 0, 'UT01')
insert into member values('1000000002', 'Bear', 'M', 'bear@e97a.com', '01012001200', '1234_abcd', '1997-02-01', 123456, 0, 'UT01')
insert into member values('1000000003', 'Chia', 'F', 'chia@e97a.com', '01010301030', '1234_abcd', '1997-03-01', 123456, 0, 'UT01')
insert into member values('1000000004', 'Dana', 'F', 'dana@e97a.com', '01040404040', '1234_abcd', '1997-04-01', 123456, 0, 'UT01')
insert into member values('1000000005', 'Ezra', 'M', 'ezra@e97a.com', '01055005500', '1234_abcd', '1997-05-01', 123456, 0, 'UT01')
