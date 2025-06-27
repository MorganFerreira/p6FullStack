DROP DATABASE p6;
CREATE DATABASE p6;
USE p6;

CREATE TABLE `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `email` VARCHAR(255) UNIQUE NOT NULL,
  `name` VARCHAR(255) UNIQUE NOT NULL,
  `password` VARCHAR(255) NOT NULL
);

CREATE TABLE `stories` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` VARCHAR(2000),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `associatedUser` INT NOT NULL,
  `associatedTheme` INT NOT NULL
);

CREATE TABLE `themes` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `content` VARCHAR(2000) NOT NULL
);

CREATE TABLE `comments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `content` VARCHAR(2000) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `associatedUser` INT NOT NULL,
  `associatedStory` INT NOT NULL
);

CREATE TABLE `subscriptions` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT, 
  `theme_id` INT
);

ALTER TABLE `stories` ADD FOREIGN KEY (`associatedUser`) REFERENCES `users` (`id`);
ALTER TABLE `stories` ADD FOREIGN KEY (`associatedTheme`) REFERENCES `themes` (`id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`associatedUser`) REFERENCES `users` (`id`);
ALTER TABLE `comments` ADD FOREIGN KEY (`associatedStory`) REFERENCES `stories` (`id`);

ALTER TABLE `subscriptions` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
ALTER TABLE `subscriptions` ADD FOREIGN KEY (`theme_id`) REFERENCES `themes` (`id`);

INSERT INTO users (email, name, password) VALUES
  ('test1@gmail.com', 'test1', 'test1test1'),
  ('test2@gmail.com', 'test2', 'test2test2'),
  ('test3@gmail.com', 'test3', 'test3test3');

INSERT INTO themes (title, content) VALUES
  ('theme1', 'contentTheme1'),
  ('theme2', 'contentTheme2'),
  ('theme3', 'contentTheme3');

INSERT INTO stories (title, content, created_at, associatedUser, associatedTheme) VALUES
  ('story1', 'contentStory1', '2025-05-17', 1, 1),
  ('story2', 'contentStory2', '2025-05-17', 2, 2),
  ('story3', 'contentStory3', '2025-05-17', 3, 3);
  
INSERT INTO comments (content, created_at, associatedUser, associatedStory) VALUES
  ('contentComment1', '2025-05-17', 1, 1),
  ('contentComment2', '2025-05-17', 1, 2),
  ('contentComment3', '2025-05-17', 1, 2);
  
INSERT INTO SUBSCRIPTIONS (user_id, theme_id) VALUES
  (1, 1),
  (1, 2),
  (2, 1);