CREATE table optionset (OptionSetID int not null auto_increment, 
OptionName varchar (100) not null, ModelID int not null,
primary key (OptionSetID),
constraint fk_ModOptSet foreign key (ModelID) references Model(ModelID)
);