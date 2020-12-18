drop table if exists `toDo` CASCADE;
drop table if exists `user` CASCADE;
 
create table if not exists USER (ID bigint PRIMARY KEY AUTO_INCREMENT, FULL_NAME varchar(255) not null);
create table if not exists TO_DO (ID bigint PRIMARY KEY AUTO_INCREMENT, TITLE varchar(255) not null, TASK varchar(255) not null, USER_ID bigint);