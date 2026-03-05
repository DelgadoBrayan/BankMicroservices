--
-- PostgreSQL database dump
--

\restrict FSX12ZE01O4C9gix4H5AyXkvOfThI06fWeLwAmCb5zr5j6cSycKBeMCuZfXWpZY

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

-- Started on 2026-03-03 12:15:34

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

DROP DATABASE account_service_db;
--
-- TOC entry 5050 (class 1262 OID 16691)
-- Name: account_service_db; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE account_service_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Colombia.1252';


ALTER DATABASE account_service_db OWNER TO postgres;

\unrestrict FSX12ZE01O4C9gix4H5AyXkvOfThI06fWeLwAmCb5zr5j6cSycKBeMCuZfXWpZY
\connect account_service_db
\restrict FSX12ZE01O4C9gix4H5AyXkvOfThI06fWeLwAmCb5zr5j6cSycKBeMCuZfXWpZY

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
-- TOC entry 221 (class 1259 OID 16832)
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
    id bigint NOT NULL,
    account_number character varying(20) NOT NULL,
    account_type character varying(20) NOT NULL,
    initial_balance numeric(15,2) DEFAULT 0.00 NOT NULL,
    available_balance numeric(15,2) DEFAULT 0.00 NOT NULL,
    status boolean DEFAULT true NOT NULL,
    client_id character varying(50) NOT NULL,
    created_at timestamp without time zone DEFAULT now(),
    updated_at timestamp without time zone DEFAULT now(),
    CONSTRAINT accounts_account_type_check CHECK (((account_type)::text = ANY ((ARRAY['SAVINGS'::character varying, 'CHECKING'::character varying])::text[])))
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16831)
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accounts_id_seq OWNER TO postgres;

--
-- TOC entry 5051 (class 0 OID 0)
-- Dependencies: 220
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.id;


--
-- TOC entry 219 (class 1259 OID 16814)
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
-- TOC entry 223 (class 1259 OID 16854)
-- Name: movements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movements (
    id bigint NOT NULL,
    account_id bigint NOT NULL,
    date timestamp without time zone DEFAULT now() NOT NULL,
    movement_type character varying(20) NOT NULL,
    amount numeric(15,2) NOT NULL,
    balance numeric(15,2) NOT NULL,
    created_at timestamp without time zone DEFAULT now()
);


ALTER TABLE public.movements OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16853)
-- Name: movements_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.movements_id_seq OWNER TO postgres;

--
-- TOC entry 5052 (class 0 OID 0)
-- Dependencies: 222
-- Name: movements_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movements_id_seq OWNED BY public.movements.id;


--
-- TOC entry 4866 (class 2604 OID 16835)
-- Name: accounts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts ALTER COLUMN id SET DEFAULT nextval('public.accounts_id_seq'::regclass);


--
-- TOC entry 4872 (class 2604 OID 16857)
-- Name: movements id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movements ALTER COLUMN id SET DEFAULT nextval('public.movements_id_seq'::regclass);


--
-- TOC entry 5042 (class 0 OID 16832)
-- Dependencies: 221
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (id, account_number, account_type, initial_balance, available_balance, status, client_id, created_at, updated_at) FROM stdin;
1	478758	CHECKING	1000.00	0.00	t	jose01	2026-03-03 11:11:51.576012	2026-03-03 11:13:56.794116
2	478756	CHECKING	1000.00	0.00	f	mariana01	2026-03-03 11:14:48.23521	2026-03-03 11:21:10.444082
\.


--
-- TOC entry 5040 (class 0 OID 16814)
-- Dependencies: 219
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	create cuentas movimientos	SQL	V1__create_cuentas_movimientos.sql	-1797760339	postgres	2026-03-03 11:09:18.351707	90	t
\.


