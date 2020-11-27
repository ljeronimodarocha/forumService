alter table comentario(
 FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
 FOREIGN KEY (codigo_post) REFERENCES post(codigo)
) 