DROP TABLE IF EXISTS participates_in;
DROP TABLE IF EXISTS registered_for;
DROP TABLE IF EXISTS assigned_for;

DROP TABLE IF EXISTS students;

DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS parents;

DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS meetings;
DROP TABLE IF EXISTS groups;
DROP TABLE IF EXISTS study_materials;

DROP TABLE IF EXISTS year_info;

-- ----------------------------
-- Table structure for users
-- ----------------------------

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(255) NOT NULL,
  lastName varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  phone varchar(255) DEFAULT NULL,
  password varchar(255) NOT NULL,
  PRIMARY KEY (id) USING BTREE,
  UNIQUE KEY email (email)
);

-- TODO: Add trigger to check if id exists in the other of two types of user tables

-- ----------------------------
-- Table structure for admins
-- ----------------------------

CREATE TABLE admins (
  id int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT admin_user FOREIGN KEY (id) REFERENCES Users (id) ON DELETE CASCADE
);

-- ----------------------------
-- Table structure for parents
-- ----------------------------

CREATE TABLE parents (
  id int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT parent_user FOREIGN KEY (id) REFERENCES Users (id) ON DELETE CASCADE
);

-- ----------------------------
-- Table structure for students
-- ----------------------------

CREATE TABLE students (
  id int(11) NOT NULL,
  grade int(11) NOT NULL,
  parent_id int(11) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT student_user FOREIGN KEY (id) REFERENCES Users (id) ON DELETE CASCADE,
  CONSTRAINT student_parent FOREIGN KEY (parent_id) REFERENCES Parents (id) ON DELETE CASCADE
);

-- ----------------------------
-- Table structure for groups
-- ----------------------------

CREATE TABLE groups (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  mentor_grade_req int(11) NOT NULL,
  mentee_grade_req int(11) NOT NULL,
  PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for registered_for
-- ----------------------------

CREATE TABLE registered_for (
  student_id int(11) NOT NULL,
  group_id int(11) NOT NULL,
  mentor_or_mentee bit(1) NOT NULL,
  PRIMARY KEY (student_id, group_id)
);

-- ----------------------------
-- Table structure for meetings
-- ----------------------------

CREATE TABLE meetings (
  id int(11) NOT NULL AUTO_INCREMENT,
  date date DEFAULT NULL,
  time int(11) NOT NULL,
  capacity int(11) NOT NULL,
  url varchar(255) NOT NULL,
  announcement varchar(255) DEFAULT NULL,
  group_id int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT meeting_group FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);

-- ----------------------------
-- Table structure for participates_in
-- ----------------------------

CREATE TABLE participates_in (
  student_id int(11) NOT NULL,
  meeting_id int(11) NOT NULL,
  PRIMARY KEY (student_id, meeting_id)
);

-- ----------------------------
-- Table structure for study_materials
-- ----------------------------

CREATE TABLE study_materials (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(255) NOT NULL,
  author varchar(255) DEFAULT NULL,
  type varchar(255) NOT NULL,
  url varchar(255) DEFAULT NULL,
  assigned_date date NOT NULL,
  notes text,
  PRIMARY KEY (id)
);

-- ----------------------------
-- Table structure for assigned_for
-- ----------------------------

CREATE TABLE assigned_for (
  study_materials_id int(11) NOT NULL,
  meeting_id int(11) NOT NULL,
  PRIMARY KEY (study_materials_id, meeting_id)
);

-- ----------------------------
-- Table structure for year_info
-- ----------------------------

CREATE TABLE year_info (
  start_date date NOT NULL,
  end_date date NOT NULL
);