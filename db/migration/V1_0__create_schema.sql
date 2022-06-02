CREATE TABLE IF NOT EXISTS `users` (

                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'is_moderator' TINYINT NOT NULL,
                        'reg_time' DATETIME NOT NULL,
                        'name' VARCHAR(255) NOT NULL,
                        'email' VARCHAR(255) NOT NULL,
                        'password' VARCHAR(255) NOT NULL,
                        'code' VARCHAR(255),
                        'photo' TEXT
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `posts` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'is_active' TINYINT NOT NULL,
                        'moderation_status' ENUM('NEW','ACCEPTED','DECLINED') NOT NULL,
                        'moderator_id' INT,
                        'user_id' INT NOT NULL,
                        'time' DATETIME NOT NULL,
                        'title' VARCHAR(255) NOT NULL,
                        'text' TEXT NOT NULL,
                        'view_count' INT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `post_votes` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'user_id' INT NOT NULL,
                        'post_id' INT NOT NULL,
                        'time' DATETIME NOT NULL,
                        'value' TINYINT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tags` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'name' VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `tag2post` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'post_id' INT NOT NULL,
                        'tag_id' INT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `post_comments` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'parent_id' INT,
                        'post_id' INT NOT NULL,
                        'user_id' INT NOT NULL,
                        'time' DATETIME NOT NULL,
                        'text' TEXT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `captcha_codes` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'time' DATETIME NOT NULL ,
                        'code' TINYTEXT NOT NULL,
                        'secret_code' TINYTEXT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE IF NOT EXISTS `global_settings` (
                        'id' INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        'code' VARCHAR(255) NOT NULL ,
                        'name' VARCHAR(255) NOT NULL ,
                        'value' VARCHAR(255) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;