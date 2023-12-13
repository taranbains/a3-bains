DROP DATABASE IF EXISTS StudentRegistration;
CREATE DATABASE StudentRegistration; 
FLUSH PRIVILEGES;
USE StudentRegistration;

DROP TABLE IF EXISTS STUDENT;
CREATE TABLE STUDENT (
	StudentID			varchar(10) not null,
	FirstName			varchar(60),
	LastName			varchar(50),
    Location			varchar(100),
	primary key (StudentID)
);

INSERT INTO STUDENT (StudentID, FirstName, LastName, Location)
VALUES
('30224483', 'Paul', 'Smith', 'Calgary, AB'),
('30224443', 'Mary', 'Wilson', 'Vancouver, BC'),
('30224490', 'Ben', 'Kenny', 'Calgary, AB'), 
('30224462', 'Sam', 'Johnson', 'Toronto, ON'), 
('30224417', 'Kyle', 'Baker', 'Calgary, AB'); 


DROP TABLE IF EXISTS COURSE;
CREATE TABLE COURSE (
	CourseID			varchar(10)	not null,
	CourseName			varchar(50),
	CourseTitle			varchar(50),
	primary key (CourseID)
);

INSERT INTO COURSE(CourseID, CourseName, CourseTitle)
VALUES
('ENSF607', 'Advanced Software Development', 'ENSF'),
('ENSF614', 'Advanced System Analysis', 'ENSF'),
('ENSF608', 'Databases', 'ENSF'),
('ENSF612', 'Large Scale Data Analytics', 'ENSF'),
('ENSF611', 'Machine Learning for Software', 'ENSF');


DROP TABLE IF EXISTS REGISTRATION;
CREATE TABLE REGISTRATION (
	RegistrationID		varchar(10)	not null,
	CourseID			varchar(10),
	StudentID			varchar(10),
	primary key (RegistrationID),
	foreign key (StudentID) references STUDENT(StudentID),
    foreign key (CourseID) references COURSE(CourseID)
);

INSERT INTO REGISTRATION(RegistrationID, CourseID, StudentID)
VALUES
('100', 'ENSF607', '30224483'),
('101', 'ENSF614', '30224483'),
('102', 'ENSF608', '30224443'),
('103', 'ENSF611', '30224443'),
('104', 'ENSF612', '30224417'),
('105', 'ENSF611', '30224417'),
('106', 'ENSF607', '30224417'),
('107', 'ENSF608', '30224462'),
('108', 'ENSF614', '30224462'),
('109', 'ENSF612', '30224490'),
('110', 'ENSF607', '30224490'); 

