DROP TABLE IF EXISTS `conferences`.`user_event_presence`;
DROP TABLE IF EXISTS `conferences`.`reports`;
DROP TABLE IF EXISTS `conferences`.`events`;
DROP TABLE IF EXISTS `conferences`.`users`;
DROP TABLE IF EXISTS `conferences`.`roles`;
DROP TABLE IF EXISTS `conferences`.`addresses`;
DROP TABLE IF EXISTS `conferences`.`topics`;


CREATE TABLE `conferences`.`roles`
(
    `id`   INT                NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(45) UNIQUE NOT NULL
);



CREATE TABLE `conferences`.`users`
(
    `id`        INT          NOT NULL AUTO_INCREMENT,
    `firstName` VARCHAR(45)  NULL,
    `lastName`  VARCHAR(45)  NULL,
    `email`     VARCHAR(45)  NOT NULL,
    `password`  VARCHAR(100) NULL,
    `id_role`   INT          NOT NULL DEFAULT 3,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    CONSTRAINT `user_roleId`
        FOREIGN KEY (`id_role`)
            REFERENCES `conferences`.`roles` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE `conferences`.`topics`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`, `name`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

CREATE TABLE `conferences`.`addresses`
(
    `id`             INT         NOT NULL AUTO_INCREMENT,
    `country`        VARCHAR(45) NOT NULL,
    `city`           VARCHAR(45) NOT NULL,
    `street`         VARCHAR(45) NOT NULL,
    `numberBuilding` INT         NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `conferences`.`events`
(
    `id`          INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45) NOT NULL,
    `date`        TIMESTAMP   NOT NULL,
    `description` VARCHAR(65),
    `id_address`  INT         NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
    INDEX `fk_events_adresses_idx` (`id_address` ASC) VISIBLE,
    CONSTRAINT `fk_events_adresses`
        FOREIGN KEY (`id_address`)
            REFERENCES `conferences`.`addresses` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE `conferences`.`reports`
(
    `id`         INT NOT NULL AUTO_INCREMENT,
    `id_event`   INT NOT NULL,
    `id_speaker` INT NOT NULL,
    `id_topic`   INT NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_topic_UNIQUE` (`id_topic` ASC) VISIBLE,
    INDEX `fk_reports_event_idx` (`id_event` ASC) VISIBLE,
    INDEX `fk_reports_users_idx` (`id_speaker` ASC) VISIBLE,
    CONSTRAINT `fk_reports_topics`
        FOREIGN KEY (`id_topic`)
            REFERENCES `conferences`.`topics` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_reports_event`
        FOREIGN KEY (`id_event`)
            REFERENCES `conferences`.`events` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_reports_users`
        FOREIGN KEY (`id_speaker`)
            REFERENCES `conferences`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE `conferences`.`user_event_presence`
(
    `id_user`  INT     NOT NULL,
    `id_event` INT     NOT NULL,
    `presence` TINYINT NULL DEFAULT 0,
    PRIMARY KEY (`id_user`, `id_event`),
    INDEX `presence_event_idx` (`id_event` ASC) VISIBLE,
    CONSTRAINT `presence_event`
        FOREIGN KEY (`id_event`)
            REFERENCES `conferences`.`events` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `presence_user`
        FOREIGN KEY (`id_user`)
            REFERENCES `conferences`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);





