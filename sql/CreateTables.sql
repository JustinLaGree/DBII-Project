/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : db2

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 2/12/2020 22:55:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `enroll`;
DROP TABLE IF EXISTS `enroll2`;
DROP TABLE IF EXISTS `assign`;
DROP TABLE IF EXISTS `mentors`;
DROP TABLE IF EXISTS `mentees`;
DROP TABLE IF EXISTS `material`;
DROP TABLE IF EXISTS `meetings`;
DROP TABLE IF EXISTS `time_slot`;
DROP TABLE IF EXISTS `groups`;
DROP TABLE IF EXISTS `admins`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `parents`;
DROP TABLE IF EXISTS `users`;

-- ----------------------------
-- Table structure for users
-- ----------------------------

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for parents
-- ----------------------------

CREATE TABLE `parents` (
  `parent_id` int(11) NOT NULL,
  PRIMARY KEY (`parent_id`),
  CONSTRAINT `parent_user` FOREIGN KEY (`parent_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for students
-- ----------------------------

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `parent_id` int(11) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `student_parent` (`parent_id`),
  CONSTRAINT `student_user` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `student_parent` FOREIGN KEY (`parent_id`) REFERENCES `parents` (`parent_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for admins
-- ----------------------------

CREATE TABLE `admins` (
  `admin_id` int(11) NOT NULL,
  PRIMARY KEY (`admin_id`),
  CONSTRAINT `admins_user` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for groups
-- ----------------------------

CREATE TABLE `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` int(11) DEFAULT NULL,
  `mentor_grade_req` int(11) DEFAULT NULL,
  `mentee_grade_req` int(11) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for time_slot
-- ----------------------------

CREATE TABLE `time_slot` (
  `time_slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `day_of_the_week` varchar(255) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  PRIMARY KEY (`time_slot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for meetings
-- ----------------------------

CREATE TABLE `meetings` (
  `meet_id` int(11) NOT NULL AUTO_INCREMENT,
  `meet_name` varchar(255) NOT NULL,
  `date` date DEFAULT NULL,
  `time_slot_id` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `announcement` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`meet_id`),
  KEY `meeting_group` (`group_id`),
  KEY `meeting_time_slot` (`time_slot_id`),
  CONSTRAINT `meeting_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`) ON DELETE CASCADE,
  CONSTRAINT `meeting_time_slot` FOREIGN KEY (`time_slot_id`) REFERENCES `time_slot` (`time_slot_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for material
-- ----------------------------

CREATE TABLE `material` (
  `material_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `assigned_date` date NOT NULL,
  `notes` text,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mentees
-- ----------------------------

CREATE TABLE `mentees` (
  `mentee_id` int(11) NOT NULL,
  PRIMARY KEY (`mentee_id`),
  CONSTRAINT `mentee_student` FOREIGN KEY (`mentee_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for mentors
-- ----------------------------

CREATE TABLE `mentors` (
  `mentor_id` int(11) NOT NULL,
  PRIMARY KEY (`mentor_id`),
  CONSTRAINT `mentor_student` FOREIGN KEY (`mentor_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enroll
-- ----------------------------

CREATE TABLE `enroll` (
  `meet_id` int(11) NOT NULL,
  `mentee_id` int(11) NOT NULL,
  PRIMARY KEY (`meet_id`,`mentee_id`),
  KEY `enroll_mentee` (`mentee_id`),
  CONSTRAINT `enroll_mentee` FOREIGN KEY (`mentee_id`) REFERENCES `mentees` (`mentee_id`) ON DELETE CASCADE,
  CONSTRAINT `enroll_meetings` FOREIGN KEY (`meet_id`) REFERENCES `meetings` (`meet_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for enroll2
-- ----------------------------

CREATE TABLE `enroll2` (
  `meet_id` int(11) NOT NULL,
  `mentor_id` int(11) NOT NULL,
  PRIMARY KEY (`meet_id`,`mentor_id`),
  KEY `enroll2_mentor` (`mentor_id`),
  CONSTRAINT `enroll2_mentor` FOREIGN KEY (`mentor_id`) REFERENCES `mentors` (`mentor_id`) ON DELETE CASCADE,
  CONSTRAINT `enroll2_meetings` FOREIGN KEY (`meet_id`) REFERENCES `meetings` (`meet_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for assign
-- ----------------------------

CREATE TABLE `assign` (
  `meet_id` int(11) NOT NULL,
  `material_id` int(11) NOT NULL,
  PRIMARY KEY (`meet_id`,`material_id`),
  KEY `assign_material` (`material_id`),
  KEY `assign_meetings` (`meet_id`),
  CONSTRAINT `assign_material` FOREIGN KEY (`material_id`) REFERENCES `material` (`material_id`) ON DELETE CASCADE,
  CONSTRAINT `assign_meetings` FOREIGN KEY (`meet_id`) REFERENCES `meetings` (`meet_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;



INSERT INTO users 
VALUES 
(1, 'HopeJohnson@gmail.com', 'chalkboard', 'Hope Johnson', '808-777-2222'),
(2, 'JohnSmith@gmail.com', 'password123', 'John Smith', '802-800-8450'),
(3, 'WillSmith@gmail.com', 'table', 'Will Smith', '802-800-8999'),
(4, 'BenBob@gmail.com', 'mechanicalpencil', 'Ben Bob', '805-800-1234'),
(5, 'JimBob@gmail.com', 'sweathshirt', 'Jim Bob', '807-800-8543'),
(6, 'LeeMilk@gmail.com', 'laptop', 'Lee Milk', '812-800-7777'),
(7, 'LannySmith@gmail.com', 'chair', 'Lanny Smith', '833-800-8876'),
(8, 'SarahWelton@gmail.com', 'tilefloor', 'Sarah Welton', '800-800-8111'),
(9, 'MileyWelton@gmail.com', 'waterbottle', 'Miley Welton', '800-800-8222'),
(10, 'DannyJohnson@gmail.com', 'whiteboard', 'Danny Johnson', '808-800-9999'),
(11, 'admin@gmail.com', 'password', 'Admin', null);

INSERT INTO admins 
VALUES 
(11);

INSERT INTO parents
VALUES 
(1), 
(2), 
(4), 
(8);

INSERT INTO students
VALUES 
(3, 7, 2),
(5, 9, 4),
(7, 11, 2),
(9, 9, 8),
(10, 6, 1);



INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 6', 6, 9, NULL);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 7', 7, 10, NULL);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 8', 8, 11, NULL);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 9', 9, 12, 6);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 10', 10, NULL, 7);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 11', 11, NULL, 8);
INSERT INTO `groups` (`name`, `description`, `mentor_grade_req`, `mentee_grade_req`) VALUES ('Group 12', 12, NULL, 9);


INSERT INTO time_slot
VALUES
(7, 'Friday', '7:30', '8:30');

INSERT INTO meetings (meet_id, meet_name, date, time_slot_id, capacity, announcement, group_id)
VALUES
(1, 'Group 6 Week 1', '2020-08-01', 7, 10, 'Test Announcement', 1),
(2, 'Group 6 Week 2', '2020-08-08', 7, 10, 'Test Announcement', 1),
(3, 'Group 6 Week 3', '2020-08-15', 7, 10, 'Test Announcement', 1),
(4, 'Group 6 Week 4', '2020-08-22', 7, 10, 'Test Announcement', 1),
(5, 'Group 6 Week 5', '2020-08-29', 7, 10, 'Test Announcement', 1),

(6, 'Group 7 Week 1', '2020-08-01', 7, 10, 'Test Announcement', 2),
(7, 'Group 7 Week 2', '2020-08-08', 7, 10, 'Test Announcement', 2),
(8, 'Group 7 Week 3', '2020-08-15', 7, 10, 'Test Announcement', 2),
(9, 'Group 7 Week 4', '2020-08-22', 7, 10, 'Test Announcement', 2),
(10, 'Group 7 Week 5', '2020-08-29', 7, 10, 'Test Announcement', 2),

(11, 'Group 8 Week 1', '2020-08-01', 7, 10, 'Test Announcement', 3),
(12, 'Group 8 Week 2', '2020-08-08', 7, 10, 'Test Announcement', 3),
(13, 'Group 8 Week 3', '2020-08-15', 7, 10, 'Test Announcement', 3),
(14, 'Group 8 Week 4', '2020-08-22', 7, 10, 'Test Announcement', 3),
(15, 'Group 8 Week 5', '2020-08-29', 7, 10, 'Test Announcement', 3),

(16, 'Group 9 Week 1', '2020-08-01', 7, 10, 'Test Announcement', 4),
(17, 'Group 9 Week 2', '2020-08-08', 7, 10, 'Test Announcement', 4),
(18, 'Group 9 Week 3', '2020-08-15', 7, 10, 'Test Announcement', 4),
(19, 'Group 9 Week 4', '2020-08-22', 7, 10, 'Test Announcement', 4),
(20, 'Group 9 Week 5', '2020-08-29', 7, 10, 'Test Announcement', 4)
