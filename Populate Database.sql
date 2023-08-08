DROP TABLE IF EXISTS `amenities`;
CREATE TABLE `amenities` (
  `amenityId` int NOT NULL AUTO_INCREMENT,
  `amenityName` varchar(45) NOT NULL,
  `amenityValue` double(3,2) NOT NULL,
  `amenitySuggestion` tinyint DEFAULT '1',
  PRIMARY KEY (`amenityId`,`amenityName`),
  UNIQUE KEY `amenityId_UNIQUE` (`amenityId`),
  UNIQUE KEY `amenityName_UNIQUE` (`amenityName`)
);

INSERT INTO `amenities` VALUES 
(1,'Pool',0.15,1),
(2,'Kitchen',0.05,0),
(3,'Wifi',0.10,0),
(4,'Game Console',0.15,1),
(5,'Central A/C',0.20,1),
(6,'Ceiling Fan',0.05,0),
(7,'First Aid Kit',0.10,0),
(8,'Security Cameras',0.25,1),
(9,'Refrigerator',0.05,0),
(10,'Microwave',0.05,0),
(11,'BBQ-Grill',0.15,1),
(12,'Patio',0.10,0),
(13,'Hot Tub',0.15,0),
(14,'Personal Gym',0.15,1),
(15,'Pool Table',0.10,0),
(16,'Fire Pit',0.10,1),
(17,'Wine Rack',0.15,1),
(18,'Washing Machine',0.05,0),
(19,'Dryer',0.05,0),
(20,'Fire Extinguisher',0.05,0);

DROP TABLE IF EXISTS `booked`;
CREATE TABLE `booked` (
  `date` date NOT NULL,
  `listingId` int NOT NULL,
  `bookedBy` varchar(32) NOT NULL,
  `reservationUsed` tinyint NOT NULL,
  `price` double(10,2) NOT NULL,
  PRIMARY KEY (`date`,`listingId`)
);

