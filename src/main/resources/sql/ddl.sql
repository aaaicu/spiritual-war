create table spiritual_war.game_setting
(
    idx                  int unsigned auto_increment
        primary key,
    total_round          int default 5  not null comment '총 라운드 수',
    afternoon_minute     int default 8  not null comment '낮 시간',
    night_minute         int default 4  not null comment '밤 시간',
    holiday_night_minute int default 12 not null comment '대명절의 밤 시간',
    citizen              int unsigned   not null comment '시민 인원 수',
    devil                int unsigned   not null comment '악마 인원 수',
    create_dt            datetime       not null comment '생성시간'
)
    charset = utf8;

create table spiritual_war.game
(
    idx              int unsigned auto_increment comment '게임 IDX'
        primary key,
    game_setting_idx int unsigned not null comment '게임세팅',
    create_dt        datetime     null comment '생성시간',
    start_dt         datetime     null comment '시작시간',
    end_dt           datetime     null comment '종료시간',
    constraint game_game_setting_idx_fk
        foreign key (game_setting_idx) references spiritual_war.game_setting (idx)
            on update cascade on delete cascade
)
    charset = utf8;

create table spiritual_war.game_round
(
    idx           int unsigned auto_increment
        primary key,
    game_idx      int unsigned         not null,
    sorting_order int                  not null comment '순서',
    holiday_yn    tinyint(1) default 0 not null comment '대명절 여부',
    success_yn    tinyint(1)           null comment '시민 성공 실패 여부',
    start_dt      datetime             null comment '시작 시간',
    end_dt        datetime             null comment '종료 시간',
    constraint game_round_round_idx_uindex
        unique (idx),
    constraint game_round_game_idx_fk
        foreign key (game_idx) references spiritual_war.game (idx)
            on update cascade on delete cascade
)
    charset = utf8;

create table spiritual_war.room
(
    idx          int unsigned auto_increment
        primary key,
    room_name    varchar(20)             not null,
    max_capacity int unsigned default 10 not null comment '최대 수용인원'
)
    charset = utf8;

create table spiritual_war.room_game_setting
(
    idx              int unsigned auto_increment
        primary key,
    game_setting_idx int unsigned           not null comment '게임 세팅	IDX',
    room_idx         int unsigned           not null comment '방 IDX',
    capacity         int unsigned default 5 not null comment '게임 내 방 정원',
    constraint room_game_setting_idx_uindex
        unique (idx),
    constraint room_game_setting_game_setting_idx_fk
        foreign key (game_setting_idx) references spiritual_war.game_setting (idx),
    constraint room_game_setting_room_idx_fk
        foreign key (room_idx) references spiritual_war.room (idx)
            on update cascade on delete cascade
)
    charset = utf8;

create table spiritual_war.user
(
    idx           int unsigned auto_increment comment '사용자 IDX'
        primary key,
    user_id       varchar(20)          not null comment '사용자 ID',
    user_password varchar(50)          not null comment '사용자 비밀번호',
    user_name     varchar(10)          null comment '사용자 이름',
    manager_yn    tinyint(1) default 0 not null,
    constraint user_idx_uindex
        unique (idx),
    constraint user_user_id_uindex
        unique (user_id)
)
    charset = utf8;

create table spiritual_war.game_participation
(
    idx                int unsigned auto_increment comment '게임 참여 IDX'
        primary key,
    game_idx           int unsigned                  not null comment '게임 IDX',
    user_idx           int unsigned                  not null comment '사용자 IDX',
    best_friend_idx    int unsigned                  null comment '커플 IDX',
    participation_role varchar(10) default 'CITIZEN' not null comment '역할 : CITIZEN / DEVIL',
    hired_dt           datetime                      null comment '매수 당한 시간',
    hire_devil_idx     int unsigned                  null,
    create_dt          datetime                      null comment '생성일자',
    fan_idx            int unsigned                  null,
    holy_watching_idx  int unsigned                  null comment '감시 대상 IDX',
    constraint game_participation_idx_uindex
        unique (idx),
    constraint game_participation_pk
        unique (game_idx, user_idx),
    constraint game_participation_game_idx_fk
        foreign key (game_idx) references spiritual_war.game (idx)
            on update cascade on delete cascade,
    constraint game_participation_game_participation_idx_fk
        foreign key (best_friend_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade,
    constraint game_participation_game_participation_idx_fk_2
        foreign key (hire_devil_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade,
    constraint game_participation_game_participation_idx_fk_3
        foreign key (fan_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade,
    constraint game_participation_game_participation_idx_fk_4
        foreign key (holy_watching_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade,
    constraint game_participation_user_idx_fk
        foreign key (user_idx) references spiritual_war.user (idx)
            on update cascade on delete cascade
)
    charset = utf8;

create table spiritual_war.devil_code
(
    idx               int unsigned auto_increment
        primary key,
    participation_idx int unsigned                         null,
    devil_code        varchar(10)                          null,
    register_yn       tinyint(1) default 0                 null,
    expire_dt         datetime                             null,
    create_dt         datetime   default CURRENT_TIMESTAMP null,
    constraint devil_code_hired_code_uindex
        unique (devil_code),
    constraint devil_code_idx_uindex
        unique (idx),
    constraint devil_code_game_participation_idx_fk
        foreign key (participation_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade
);

create table spiritual_war.room_hist
(
    idx               int unsigned auto_increment
        primary key,
    game_idx          int unsigned not null,
    game_round_idx    int unsigned not null,
    participation_idx int unsigned not null,
    room_idx          int unsigned not null,
    constraint room_hist_idx_uindex
        unique (idx),
    constraint room_hist_game_idx_fk
        foreign key (game_idx) references spiritual_war.game (idx)
            on update cascade on delete cascade,
    constraint room_hist_game_participation_idx_fk
        foreign key (participation_idx) references spiritual_war.game_participation (idx)
            on update cascade on delete cascade,
    constraint room_hist_room_idx_fk
        foreign key (room_idx) references spiritual_war.room (idx)
            on update cascade on delete cascade,
    constraint room_hist_game_round_idx_fk
        foreign key (room_idx) references spiritual_war.game_round (idx)
            on update cascade on delete cascade
)
    charset = utf8;

