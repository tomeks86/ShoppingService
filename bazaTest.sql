--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.10
-- Dumped by pg_dump version 9.5.10

SET statement_timeout = 0;
SET lock_timeout = 0;
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


--
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: auctions; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE auctions (
    id integer NOT NULL,
    title character varying(100) NOT NULL,
    description text,
    price money NOT NULL,
    ownerid integer,
    categoryid integer,
    isactive boolean DEFAULT true,
    winnerid integer,
    bidcounter integer DEFAULT 0,
    CONSTRAINT auctions_bidcounter_check CHECK ((bidcounter < 4))
);


ALTER TABLE auctions OWNER TO zenek;

--
-- Name: auctions_old; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE auctions_old (
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


ALTER TABLE auctions_old OWNER TO zenek;

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

ALTER SEQUENCE auctions_id_seq OWNED BY auctions_old.id;


--
-- Name: auctions_id_seq1; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE auctions_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE auctions_id_seq1 OWNER TO zenek;

--
-- Name: auctions_id_seq1; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE auctions_id_seq1 OWNED BY auctions.id;


--
-- Name: category; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE category (
    id integer NOT NULL,
    categoryname character varying(100),
    parentcategoryid integer
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
-- Name: login; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE login (
    id integer NOT NULL,
    login character varying(20) NOT NULL,
    password text NOT NULL,
    salt text
);


ALTER TABLE login OWNER TO zenek;

--
-- Name: login_id_seq; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE login_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE login_id_seq OWNER TO zenek;

--
-- Name: login_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE login_id_seq OWNED BY login.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: zenek
--

CREATE TABLE users (
    id integer NOT NULL,
    login character varying(31),
    password character varying(60),
    salt character varying(29),
    CONSTRAINT users_login_check CHECK (((login)::text ~* '^[A-Za-z]\w{0,30}$'::text))
);


ALTER TABLE users OWNER TO zenek;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: zenek
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO zenek;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: zenek
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions ALTER COLUMN id SET DEFAULT nextval('auctions_id_seq1'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions_old ALTER COLUMN id SET DEFAULT nextval('auctions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category ALTER COLUMN id SET DEFAULT nextval('category_id_seq'::regclass);


--
-- Name: parentcategoryid; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category ALTER COLUMN parentcategoryid SET DEFAULT nextval('category_parentcategoryid_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY login ALTER COLUMN id SET DEFAULT nextval('login_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: auctions; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY auctions (id, title, description, price, ownerid, categoryid, isactive, winnerid, bidcounter) FROM stdin;
1	BMW	nowe sportowe BMW	120.000,00 zł	2	5	t	\N	0
4	bluzecka	różowa bluzka M	30,00 zł	7	4	t	\N	0
5	majtki	majtki L	40,00 zł	6	3	f	2	3
6	fdsfds	fdsfds	32.324,00 zł	6	6	f	\N	0
\.


--
-- Name: auctions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('auctions_id_seq', 1, true);


--
-- Name: auctions_id_seq1; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('auctions_id_seq1', 6, true);


--
-- Data for Name: auctions_old; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY auctions_old (id, description, title, price, userlogin, categoryid, isactive, winnerlogin, bidcounter) FROM stdin;
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY category (id, categoryname, parentcategoryid) FROM stdin;
3	underwear	2
4	t-shirts	2
5	cars	1
6	tires	1
1	vehicles	\N
2	clothes	\N
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('category_id_seq', 1, false);


--
-- Name: category_parentcategoryid_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('category_parentcategoryid_seq', 2, true);


--
-- Data for Name: login; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY login (id, login, password, salt) FROM stdin;
5	admin	$2a$06$IVca3IF/wwgjEfHArGdhbu3ZJG41/woAMR9Xjd4h7J3UkttDXZuIC	$2a$06$IVca3IF/wwgjEfHArGdhbu
6	franek	$2a$06$BM4TtnREyrz/4KyqkFck2OMdpUKZ9G2RcN715ciOGQZ0suIJ4EKnO	$2a$06$BM4TtnREyrz/4KyqkFck2O
\.


--
-- Name: login_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('login_id_seq', 6, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: zenek
--

COPY users (id, login, password, salt) FROM stdin;
2	stefan	$2a$06$AR0YnPXc.3y78KBGG7RqHOB2e1KgU4xUwArX8Ugqnhm8jHYBzz6gS	$2a$06$AR0YnPXc.3y78KBGG7RqHO
6	beton	$2a$06$uh.EA.WYLs9C5o/U/jHXiOIhBsm39e3bM1BqF3TIE7QlOY1fHWsSK	$2a$06$uh.EA.WYLs9C5o/U/jHXiO
7	rebeka	$2a$06$W7cicvOAdBtb1pyGeBuHPOmh7W12WxpDDD7l6Qw/ppsX8pTQA3jFi	$2a$06$W7cicvOAdBtb1pyGeBuHPO
9	franek	$2a$06$XDNKmi0RTgvuFHY4mZ1Hgey3pJkdCTXt46N.u0g.TKwKr48A4VmoG	$2a$06$XDNKmi0RTgvuFHY4mZ1Hge
12	franeks	$2a$06$oUJw/bwEJUobjj6xXApbQ.b4jVfxaviJr3HDVocevfIVfH/EqThfW	$2a$06$oUJw/bwEJUobjj6xXApbQ.
14	franek5	$2a$06$vRFUG.z5deGzA7ezNuNmGO/T8jLJWQ6lFnsfteJsnGH46yDPqwx7.	$2a$06$vRFUG.z5deGzA7ezNuNmGO
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: zenek
--

SELECT pg_catalog.setval('users_id_seq', 15, true);


--
-- Name: auctions_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions_old
    ADD CONSTRAINT auctions_pkey PRIMARY KEY (id);


--
-- Name: auctions_pkey1; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_pkey1 PRIMARY KEY (id);


--
-- Name: category_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: login_pkey; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY login
    ADD CONSTRAINT login_pkey PRIMARY KEY (id);


--
-- Name: users_login_key; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_login_key UNIQUE (login);


--
-- Name: users_pkey1; Type: CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey1 PRIMARY KEY (id);


--
-- Name: login_login_idx; Type: INDEX; Schema: public; Owner: zenek
--

CREATE INDEX login_login_idx ON login USING btree (login);


--
-- Name: auctions_categoryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_categoryid_fkey FOREIGN KEY (categoryid) REFERENCES category(id);


--
-- Name: auctions_ownerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_ownerid_fkey FOREIGN KEY (ownerid) REFERENCES users(id);


--
-- Name: auctions_winnerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY auctions
    ADD CONSTRAINT auctions_winnerid_fkey FOREIGN KEY (winnerid) REFERENCES users(id);


--
-- Name: category_parentcategoryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: zenek
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_parentcategoryid_fkey FOREIGN KEY (parentcategoryid) REFERENCES category(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

