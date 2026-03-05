--
-- PostgreSQL database dump
--

\restrict WDI7I3vhhO7qKzpaJznYylSeJ4V54aPPAx1pHRs1IdCIhI4GcEFbnYtRdgE49cB

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-03-03 12:16:05

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

DROP DATABASE client_service_db;
--
-- TOC entry 5046 (class 1262 OID 16690)
-- Name: client_service_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE client_service_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';


ALTER DATABASE client_service_db OWNER TO postgres;

\unrestrict WDI7I3vhhO7qKzpaJznYylSeJ4V54aPPAx1pHRs1IdCIhI4GcEFbnYtRdgE49cB
\connect client_service_db
\restrict WDI7I3vhhO7qKzpaJznYylSeJ4V54aPPAx1pHRs1IdCIhI4GcEFbnYtRdgE49cB

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
-- TOC entry 223 (class 1259 OID 16724)
-- Name: clients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    person_id bigint NOT NULL,
    client_id character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    status boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.clients OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16723)
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clients_id_seq OWNER TO postgres;

--
-- TOC entry 5047 (class 0 OID 0)
-- Dependencies: 222
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clients_id_seq OWNED BY public.clients.id;


--
-- TOC entry 219 (class 1259 OID 16692)
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
-- TOC entry 221 (class 1259 OID 16710)
-- Name: persons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persons (
    id bigint NOT NULL,
    name character varying(100) NOT NULL,
    gender character varying(20),
    age integer,
    identification character varying(20) NOT NULL,
    address character varying(200),
    phone character varying(20),
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.persons OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16709)
-- Name: persons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.persons_id_seq OWNER TO postgres;

--
-- TOC entry 5048 (class 0 OID 0)
-- Dependencies: 220
-- Name: persons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persons_id_seq OWNED BY public.persons.id;


--
-- TOC entry 4869 (class 2604 OID 16727)
-- Name: clients id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients ALTER COLUMN id SET DEFAULT nextval('public.clients_id_seq'::regclass);


--
-- TOC entry 4866 (class 2604 OID 16713)
-- Name: persons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons ALTER COLUMN id SET DEFAULT nextval('public.persons_id_seq'::regclass);


--
-- TOC entry 5040 (class 0 OID 16724)
-- Dependencies: 223
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clients (id, person_id, client_id, password, status, created_at, updated_at) FROM stdin;
2	2	mariana01	5678	t	2026-03-03 11:10:12.202751	2026-03-03 11:10:12.202751
1	1	jose01	newpass123	t	2026-03-03 11:10:06.28137	2026-03-03 11:10:28.631988
\.


--
-- TOC entry 5036 (class 0 OID 16692)
-- Dependencies: 219
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create personas clientes	SQL	V1__create_personas_clientes.sql	-1620279183	postgres	2026-03-03 08:56:24.4639	72	t
\.


--
-- TOC entry 5038 (class 0 OID 16710)
-- Dependencies: 221
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.persons (id, name, gender, age, identification, address, phone, created_at, updated_at) FROM stdin;
2	Mariana Montalvo	Female	25	1712345678	Armenia y Rocafuerte	097548965	2026-03-03 11:10:12.188992	2026-03-03 11:10:12.188992
1	Jose Lema Updated	Male	31	0912345678	Nueva direccion 123	098000000	2026-03-03 11:10:06.147912	2026-03-03 11:10:28.60041
\.


--
-- TOC entry 5049 (class 0 OID 0)
-- Dependencies: 222
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clients_id_seq', 2, true);


--
-- TOC entry 5050 (class 0 OID 0)
-- Dependencies: 220
-- Name: persons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.persons_id_seq', 2, true);


--
-- TOC entry 4882 (class 2606 OID 16739)
-- Name: clients clients_client_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_client_id_key UNIQUE (client_id);


--
-- TOC entry 4884 (class 2606 OID 16737)
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- TOC entry 4874 (class 2606 OID 16707)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4878 (class 2606 OID 16722)
-- Name: persons persons_identification_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_identification_key UNIQUE (identification);


--
-- TOC entry 4880 (class 2606 OID 16720)
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- TOC entry 4875 (class 1259 OID 16708)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4885 (class 1259 OID 16746)
-- Name: idx_clients_client_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_clients_client_id ON public.clients USING btree (client_id);


--
-- TOC entry 4886 (class 1259 OID 16747)
-- Name: idx_clients_person_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clients_person_id ON public.clients USING btree (person_id);


--
-- TOC entry 4887 (class 1259 OID 16748)
-- Name: idx_clients_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clients_status ON public.clients USING btree (status);


--
-- TOC entry 4876 (class 1259 OID 16745)
-- Name: idx_persons_identification; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_persons_identification ON public.persons USING btree (identification);


--
-- TOC entry 4888 (class 2606 OID 16740)
-- Name: clients clients_person_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_person_id_fkey FOREIGN KEY (person_id) REFERENCES public.persons(id) ON DELETE CASCADE;


-- Completed on 2026-03-03 12:16:06

--
-- PostgreSQL database dump complete
--

\unrestrict WDI7I3vhhO7qKzpaJznYylSeJ4V54aPPAx1pHRs1IdCIhI4GcEFbnYtRdgE49cB

