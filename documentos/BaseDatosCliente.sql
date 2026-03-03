--
-- PostgreSQL database dump
--

\restrict LQCkdqFvckSRvTake8CQXvGszR7fIQeSsOfg74JYPRhmBHygBrw9cojfpQaErgc

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-03-02 15:45:51

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE banco_clientes;
--
-- TOC entry 5046 (class 1262 OID 16500)
-- Name: banco_clientes; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE banco_clientes WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';


ALTER DATABASE banco_clientes OWNER TO postgres;

\unrestrict LQCkdqFvckSRvTake8CQXvGszR7fIQeSsOfg74JYPRhmBHygBrw9cojfpQaErgc
\connect banco_clientes
\restrict LQCkdqFvckSRvTake8CQXvGszR7fIQeSsOfg74JYPRhmBHygBrw9cojfpQaErgc

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 223 (class 1259 OID 16534)
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    id bigint NOT NULL,
    persona_id bigint NOT NULL,
    cliente_id character varying(50) NOT NULL,
    contrasena character varying(100) NOT NULL,
    estado boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16533)
-- Name: clientes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clientes_id_seq OWNER TO postgres;

--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 222
-- Name: clientes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_id_seq OWNED BY public.clientes.id;


--
-- TOC entry 219 (class 1259 OID 16502)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16520)
-- Name: personas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personas (
    id bigint NOT NULL,
    nombre character varying(100) NOT NULL,
    genero character varying(20),
    edad integer,
    identificacion character varying(20) NOT NULL,
    direccion character varying(200),
    telefono character varying(20),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.personas OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16519)
-- Name: personas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.personas_id_seq OWNER TO postgres;

--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 220
-- Name: personas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personas_id_seq OWNED BY public.personas.id;


--
-- TOC entry 4869 (class 2604 OID 16537)
-- Name: clientes id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN id SET DEFAULT nextval('public.clientes_id_seq'::regclass);


--
-- TOC entry 4866 (class 2604 OID 16523)
-- Name: personas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas ALTER COLUMN id SET DEFAULT nextval('public.personas_id_seq'::regclass);


--
-- TOC entry 5040 (class 0 OID 16534)
-- Dependencies: 223
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clientes (id, persona_id, cliente_id, contrasena, estado, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 5036 (class 0 OID 16502)
-- Dependencies: 219
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create personas clientes	SQL	V1__create_personas_clientes.sql	1921836492	postgres	2026-03-02 15:44:36.439833	188	t
\.


--
-- TOC entry 5038 (class 0 OID 16520)
-- Dependencies: 221
-- Data for Name: personas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personas (id, nombre, genero, edad, identificacion, direccion, telefono, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 222
-- Name: clientes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clientes_id_seq', 1, false);


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 220
-- Name: personas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personas_id_seq', 1, false);


--
-- TOC entry 4882 (class 2606 OID 16549)
-- Name: clientes clientes_cliente_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_cliente_id_key UNIQUE (cliente_id);


--
-- TOC entry 4884 (class 2606 OID 16547)
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id);


--
-- TOC entry 4874 (class 2606 OID 16517)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4878 (class 2606 OID 16532)
-- Name: personas personas_identificacion_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_identificacion_key UNIQUE (identificacion);


--
-- TOC entry 4880 (class 2606 OID 16530)
-- Name: personas personas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personas
    ADD CONSTRAINT personas_pkey PRIMARY KEY (id);


--
-- TOC entry 4875 (class 1259 OID 16518)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4885 (class 1259 OID 16556)
-- Name: idx_clientes_cliente_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_clientes_cliente_id ON public.clientes USING btree (cliente_id);


--
-- TOC entry 4886 (class 1259 OID 16558)
-- Name: idx_clientes_estado; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clientes_estado ON public.clientes USING btree (estado);


--
-- TOC entry 4887 (class 1259 OID 16557)
-- Name: idx_clientes_persona_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clientes_persona_id ON public.clientes USING btree (persona_id);


--
-- TOC entry 4876 (class 1259 OID 16555)
-- Name: idx_personas_identificacion; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_personas_identificacion ON public.personas USING btree (identificacion);


--
-- TOC entry 4888 (class 2606 OID 16550)
-- Name: clientes clientes_persona_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_persona_id_fkey FOREIGN KEY (persona_id) REFERENCES public.personas(id) ON DELETE CASCADE;


-- Completed on 2026-03-02 15:45:53

--
-- PostgreSQL database dump complete
--

\unrestrict LQCkdqFvckSRvTake8CQXvGszR7fIQeSsOfg74JYPRhmBHygBrw9cojfpQaErgc

