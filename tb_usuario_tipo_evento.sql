--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2023-01-10 00:24:41

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
-- TOC entry 227 (class 1259 OID 378188)
-- Name: usuario_tipo_evento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_tipo_evento (
    idusuario_tipo_evento integer NOT NULL,
    fecha_creado timestamp without time zone NOT NULL,
    creado_por text NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE public.usuario_tipo_evento OWNER TO postgres;

--
-- TOC entry 2291 (class 0 OID 378188)
-- Dependencies: 227
-- Data for Name: usuario_tipo_evento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_tipo_evento (idusuario_tipo_evento, fecha_creado, creado_por, nombre) FROM stdin;
1	2023-01-09 15:31:38.432	PROGRAMADOR ADMIN	SIN-DATO
2	2023-01-09 15:32:05.752	PROGRAMADOR ADMIN	BOTON EDITAR
3	2023-01-09 19:42:36.337	PROGRAMADOR ADMIN	HABILITAR jTabbedPane 
\.


--
-- TOC entry 2176 (class 2606 OID 378313)
-- Name: usuario_tipo_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_tipo_evento
    ADD CONSTRAINT usuario_tipo_evento_pkey PRIMARY KEY (idusuario_tipo_evento);


-- Completed on 2023-01-10 00:24:41

--
-- PostgreSQL database dump complete
--

