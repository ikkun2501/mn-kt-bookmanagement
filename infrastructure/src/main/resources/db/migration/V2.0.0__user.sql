create table user
(
	user_id int auto_increment comment 'ユーザID'
		primary key,
	created_at datetime default CURRENT_TIMESTAMP not null comment '作成日時'
)
comment 'ユーザ';

create table user_authentication
(
	user_id int not null comment 'ユーザID'
		primary key,
	login_id varchar(20) not null comment 'ログインID',
	password varchar(60) not null comment 'パスワード(BCryptで暗号化されたハッシュ値)',
	created_at datetime default CURRENT_TIMESTAMP not null comment '作成日時',
	constraint user_authentication_login_id_uindex
		unique (login_id),
	constraint user_authentication_user_user_id_fk
		foreign key (user_id) references user (user_id)
)
comment 'ユーザ認証';

create table user_authorization
(
	user_id int not null comment 'ユーザID',
	user_role varchar(20) not null comment 'ユーザロール',
	created_at datetime default CURRENT_TIMESTAMP not null comment '作成日時',
		constraint user_authorization_pk
		primary key (user_id,user_role),
	constraint user_authorization_user_user_id_fk
		foreign key (user_id) references user (user_id)
)
comment 'ユーザ認可';

create table user_detail
(
	user_id int not null comment 'ユーザID'
		primary key,
	user_name varchar(255) null comment 'ユーザ名',
	birthday date not null comment '誕生日',
	created_at datetime default CURRENT_TIMESTAMP not null comment '作成日時',
	constraint user_detail_user_user_id_fk
		foreign key (user_id) references user (user_id)
)
comment 'ユーザ詳細';

