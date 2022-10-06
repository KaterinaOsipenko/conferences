CREATE SCHEMA IF NOT EXIST `conferences` DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS `conferences`.`roles`;

CREATE TABLE IF NOT EXISTS`conferences`.`roles` (
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);

DROP TABLE IF EXISTS `conferences`.`users`;

CREATE TABLE `conferences`.`users` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `firstName` VARCHAR(45) NULL,
                                       `lastName` VARCHAR(45) NULL,
                                       `email` VARCHAR(45) NULL,
                                       `password` VARCHAR(45) NULL,
                                       `roleId` VARCHAR(45) NULL,
                                       PRIMARY KEY (`id`),
                                       UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                       CONSTRAINT `user_roleId`
                                           FOREIGN KEY (`id`)
                                               REFERENCES `conferences`.`roles` (`id`)
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE);



