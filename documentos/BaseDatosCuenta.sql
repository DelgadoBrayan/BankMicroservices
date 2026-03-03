--
-- PostgreSQL database dump
--

\restrict mEA7qYkb5f94nCKD8QHtABEApKN0i40NhvJ0umia2N6VuLBWx4nlmNVP6wihnLM

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-03-02 15:46:55

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

DROP DATABASE banco_cuentas;
--
-- TOC entry 5049 (class 1262 OID 16501)
-- Name: banco_cuentas; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE banco_cuentas WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';


ALTER DATABASE banco_cuentas OWNER TO postgres;

\unrestrict mEA7qYkb5f94nCKD8QHtABEApKN0i40NhvJ0umia2N6VuLBWx4nlmNVP6wihnLM
\connect banco_cuentas
\restrict mEA7qYkb5f94nCKD8QHtABEApKN0i40NhvJ0umia2N6VuLBWx4nlmNVP6wihnLM

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
-- TOC entry 221 (class 1259 OID 16577)
-- Name: cuentas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuentas (
    id bigint NOT NULL,
    numero_cuenta character varying(20) NOT NULL,
    tipo_cuenta character varying(30) NOT NULL,
    saldo_inicial numeric(15,2) DEFAULT 0.00 NOT NULL,
    saldo_disponible numeric(15,2) DEFAULT 0.00 NOT NULL,
    estado boolean DEFAULT true NOT NULL,
    cliente_id character varying(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.cuentas OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16576)
-- Name: cuentas_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuentas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cuentas_id_seq OWNER TO postgres;

--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 220
-- Name: cuentas_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuentas_id_seq OWNED BY public.cuentas.id;


--
-- TOC entry 219 (class 1259 OID 16559)
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
-- TOC entry 223 (class 1259 OID 16598)
-- Name: movimientos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movimientos (
    id bigint NOT NULL,
    cuenta_id bigint NOT NULL,
    fecha timestamp without time zone DEFAULT now() NOT NULL,
    tipo_movimiento character varying(20) NOT NULL,
    valor numeric(15,2) NOT NULL,
    saldo numeric(15,2) NOT NULL,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.movimientos OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16597)
-- Name: movimientos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimientos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.movimientos_id_seq OWNER TO postgres;

--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 222
-- Name: movimientos_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movimientos_id_seq OWNED BY public.movimientos.id;


--
-- TOC entry 4866 (class 2604 OID 16580)
-- Name: cuentas id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas ALTER COLUMN id SET DEFAULT nextval('public.cuentas_id_seq'::regclass);


--
-- TOC entry 4872 (class 2604 OID 16601)
-- Name: movimientos id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimientos ALTER COLUMN id SET DEFAULT nextval('public.movimientos_id_seq'::regclass);


--
-- TOC entry 5041 (class 0 OID 16577)
-- Dependencies: 221
-- Data for Name: cuentas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuentas (id, numero_cuenta, tipo_cuenta, saldo_inicial, saldo_disponible, estado, cliente_id, created_at, updated_at) FROM stdin;
\.


--
-- TOC entry 5039 (class 0 OID 16559)
-- Dependencies: 219
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create cuentas movimientos	SQL	V1__create_cuentas_movimientos.sql	-1289016099	postgres	2026-03-02 15:45:40.338789	414	t
\.


--
-- TOC entry 5043 (class 0 OID 16598)
-- Dependencies: 223
-- Data for Name: movimientos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movimientos (id, cuenta_id, fecha, tipo_movimiento, valor, saldo, created_at) FROM stdin;
\.


--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 220
-- Name: cuentas_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuentas_id_seq', 1, false);


--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 222
-- Name: movimientos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movimientos_id_seq', 1, false);


--
-- TOC entry 4879 (class 2606 OID 16596)
-- Name: cuentas cuentas_numero_cuenta_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas
    ADD CONSTRAINT cuentas_numero_cuenta_key UNIQUE (numero_cuenta);


--
-- TOC entry 4881 (class 2606 OID 16594)
-- Name: cuentas cuentas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas
    ADD CONSTRAINT cuentas_pkey PRIMARY KEY (id);


--
-- TOC entry 4876 (class 2606 OID 16574)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4890 (class 2606 OID 16611)
-- Name: movimientos movimientos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimientos
    ADD CONSTRAINT movimientos_pkey PRIMARY KEY (id);


--
-- TOC entry 4877 (class 1259 OID 16575)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4882 (class 1259 OID 16620)
-- Name: idx_cuentas_cliente_estado; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_cuentas_cliente_estado ON public.cuentas USING btree (cliente_id, estado);


--
-- TOC entry 4883 (class 1259 OID 16618)
-- Name: idx_cuentas_cliente_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_cuentas_cliente_id ON public.cuentas USING btree (cliente_id);


--
-- TOC entry 4884 (class 1259 OID 16619)
-- Name: idx_cuentas_estado; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_cuentas_estado ON public.cuentas USING btree (estado);


--
-- TOC entry 4885 (class 1259 OID 16617)
-- Name: idx_cuentas_numero_cuenta; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_cuentas_numero_cuenta ON public.cuentas USING btree (numero_cuenta);


--
-- TOC entry 4886 (class 1259 OID 16623)
-- Name: idx_movimientos_cuenta_fecha; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movimientos_cuenta_fecha ON public.movimientos USING btree (cuenta_id, fecha);


--
-- TOC entry 4887 (class 1259 OID 16621)
-- Name: idx_movimientos_cuenta_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movimientos_cuenta_id ON public.movimientos USING btree (cuenta_id);


--
-- TOC entry 4888 (class 1259 OID 16622)
-- Name: idx_movimientos_fecha; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movimientos_fecha ON public.movimientos USING btree (fecha);


--
-- TOC entry 4891 (class 2606 OID 16612)
-- Name: movimientos movimientos_cuenta_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimientos
    ADD CONSTRAINT movimientos_cuenta_id_fkey FOREIGN KEY (cuenta_id) REFERENCES public.cuentas(id);


-- Completed on 2026-03-02 15:46:56

--
-- PostgreSQL database dump complete
--

\unrestrict mEA7qYkb5f94nCKD8QHtABEApKN0i40NhvJ0umia2N6VuLBWx4nlmNVP6wihnLM

