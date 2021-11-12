create table message
(
    message_id   bigserial  not null,
    message_text varchar(2048) not null,
    message_time timestamp not null,
    room_id      int8 not null,
    user_id      int8 not null,
    read         boolean not null,
    deleted      boolean not null,
    primary key (message_id)
);
create table bot_reactions
(
    reaction_id  bigserial not null,
    output_message varchar(2048) not null,
    primary key (reaction_id)
);
create table roles
(
    role varchar(16) not null,
    primary key (role)
);
create table room
(
    room_id   bigserial not null,
    owner_id  int8   not null,
    room_name varchar(255)  not null,
    type_room varchar(16) not null,
    primary key (room_id)
);
create table room_list
(
    user_id  int8 not null,
    ban_time timestamp,
    roles    varchar(16)  not null,
    room_id  int8 not null,
    primary key (user_id, room_id)
);

create table users
(
    user_id       bigserial not null,
    active boolean not null,
    user_login    varchar(32) unique not null,
    user_name     varchar(64)  not null,
    user_password varchar(32) not null,
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
