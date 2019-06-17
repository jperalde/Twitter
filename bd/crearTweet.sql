--crearTweet

create table Tweet(
	nick varchar(20) not null,
	texto varchar(280) not null,
	instante numeric not null,
	foreign key (nick) references Usuario(nick) ON DELETE CASCADE,
	primary key (nick, instante)
);