INSERT INTO `booked` (date,listingId,bookedBy,reservationUsed,price) VALUES 
('2023-08-07', 2, 'david66', 1, 600),
('2023-08-08', 2, 'natalie45', 1, 600),
('2023-08-09', 2, 'ryan78', 2, 600),
('2023-08-10', 2, 'chris77', 3, 600),
('2023-08-11', 4, 'sara82', 0, 1900),
('2023-08-12', 4, 'alex55', 1, 1900),
('2023-08-13', 4, 'emily33', 2, 1900),
('2023-08-14', 4, 'lisa44', 3, 1900),
('2023-08-15', 6, 'olivia89', 1, 1200),
('2023-08-16', 6, 'sammy12', 1, 1200),
('2023-08-17', 6, 'alex551', 2, 1200),
('2023-08-18', 6, 'sara82', 3, 1200),
('2023-08-19', 8, 'laura23', 0, 550),
('2023-08-20', 8, 'david66', 1, 550),
('2023-08-21', 8, 'natalie45', 2, 550),
('2023-08-22', 8, 'ryan78', 3, 550),
('2023-08-23', 10, 'chris77', 0, 2000),
('2023-08-24', 10, 'sara82', 1, 2000),
('2023-08-25', 10, 'alex55', 2, 2000),
('2023-08-26', 10, 'emily33', 3, 2000),
('2023-08-27', 12, 'lisa44', 0, 1700),
('2023-08-28', 12, 'olivia89', 1, 1700),
('2023-08-29', 12, 'sammy12', 2, 1700),
('2023-08-30', 12, 'jane87', 3, 1700),
('2023-08-31', 14, 'jane87', 0, 400),
('2023-09-01', 14, 'laura23', 1, 400),
('2023-09-02', 14, 'david66', 2, 400),
('2023-09-03', 14, 'natalie45', 3, 400),
('2023-09-04', 16, 'ryan78', 0, 1800),
('2023-09-05', 16, 'chris77', 1, 1800),
('2023-09-06', 16, 'emily33', 2, 1800),
('2023-09-07', 16, 'alex55', 3, 1800),
('2023-09-08', 18, 'emily33', 0, 1300),
('2023-09-09', 18, 'lisa44', 1, 1300),
('2023-09-10', 18, 'olivia89', 2, 1300),
('2023-09-11', 18, 'sammy12', 3, 1300),
('2023-09-12', 20, 'mike01', 0, 600),
('2023-09-13', 20, 'jane87', 1, 600),
('2023-09-14', 20, 'laura23', 2, 600),
('2023-09-15', 20, 'david66', 3, 600),
('2023-09-16', 22, 'natalie45', 0, 1800),
('2023-09-17', 22, 'ryan78', 1, 1800),
('2023-09-18', 22, 'chris77', 2, 1800),
('2023-09-19', 22, 'sara82', 3, 1800),
('2023-09-20', 24, 'alex55', 0, 1700),
('2023-09-21', 24, 'chris77', 1, 1700),
('2023-09-22', 24, 'lisa44', 2, 1700),
('2023-09-23', 24, 'olivia89', 3, 1700),
('2023-09-24', 26, 'sammy12', 0, 1800),
('2023-09-25', 26, 'mike01', 1, 1800),
('2023-09-26', 26, 'jane87', 2, 1800),
('2023-09-27', 26, 'laura23', 3, 1800),
('2023-09-28', 28, 'david66', 0, 1700),
('2023-09-29', 28, 'natalie45', 1, 1700),
('2023-09-30', 28, 'ryan78', 2, 1700),
('2023-10-01', 28, 'sara82', 3, 1700),
('2023-10-02', 30, 'sara82', 0, 1300),
('2023-10-03', 30, 'alex55', 1, 1300),
('2023-10-04', 30, 'emily33', 2, 1300),
('2023-10-05', 30, 'sara82', 3, 1300),
('2023-10-06', 32, 'olivia89', 0, 1700),
('2023-10-07', 32, 'sammy12', 1, 1700),
('2023-10-08', 32, 'mike01', 2, 1700),
('2023-10-09', 32, 'jane87', 3, 1700),
('2023-10-10', 34, 'laura23', 0, 550),
('2023-10-11', 34, 'jane87', 1, 550),
('2023-10-12', 34, 'natalie45', 2, 550),
('2023-10-13', 34, 'ryan78', 3, 550),
('2023-10-14', 36, 'chris77', 0, 2000),
('2023-10-15', 36, 'sara82', 1, 2000),
('2023-10-16', 36, 'alex55', 2, 2000),
('2023-10-17', 36, 'emily33', 3, 2000),
('2023-10-18', 38, 'lisa44', 0, 1700),
('2023-10-19', 38, 'emily33', 1, 1700),
('2023-10-20', 38, 'sammy12', 2, 1700),
('2023-10-21', 38, 'mike01', 3, 1700),
('2023-10-22', 40, 'jane87', 0, 400),
('2023-10-23', 40, 'laura23', 1, 400),
('2023-10-24', 40, 'david66', 2, 400),
('2023-10-25', 40, 'natalie45', 3, 400),
('2023-10-26', 42, 'ryan78', 0, 1800),
('2023-10-27', 42, 'chris77', 1, 1800),
('2023-10-28', 42, 'sara82', 2, 1800),
('2023-10-29', 42, 'alex55', 3, 1800),
('2023-10-30', 44, 'emily33', 0, 1300),
('2023-10-31', 44, 'lisa44', 1, 1300),
('2023-11-01', 44, 'olivia89', 2, 1300),
('2023-11-02', 44, 'mike01', 3, 1300),
('2023-11-03', 46, 'mike01', 0, 600),
('2023-11-04', 46, 'jane87', 1, 600),
('2023-11-05', 46, 'laura23', 2, 600),
('2023-11-06', 46, 'david66', 3, 600),
('2023-11-07', 48, 'olivia89', 0, 1800),
('2023-11-08', 48, 'ryan78', 1, 1800),
('2023-11-09', 48, 'chris77', 2, 1800),
('2023-11-10', 48, 'sara82', 3, 1800),
('2023-11-11', 50, 'alex55', 0, 1700),
('2023-11-12', 50, 'emily33', 1, 1700),
('2023-11-13', 50, 'lisa44', 2, 1700),
('2023-11-14', 50, 'olivia89', 3, 1700),
('2023-11-15', 52, 'sammy12', 0, 1800),
('2023-11-16', 52, 'mike01', 1, 1800),
('2023-11-17', 52, 'jane87', 2, 1800),
('2023-11-18', 52, 'laura23', 3, 1800),
('2023-11-19', 54, 'david66', 0, 1700),
('2023-11-20', 54, 'natalie45', 1, 1700),
('2023-11-21', 54, 'ryan78', 2, 1700),
('2023-11-22', 54, 'chris77', 3, 1700),
('2023-11-23', 56, 'sara82', 0, 1300),
('2023-11-24', 56, 'alex55', 1, 1300),
('2023-11-25', 56, 'emily33', 2, 1300),
('2023-11-26', 56, 'lisa44', 3, 1300),
('2023-11-27', 58, 'olivia89', 0, 1700),
('2023-11-28', 58, 'sammy12', 1, 1700),
('2023-11-29', 58, 'mike01', 2, 1700),
('2023-11-30', 58, 'jane87', 3, 1700),
('2023-12-01', 60, 'laura23', 0, 550),
('2023-12-02', 60, 'david66', 1, 550),
('2023-12-03', 60, 'natalie45', 2, 550),
('2023-12-04', 60, 'ryan78', 3, 550);


