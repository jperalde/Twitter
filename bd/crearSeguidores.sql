--crearSeguidores

create table Seguidores(
	nickSeguidor varchar(20) not null,
	nickSeguido varchar(20) not null,
	foreign key (nickSeguidor) references Usuario(nick) ON DELETE CASCADE,
	foreign key (nickSeguido) references Usuario(nick) ON DELETE CASCADE,
	primary key(nickSeguidor,nickSeguido)
);
