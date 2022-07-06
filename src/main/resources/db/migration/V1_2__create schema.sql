INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-01','Елена Прекрасная','elena@mail.ru', 'elena01',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-02','Игорь Рюрикович','igor@mail.ru', 'igor01',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-02','Ольга Подольская','olga@mail.ru', 'olga01',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-02','Александр Рябчинский','rialex@rambler.ru', 'rialex01',null,null);
INSERT INTO posts(is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) VALUES(1, 'ACCEPTED',1,1, '2022-07-05-12-01-01','Классы и методы','Поговорим об интерфейсах',2);
INSERT INTO tags(name) VALUES('Java');
INSERT INTO tags(name) VALUES('HTML');
INSERT INTO tags(name) VALUES('CSS');
INSERT INTO tags(name) VALUES('Spring');