DROP TABLE IF EXISTS `forrent`;
CREATE TABLE `forrent` (
  `listingId` int NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`listingId`,`date`)
);

INSERT INTO `forrent` (listingId, date) VALUES
(1, '2023-08-07'),
(1, '2023-08-08'),
(1, '2023-08-09'),
(1, '2023-08-10'),
(3, '2023-08-11'),
(3, '2023-08-12'),
(3, '2023-08-13'),
(3, '2023-08-14'),
(5, '2023-08-15'),
(5, '2023-08-16'),
(5, '2023-08-17'),
(5, '2023-08-18'),
(7, '2023-08-19'),
(7, '2023-08-20'),
(7, '2023-08-21'),
(7, '2023-08-22'),
(9, '2023-08-23'),
(9, '2023-08-24'),
(9, '2023-08-25'),
(9, '2023-08-26'),
(11, '2023-08-27'),
(11, '2023-08-28'),
(11, '2023-08-29'),
(11, '2023-08-30'),
(13, '2023-08-31'),
(13, '2023-09-01'),
(13, '2023-09-02'),
(13, '2023-09-03'),
(15, '2023-09-04'),
(15, '2023-09-05'),
(15, '2023-09-06'),
(15, '2023-09-07'),
(17, '2023-09-08'),
(17, '2023-09-09'),
(17, '2023-09-10'),
(17, '2023-09-11'),
(19, '2023-09-12'),
(19, '2023-09-13'),
(19, '2023-09-14'),
(19, '2023-09-15'),
(21, '2023-09-16'),
(21, '2023-09-17'),
(21, '2023-09-18'),
(21, '2023-09-19'),
(23, '2023-09-20'),
(23, '2023-09-21'),
(23, '2023-09-22'),
(23, '2023-09-23'),
(25, '2023-09-24'),
(25, '2023-09-25'),
(25, '2023-09-26'),
(25, '2023-09-27'),
(27, '2023-09-28'),
(27, '2023-09-29'),
(27, '2023-09-30'),
(27, '2023-10-01'),
(29, '2023-10-02'),
(29, '2023-10-03'),
(29, '2023-10-04'),
(29, '2023-10-05'),
(31, '2023-10-06'),
(31, '2023-10-07'),
(31, '2023-10-08'),
(31, '2023-10-09'),
(33, '2023-10-10'),
(33, '2023-10-11'),
(33, '2023-10-12'),
(33, '2023-10-13'),
(35, '2023-10-14'),
(35, '2023-10-15'),
(35, '2023-10-16'),
(35, '2023-10-17'),
(37, '2023-10-18'),
(37, '2023-10-19'),
(37, '2023-10-20'),
(37, '2023-10-21'),
(39, '2023-10-22'),
(39, '2023-10-23'),
(39, '2023-10-24'),
(39, '2023-10-25'),
(41, '2023-10-26'),
(41, '2023-10-27'),
(41, '2023-10-28'),
(41, '2023-10-29'),
(43, '2023-10-30'),
(43, '2023-10-31'),
(43, '2023-11-01'),
(43, '2023-11-02'),
(45, '2023-11-03'),
(45, '2023-11-04'),
(45, '2023-11-05'),
(45, '2023-11-06'),
(47, '2023-11-07'),
(47, '2023-11-08'),
(47, '2023-11-09'),
(47, '2023-11-10'),
(49, '2023-11-11'),
(49, '2023-11-12'),
(49, '2023-11-13'),
(49, '2023-11-14'),
(51, '2023-11-15'),
(51, '2023-11-16'),
(51, '2023-11-17'),
(51, '2023-11-18'),
(53, '2023-11-19'),
(53, '2023-11-20'),
(53, '2023-11-21'),
(53, '2023-11-22'),
(55, '2023-11-23'),
(55, '2023-11-24'),
(55, '2023-11-25'),
(55, '2023-11-26'),
(57, '2023-11-27'),
(57, '2023-11-28'),
(57, '2023-11-29'),
(57, '2023-11-30'),
(59, '2023-12-01'),
(59, '2023-12-02'),
(59, '2023-12-03'),
(59, '2023-12-04');


