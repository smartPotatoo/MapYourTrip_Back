DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS users_picture;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS schedules_date;
DROP TABLE IF EXISTS schedules_time;

CREATE TABLE `users` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(50) UNIQUE NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL,
  `updated_at` datetime,
  `created_at` datetime NOT NULL
)engine=InnoDB;;

CREATE TABLE `users_picture` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `original_file_name` varchar(100) NOT NULL,
  `file_path` varchar(200) NOT NULL,
  `file_size` varchar(45) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime
)engine=InnoDB;;

CREATE TABLE `schedules` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `trip_name` varchar(30) NOT NULL,
  `address` varchar(200) NOT NULL,
  `start_date` varchar(20) NOT NULL,
  `end_date` varchar(20) NOT NULL
)engine=InnoDB;;

CREATE TABLE `schedules_date` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `schedules_id` integer NOT NULL,
  `date` varchar(20) NOT NULL,
  `content` text
)engine=InnoDB;;

CREATE TABLE `schedules_time` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `schedules_date_id` integer NOT NULL,
  `start_time` varchar(20) NOT NULL,
  `end_time` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(200) NOT NULL,
  `x` varchar(200) NOT NULL,
  `y` varchar(200) NOT NULL
)engine=InnoDB;;

ALTER TABLE `schedules` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`username`)  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `users_picture` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`username`)  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `schedules_date` ADD FOREIGN KEY (`schedules_id`) REFERENCES `schedules` (`id`)  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `schedules_time` ADD FOREIGN KEY (`schedules_date_id`) REFERENCES `schedules_date` (`id`)  ON DELETE CASCADE ON UPDATE CASCADE;
