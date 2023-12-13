DROP DATABASE IF EXISTS ServiceTickets;
CREATE DATABASE ServiceTickets; 
FLUSH PRIVILEGES;
USE ServiceTickets;

DROP TABLE IF EXISTS EventActivity;
CREATE TABLE EventActivity (
	ID						int AUTO_INCREMENT not null,
	ActivityName			varchar(20),
	primary key (ID)
);

DROP TABLE IF EXISTS EventOrigin;
CREATE TABLE EventOrigin (
	ID						int AUTO_INCREMENT not null,
	ActivityName			varchar(20),
	primary key (ID)
); 

DROP TABLE IF EXISTS EventStatus;
CREATE TABLE EventStatus (
	ID					int AUTO_INCREMENT not null,
	Status				varchar(20),
	primary key (ID)
);

DROP TABLE IF EXISTS EventClass;
CREATE TABLE EventClass (
	ID					int AUTO_INCREMENT not null,
	Class				varchar(20),
	primary key (ID)
);

DROP TABLE IF EXISTS EventLog;
CREATE TABLE EventLog (
	ID						int AUTO_INCREMENT not null,
	CaseID					varchar(20),
    Activity				varchar(20),
    Urgency 				varchar(1),
    Impact 					varchar(1),
    Priority				varchar(1),
    StartDate				date,
    EndDate					date,
    TicketStatus			varchar(20),
    UpdateDateTime			datetime,
    Duration				int,
    Origin					varchar(20),
    Class					varchar(20),
	PRIMARY KEY (ID)
    -- FOREIGN KEY (Activity) REFERENCES EventActivity(ID),
	-- FOREIGN KEY (Origin) REFERENCES EventOrigin(ID),
    -- FOREIGN KEY (Class) REFERENCES EventClass(ID),
    -- FOREIGN KEY (TicketStatus) REFERENCES EventStatus(ID)
);