create table comentario(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    comentario VARCHAR(50) NOT NULL,
    dataComentario DATE NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_post BIGINT(20) NOT NULL
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;
create table post(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(50) NOT NULL,
    codigo_dono BIGINT(20) NOT NULL,
    codigo_comentario BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_dono) REFERENCES usuario(codigo),
	FOREIGN KEY (codigo_comentario) REFERENCES comentario(codigo)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;