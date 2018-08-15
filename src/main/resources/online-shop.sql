create database online_shop_db  character set utf8 collate utf8_general_ci;
use online_shop_db;

create table user(
  user_id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null ,
  password varchar(255) not null ,
  img_url varchar(255) not null ,
  balance double not null ,
  role varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  cat_id int not null auto_increment primary key ,
  category_name varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product(
  product_id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  price double not null ,
  quantity int not null ,
  created_date datetime not null,
  product_img_url varchar(255) not null ,
  user_id int not null ,
  foreign key (user_id) references user(user_id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table image(
  id int not null auto_increment primary key ,
  image varchar(255) not null ,
  product_id int not null ,
  foreign key (product_id) references product(product_id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category_attribute(
  attr_id int not null auto_increment primary key ,
  name varchar(255) not null ,
  category_id int not null ,
  foreign key (category_id) references category(cat_id)  on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table attribute_value(
  id int not null auto_increment primary key ,
  value varchar(255) not null ,
  category_attribute_id int not null ,
  product_id int not null ,
  foreign key (category_attribute_id) references category_attribute(attr_id)on delete cascade ,
  foreign key (product_id) references product(product_id)on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product_category(
  product_id int not null ,
  category_id int not null ,
  foreign key (product_id) references product(product_id) on delete cascade ,
  foreign key (category_id) references category(cat_id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product_cart(
  user_id int not null ,
  product_id int not null ,
  foreign key (user_id) references user(user_id) on delete cascade ,
  foreign key (product_id) references product(product_id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table product_order(
  id int not null auto_increment primary key ,
  user_id int not null ,
  product_id int not null ,
  count int not null ,
  foreign key (user_id) references user(user_id) on delete cascade ,
  foreign key (product_id) references product(product_id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;


create table reviews(
  id int not null auto_increment primary key ,
  review_text text not null ,
  rating double not null ,
  send_date date not null,
  user_id int not null ,
  product_id int not null ,
  foreign key (user_id) references user(user_id) on delete cascade ,
  foreign key (product_id) references product(product_id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

insert into user(name, surname, username, password, img_url, role) VALUES
  ('Admin','Admin','admin','admin','admin/admin.png','ADMIN');

insert into category(category_name)values
  ('Smartphones'),
  ('Cell phones'),
  ('Men clothing'),
  ('Women clothing'),
  ('Motorcycles'),
  ('Cars'),
  ('Toys');


insert into category_attribute(name, category_id) values
  ('ram',1),
  ('memory',2),
  ('memory',1),
  ('camera',1),
  ('size',3),
  ('size',4),
  ('condition',1),
  ('condition',2),
  ('condition',5),
  ('condition',6),
  ('condition',9),
  ('horsepower',5),
  ('horsepower',6);