DROP TABLE IF EXISTS `hostcommenting`;
CREATE TABLE `hostcommenting` (
  `comment` varchar(1024) DEFAULT NULL,
  `host` varchar(32) NOT NULL,
  `tenant` varchar(32) NOT NULL,
  `rating` tinyint DEFAULT NULL,
  PRIMARY KEY (`host`,`tenant`)
);

INSERT INTO `hostcommenting` (comment,host,tenant,rating) VALUES 

('We were fortunate to have such a respectful and responsible tenant for our property. They left the place in impeccable condition, showing a high level of care.','user123','david66',3),
('The tenant was an absolute pleasure to host. Their timely communication, adherence to the house rules, and respectful behavior made their stay smooth and enjoyable.','user123','natalie45',4),
('The tenants consideration for our property was outstanding. They treated it as if it were their own, leaving no trace behind and ensuring everything was in order.','jane87','olivia89',5),
('Hosting this tenant was a breeze. Their punctuality, courteous demeanor, and cooperation throughout the day reflected their commitment to being a reliable guest.','jane87','sammy12',5);


DROP TABLE IF EXISTS `listings`;
CREATE TABLE `listings` (
  `ownerUName` varchar(32) DEFAULT NULL,
  `listingId` int NOT NULL AUTO_INCREMENT,
  `listingtype` varchar(32) NOT NULL,
  `latitude` decimal(10,5) NOT NULL,
  `longitude` decimal(10,5) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `address` varchar(64) NOT NULL,
  `postalCode` int NOT NULL,
  `unitNumber` int DEFAULT NULL,
  `city` varchar(32) NOT NULL,
  `country` varchar(32) NOT NULL,
  `amenities` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`listingId`),
  UNIQUE KEY `listingId_UNIQUE` (`listingId`)
);

