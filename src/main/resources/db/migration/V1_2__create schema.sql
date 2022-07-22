INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-01','Елена Прекрасная','elena@mail.ru', '$2a$12$VXoLoory05dhneIYI3PXzueem98VdbAbPlEaycmU4Lhy6cJLlP0V6',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-02','Игорь Рюрикович','igor@mail.ru', '$2a$12$VXoLoory05dhneIYI3PXzueem98VdbAbPlEaycmU4Lhy6cJLlP0V6',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(0, '2022-07-05-12-00-02','Ольга Подольская','olga@mail.ru', '$2a$12$VXoLoory05dhneIYI3PXzueem98VdbAbPlEaycmU4Lhy6cJLlP0V6',null,null);
INSERT INTO users(is_moderator, reg_time, name, email, password, code, photo) VALUES(1, '2022-07-05-12-00-02','Александр Рябчинский','rialex@rambler.ru', '$2a$12$VXoLoory05dhneIYI3PXzueem98VdbAbPlEaycmU4Lhy6cJLlP0V6',null,null);
INSERT INTO posts(is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) VALUES(1, 'ACCEPTED',1,1, '2022-07-05-12-01-01','Классы и методы','Поговорим об интерфейсах',2);
INSERT INTO tags(name) VALUES('Java');
INSERT INTO tags(name) VALUES('HTML');
INSERT INTO tags(name) VALUES('CSS');
INSERT INTO tags(name) VALUES('Spring');