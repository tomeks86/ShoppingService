--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.6

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: auctions; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE auctions (
    id integer NOT NULL,
    description text NOT NULL,
    title character varying(100) NOT NULL,
    price numeric,
    userlogin character varying(100) NOT NULL,
    categoryid integer,
    isactive boolean,
    winnerlogin character varying(100),
    bidcounter integer
);


ALTER TABLE auctions OWNER TO zenek;

--
-- Name: auctions_id_seq; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE auctions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auctions_id_seq OWNER TO zenek;

--
-- Name: auctions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE auctions_id_seq OWNED BY auctions.id;


--
-- Name: category; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE category (
    id integer NOT NULL,
    categoryname character varying(100),
    parentcategoryid integer NOT NULL
);


ALTER TABLE category OWNER TO zenek;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE category_id_seq OWNER TO zenek;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE category_id_seq OWNED BY category.id;


--
-- Name: category_parentcategoryid_seq; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE category_parentcategoryid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE category_parentcategoryid_seq OWNER TO zenek;

--
-- Name: category_parentcategoryid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE category_parentcategoryid_seq OWNED BY category.parentcategoryid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE users (
    login character varying(100) NOT NULL,
    password character varying(100) NOT NULL
);


ALTER TABLE users OWNER TO zenek;

--
-- Name: auctions id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions ALTER COLUMN id SET DEFAULT nextval('auctions_id_seq'::regclass);


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category ALTER COLUMN id SET DEFAULT nextval('category_id_seq'::regclass);


--
-- Name: category parentcategoryid; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category ALTER COLUMN parentcategoryid SET DEFAULT nextval('category_parentcategoryid_seq'::regclass);


--
-- Data for Name: auctions; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY auctions (id, description, title, price, userlogin, categoryid, isactive, winnerlogin, bidcounter) FROM stdin;
1	Aukcja samochodu	BMW	15.5	aaaa	5	t	\N	0
\.


--
-- Name: auctions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('auctions_id_seq', 1, true);


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY category (id, categoryname, parentcategoryid) FROM stdin;
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('category_id_seq', 1, false);


--
-- Name: category_parentcategoryid_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('category_parentcategoryid_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY users (login, password) FROM stdin;
aaaa	aaaa
\.


--
-- Name: auctions auctions_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (login);


--
-- Name: auctions auctions_userlogin_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_userlogin_fkey FOREIGN KEY (userlogin) REFERENCES users(login);


--
-- Name: auctions auctions_winnerlogin_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_winnerlogin_fkey FOREIGN KEY (winnerlogin) REFERENCES users(login);


--
-- Name: category category_parentcategoryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_parentcategoryid_fkey FOREIGN KEY (parentcategoryid) REFERENCES category(id);


--
-- PostgreSQL database dump complete
--