INSERT INTO `listings` (ownerUName, listingId, listingType, latitude, longitude, price,address,postalCode,unitNumber,city,country,amenities) VALUES
('user123', 1, 'Full House', '40.7128', '-74.0060', 1400, '123 Main St', 123456, null, 'New York', 'United States', ',Pool,Wifi,Refrigerator,Patio,'),
('user123', 2, 'Room', '40.7128', '-74.0060', 600, '456 Elm St', 654321, 3, 'New York', 'United States', ',Kitchen,Wifi,Refrigerator,Patio,'),
('user123', 3, 'Apartement', '40.7128', '-74.0060', 1800, '789 Oak St', 987654, 8, 'New York', 'United States', ',Pool,Wifi,Microwave,Patio,'),
('user123', 4, 'Full House', '40.7128', '-74.0060', 1900, '101 Maple Ave', 456789, null, 'New York', 'United States', ',Kitchen,Wifi,Refrigerator,Hot-Tub,'),
('jane87', 5, 'Room', '48.8566', '2.3522', 500, '1 Champs-Elysees', 234567, 2, 'Paris', 'France', ',Kitchen,Wifi,Refrigerator,Patio,'),
('jane87', 6, 'Apartement', '48.8566', '2.3522', 1200, '2 Rue de Rivoli', 765432, 5, 'Paris', 'France', ',Wifi,Microwave,Patio,'),
('jane87', 7, 'Full House', '48.8566', '2.3522', 1700, '3 Avenue Montaigne', 321098, null, 'Paris', 'France', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('jane87', 8, 'Room', '48.8566', '2.3522', 550, '4 Boulevard Saint-Germain', 890123, 9, 'Paris', 'France', ',Kitchen,Wifi,Microwave,Hot-Tub,'),
('mike01', 9, 'Apartement', '34.0522', '-118.2437', 1500, '123 Hollywood Blvd', 210987, 7, 'Los Angeles', 'United States', ',Wifi,Microwave,Patio,'),
('mike01', 10, 'Full House', '34.0522', '-118.2437', 2000, '456 Sunset Blvd', 543210, null, 'Los Angeles', 'United States', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('mike01', 11, 'Room', '34.0522', '-118.2437', 450, '789 Santa Monica Blvd', 876543, 1, 'Los Angeles', 'United States', ',Kitchen,Wifi,Refrigerator,Patio,'),
('mike01', 12, 'Apartement', '34.0522', '-118.2437', 1700, '101 Venice Beach', 123456, 4, 'Los Angeles', 'United States', ',Pool,Wifi,Microwave,Hot-Tub,'),
('sara82', 13, 'Full House', '19.4326', '-99.1332', 1300, '123 Reforma Ave', 654321, null, 'Mexico City', 'Mexico', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('sara82', 14, 'Room', '19.4326', '-99.1332', 400, '456 Zocalo St', 987654, 6, 'Mexico City', 'Mexico', ',Kitchen,Wifi,Refrigerator,Patio,'),
('sara82', 15, 'Apartement', '19.4326', '-99.1332', 1600, '789 Chapultepec Park', 234567, 3, 'Mexico City', 'Mexico', ',Pool,Wifi,Microwave,Patio,'),
('sara82', 16, 'Full House', '19.4326', '-99.1332', 1800, '101 Frida Kahlo St', 765432, null, 'Mexico City', 'Mexico', ',Pool,Kitchen,Wifi,Refrigerator,Hot-Tub,'),
('alex55', 17, 'Room', '51.5074', '-0.1278', 550, '1 Buckingham Palace Rd', 321098, 2, 'London', 'United Kingdom', ',Kitchen,Wifi,Refrigerator,Patio,'),
('alex55', 18, 'Apartement', '51.5074', '-0.1278', 1300, '2 Trafalgar Square', 890123, 5, 'London', 'United Kingdom', ',Wifi,Microwave,Patio,'),
('alex55', 19, 'Full House', '51.5074', '-0.1278', 1800, '3 Tower Bridge', 210987, null, 'London', 'United Kingdom', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('alex55', 20, 'Room', '51.5074', '-0.1278', 600, '4 Piccadilly Circus', 543210, 9, 'London', 'United Kingdom', ',Kitchen,Wifi,Microwave,Hot-Tub,'),
('emily33', 21, 'Apartement', '48.1351', '11.5820', 1600, '123 Marienplatz', 876543, 7, 'Munich', 'Germany', ',Wifi,Microwave,Patio,'),
('emily33', 22, 'Full House', '48.1351', '11.5820', 1900, '456 Odeonsplatz', 123456, null, 'Munich', 'Germany', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('emily33', 23, 'Room', '48.1351', '11.5820', 500, '789 Nymphenburg Palace', 654321, 1, 'Munich', 'Germany', ',Kitchen,Wifi,Refrigerator,Patio,'),
('emily33', 24, 'Apartement', '48.1351', '11.5820', 1700, '101 Viktualienmarkt', 987654, 4, 'Munich', 'Germany', ',Pool,Wifi,Microwave,Patio,'),
('chris77', 25, 'Full House', '40.4168', '-3.7038', 1800, '123 Gran Via', 234567, null, 'Madrid', 'Spain', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('chris77', 26, 'Room', '40.4168', '-3.7038', 550, '456 Puerta del Sol', 765432, 9, 'Madrid', 'Spain', ',Kitchen,Wifi,Refrigerator,Patio,'),
('chris77', 27, 'Apartement', '40.4168', '-3.7038', 1400, '789 Retiro Park', 321098, 3, 'Madrid', 'Spain', ',Pool,Wifi,Microwave,Patio,'),
('chris77', 28, 'Full House', '40.4168', '-3.7038', 2100, '101 Plaza Mayor', 890123, null, 'Madrid', 'Spain', ',Pool,Kitchen,Wifi,Refrigerator,Hot-Tub,'),
('lisa44', 29, 'Room', '45.4642', '9.1900', 500, '1 Duomo Cathedral', 210987, 2, 'Milan', 'Italy', ',Kitchen,Wifi,Refrigerator,Patio,'),
('lisa44', 30, 'Apartement', '45.4642', '9.1900', 1300, '2 Galleria Vittorio Emanuele II', 543210, 5, 'Milan', 'Italy', ',Wifi,Microwave,Patio,'),
('lisa44', 31, 'Full House', '45.4642', '9.1900', 1700, '3 Sforza Castle', 876543, null, 'Milan', 'Italy', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('lisa44', 32, 'Room', '45.4642', '9.1900', 600, '4 Brera Art Gallery', 123456, 9, 'Milan', 'Italy', ',Kitchen,Wifi,Microwave,Hot-Tub,'),
('david66', 33, 'Apartement', '52.5200', '13.4050', 1500, '123 Brandenburg Gate', 654321, 7, 'Berlin', 'Germany', ',Wifi,Microwave,Patio,'),
('david66', 34, 'Full House', '52.5200', '13.4050', 2000, '456 Reichstag Building', 987654, null, 'Berlin', 'Germany', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('david66', 35, 'Room', '52.5200', '13.4050', 450, '789 Alexanderplatz', 234567, 1, 'Berlin', 'Germany', ',Kitchen,Wifi,Refrigerator,Patio,'),
('david66', 36, 'Apartement', '52.5200', '13.4050', 1700, '101 East Side Gallery', 765432, 4, 'Berlin', 'Germany', ',Pool,Wifi,Microwave,Hot-Tub,'),
('olivia89', 37, 'Full House', '48.8566', '2.3522', 1300, '123 Eiffel Tower', 210987, null, 'Paris', 'France', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('olivia89', 38, 'Room', '48.8566', '2.3522', 400, '456 Louvre Museum', 543210, 6, 'Paris', 'France', ',Kitchen,Wifi,Refrigerator,Patio,'),
('olivia89', 39, 'Apartement', '48.8566', '2.3522', 1600, '789 Montmartre', 876543, 3, 'Paris', 'France', ',Pool,Wifi,Microwave,Patio,'),
('olivia89', 40, 'Full House', '48.8566', '2.3522', 1800, '101 Seine River', 123456, null, 'Paris', 'France', ',Pool,Kitchen,Wifi,Refrigerator,Hot-Tub,'),
('sammy12', 41, 'Room', '34.0522', '-118.2437', 550, '1 Griffith Observatory', 654321, 2, 'Los Angeles', 'United States', ',Kitchen,Wifi,Refrigerator,Patio,'),
('sammy12', 42, 'Apartement', '34.0522', '-118.2437', 1300, '2 Getty Center', 987654, 5, 'Los Angeles', 'United States', ',Wifi,Microwave,Patio,'),
('sammy12', 43, 'Full House', '34.0522', '-118.2437', 1700, '3 Santa Monica Pier', 234567, null, 'Los Angeles', 'United States', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('sammy12', 44, 'Room', '34.0522', '-118.2437', 600, '4 Hollywood Sign', 765432, 9, 'Los Angeles', 'United States', ',Kitchen,Wifi,Microwave,Hot-Tub,'),
('natalie45', 45, 'Apartement', '51.5074', '-0.1278', 1600, '123 British Museum', 210987, 7, 'London', 'United Kingdom', ',Wifi,Microwave,Patio,'),
('natalie45', 46, 'Full House', '51.5074', '-0.1278', 1900, '456 Westminster Abbey', 543210, null, 'London', 'United Kingdom', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('natalie45', 47, 'Room', '51.5074', '-0.1278', 500, '789 Covent Garden', 876543, 1, 'London', 'United Kingdom', ',Kitchen,Wifi,Refrigerator,Patio,'),
('natalie45', 48, 'Apartement', '51.5074', '-0.1278', 1700, '101 The Shard', 123456, 4, 'London', 'United Kingdom', ',Pool,Wifi,Microwave,Patio,'),
('ryan78', 49, 'Full House', '48.1351', '11.5820', 1800, '123 Nymphenburg Palace', 654321, null, 'Munich', 'Germany', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('ryan78', 50, 'Room', '48.1351', '11.5820', 550, '456 English Garden', 987654, 9, 'Munich', 'Germany', ',Kitchen,Wifi,Refrigerator,Patio,'),
('ryan78', 51, 'Apartement', '48.1351', '11.5820', 1400, '789 BMW Museum', 234567, 3, 'Munich', 'Germany', ',Pool,Wifi,Microwave,Patio,'),
('ryan78', 52, 'Full House', '48.1351', '11.5820', 2100, '101 Viktualienmarkt', 765432, null, 'Munich', 'Germany', ',Pool,Kitchen,Wifi,Refrigerator,Hot-Tub,'),
('laura23', 53, 'Room', '40.4168', '-3.7038', 500, '1 Puerta del Sol', 210987, 2, 'Madrid', 'Spain', ',Kitchen,Wifi,Refrigerator,Patio,'),
('laura23', 54, 'Apartement', '40.4168', '-3.7038', 1300, '2 Plaza Mayor', 543210, 5, 'Madrid', 'Spain', ',Wifi,Microwave,Patio,'),
('laura23', 55, 'Full House', '40.4168', '-3.7038', 1700, '3 Retiro Park', 876543, null, 'Madrid', 'Spain', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('laura23', 56, 'Room', '40.4168', '-3.7038', 600, '4 Gran Via', 123456, 9, 'Madrid', 'Spain', ',Kitchen,Wifi,Microwave,Hot-Tub,'),
('kevin56', 57, 'Apartement', '45.4642', '9.1900', 1600, '123 Galleria Vittorio Emanuele II', 654321, 7, 'Milan', 'Italy', ',Wifi,Microwave,Patio,'),
('kevin56', 58, 'Full House', '45.4642', '9.1900', 1900, '456 Duomo Cathedral', 987654, null, 'Milan', 'Italy', ',Pool,Kitchen,Wifi,Refrigerator,Patio,'),
('kevin56', 59, 'Room', '45.4642', '9.1900', 500, '789 Brera Art Gallery', 234567, 1, 'Milan', 'Italy', ',Kitchen,Wifi,Refrigerator,Patio,'),
('kevin56', 60, 'Apartement', '45.4642', '9.1900', 1700, '101 Sforza Castle', 765432, 4, 'Milan', 'Italy', ',Pool,Wifi,Microwave,Patio,');


DROP TABLE IF EXISTS `tenantcommenting`;
CREATE TABLE `tenantcommenting` (
  `comment` varchar(1024) NOT NULL,
  `tenant` varchar(32) NOT NULL,
  `listingId` int NOT NULL,
  `rating` tinyint NOT NULL,
  PRIMARY KEY (`tenant`,`listingId`)
);

INSERT INTO `tenantcommenting` (comment,listingId,tenant,rating) VALUES 

('The property was a delightful retreat for a day. Its modern amenities and well-maintained interiors offered a comfortable and inviting atmosphere.',2,'david66',1),
('We thoroughly enjoyed our short stay at the property. The stunning views, coupled with the tasteful decor and convenient location, made it a memorable experience.',2,'natalie45',2),
('A true gem for a days rental! The propertys attention to detail was evident, from the plush furnishings to the state-of-the-art appliances. A perfect escape.',6,'olivia89',3),
('Our day spent at this property was exceptional. Its spacious layout, combined with the serene outdoor space, created a perfect setting for relaxation and enjoyment.',6,'sammy12',2);


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `uname` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `age` int NOT NULL,
  `SIN` int NOT NULL,
  `occupation` varchar(32) NOT NULL,
  `creditCard` int NOT NULL,
  PRIMARY KEY (`uname`),
  UNIQUE KEY `uname_UNIQUE` (`uname`)
);

INSERT INTO `users` (uName, password, name, age, SIN, occupation, creditCard) VALUES 
('user123', 'pass456', 'John Doe', 30, '123456789', 'Engineer', '987654321'),
('jane87', 'securePwd', 'Jane Smith', 25, '987654321', 'Designer', '123456789'),
('mike01', 'strongPass', 'Mike Johnson', 40, '234567890', 'Manager', '876543210'),
('sara82', 'safePassword', 'Sara Brown', 28, '765432109', 'Teacher', '210987654'),
('alex55', 'secret123', 'Alex Davis', 22, '543210987', 'Student', '765432109'),
('emily33', 'hiddenPwd', 'Emily Wilson', 35, '678901234', 'Doctor', '345678901'),
('chris77', 'privatePass', 'Chris Taylor', 31, '456789012', 'Accountant', '654321098'),
('lisa44', 'confidential', 'Lisa Anderson', 29, '890123456', 'Writer', '789012345'),
('david66', 'protected123', 'David Martinez', 45, '321098765', 'Artist', '456789012'),
('olivia89', 'classified', 'Olivia Lee', 27, '012345678', 'Scientist', '543210987'),
('sammy12', 'strongPass123', 'Samuel White', 33, '567890123', 'Architect', '876543210'),
('natalie45', 'secretCode', 'Natalie Johnson', 28, '890123456', 'Photographer', '234567890'),
('ryan78', 'hiddenKey', 'Ryan Miller', 37, '456789012', 'Entrepreneur', '678901234'),
('laura23', 'private123', 'Laura Turner', 26, '012345678', 'Chef', '890123456'),
('kevin56', 'confidentialPwd', 'Kevin Clark', 29, '765432109', 'Marketing', '123456789');