--
-- TOC entry 5044 (class 0 OID 16854)
-- Dependencies: 223
-- Data for Name: movements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.movements (id, account_id, date, movement_type, amount, balance, created_at) FROM stdin;
1	1	2026-03-03 11:13:23.627656	WITHDRAWAL	500.00	500.00	2026-03-03 11:13:23.631128
2	1	2026-03-03 11:13:56.782167	WITHDRAWAL	500.00	0.00	2026-03-03 11:13:56.798734
3	2	2026-03-03 11:15:24.636371	WITHDRAWAL	100.00	900.00	2026-03-03 11:15:24.655599
4	2	2026-03-03 11:15:26.002508	WITHDRAWAL	100.00	800.00	2026-03-03 11:15:26.018024
5	2	2026-03-03 11:15:27.022692	WITHDRAWAL	100.00	700.00	2026-03-03 11:15:27.037222
6	2	2026-03-03 11:15:27.889679	WITHDRAWAL	100.00	600.00	2026-03-03 11:15:27.899321
7	2	2026-03-03 11:15:28.721633	WITHDRAWAL	100.00	500.00	2026-03-03 11:15:28.735686
8	2	2026-03-03 11:15:29.570261	WITHDRAWAL	100.00	400.00	2026-03-03 11:15:29.585473
9	2	2026-03-03 11:15:30.400602	WITHDRAWAL	100.00	300.00	2026-03-03 11:15:30.403995
10	2	2026-03-03 11:15:31.139693	WITHDRAWAL	100.00	200.00	2026-03-03 11:15:31.153624
11	2	2026-03-03 11:15:32.066299	WITHDRAWAL	100.00	100.00	2026-03-03 11:15:32.072138
12	2	2026-03-03 11:15:32.924221	WITHDRAWAL	100.00	0.00	2026-03-03 11:15:32.924221
13	2	2026-03-03 11:18:44.257174	DEPOSIT	100.00	100.00	2026-03-03 11:18:44.268934
14	2	2026-03-03 11:21:10.444082	WITHDRAWAL	100.00	0.00	2026-03-03 11:21:10.455377
\.


--
-- TOC entry 5053 (class 0 OID 0)
-- Dependencies: 220
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.accounts_id_seq', 2, true);


--
-- TOC entry 5054 (class 0 OID 0)
-- Dependencies: 222
-- Name: movements_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movements_id_seq', 14, true);


--
-- TOC entry 4880 (class 2606 OID 16852)
-- Name: accounts accounts_account_number_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_account_number_key UNIQUE (account_number);


--
-- TOC entry 4882 (class 2606 OID 16850)
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- TOC entry 4877 (class 2606 OID 16829)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4891 (class 2606 OID 16867)
-- Name: movements movements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movements
    ADD CONSTRAINT movements_pkey PRIMARY KEY (id);


--
-- TOC entry 4878 (class 1259 OID 16830)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4883 (class 1259 OID 16873)
-- Name: idx_accounts_account_number; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX idx_accounts_account_number ON public.accounts USING btree (account_number);


--
-- TOC entry 4884 (class 1259 OID 16874)
-- Name: idx_accounts_client_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_accounts_client_id ON public.accounts USING btree (client_id);


--
-- TOC entry 4885 (class 1259 OID 16876)
-- Name: idx_accounts_client_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_accounts_client_status ON public.accounts USING btree (client_id, status);


--
-- TOC entry 4886 (class 1259 OID 16875)
-- Name: idx_accounts_status; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_accounts_status ON public.accounts USING btree (status);


--
-- TOC entry 4887 (class 1259 OID 16879)
-- Name: idx_movements_account_date; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movements_account_date ON public.movements USING btree (account_id, date);


--
-- TOC entry 4888 (class 1259 OID 16877)
-- Name: idx_movements_account_id; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movements_account_id ON public.movements USING btree (account_id);


--
-- TOC entry 4889 (class 1259 OID 16878)
-- Name: idx_movements_date; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_movements_date ON public.movements USING btree (date);


--
-- TOC entry 4892 (class 2606 OID 16868)
-- Name: movements movements_account_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movements
    ADD CONSTRAINT movements_account_id_fkey FOREIGN KEY (account_id) REFERENCES public.accounts(id);


-- Completed on 2026-03-03 12:15:34

--
-- PostgreSQL database dump complete
--

\unrestrict FSX12ZE01O4C9gix4H5AyXkvOfThI06fWeLwAmCb5zr5j6cSycKBeMCuZfXWpZY

