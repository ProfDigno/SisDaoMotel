--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.14
-- Dumped by pg_dump version 9.5.14

-- Started on 2023-01-10 00:21:01

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
-- TOC entry 224 (class 1259 OID 378170)
-- Name: usuario_formulario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario_formulario (
    idusuario_formulario integer NOT NULL,
    fecha_creado timestamp without time zone NOT NULL,
    creado_por text NOT NULL,
    nombre text NOT NULL
);


ALTER TABLE public.usuario_formulario OWNER TO postgres;

--
-- TOC entry 2291 (class 0 OID 378170)
-- Dependencies: 224
-- Data for Name: usuario_formulario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario_formulario (idusuario_formulario, fecha_creado, creado_por, nombre) FROM stdin;
1	2023-01-09 15:29:58.811	PROGRAMADOR ADMIN	SIN-DATO
2	2023-01-09 15:30:51	PROGRAMADOR ADMIN	PRODUCTO
3	2023-01-09 19:40:52.595	PROGRAMADOR ADMIN	CAJA
\.


--
-- TOC entry 2176 (class 2606 OID 378305)
-- Name: usuario_formulario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario_formulario
    ADD CONSTRAINT usuario_formulario_pkey PRIMARY KEY (idusuario_formulario);


-- Completed on 2023-01-10 00:21:01

--
-- PostgreSQL database dump complete
--

