create table usuario(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50),
    state VARCHAR(50)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;