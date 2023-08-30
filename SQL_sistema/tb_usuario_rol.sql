--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2023-01-10 00:24:05

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
-- TOC entry 226 (class 1259 OID 378182)
-- Name: usuario_rol; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_rol (
    idusuario_rol integer NOT NULL,
    fecha_creado timestamp without time zone NOT NULL,
    creado_por text NOT NULL,
    nombre text NOT NULL,
    descripcion text NOT NULL
);


ALTER TABLE public.usuario_rol OWNER TO postgres;

--
-- TOC entry 2291 (class 0 OID 378182)
-- Dependencies: 226
-- Data for Name: usuario_rol; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_rol (idusuario_rol, fecha_creado, creado_por, nombre, descripcion) FROM stdin;
1	2023-01-09 15:32:55.673	PROGRAMADOR ADMIN	SIN-DATO	SIN
2	2023-01-09 15:33:22.785	PROGRAMADOR ADMIN	CAJERO	USUARIO CAJA
3	2023-01-09 15:33:58.057	PROGRAMADOR ADMIN	ADMINISTRADOR	NIVEL 2 DE ACCESO
4	2023-01-09 15:34:22.025	PROGRAMADOR ADMIN	PROGRAMADOR	ADMINISTRA EL SISTEMA
\.


--
-- TOC entry 2176 (class 2606 OID 378311)
-- Name: usuario_rol_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_rol
    ADD CONSTRAINT usuario_rol_pkey PRIMARY KEY (idusuario_rol);


-- Completed on 2023-01-10 00:24:06

--
-- PostgreSQL database dump complete
--

