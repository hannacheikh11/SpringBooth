INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Jose','Perez','jp@hotmail.com',65433223,'2021-10-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Carlos','Lopez','cl@hotmail.com',65433223,'2021-01-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Maria','Orillana','mo@hotmail.com',65433223,'2021-02-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Dina','Ramirez','dr@hotmail.com',65433223,'2021-03-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Mirna','Ramos','mr@hotmail.com',65433223,'2021-04-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Pepe','Mojica','pm@hotmail.com',65433223,'2021-05-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Juan','Chavez','jc@hotmail.com',65433223,'2021-06-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Enrrique','Iglesias','ei@hotmail.com',65433223,'2021-07-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Pedro','Diaz','pd@hotmail.com',65433223,'2021-08-01');
INSERT INTO alumnos (nombre,apellido,email,telefono,create_at) VALUES('Ramon','Gonzalez','rgonzalez@gmail.com',65433223,'2021-09-01');

INSERT INTO `usuarios` (username,password,enabled) VALUES('jose','$2a$10$Jf1B1DvYy3spSruEe8kf4OXx1jeyPaOgTHPgXiUaUQQ/s/O.PWhbu',1);
INSERT INTO `usuarios` (username,password,enabled) VALUES('admin','$2a$10$8t2e9DEl.ZSajFHzwu/JKexkpmgoIpH6JQsK.rWlseVjAxCCzuf/K',1);

INSERT INTO `roles` (nombre) VALUES('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES(1,1);
INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES(2,2);
INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES(2,1);