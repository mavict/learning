use test;

drop table goods;
drop table category;

create table category(
   cid int not null auto_increment,
   /* ������� */
   ctype varchar(200),
   primary key (cid)
);

create table goods(
   gid int not null auto_increment,
   /* ��Ʒ���� */
   gname varchar(200),
   /* ��Ʒ�۸� */
   gprice double,
   /* ��ƷͼƬ */
   gpic varchar(100),
   /* �򵥽���*/
   gremark varchar(200),
   /* ��ϸ���� */
   gxremark varchar(200),
   /* �������id */
   cid int,
   primary key (gid),
   foreign key (cid) references category(cid)
);
SELECT * FROM goods;
SELECT * FROM category;
INSERT INTO category (ctype) values ('��ʿ����');
UPDATE goods set gpic='1.jpg';
