create table publish_question
(
	id int auto_increment,
	title varchar(50),
	content text,
	tag varchar(150),
	gmt_create bigint,
	gmt_modified bigint,
	view_count int default 0,
	like_count int default 0,
	creator int,
	comment_count int default 0,
	constraint publish_question_pk
		primary key (id)
);

