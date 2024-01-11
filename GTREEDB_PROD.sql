-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 11, 2024 at 10:23 PM
-- Server version: 10.11.2-MariaDB
-- PHP Version: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `GTREEDB_PROD`
--

-- --------------------------------------------------------

--
-- Table structure for table `conversation`
--

CREATE TABLE `conversation` (
  `id` bigint(20) NOT NULL,
  `user_1_id` bigint(20) DEFAULT NULL,
  `user_2_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `conversation`
--

INSERT INTO `conversation` (`id`, `user_1_id`, `user_2_id`) VALUES
(1, 2, 2),
(3, 1, 4),
(4, 5, 1),
(5, 5, 4),
(6, 5, 6),
(7, 9, 5),
(8, 6, 9),
(9, 6, 8),
(10, 6, 7),
(11, 9, 1),
(12, 9, 4);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `validation_type` tinyint(4) DEFAULT NULL,
  `conversation_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `message_date_time` datetime(6) DEFAULT NULL,
  `content` varchar(4094) DEFAULT NULL,
  `validation_infos` varchar(4094) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`validation_type`, `conversation_id`, `id`, `message_date_time`, `content`, `validation_infos`) VALUES
(0, 1, 1, '2024-01-10 20:56:54.000000', 'A new user as been created and request a validation\n User to validate :User[id=3, personInfo=PersonInfo [id=3, lastName=Test, firstName=Test, gender=1, dateOfBirth=1980-12-12, countryOfBirth=Test, cityOfBirth=Test, isDead=false, nationality=Test, adress=Test, postalCode=14563, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =3, relatedNode =3], email=testest@mail.com, password=Password1, validated=false, isAdmin=false, noSecu=1234567891235, noPhone=0458963584]', '{\"concernedUserId\":\"3\"}'),
(NULL, 1, 2, '2024-01-10 21:54:19.000000', 'A new user as been created and request a validation\n User to validate :User[id=4, personInfo=PersonInfo [id=17, lastName=Gautier, firstName=Djaouida, gender=1, dateOfBirth=2000-01-10, countryOfBirth=Paris, cityOfBirth=Paris, isDead=false, nationality=Parisienne, adress=Paris , postalCode=75000, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =4, relatedNode =17], email=djaouida.g@gmail.com, password=Paris75, validated=false, isAdmin=false, noSecu=0101010101241, noPhone=0836656565]\nUser has been validated by the admin: System Admin on 2024-01-10T22:46:58.983842500', NULL),
(NULL, 3, 3, '2024-01-10 22:49:00.000000', 'Bonjour Djaouida', NULL),
(NULL, 3, 4, '2024-01-10 22:49:22.000000', 'Salut ça va', NULL),
(NULL, 1, 5, '2024-01-10 22:51:23.000000', 'A new user as been created and request a validation\n User to validate :User[id=5, personInfo=PersonInfo [id=25, lastName=Hernandez, firstName=Carlos, gender=2, dateOfBirth=1980-12-12, countryOfBirth=Frenca, cityOfBirth=Piras, isDead=false, nationality=Fr, adress=2 rue, postalCode=86257, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =5, relatedNode =25], email=carloshernandez@mail.fr, password=Password2, validated=false, isAdmin=false, noSecu=1234569871235, noPhone=0687945215]\nUser has been validated by the admin: System Admin on 2024-01-10T23:03:10.260350200', NULL),
(NULL, 1, 6, '2024-01-10 22:54:24.000000', 'A new user as been created and request a validation\n User to validate :User[id=6, personInfo=PersonInfo [id=27, lastName=Ivanov, firstName=Andrei, gender=0, dateOfBirth=1969-12-12, countryOfBirth=MM, cityOfBirth=M, isDead=false, nationality=MMM, adress=MMMM, postalCode=56978, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =6, relatedNode =27], email=A.Ivanov@mail.com, password=Password5, validated=false, isAdmin=false, noSecu=5698741236975, noPhone=0578926599]\nUser has been validated by the admin: System Admin on 2024-01-10T23:02:57.656529600', NULL),
(NULL, 1, 7, '2024-01-10 23:04:41.000000', 'A new user as been created and request a validation\n User to validate :User[id=7, personInfo=PersonInfo [id=30, lastName=Daniel, firstName=JDG, gender=0, dateOfBirth=1993-06-15, countryOfBirth=France, cityOfBirth=Bretagne, isDead=false, nationality=oui, adress=oui, postalCode=8, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =7, relatedNode =30], email=JDG@gmail.com, password=Jdg77777, validated=false, isAdmin=false, noSecu=1111111111111, noPhone=0836656565]\nUser has been validated by the admin: System Admin on 2024-01-10T23:09:32.425316400', NULL),
(NULL, 4, 8, '2024-01-10 23:05:15.000000', 'Hello', NULL),
(NULL, 4, 9, '2024-01-10 23:05:27.000000', 'I want to be your friend', NULL),
(NULL, 4, 10, '2024-01-10 23:05:36.000000', 'please, I beg you', NULL),
(NULL, 5, 11, '2024-01-10 23:06:09.000000', 'Hola, me llamo Carlos', NULL),
(NULL, 1, 12, '2024-01-10 23:06:34.000000', 'A new user as been created and request a validation\n User to validate :User[id=8, personInfo=PersonInfo [id=31, lastName=Toi, firstName=Salut, gender=2, dateOfBirth=1945-04-05, countryOfBirth=France, cityOfBirth=Bretagne, isDead=false, nationality=Jamaicain, adress=Fabuleux World, postalCode=8, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =8, relatedNode =31], email=SalutToi@gmail.com, password=Fabuleux7, validated=false, isAdmin=false, noSecu=7777777777777, noPhone=0836656565]\nUser has been validated by the admin: System Admin on 2024-01-10T23:09:28.291843600', NULL),
(NULL, 6, 13, '2024-01-10 23:06:50.000000', 'Меня зовут Карлос', NULL),
(NULL, 1, 14, '2024-01-10 23:08:20.000000', 'A new user as been created and request a validation\n User to validate :User[id=9, personInfo=PersonInfo [id=32, lastName=Palmier, firstName=Fabuleux, gender=0, dateOfBirth=1912-02-12, countryOfBirth=France, cityOfBirth=Bretagne, isDead=false, nationality=45, adress=45, postalCode=8, profilPictureUrl=https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg, relatedUser =9, relatedNode =32], email=FP@gmail.com, password=Gg454545, validated=false, isAdmin=false, noSecu=4444444444444, noPhone=0897979797]\nUser has been validated by the admin: System Admin on 2024-01-10T23:09:25.459686200', NULL),
(NULL, 7, 15, '2024-01-10 23:11:50.000000', 'Je suis un beau palmier', NULL),
(NULL, 6, 16, '2024-01-10 23:12:15.000000', 'хорошо', NULL),
(NULL, 8, 17, '2024-01-10 23:12:49.000000', 'Bonjour M Fabuleux, vous êtes très beau', NULL),
(NULL, 9, 18, '2024-01-10 23:14:50.000000', 'Salut Toi', NULL),
(NULL, 11, 19, '2024-01-10 23:15:36.000000', 'SALUT TOI ', NULL),
(NULL, 11, 20, '2024-01-10 23:15:42.000000', 'Comment vas tu ? ', NULL),
(NULL, 11, 21, '2024-01-10 23:15:46.000000', 'moi ça va', NULL),
(NULL, 11, 22, '2024-01-10 23:15:55.000000', 'j\'ai passé un super week end', NULL),
(NULL, 12, 23, '2024-01-10 23:16:10.000000', 'SALUT TOI !', NULL),
(NULL, 12, 24, '2024-01-10 23:16:15.000000', 'comment ça va ? ', NULL),
(NULL, 12, 25, '2024-01-10 23:16:19.000000', 'moi ça va bien ', NULL),
(NULL, 12, 26, '2024-01-10 23:16:25.000000', 'j\'ai passé des super vacance', NULL),
(NULL, 11, 27, '2024-01-10 23:16:36.000000', 'Salut toi', NULL),
(NULL, 11, 28, '2024-01-10 23:16:52.000000', 'cool', NULL),
(NULL, 11, 29, '2024-01-10 23:17:07.000000', 'moi ça va pas fort', NULL),
(NULL, 12, 30, '2024-01-10 23:18:09.000000', 'Laisse moi tranquille stp', NULL),
(NULL, 5, 31, '2024-01-10 23:19:00.000000', 'tu connais Born To be Alive ? ', NULL),
(NULL, 5, 32, '2024-01-10 23:19:18.000000', 'C\'est on frère ? ', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `message_receiver`
--

CREATE TABLE `message_receiver` (
  `id` bigint(20) NOT NULL,
  `receiver_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `message_receiver`
--

INSERT INTO `message_receiver` (`id`, `receiver_id`) VALUES
(4, 1),
(8, 1),
(9, 1),
(10, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(1, 2),
(2, 2),
(5, 2),
(6, 2),
(7, 2),
(12, 2),
(14, 2),
(3, 4),
(11, 4),
(23, 4),
(24, 4),
(25, 4),
(26, 4),
(15, 5),
(16, 5),
(31, 5),
(32, 5),
(13, 6),
(18, 8),
(17, 9),
(27, 9),
(28, 9),
(29, 9),
(30, 9);

-- --------------------------------------------------------

--
-- Table structure for table `message_sender`
--

CREATE TABLE `message_sender` (
  `id` bigint(20) NOT NULL,
  `sender_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `message_sender`
--

INSERT INTO `message_sender` (`id`, `sender_id`) VALUES
(3, 1),
(27, 1),
(28, 1),
(29, 1),
(1, 2),
(2, 2),
(5, 2),
(6, 2),
(7, 2),
(12, 2),
(14, 2),
(4, 4),
(30, 4),
(31, 4),
(32, 4),
(8, 5),
(9, 5),
(10, 5),
(11, 5),
(13, 5),
(16, 6),
(17, 6),
(18, 6),
(15, 9),
(19, 9),
(20, 9),
(21, 9),
(22, 9),
(23, 9),
(24, 9),
(25, 9),
(26, 9);

-- --------------------------------------------------------

--
-- Table structure for table `node`
--

CREATE TABLE `node` (
  `date_of_death` date DEFAULT NULL,
  `has_child` bit(1) NOT NULL,
  `privacy` int(11) NOT NULL,
  `created_by_user_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `parent_1_node_id` bigint(20) DEFAULT NULL,
  `parent_2_node_id` bigint(20) DEFAULT NULL,
  `partner_node_id` bigint(20) DEFAULT NULL,
  `person_info_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `node`
--

INSERT INTO `node` (`date_of_death`, `has_child`, `privacy`, `created_by_user_id`, `id`, `parent_1_node_id`, `parent_2_node_id`, `partner_node_id`, `person_info_id`) VALUES
(NULL, b'1', 2, 1, 1, 4, 5, 6, 1),
(NULL, b'0', 2, 2, 2, NULL, NULL, NULL, 2),
(NULL, b'0', 2, 3, 3, NULL, NULL, NULL, 3),
(NULL, b'1', 2, 1, 4, 18, 20, 5, 4),
(NULL, b'1', 2, 1, 5, 15, 16, 4, 5),
(NULL, b'1', 2, 1, 6, 8, 9, 1, 6),
(NULL, b'0', 2, 1, 7, 1, 6, NULL, 7),
(NULL, b'1', 1, 1, 8, 12, 13, 9, 8),
('2023-07-13', b'1', 2, 1, 9, NULL, NULL, 8, 9),
(NULL, b'0', 2, 1, 10, 9, 8, NULL, 10),
(NULL, b'0', 2, 1, 11, 8, 9, NULL, 11),
('2013-07-15', b'1', 2, 1, 12, NULL, NULL, 13, 12),
('2022-09-18', b'1', 0, 1, 13, 23, 24, 12, 13),
(NULL, b'0', 0, 1, 14, 4, 5, NULL, 14),
('1992-06-15', b'1', 2, 1, 15, NULL, NULL, 16, 15),
('1995-05-12', b'1', 2, 1, 16, NULL, NULL, 15, 16),
(NULL, b'0', 2, 4, 17, 19, 21, NULL, 17),
(NULL, b'1', 2, 1, 18, NULL, NULL, 20, 18),
('2024-01-09', b'1', 2, 4, 19, 28, 29, 21, 19),
('2020-12-12', b'1', 2, 1, 20, NULL, NULL, 18, 20),
('2024-01-09', b'1', 2, 4, 21, 22, NULL, 19, 21),
('1980-10-10', b'1', 2, 4, 22, NULL, NULL, 26, 22),
('1999-09-17', b'1', 0, 1, 23, NULL, NULL, NULL, 23),
('2024-01-09', b'1', 2, 1, 24, NULL, NULL, NULL, 24),
(NULL, b'0', 2, 5, 25, NULL, NULL, NULL, 25),
('2023-08-08', b'0', 2, 4, 26, NULL, NULL, 22, 26),
(NULL, b'0', 2, 6, 27, NULL, NULL, NULL, 27),
(NULL, b'1', 0, 4, 28, NULL, NULL, 29, 28),
(NULL, b'1', 2, 4, 29, NULL, NULL, 28, 29),
(NULL, b'0', 2, 7, 30, NULL, NULL, NULL, 30),
(NULL, b'0', 2, 8, 31, NULL, NULL, NULL, 31),
(NULL, b'0', 2, 9, 32, NULL, NULL, NULL, 32);

-- --------------------------------------------------------

--
-- Table structure for table `node_ex_partners`
--

CREATE TABLE `node_ex_partners` (
  `ex_partners_id` bigint(20) NOT NULL,
  `node_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `node_ex_partners`
--

INSERT INTO `node_ex_partners` (`ex_partners_id`, `node_id`) VALUES
(23, 24),
(24, 23);

-- --------------------------------------------------------

--
-- Table structure for table `node_siblings`
--

CREATE TABLE `node_siblings` (
  `node_id` bigint(20) NOT NULL,
  `siblings_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `person_info`
--

CREATE TABLE `person_info` (
  `date_of_birth` date DEFAULT NULL,
  `gender` int(11) NOT NULL,
  `is_dead` bit(1) DEFAULT NULL,
  `postal_code` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `adress` varchar(4094) DEFAULT NULL,
  `profil_picture_url` varchar(4094) DEFAULT NULL,
  `city_of_birth` varchar(255) DEFAULT NULL,
  `country_of_birth` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `person_info`
--

INSERT INTO `person_info` (`date_of_birth`, `gender`, `is_dead`, `postal_code`, `id`, `adress`, `profil_picture_url`, `city_of_birth`, `country_of_birth`, `first_name`, `last_name`, `nationality`) VALUES
('2002-05-13', 0, b'0', 95000, 1, '10 Avenue du Parc', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Cergy', 'France', 'Fabien', 'Cerf', 'Français'),
('1930-01-01', 0, b'0', 12000, 2, '1 Avenue du Bourg Palette', 'https://media.licdn.com/dms/image/C4E03AQEVvI1smo3OVA/profile-displayphoto-shrink_400_400/0/1653388794981?e=1707350400&v=beta&t=IBYKa81wTiNOuRJAjtI47GNLFFujzw4Sh37WFBBjKOc', 'Bourg Palette', 'Wakanda', 'Admin', 'System', 'Wakandai'),
('1980-12-12', 1, b'0', 14563, 3, 'Test', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Test', 'Test', 'Test', 'Test', 'Test'),
('1980-12-12', 1, b'0', 99999, 4, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Marie', 'Cerf', ''),
('1979-06-15', 0, b'0', 46986, 5, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Olivier', 'Cerf', ''),
('2002-12-12', 1, b'0', 78926, 6, '12 avenue d\'ici', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Elena', 'Rodriguez', 'Française'),
('2023-12-12', 0, b'0', 78962, 7, '15 rue ici', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Poitiers', 'France', 'Jean-Luc', 'Cerf-Rodriguez', 'Français'),
('1972-07-12', 1, b'0', 99999, 8, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Priya', 'Rodriguez', ''),
('1969-06-05', 0, b'0', 99999, 9, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Test', 'Test', 'Carlos', 'Rodriguez', ''),
('2010-05-12', 0, b'0', 99999, 10, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Michel', 'Rodriguez', ''),
('2010-05-12', 1, b'0', 99999, 11, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Michelle', 'Rodriguez', ''),
('1940-05-06', 0, b'0', 99999, 12, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Papi', 'Rodriguez', 'Français'),
('1943-05-12', 1, b'0', 89556, 13, 'Là-bas', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Cergy', 'France', 'Mami', 'Rodriguez', 'Française'),
('1999-12-12', 1, b'0', 96285, 14, '1 boulevard des roses', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Isabella', 'Cerf', 'Française'),
('1899-02-12', 1, b'0', 99999, 15, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Patrick', 'Cerf', ''),
('1897-06-05', 0, b'0', 99999, 16, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Marseille', 'France', 'Julia', 'Cerf', ''),
('2000-01-10', 1, b'0', 75000, 17, 'Paris ', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'Paris', 'Djaouida', 'Gautier', 'Parisienne'),
('1942-04-12', 1, b'0', 99999, 18, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Michelle', 'Dupont', ''),
('1000-10-10', 0, b'0', 75200, 19, 'Paris 19e', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'Paris Country', 'JiJi', 'Gautier-Deflore', 'Indo-Sénégalaise'),
('1933-04-15', 0, b'0', 99999, 20, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Paris', 'France', 'Luca', 'Dupont', ''),
('1980-01-10', 1, b'0', 56560, 21, 'Peroucity 2', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Jouis-en-Josas', 'Perou', 'JoJo', 'Gautier', 'Peroutéen'),
('1940-10-10', 1, b'0', 95200, 22, 'Pétain rue 3', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Pétain', 'Russie', 'Yeye', 'Gautier', 'Jiji'),
('1900-06-05', 1, b'0', 99999, 23, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Là-bas', 'Ici', 'Justine', 'Justine', ''),
('1900-06-05', 0, b'0', 99999, 24, '', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'TUSAISPAS', 'ok', 'Justin', 'Justino', ''),
('1980-12-12', 2, b'0', 86257, 25, '2 rue', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Piras', 'Frenca', 'Carlos', 'Hernandez', 'Fr'),
('1978-04-04', 0, b'0', 85850, 26, 'Chez moi', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'OuiOuiLand', 'NonNonLand', 'Java', 'Gautier', 'Trepasse'),
('1969-12-12', 0, b'0', 56978, 27, 'MMMM', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'M', 'MM', 'Andrei', 'Ivanov', 'MMM'),
('1930-10-10', 1, b'0', 69694, 28, 'Levallois Perret', 'https://th.bing.com/th?id=OSK.ee9a3607808b71d048b45f67c4d78c9c&w=46&h=46&c=12&rs=1&qlt=80&o=6&dpr=1.4&pid=SANGAM', 'Patrick', 'Balkany', 'Salut', 'Deflore', 'Argent'),
('1930-04-05', 0, b'0', 8, 29, 'Hopital De Bretagne', 'https://th.bing.com/th?id=ODL.9e601d664bcee13522bc4d9b7069e025&w=80&h=80&c=12&rs=1&qlt=90&o=6&dpr=1.4&pid=13.1', 'Bretagne', 'Bretagne', 'JDG', 'Daniel', 'popole'),
('1993-06-15', 0, b'0', 8, 30, 'oui', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Bretagne', 'France', 'JDG', 'Daniel', 'oui'),
('1945-04-05', 2, b'0', 8, 31, 'Fabuleux World', 'https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg', 'Bretagne', 'France', 'Salut', 'Toi', 'Jamaicain'),
('1912-02-12', 0, b'0', 8, 32, '45', 'https://th.bing.com/th?id=ODL.9e601d664bcee13522bc4d9b7069e025&w=80&h=80&c=12&rs=1&qlt=90&o=6&dpr=1.4&pid=13.1', 'Bretagne', 'France', 'Fabuleux', 'Palmier', '45');

-- --------------------------------------------------------

--
-- Table structure for table `tree`
--

CREATE TABLE `tree` (
  `privacy` int(11) NOT NULL,
  `vomreset_date` date DEFAULT NULL,
  `voyreset_date` date DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `view_of_month` bigint(20) NOT NULL,
  `view_of_year` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `tree`
--

INSERT INTO `tree` (`privacy`, `vomreset_date`, `voyreset_date`, `id`, `view_of_month`, `view_of_year`, `name`) VALUES
(0, '1900-01-01', '2024-01-10', 1, 1, 1, 'Fabien Cerf\'s Tree'),
(0, '1900-01-01', '1900-01-01', 2, 0, 0, 'Admin System\'s Tree'),
(0, '1900-01-01', '1900-01-01', 3, 0, 0, 'Test Test\'s Tree'),
(0, '1900-01-01', '1900-01-01', 4, 0, 0, 'Djaouida Gautier\'s Tree'),
(0, '1900-01-01', '1900-01-01', 5, 0, 0, 'Carlos Hernandez\'s Tree'),
(0, '1900-01-01', '1900-01-01', 6, 0, 0, 'Andrei Ivanov\'s Tree'),
(0, '1900-01-01', '1900-01-01', 7, 0, 0, 'JDG Daniel\'s Tree'),
(0, '1900-01-01', '1900-01-01', 8, 0, 0, 'Salut Toi\'s Tree'),
(1, '1900-01-01', '1900-01-01', 9, 0, 0, 'Fabuleux Palmier\'s Tree');

-- --------------------------------------------------------

--
-- Table structure for table `tree_nodes`
--

CREATE TABLE `tree_nodes` (
  `depth` int(11) NOT NULL,
  `privacy` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `node_id` bigint(20) DEFAULT NULL,
  `tree_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `tree_nodes`
--

INSERT INTO `tree_nodes` (`depth`, `privacy`, `id`, `node_id`, `tree_id`) VALUES
(0, 1, 1, 1, 1),
(0, 1, 2, 2, 2),
(0, 1, 3, 3, 3),
(1, 2, 4, 4, 1),
(1, 2, 5, 5, 1),
(0, 2, 6, 6, 1),
(-1, 2, 7, 7, 1),
(1, 1, 8, 8, 1),
(1, 2, 9, 9, 1),
(0, 2, 10, 10, 1),
(0, 2, 11, 11, 1),
(2, 2, 12, 12, 1),
(2, 0, 13, 13, 1),
(0, 0, 14, 14, 1),
(2, 2, 15, 15, 1),
(2, 2, 16, 16, 1),
(0, 1, 17, 17, 4),
(2, 2, 18, 18, 1),
(1, 2, 19, 19, 4),
(2, 2, 20, 20, 1),
(1, 2, 21, 21, 4),
(2, 2, 22, 22, 4),
(3, 0, 23, 23, 1),
(3, 2, 24, 24, 1),
(0, 1, 25, 25, 5),
(2, 2, 26, 26, 4),
(0, 1, 27, 27, 6),
(2, 0, 28, 28, 4),
(2, 2, 29, 29, 4),
(0, 1, 30, 30, 7),
(0, 1, 31, 31, 8),
(0, 1, 32, 32, 9);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `is_admin` bit(1) DEFAULT NULL,
  `validated` bit(1) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `my_tree_id` bigint(20) DEFAULT NULL,
  `person_info_id` bigint(20) DEFAULT NULL,
  `no_secu` varchar(4094) DEFAULT NULL,
  `password` varchar(4094) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `no_phone` varchar(255) DEFAULT NULL,
  `private_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`is_admin`, `validated`, `id`, `my_tree_id`, `person_info_id`, `no_secu`, `password`, `email`, `no_phone`, `private_code`) VALUES
(b'0', b'1', 1, 1, 1, '1234567891234', 'Ceciest1mdp', 'fabiencerf@mail.com', '0689534896', 'ZTNKcg8o6tl9NhBo6CM4e890Xh7intbu9aJAo56ZQZ5P7q03ckj72E8uTZM8kJR0MKD6G5A2IZT209Bu3qJSiGWtQJfwxz6g3hs7ezTm9Y37YNMr43fVm1cCn4YVeb0Z'),
(b'1', b'1', 2, 2, 2, '1234567890123', 'TestTest7', 'system.admin.account@gmail.com', '+33836656565', 'TheSystemAdmin'),
(b'0', b'0', 3, 3, 3, '1234567891235', 'Password1', 'testest@mail.com', '0458963584', 'qjw4EVr4tBcWt55y8iRm2pd7Zq1L8rmVxb7xdE8Izi8sLE2Uo0zf392p6nG5aA001PMeaF58pbVl51OpgClGi5znd287J11vBu1dfE26pBAf83C5Y6l3KoJhXN6k9uKU'),
(b'0', b'1', 4, 4, 17, '0101010101241', 'Paris75', 'djaouida.g@gmail.com', '0836656565', 'jgspQ48e4DKQ2g47gVoxYtH89w7pdM321F8hA6JY5j1968cwZ2uP52eO4v28Be7DbdVg4h3XTbdHr4TzNpnsqNkbJxgAn0US7vo7STcnI1PbgMx4v7a4WrwD3ksX328g'),
(b'0', b'1', 5, 5, 25, '1234569871235', 'Password2', 'carloshernandez@mail.fr', '0687945215', '784J68FhJBQokyd6etsveGsH5S8uIKLo8jiiob0sMpHTE7DhqLyb9M029bZdserlzTQI0LQ7pn08vM5mUU87CBo7PfOGyVzJGjZE1pvc06MXM6f03I5CqZG7XD824k8N'),
(b'0', b'1', 6, 6, 27, '5698741236975', 'Password5', 'A.Ivanov@mail.com', '0578926599', '0SLY0K9PoWo5QZ5uqIVFioGWR65NLOH895Z2Y35UKB6xkFPLFv7Wv348VF6R1w7k97fwpcU81ie2s3d0B7fQcL2thFZP79u1X7o3tYDuVfD4kEA0zj5ENt2WHzH0x298'),
(b'0', b'1', 7, 7, 30, '1111111111111', 'Jdg77777', 'JDG@gmail.com', '0836656565', 'nrx871ME7zy5MKU1k16ashZ32220K81NCP71TIBD19UeeIi5L9943jb9YN4A5qs45zKKcw6VlPDG97699bIi06Z21rQ4wiSw36TDd0712bj67unGCmGBot4G1j5zWf4C'),
(b'0', b'1', 8, 8, 31, '7777777777777', 'Fabuleux7', 'SalutToi@gmail.com', '0836656565', '4PNh8M5jK5BbW4dMv6j1Y209P2oMuCWQPpVFo5H8827lkpC9X00SR4JJyg9GXGh8dR05x1hDrN3698zBu90CnI10KPskg2sQy9MK6UiZfixgscaT7ygi8NdBiaMVH6q1'),
(b'0', b'1', 9, 9, 32, '4444444444444', 'Gg454545', 'FP@gmail.com', '0897979797', '6xPKkW95vR5QUN51Ds7m4CE6OiysV01Ev79WrgIGnAWkZq11V4Eq3Taf4DMj4jf1E7K166d9M3H7Pfq10M06BPL3hAe7A1N1o6F6cUnz3Zv20mX4c9YPWXp9G01EUhD3');

-- --------------------------------------------------------

--
-- Table structure for table `user_conversation1`
--

CREATE TABLE `user_conversation1` (
  `conversations1_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `user_conversation1`
--

INSERT INTO `user_conversation1` (`conversations1_id`, `user_id`) VALUES
(1, 2),
(3, 1),
(4, 5),
(5, 5),
(6, 5),
(7, 9),
(8, 6),
(9, 6),
(10, 6),
(11, 9),
(12, 9);

-- --------------------------------------------------------

--
-- Table structure for table `user_conversation2`
--

CREATE TABLE `user_conversation2` (
  `conversations2_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

--
-- Dumping data for table `user_conversation2`
--

INSERT INTO `user_conversation2` (`conversations2_id`, `user_id`) VALUES
(1, 2),
(3, 4),
(4, 1),
(5, 4),
(6, 6),
(7, 5),
(8, 9),
(9, 8),
(10, 7),
(11, 1),
(12, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `conversation`
--
ALTER TABLE `conversation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq6053gjdyl9lfpthal0jf9ovg` (`user_1_id`),
  ADD KEY `FKcq5lfuonkjrh8a4mrcx6m3vx` (`user_2_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6yskk3hxw5sklwgi25y6d5u1l` (`conversation_id`);

--
-- Indexes for table `message_receiver`
--
ALTER TABLE `message_receiver`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7f7tmqcy2gi0ee77yhd7ib8cu` (`receiver_id`);

--
-- Indexes for table `message_sender`
--
ALTER TABLE `message_sender`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKch40nd0g16kyicpa6q2jgijo8` (`sender_id`);

--
-- Indexes for table `node`
--
ALTER TABLE `node`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gpu4plgunwpdseufuw4toy91` (`partner_node_id`),
  ADD UNIQUE KEY `UK_abyat961kfeu3ree1tyvir7kx` (`person_info_id`),
  ADD KEY `FKavuktws4fcj9p4op6kt8stmdt` (`created_by_user_id`),
  ADD KEY `FK71897ty7mq24gnry7c3q9rj7j` (`parent_1_node_id`),
  ADD KEY `FKful8fhtpeutb12jvnguyo3bpg` (`parent_2_node_id`);

--
-- Indexes for table `node_ex_partners`
--
ALTER TABLE `node_ex_partners`
  ADD PRIMARY KEY (`ex_partners_id`,`node_id`),
  ADD UNIQUE KEY `UK_iifq0uyvfy0h176t58vl26rox` (`ex_partners_id`),
  ADD KEY `FK7t8j83gw4na8eepba7fi90dk1` (`node_id`);

--
-- Indexes for table `node_siblings`
--
ALTER TABLE `node_siblings`
  ADD PRIMARY KEY (`node_id`,`siblings_id`),
  ADD UNIQUE KEY `UK_hrqnf4r4h4kv3x7jvkqd6r94t` (`siblings_id`);

--
-- Indexes for table `person_info`
--
ALTER TABLE `person_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tree`
--
ALTER TABLE `tree`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tree_nodes`
--
ALTER TABLE `tree_nodes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfua5jg64brdlnyu1y4lidclup` (`node_id`),
  ADD KEY `FKno8gej3hv8g4t9rf1onhbu2c3` (`tree_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gk4ic3y5hvhknkqxeonbjosgd` (`my_tree_id`),
  ADD UNIQUE KEY `UK_rroonoevg9arr776u144nm1q2` (`person_info_id`);

--
-- Indexes for table `user_conversation1`
--
ALTER TABLE `user_conversation1`
  ADD PRIMARY KEY (`conversations1_id`,`user_id`),
  ADD UNIQUE KEY `UK_9etxcq12nq12c31x23w3b3pur` (`conversations1_id`),
  ADD KEY `FKgtkhy3oih1bttypww638kmpj2` (`user_id`);

--
-- Indexes for table `user_conversation2`
--
ALTER TABLE `user_conversation2`
  ADD PRIMARY KEY (`conversations2_id`,`user_id`),
  ADD UNIQUE KEY `UK_2n66kvi12sbaq4gt45btsf6rw` (`conversations2_id`),
  ADD KEY `FK5kklam3phne3xon74pnnmf4dd` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `conversation`
--
ALTER TABLE `conversation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `node`
--
ALTER TABLE `node`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `person_info`
--
ALTER TABLE `person_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `tree`
--
ALTER TABLE `tree`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tree_nodes`
--
ALTER TABLE `tree_nodes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `conversation`
--
ALTER TABLE `conversation`
  ADD CONSTRAINT `FKcq5lfuonkjrh8a4mrcx6m3vx` FOREIGN KEY (`user_2_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKq6053gjdyl9lfpthal0jf9ovg` FOREIGN KEY (`user_1_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK6yskk3hxw5sklwgi25y6d5u1l` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`id`);

--
-- Constraints for table `message_receiver`
--
ALTER TABLE `message_receiver`
  ADD CONSTRAINT `FK7f7tmqcy2gi0ee77yhd7ib8cu` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKr4vfdrf0tixv3rsvq4nwpt078` FOREIGN KEY (`id`) REFERENCES `message` (`id`);

--
-- Constraints for table `message_sender`
--
ALTER TABLE `message_sender`
  ADD CONSTRAINT `FKch40nd0g16kyicpa6q2jgijo8` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKk6ndb925ooh1gat4t2heihayg` FOREIGN KEY (`id`) REFERENCES `message` (`id`);

--
-- Constraints for table `node`
--
ALTER TABLE `node`
  ADD CONSTRAINT `FK71897ty7mq24gnry7c3q9rj7j` FOREIGN KEY (`parent_1_node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FKavuktws4fcj9p4op6kt8stmdt` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKc9i5039300w189dt1u6a4k7sx` FOREIGN KEY (`partner_node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FKful8fhtpeutb12jvnguyo3bpg` FOREIGN KEY (`parent_2_node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FKljsr3rvodj7m57pcym78kh3tq` FOREIGN KEY (`person_info_id`) REFERENCES `person_info` (`id`);

--
-- Constraints for table `node_ex_partners`
--
ALTER TABLE `node_ex_partners`
  ADD CONSTRAINT `FK7t8j83gw4na8eepba7fi90dk1` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FK9yban17j4srr4b8y7hnav37gb` FOREIGN KEY (`ex_partners_id`) REFERENCES `node` (`id`);

--
-- Constraints for table `node_siblings`
--
ALTER TABLE `node_siblings`
  ADD CONSTRAINT `FKedkttdmtq2ryu4f2etj6e25nx` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FKpg1amt49ihtxxb5020wx8w5nf` FOREIGN KEY (`siblings_id`) REFERENCES `node` (`id`);

--
-- Constraints for table `tree_nodes`
--
ALTER TABLE `tree_nodes`
  ADD CONSTRAINT `FKfua5jg64brdlnyu1y4lidclup` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`),
  ADD CONSTRAINT `FKno8gej3hv8g4t9rf1onhbu2c3` FOREIGN KEY (`tree_id`) REFERENCES `tree` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK1trx0xa2d8u8k166go8rmsfbx` FOREIGN KEY (`person_info_id`) REFERENCES `person_info` (`id`),
  ADD CONSTRAINT `FK22ofpcmrkfn10bur0q3701ajp` FOREIGN KEY (`my_tree_id`) REFERENCES `tree` (`id`);

--
-- Constraints for table `user_conversation1`
--
ALTER TABLE `user_conversation1`
  ADD CONSTRAINT `FKgtkhy3oih1bttypww638kmpj2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKo5rix6vl90v00kisxyj86p4o5` FOREIGN KEY (`conversations1_id`) REFERENCES `conversation` (`id`);

--
-- Constraints for table `user_conversation2`
--
ALTER TABLE `user_conversation2`
  ADD CONSTRAINT `FK5kklam3phne3xon74pnnmf4dd` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKatu1r3k9ay49abuaguva1k4lh` FOREIGN KEY (`conversations2_id`) REFERENCES `conversation` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
