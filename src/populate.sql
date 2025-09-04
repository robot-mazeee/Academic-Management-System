--
-- PostgreSQL database dump
--

-- Dumped from database version 14.17
-- Dumped by pg_dump version 14.18 (Ubuntu 14.18-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: courses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.courses (
    duration integer NOT NULL,
    id bigint NOT NULL,
    code character varying(255),
    name character varying(255)
);


ALTER TABLE public.courses OWNER TO postgres;

--
-- Name: courses_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.courses_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.courses_seq OWNER TO postgres;

--
-- Name: curricular_units; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.curricular_units (
    semester integer NOT NULL,
    course_id bigint,
    id bigint NOT NULL,
    main_teacher bigint,
    code character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.curricular_units OWNER TO postgres;

--
-- Name: curricular_units_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.curricular_units_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.curricular_units_seq OWNER TO postgres;

--
-- Name: enrollment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enrollment (
    final_grade double precision NOT NULL,
    curricular_unit_id bigint,
    id bigint NOT NULL,
    student_id bigint,
    status character varying(255),
    CONSTRAINT enrollment_status_check CHECK (((status)::text = ANY ((ARRAY['ENROLLED'::character varying, 'APPROVED'::character varying, 'FAILED'::character varying])::text[])))
);


ALTER TABLE public.enrollment OWNER TO postgres;

--
-- Name: enrollment_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enrollment_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.enrollment_seq OWNER TO postgres;

--
-- Name: evaluation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.evaluation (
    weight double precision,
    curricular_unit_id bigint,
    id bigint NOT NULL,
    title character varying(255)
);


ALTER TABLE public.evaluation OWNER TO postgres;

--
-- Name: evaluation_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.evaluation_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.evaluation_seq OWNER TO postgres;

--
-- Name: people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.people (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    ist_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT people_type_check CHECK (((type)::text = ANY ((ARRAY['ADMINISTRATOR'::character varying, 'MAIN_TEACHER'::character varying, 'TEACHING_ASSISTANT'::character varying, 'STUDENT'::character varying, 'TEACHER'::character varying])::text[])))
);


ALTER TABLE public.people OWNER TO postgres;

--
-- Name: people_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.people_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.people_seq OWNER TO postgres;

--
-- Name: project; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project (
    max_group_size integer,
    submission_deadline date,
    id bigint NOT NULL
);


ALTER TABLE public.project OWNER TO postgres;

--
-- Name: project_group; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project_group (
    id bigint NOT NULL,
    project_id bigint
);


ALTER TABLE public.project_group OWNER TO postgres;

--
-- Name: project_group_members; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project_group_members (
    group_id bigint NOT NULL,
    members_id bigint NOT NULL
);


ALTER TABLE public.project_group_members OWNER TO postgres;

--
-- Name: project_group_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.project_group_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.project_group_seq OWNER TO postgres;

--
-- Name: project_submission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.project_submission (
    grade double precision NOT NULL,
    group_id bigint,
    id bigint NOT NULL,
    project_id bigint,
    sub_date_time timestamp(6) without time zone
);


ALTER TABLE public.project_submission OWNER TO postgres;

--
-- Name: project_submission_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.project_submission_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.project_submission_seq OWNER TO postgres;

--
-- Name: test; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.test (
    test_date date,
    id bigint NOT NULL
);


ALTER TABLE public.test OWNER TO postgres;

--
-- Name: test_grade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.test_grade (
    grade double precision NOT NULL,
    id bigint NOT NULL,
    student_id bigint,
    test_id bigint
);


ALTER TABLE public.test_grade OWNER TO postgres;

--
-- Name: test_grade_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.test_grade_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.test_grade_seq OWNER TO postgres;

--
-- Name: uc_students; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.uc_students (
    student_id bigint NOT NULL,
    uc_id bigint NOT NULL
);


ALTER TABLE public.uc_students OWNER TO postgres;

--
-- Name: uc_teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.uc_teachers (
    teacher_id bigint NOT NULL,
    uc_id bigint NOT NULL
);


ALTER TABLE public.uc_teachers OWNER TO postgres;

--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.courses (duration, id, code, name) FROM stdin;
3	1	LEIC	Informática
\.


--
-- Data for Name: curricular_units; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.curricular_units (semester, course_id, id, main_teacher, code, name) FROM stdin;
1	1	1	8	FP	Fundamentos Prog.
\.


--
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enrollment (final_grade, curricular_unit_id, id, student_id, status) FROM stdin;
0	1	1	4	ENROLLED
0	1	2	3	ENROLLED
0	1	4	5	ENROLLED
0	1	6	1	ENROLLED
0	1	5	6	ENROLLED
0	1	3	2	ENROLLED
\.


--
-- Data for Name: evaluation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.evaluation (weight, curricular_unit_id, id, title) FROM stdin;
50	1	1	Loops
50	1	2	Jogo da Cobra
\.


--
-- Data for Name: people; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.people (id, email, ist_id, name, type) FROM stdin;
1	maria.dow@tecnico.ulisboa.pt	110283	Maria Dow	STUDENT
2	john.doe@tecnico.ulisboa.pt	111837	John Doe	STUDENT
3	miguel.soares@tecnico.ulisboa.pt	110273	Miguel Soares	STUDENT
4	jessica.tavares@tecnic.ulisboa.pt	111937	Jessica Tavares	STUDENT
5	laura.joanes@tecnico.ulisboa.pt	112421	Laura Jones	STUDENT
6	pedro.caneca@tecnico.ulisboa.pt	118732	Pedro Caneca	STUDENT
10	luis.david@tecnico.ulisboa.pt	4038	Luís David	ADMINISTRATOR
8	joana.bastos@tecnico.ulisboa.pt	4326	Joana Bastos	MAIN_TEACHER
9	joao.abecassiz@tecnico.ulisboa.pt	4072	João Abecassiz	TEACHING_ASSISTANT
7	jose.miguel@tecnico.ulisboa.pt	4039	José Miguel	TEACHING_ASSISTANT
11	leonor.ferreira@tecnico.ulisboa.pt	4027	Leonor Ferreira	TEACHER
13	rodrigo.batata	4031	Rodrigo Batata	TEACHER
\.


--
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.project (max_group_size, submission_deadline, id) FROM stdin;
2	2025-09-26	2
\.


--
-- Data for Name: project_group; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.project_group (id, project_id) FROM stdin;
1	2
2	2
3	2
\.


--
-- Data for Name: project_group_members; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.project_group_members (group_id, members_id) FROM stdin;
1	2
1	1
2	6
2	4
3	5
3	3
\.


--
-- Data for Name: project_submission; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.project_submission (grade, group_id, id, project_id, sub_date_time) FROM stdin;
\.


--
-- Data for Name: test; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.test (test_date, id) FROM stdin;
2025-09-29	1
\.


--
-- Data for Name: test_grade; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.test_grade (grade, id, student_id, test_id) FROM stdin;
\.


--
-- Data for Name: uc_students; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.uc_students (student_id, uc_id) FROM stdin;
6	1
1	1
4	1
5	1
2	1
3	1
\.


--
-- Data for Name: uc_teachers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.uc_teachers (teacher_id, uc_id) FROM stdin;
9	1
7	1
\.


--
-- Name: courses_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.courses_seq', 1, true);


--
-- Name: curricular_units_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.curricular_units_seq', 1, true);


--
-- Name: enrollment_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enrollment_seq', 51, true);


--
-- Name: evaluation_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.evaluation_seq', 51, true);


--
-- Name: people_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.people_seq', 51, true);


--
-- Name: project_group_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.project_group_seq', 51, true);


--
-- Name: project_submission_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.project_submission_seq', 1, false);


--
-- Name: test_grade_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.test_grade_seq', 1, false);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: curricular_units curricular_units_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curricular_units
    ADD CONSTRAINT curricular_units_pkey PRIMARY KEY (id);


--
-- Name: enrollment enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (id);


--
-- Name: evaluation evaluation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evaluation
    ADD CONSTRAINT evaluation_pkey PRIMARY KEY (id);


--
-- Name: people people_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_email_key UNIQUE (email);


--
-- Name: people people_ist_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_ist_id_key UNIQUE (ist_id);


--
-- Name: people people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_pkey PRIMARY KEY (id);


--
-- Name: project_group project_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_group
    ADD CONSTRAINT project_group_pkey PRIMARY KEY (id);


--
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- Name: project_submission project_submission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_submission
    ADD CONSTRAINT project_submission_pkey PRIMARY KEY (id);


--
-- Name: test_grade test_grade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_grade
    ADD CONSTRAINT test_grade_pkey PRIMARY KEY (id);


--
-- Name: test test_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT test_pkey PRIMARY KEY (id);


--
-- Name: uc_teachers uc_teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uc_teachers
    ADD CONSTRAINT uc_teachers_pkey PRIMARY KEY (teacher_id, uc_id);


--
-- Name: project_group fk5xrhlu465h3nbqcukpbc6pko7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_group
    ADD CONSTRAINT fk5xrhlu465h3nbqcukpbc6pko7 FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- Name: enrollment fk6by9j3a1n9fpckwurtei2r5kb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fk6by9j3a1n9fpckwurtei2r5kb FOREIGN KEY (curricular_unit_id) REFERENCES public.curricular_units(id);


--
-- Name: project_group_members fk7vq3j22xd611u9l6voh1tg4vt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_group_members
    ADD CONSTRAINT fk7vq3j22xd611u9l6voh1tg4vt FOREIGN KEY (group_id) REFERENCES public.project_group(id);


--
-- Name: test_grade fk9jogkkus3n7lg317lp1u9bn0f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_grade
    ADD CONSTRAINT fk9jogkkus3n7lg317lp1u9bn0f FOREIGN KEY (student_id) REFERENCES public.people(id);


--
-- Name: uc_students fk9wmfq0n2whl9ybhobpns0o329; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uc_students
    ADD CONSTRAINT fk9wmfq0n2whl9ybhobpns0o329 FOREIGN KEY (student_id) REFERENCES public.people(id);


--
-- Name: uc_students fkbayfrvwxmwchgpc1h8ev6ynnh; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uc_students
    ADD CONSTRAINT fkbayfrvwxmwchgpc1h8ev6ynnh FOREIGN KEY (uc_id) REFERENCES public.curricular_units(id);


--
-- Name: enrollment fkbcm01ai769v3plnjrir6xgi9l; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fkbcm01ai769v3plnjrir6xgi9l FOREIGN KEY (student_id) REFERENCES public.people(id);


--
-- Name: project_group_members fkcd362esl2pc1mog6f3kvt5tu0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_group_members
    ADD CONSTRAINT fkcd362esl2pc1mog6f3kvt5tu0 FOREIGN KEY (members_id) REFERENCES public.people(id);


--
-- Name: project_submission fkg2k7sbtfbup9hlbxjue287hqe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_submission
    ADD CONSTRAINT fkg2k7sbtfbup9hlbxjue287hqe FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- Name: uc_teachers fkhmufs0lo924nswal6s5ep5ydu; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uc_teachers
    ADD CONSTRAINT fkhmufs0lo924nswal6s5ep5ydu FOREIGN KEY (teacher_id) REFERENCES public.people(id);


--
-- Name: project_submission fkih1ye423e4lhqbggj91ksseg; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project_submission
    ADD CONSTRAINT fkih1ye423e4lhqbggj91ksseg FOREIGN KEY (group_id) REFERENCES public.project_group(id);


--
-- Name: curricular_units fkisro6uub4p4a4vq4ticswy1qa; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curricular_units
    ADD CONSTRAINT fkisro6uub4p4a4vq4ticswy1qa FOREIGN KEY (main_teacher) REFERENCES public.people(id);


--
-- Name: evaluation fkk2om0cm93609474o5dtur29ej; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.evaluation
    ADD CONSTRAINT fkk2om0cm93609474o5dtur29ej FOREIGN KEY (curricular_unit_id) REFERENCES public.curricular_units(id);


--
-- Name: curricular_units fknoy9en0d6at71s87ws3ihn5fi; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.curricular_units
    ADD CONSTRAINT fknoy9en0d6at71s87ws3ihn5fi FOREIGN KEY (course_id) REFERENCES public.courses(id);


--
-- Name: project fkq9eghlv6fymgs6lfp1swvmbpe; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fkq9eghlv6fymgs6lfp1swvmbpe FOREIGN KEY (id) REFERENCES public.evaluation(id);


--
-- Name: uc_teachers fkry7qhjfjms3od4vkmqwt01voj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.uc_teachers
    ADD CONSTRAINT fkry7qhjfjms3od4vkmqwt01voj FOREIGN KEY (uc_id) REFERENCES public.curricular_units(id);


--
-- Name: test_grade fksivbjhhoanm0bln3cgj4nk5w3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test_grade
    ADD CONSTRAINT fksivbjhhoanm0bln3cgj4nk5w3 FOREIGN KEY (test_id) REFERENCES public.test(id);


--
-- Name: test fkto8ubffd7yuxfar019r72co9m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.test
    ADD CONSTRAINT fkto8ubffd7yuxfar019r72co9m FOREIGN KEY (id) REFERENCES public.evaluation(id);


--
-- PostgreSQL database dump complete
--

