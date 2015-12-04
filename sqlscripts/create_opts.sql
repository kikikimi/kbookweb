Create table Opts (
OptionID int not null auto_increment,
OptionPrice decimal(9, 2),
OptionValue varchar(150) not null, 
OptionSetID int not null,
Primary key (OptionID),
constraint fk_OpSetOpt foreign key (OptionSetID) references optionset(OptionSetID)
);