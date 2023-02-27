create database blog character set utf8 collate utf8_general_ci;
use blog;

drop table if exists tb_sys_user;
create table tb_sys_user(
	id int not null auto_increment,
	account varchar(64) not null comment '账号',
	password varchar(64) not null comment '密码',
	admin bit(1) null default null  comment '是否为管理员',
	avatar varchar(255) null default null comment '头像',
	email varchar(128) null default null comment '邮箱',
	mobile_phone varchar(20) null default null comment '手机号码',
	nickname varchar(64) null default null comment '昵称',
	last_login bigint null default null comment '最后一次登陆时间',
	primary key(id) using btree
);

drop table if exists tb_article_tag;
create table tb_article_tag(
	id int not null auto_increment,
	article_id int not null,
	tag_id int not null,
	primary key(id) using btree,
	index article_id(article_id) using btree,
	index tag_id(tag_id) using btree
);

drop table if exists tb_tag;
create table tb_tag(
	id int not null auto_increment,
	tag_name varchar(64) not null comment '标签名称',
	primary key(id) using btree
);

drop table if exists tb_category;
create table tb_category(
	id int not null auto_increment,
	category varchar(64) not null comment '分类名称',
	primary key(id) using btree
);


drop table if exists tb_article;
create table tb_article (
	id int not null auto_increment,
	title varchar(64) character set utf8 collate utf8_general_ci null default null comment '标题',
	summary varchar(255) character set utf8 collate utf8_general_ci null default null comment '简介',
	like_counts int null default null comment '点赞量',
	view_counts int null default null comment '浏览量',
	create_time bigint null default null comment '创建时间',
	last_edit_time bigint null default null comment '最后一次修改时间',
	author_id int null default null comment '作者id',
	content_id int null default null comment '对应内容id',
	category_id int null default null comment'分类id',
	primary key(id) using btree,
	foreign key(author_id) references tb_sys_user(id)
);

drop table if exists tb_article_content;
create table tb_article_content(
	id int not null auto_increment,
	content longtext null default null comment '文章内容',
	content_html longtext null default null comment '文章内容html',
	article_id int null default null comment '对应article的id',
	primary key(id) using btree
);
alter table tb_article_content
add foreign key(article_id) references tb_article(id);

drop table if exists tb_comment;
create table tb_comment(
	id int not null auto_increment,
	message varchar(500) null default null comment '留言内容',
	username varchar(64) null default null comment '留言人名称',
	email varchar(64) null default null comment '留言人邮箱',
	create_time bigint null default null comment '留言时间',
	parent_id int null default null comment '对应的父评论',
	level varchar(1) not null comment '层级',
	author bit(1) null default null comment '是否为博主',
	primary key(id) using btree
);

