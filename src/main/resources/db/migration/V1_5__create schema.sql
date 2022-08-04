INSERT INTO global_settings(code, name, value) VALUES('MULTIUSER_MODE',  'Многопользовательский режим', 'YES');
INSERT INTO global_settings(code, name, value) VALUES('POST_PREMODERATION',  'Премодерация постов', 'YES');
INSERT INTO global_settings(code, name, value) VALUES('STATISTICS_IS_PUBLIC',  'Показывать всем статистику блога ', 'YES');
INSERT INTO posts(is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) VALUES(1, 'NEW',6,6, '2022-07-16-12-01-01','Java Script','JS часто применяется при написании frontend',0);
INSERT INTO posts(is_active, moderation_status, moderator_id, user_id, time, title, text, view_count) VALUES(1, 'DECLINED',6,6, '2022-07-16-12-01-01','JSON','JSON это формат представления данных, получающий всё более широкую популярность',0);
