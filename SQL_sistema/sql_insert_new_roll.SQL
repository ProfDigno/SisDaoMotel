INSERT INTO public.usuario_evento(idusuario_evento, fecha_creado, creado_por, codigo, nombre, descripcion, fk_idusuario_tipo_evento, fk_idusuario_formulario, mensaje_error) 
values
(1,'2023-01-09 15:36:21.209','PROGRAMADOR ADMIN',1001,'HABILITAR EDITAR PRODUCTO','permite editar todos los datos del producto',2,2,'TU ROLL NO PUEDE EDITAR EL PRODUCTO'),
(2,'2023-01-09 19:44:33.985','PROGRAMADOR ADMIN',1002,'HABILITAR TABBED CAJA CERRADO','muestra todas las cajas cerrados anteriormente',3,3,'TU ROLL NO PUEDE VER CAJA CERRADO');

INSERT INTO  public.usuario_formulario (idusuario_formulario, fecha_creado, creado_por, nombre) values
(1,'2023-01-09 15:29:58.811','PROGRAMADOR ADMIN','SIN-DATO'),
(2,'2023-01-09 15:30:51','PROGRAMADOR ADMIN','PRODUCTO'),
(3,'2023-01-09 19:40:52.595','PROGRAMADOR ADMIN','CAJA');

INSERT INTO public.usuario_item_rol (idusuario_item_rol, fecha_creado, creado_por, activo, fk_idusuario_rol, fk_idusuario_evento) VALUES
('2','2023-01-09 15:36:22.506','PROGRAMADOR ADMIN','f','3','1'),
('3','2023-01-09 15:36:22.514','PROGRAMADOR ADMIN','f','2','1'),
('4','2023-01-09 15:36:22.519','PROGRAMADOR ADMIN','f','1','1'),
('6','2023-01-09 19:44:35.164','PROGRAMADOR ADMIN','f','3','2'),
('7','2023-01-09 19:44:35.17','PROGRAMADOR ADMIN','f','2','2'),
('8','2023-01-09 19:44:35.174','PROGRAMADOR ADMIN','f','1','2'),
('5','2023-01-09 19:44:35.159','PROGRAMADOR ADMIN','t','4','2'),
('1','2023-01-09 15:36:22.499','PROGRAMADOR ADMIN','t','4','1');

INSERT INTO  public.usuario_rol (idusuario_rol, fecha_creado, creado_por, nombre, descripcion) VALUES
('1','2023-01-09 15:32:55.673','PROGRAMADOR ADMIN','SIN-DATO','SIN'),
('2','2023-01-09 15:33:22.785','PROGRAMADOR ADMIN','CAJERO','USUARIO CAJA'),
('3','2023-01-09 15:33:58.057','PROGRAMADOR ADMIN','ADMINISTRADOR','NIVEL 2 DE ACCESO'),
('4','2023-01-09 15:34:22.025','PROGRAMADOR ADMIN','PROGRAMADOR','ADMINISTRA EL SISTEMA');

INSERT INTO  public.usuario_tipo_evento (idusuario_tipo_evento, fecha_creado, creado_por, nombre) VALUES
('1','2023-01-09 15:31:38.432','PROGRAMADOR ADMIN','SIN-DATO'),
('2','2023-01-09 15:32:05.752','PROGRAMADOR ADMIN','BOTON EDITAR'),
('3','2023-01-09 19:42:36.337','PROGRAMADOR ADMIN','HABILITAR jTabbedPane'); 