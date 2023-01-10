--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2023-01-10 00:22:05

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
-- TOC entry 225 (class 1259 OID 378176)
-- Name: usuario_item_rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_item_rol (
    idusuario_item_rol integer NOT NULL,
    fecha_creado timestamp without time zone NOT NULL,
    creado_por text NOT NULL,
    activo boolean NOT NULL,
    fk_idusuario_rol integer NOT NULL,
    fk_idusuario_evento integer NOT NULL
);


ALTER TABLE public.usuario_item_rol OWNER TO postgres;

--
-- TOC entry 2291 (class 0 OID 378176)
-- Dependencies: 225
-- Data for Name: usuario_item_rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_item_rol (idusuario_item_rol, fecha_creado, creado_por, activo, fk_idusuario_rol, fk_idusuario_evento) FROM stdin;
2	2023-01-09 15:36:22.506	PROGRAMADOR ADMIN	f	3	1
3	2023-01-09 15:36:22.514	PROGRAMADOR ADMIN	f	2	1
4	2023-01-09 15:36:22.519	PROGRAMADOR ADMIN	f	1	1
6	2023-01-09 19:44:35.164	PROGRAMADOR ADMIN	f	3	2
7	2023-01-09 19:44:35.17	PROGRAMADOR ADMIN	f	2	2
8	2023-01-09 19:44:35.174	PROGRAMADOR ADMIN	f	1	2
5	2023-01-09 19:44:35.159	PROGRAMADOR ADMIN	t	4	2
1	2023-01-09 15:36:22.499	PROGRAMADOR ADMIN	t	4	1
\.


--
-- TOC entry 2176 (class 2606 OID 378307)
-- Name: usuario_item_rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_item_rol
    ADD CONSTRAINT usuario_item_rol_pkey PRIMARY KEY (idusuario_item_rol);


-- Completed on 2023-01-10 00:22:06

--
-- PostgreSQL database dump complete
--

