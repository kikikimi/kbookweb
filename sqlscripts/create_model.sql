create table model ( BasePrice DECIMAL(12,2) not null auto_increment,
Make VARCHAR(100) not null,
ModelName VARCHAR (255) not null,
ModelID int not null primary key,
CONSTRAINT uc_MakeModel UNIQUE (ModelName, Make)
);