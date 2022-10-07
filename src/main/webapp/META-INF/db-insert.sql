SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `conferences`.`roles`;
TRUNCATE TABLE `conferences`.`users`;
TRUNCATE TABLE `conferences`.`topics`;
TRUNCATE TABLE `conferences`.`addresses`;
TRUNCATE TABLE `conferences`.`events`;
TRUNCATE TABLE reports;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `conferences`.`roles` (`name`)
VALUES ('moderator');
INSERT INTO `conferences`.`roles` (`name`)
VALUES ('speaker');
INSERT INTO `conferences`.`roles` (`name`)
VALUES ('user');

INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Катя', 'Brown', 'megan.brown@gmail.com', '123456789', '1');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Lily', 'Holms', 'lily.holms123@gmail.com', '4567890', '2');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Bob', 'Backer', 'bob.baker@gmail.com', '67890', '3');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Mile', 'Addison', 'mike.addison345@gmail.com', '567890', '2');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Luke', 'Sloan', 'luke.sloan68@gmail.com', '5678', '3');

INSERT INTO `conferences`.`topics` (`name`)
VALUES ('Medicine Facts');
INSERT INTO `conferences`.`topics` (`name`)
VALUES ('Computer Networking');
INSERT INTO `conferences`.`topics` (`name`)
VALUES ('Programming in mideicine ');
INSERT INTO `conferences`.`topics` (`name`)
VALUES ('Hands for soldiers');
INSERT INTO `conferences`.`topics` (`name`)
VALUES ('Computer Science');

INSERT INTO `conferences`.`addresses` (`country`, `city`, `street`, `numberBuilding`)
VALUES ('Ukraine', 'Kyiv', 'Khreshatyk', '25');
INSERT INTO `conferences`.`addresses` (`country`, `city`, `street`, `numberBuilding`, `numberApartment`)
VALUES ('Ukraine', 'Lviv', 'Square', '13', '2');
INSERT INTO `conferences`.`addresses` (`country`, `city`, `street`, `numberBuilding`, `numberApartment`)
VALUES ('Great Britain ', 'London', 'Piccadilly', '24', '56');
INSERT INTO `conferences`.`addresses` (`country`, `city`, `street`, `numberBuilding`, `numberApartment`)
VALUES ('USA', 'New Yourk', 'Manhetten', '9', '67');

INSERT INTO `conferences`.`events` (`name`, `date`, `id_address`)
VALUES ('Medicine today', '25.10.2022', '1');
INSERT INTO `conferences`.`events` (`name`, `date`, `id_address`)
VALUES ('Computer Science', '23.5.2023', '2');

INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('1', '2', '1');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('2', '4', '2');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('1', '4', '3');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('2', '2', '4');
