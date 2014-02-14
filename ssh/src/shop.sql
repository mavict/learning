use test;

drop table goods;
drop table category;

create table category(
   cid int not null auto_increment,
   /* 类别名称 */
   ctype varchar(200),
   primary key (cid)
);

create table goods(
   gid int not null auto_increment,
   /* 商品名称 */
   gname varchar(200),
   /* 商品价格 */
   gprice double,
   /* 商品图片 */
   gpic varchar(100),
   /* 简单介绍*/
   gremark varchar(200),
   /* 详细介绍 */
   gxremark varchar(200),
   /* 所属类别id */
   cid int,
   primary key (gid),
   foreign key (cid) references category(cid)
);
SELECT * FROM goods;
SELECT * FROM category;
INSERT INTO category (ctype) values ('男士休闲');
UPDATE goods set gpic='1.jpg';
