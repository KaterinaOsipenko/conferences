SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `conferences`.`roles`;
TRUNCATE TABLE `conferences`.`users`;
TRUNCATE TABLE `conferences`.`topics`;
TRUNCATE TABLE `conferences`.`addresses`;
TRUNCATE TABLE `conferences`.`events`;
TRUNCATE TABLE reports;
TRUNCATE TABLE user_event_presence;

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
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`)
VALUES ('Bob', 'Backer', 'bob.baker@gmail.com', '67890');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`, `id_role`)
VALUES ('Mile', 'Addison', 'mike.addison345@gmail.com', '567890', '2');
INSERT INTO `conferences`.`users` (`firstName`, `lastName`, `email`, `password`)
VALUES ('Luke', 'Sloan', 'luke.sloan68@gmail.com', '5678');

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

INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Medicine today', '2022-10-25 19:00:00', 'Conference about investigations in medicine nowadays', '1');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Computer Science', '2023-05-23 18:00:00', 'Computer Technologies in our world ', '2');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Science', '2023-08-08 17:00:00', 'Science discoveries in North part of world', '3');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('CompEngineering', '2022-12-23 19:30:00', 'IT - the future of humanity.', '4');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Engineering', '2022-11-20 20:00:00', 'Engineering for students.', '4');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Art', '2023-01-23 18:30:00', 'Art therapy for injured', '3');
INSERT INTO `conferences`.`events` (`name`, `date`, `description`, `id_address`)
VALUES ('Nutrition', '2023-05-12 18:30:00', 'All secrets of healthy food', '2');

INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('1', '2', '1');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('2', '4', '2');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('1', '4', '3');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('2', '2', '4');
INSERT INTO `conferences`.`reports` (`id_event`, `id_speaker`, `id_topic`)
VALUES ('5', '5', '5');
