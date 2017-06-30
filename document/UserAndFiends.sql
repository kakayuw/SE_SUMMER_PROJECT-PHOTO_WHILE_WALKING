DROP DATABASE if exists photo_walking;
create DATABASE photo_walking;
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/6/30 10:17:26                           */
/*==============================================================*/


drop table if exists Friend;

drop table if exists User;

/*==============================================================*/
/* Table: Friend                                                */
/*==============================================================*/
create table Friend
(
   fid                  int not null auto_increment,
   uid1                 int not null,
   uid2                 int not null,
   remark               varchar(100),
   auth                 smallint,
   primary key (fid)
);

/*==============================================================*/
/* Table: User                                                  */
/*==============================================================*/
create table User
(
   uid                  int not null auto_increment,
   username             varchar(100) unique,
   password             varchar(100),
   email                varchar(100) unique,
   phone                varchar(100),
   primary key (uid)
);

alter table Friend add constraint FK_Friend foreign key (uid1)
      references User (uid) on delete restrict on update restrict;

alter table Friend add constraint FK_Friend2 foreign key (uid2)
      references User (uid) on delete restrict on update restrict;

