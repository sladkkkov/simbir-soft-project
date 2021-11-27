
create table message
(
    message_id   bigserial not null,
    deleted      boolean,
    message_text varchar(255),
    message_time timestamp,
    read         boolean,
    room_id      int8,
    user_id      int8,
    primary key (message_id)
);
create table roles
(
    role varchar(255) not null,
    primary key (role)
);
create table room
(
    room_id   bigserial not null,
    owner_id  int8,
    room_name varchar(255),
    type_room varchar(255),
    primary key (room_id)
);
create table room_list
(
    user_id  int8 not null,
    ban_time timestamp,
    roles    varchar(255),
    room_id  int8 not null,
    primary key (user_id,room_id)
);
create table users
(
    user_id       bigserial not null,
    role          varchar(255),
    status        varchar(255),
    login    varchar(255),
    name     varchar(255),
    password varchar(255),
    primary key (user_id)
);
alter table message
    add constraint fk_room_id foreign key (room_id) references room;
alter table message
    add constraint fk_user_id foreign key (user_id) references users;
alter table room_list
    add constraint fk_roles foreign key (roles) references roles;
alter table room_list
    add constraint fk_room_id foreign key (room_id) references room;
alter table room_list
    add constraint fk_users_id foreign key (user_id) references users;

