--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2023-01-10 00:20:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 223 (class 1259 OID 378163)
-- Name: usuario_evento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_evento (
    idusuario_evento integer NOT NULL,
    fecha_creado timestamp without time zone NOT NULL,
    creado_por text NOT NULL,
    codigo integer NOT NULL,
    nombre text NOT NULL,
    descripcion text NOT NULL,
    fk_idusuario_tipo_evento integer NOT NULL,
    fk_idusuario_formulario integer NOT NULL,
    mensaje_error text DEFAULT 'error'::text
);


ALTER TABLE public.usuario_evento OWNER TO postgres;

--
-- TOC entry 2292 (class 0 OID 378163)
-- Dependencies: 223
-- Data for Name: usuario_evento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_evento (idusuario_evento, fecha_creado, creado_por, codigo, nombre, descripcion, fk_idusuario_tipo_evento, fk_idusuario_formulario, mensaje_error) FROM stdin;
1	2023-01-09 15:36:21.209	PROGRAMADOR ADMIN	1001	HABILITAR EDITAR PRODUCTO	permite editar todos los datos del producto	2	2	TU ROLL NO PUEDE EDITAR EL PRODUCTO
2	2023-01-09 19:44:33.985	PROGRAMADOR ADMIN	1002	HABILITAR TABBED CAJA CERRADO	muestra todas las cajas cerrados anteriormente	3	3	TU ROLL NO PUEDE VER CAJA CERRADO
\.


--
-- TOC entry 2177 (class 2606 OID 378303)
-- Name: usuario_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_evento
    ADD CONSTRAINT usuario_evento_pkey PRIMARY KEY (idusuario_evento);


-- Completed on 2023-01-10 00:20:27

--
-- PostgreSQL database dump complete
--

