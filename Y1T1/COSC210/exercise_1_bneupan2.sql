--
-- PostgreSQL database dump
--

-- Dumped from database version 14.3
-- Dumped by pg_dump version 14.3

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
-- Name: customers; Type: TABLE; Schema: public; Owner: bneupan2
--

CREATE TABLE public.customers (
    customer_id integer NOT NULL,
    last_name character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    address character varying(200),
    city character varying(50),
    state character(3) NOT NULL,
    postcode character varying(8),
    CONSTRAINT customers_state_check CHECK ((state = ANY (ARRAY['NSW'::bpchar, 'VIC'::bpchar, 'QLD'::bpchar, 'ACT'::bpchar, 'TAS'::bpchar, 'NT'::bpchar, 'SA'::bpchar, 'WA'::bpchar])))
);


ALTER TABLE public.customers OWNER TO bneupan2;

--
-- Name: movies; Type: TABLE; Schema: public; Owner: bneupan2
--

CREATE TABLE public.movies (
    movie_id integer NOT NULL,
    movie_title character varying(100) NOT NULL,
    director_last_name character varying(50) NOT NULL,
    director_first_name character varying(50) NOT NULL,
    genre character varying(20) NOT NULL,
    release_date date,
    studio_name character varying(50),
    CONSTRAINT movies_genre_check CHECK (((genre)::text = ANY ((ARRAY['Action'::character varying, 'Adventure'::character varying, 'Comedy'::character varying, 'Romance'::character varying, 'Science Fiction'::character varying, 'Documentary'::character varying, 'Drama'::character varying, 'Horror'::character varying])::text[])))
);


ALTER TABLE public.movies OWNER TO bneupan2;

--
-- Name: shipments; Type: TABLE; Schema: public; Owner: bneupan2
--

CREATE TABLE public.shipments (
    shipment_id integer NOT NULL,
    customer_id integer NOT NULL,
    movie_id integer NOT NULL,
    media_type character varying(20) NOT NULL,
    shipment_date date
);


ALTER TABLE public.shipments OWNER TO bneupan2;

--
-- Name: stock; Type: TABLE; Schema: public; Owner: bneupan2
--

CREATE TABLE public.stock (
    movie_id integer NOT NULL,
    media_type character varying(20) NOT NULL,
    cost_price real NOT NULL,
    retail_price real NOT NULL,
    current_stock real NOT NULL,
    CONSTRAINT stock_cost_price_check CHECK ((cost_price > (0)::double precision)),
    CONSTRAINT stock_current_stock_check CHECK ((current_stock >= (0)::double precision)),
    CONSTRAINT stock_media_type_check CHECK (((media_type)::text = ANY ((ARRAY['DVD'::character varying, 'Blu-Ray'::character varying, 'Stream-Media'::character varying])::text[]))),
    CONSTRAINT stock_retail_price_check CHECK ((retail_price > (0)::double precision))
);


ALTER TABLE public.stock OWNER TO bneupan2;

--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: bneupan2
--

COPY public.customers (customer_id, last_name, first_name, address, city, state, postcode) FROM stdin;
1	Woodcock	Paige	1 Ranworth Road	MYAREE	WA 	6154
2	Kirke	Eirn	34 Friar John Way	BALDIVIS	WA 	6171
3	Wood	Owen	63 Kogil Street	HARPARARY	NSW	2390
4	Powlett	Beau	44 Girvan Grove	COMET HILL	VIC	3556
5	Freehill	Harry	27 Taylor Street	KAARIMBA	VIC	3635
6	Luisini	Makayla	78 Arthur Street	SNAKES PLAIN	NSW	2824
7	Joyner	Kate	8 Mt Berryman Road	SPRING CREEK	QLD	4343
8	Leidig	Hugo	87 Bayfield Street	PORT ARTHUR	TAS	7182
9	Sachse	Timothy	32 Reynolds Road	GLEN ECHO	QLD	4570
10	Weingarth	Ashton	96 Stanley Drive	GREGORY RIVER	QLD	4800
\.


--
-- Data for Name: movies; Type: TABLE DATA; Schema: public; Owner: bneupan2
--

COPY public.movies (movie_id, movie_title, director_last_name, director_first_name, genre, release_date, studio_name) FROM stdin;
1	The Shawshank Redemption	Darabont	Frank	Drama	1994-01-01	Columbia Pictures
2	The Godfather	Ford-Coppola	Francis	Drama	1972-01-01	Paramount Pictures
3	The Godfather: Part II	Ford-Coppola	Francis	Drama	1974-01-01	Paramount Pictures
4	The Dark Knight	Nolan	Christopher	Action	2008-01-01	Warner Bros. Pictures/Legendary
5	12 Angry Men	Lumet	Sidney	Drama	1957-01-01	Criterion Collection
6	Schindlers List	Spielberg	Steven	Drama	1993-01-01	Universal Pictures
7	The Lord of the Rings: The Return of the King	Jackson	Peter	Adventure	2003-01-01	New Line Cinema
8	Pulp Fiction	Tarantino	Quentin	Drama	1994-01-01	Miramax Films
9	Fight Club	Fincher	David	Drama	1999-01-01	20th Century Fox
10	The Lord of the Rings: The Fellowship of the Ring	Jackson	Peter	Adventure	2001-01-01	New Line Cinema
11	Forrest Gump	Zemeckis	Robert	Comedy	1994-01-01	Paramount Pictures
12	Star Wars: Episode V - The Empire Strikes Back	Kershner	Irvin	Action	1980-01-01	Twentieth Century Fox
13	Inception	Nolan	Christopher	Action	2010-01-01	Warner Bros. Pictures
14	The Lord of the Rings: The Two Towers	Jackson	Peter	Adventure	2002-01-01	New Line Cinema
15	One Flew Over the Cuckoos Nest	Forman	Milos	Drama	1975-01-01	United Artists
16	Goodfellas	Scorsese	Martin	Drama	1990-01-01	Warner Bros.
17	The Matrix	Wachowski	L&L	Action	1999-01-01	Warner Bros. Pictures
18	Star Wars: Episode IV - A New Hope	Lucas	George	Action	1977-01-01	20th Century Fox
19	Se7en	Fincher	David	Drama	1995-01-01	New Line Cinema
20	Its a Wonderful Life	Capra	Frank	Drama	1946-01-01	Liberty Films
21	The Silence of the Lambs	Demme	Jonathan	Drama	1991-01-01	Orion Pictures Corporation
22	The Usual Suspects	Singer	Bryan	Drama	1995-01-01	Gramercy Pictures
23	Léon: The Professional	Besson	Luc	Drama	1994-01-01	Columbia Pictures
24	Saving Private Ryan	Spielberg	Steven	Drama	1998-01-01	Paramount Pictures
25	City Lights	Chaplin	Charles	Comedy	1931-01-01	Twentieth Century Fox Home Entertainment
26	Interstellar	Nolan	Christopher	Adventure	2014-01-01	Paramount Pictures
27	American History X	Kaye	Tony	Drama	1998-01-01	New Line Cinema
28	Modern Times	Chaplin	Charles	Comedy	1936-01-01	United Artists
29	Casablanca	Curtiz	Michael	Drama	1942-01-01	Warner Bros. Pictures
30	The Green Mile	Darabont	Frank	Drama	1999-01-01	Warner Bros. Pictures
31	Psycho	Hitchcock	Alfred	Horror	1960-01-01	Paramount Pictures
32	Raiders of the Lost Ark	Spielberg	Steven	Action	1981-01-01	Paramount Pictures
33	The Pianist	Polanski	Roman	Drama	2002-01-01	Focus Features
34	Rear Window	Hitchcock	Alfred	Horror	1954-01-01	Paramount Pictures
35	The Departed	Scorsese	Martin	Drama	2006-01-01	Warner Bros. Pictures
36	Whiplash	Chazelle	Damien	Drama	2014-01-01	Sony Pictures Classics
37	Terminator 2: Judgment Day	Cameron	James	Action	1991-01-01	TriStar Pictures
38	Back to the Future	Zemeckis	Robert	Adventure	1985-01-01	Universal Pictures
39	Gladiator	Scott	Ridley	Action	2000-01-01	Dreamworks Distribution LLC
40	The Lion King	Allers	Roger	Adventure	1994-01-01	Buena Vista
41	The Prestige	Nolan	Christopher	Drama	2006-01-01	Buena Vista Pictures
42	Apocalypse Now	Ford-Coppola	Francis	Drama	1979-01-01	United Artists
43	Memento	Nolan	Christopher	Drama	2000-01-01	Newmarket Films
44	The Great Dictator	Chaplin	Charles	Comedy	1940-01-01	Criterion Collection
45	Sunset Boulevard	Wilder	Billy	Drama	1950-01-01	Paramount Pictures
46	Alien	Scott	Ridley	Science Fiction	1979-01-01	20th Century Fox
47	Dr. Strangelove or: How I Learned to Stop Worrying and Love the Bomb	Kubrick	Stanley	Comedy	1964-01-01	Sony Pictures
48	Paths of Glory	Kubrick	Stanley	Drama	1957-01-01	United Artists
49	Django Unchained	Tarantino	Quentin	Drama	2012-01-01	The Weinstein Co.
50	The Shining	Kubrick	Stanley	Drama	1980-01-01	Warner Bros. Pictures
51	Witness for the Prosecution	Wilder	Billy	Drama	1957-01-01	MGM
52	The Dark Knight Rises	Nolan	Christopher	Action	2012-01-01	Warner Bros. Pictures
53	WALL·E	Stanton	Andrew	Adventure	2008-01-01	Walt Disney Pictures
54	American Beauty	Mendes	Sam	Drama	1999-01-01	Dream Works
55	Once Upon a Time in America	Leone	Sergio	Drama	1984-01-01	Warner Home Video
56	Aliens	Cameron	James	Action	1986-01-01	20th Century Fox Film Corporat
57	Citizen Kane	Welles	Orson	Drama	1941-01-01	RKO Radio Pictures
58	North by Northwest	Hitchcock	Alfred	Action	1959-01-01	Turner Entertainment
59	Vertigo	Hitchcock	Alfred	Drama	1958-01-01	Paramount Pictures
60	Star Wars: Episode VI - Return of the Jedi	Marquand	Richard	Action	1983-01-01	Twentieth Century Fox
61	Braveheart	Gibson	Mel	Drama	1995-01-01	Paramount Pictures
62	Reservoir Dogs	Tarantino	Quentin	Drama	1992-01-01	Miramax Films
63	Double Indemnity	Wilder	Billy	Drama	1944-01-01	Paramount Pictures
64	Requiem for a Dream	Aronofsky	Darren	Drama	2000-01-01	Artisan Entertainment
65	Lawrence of Arabia	Lean	David	Adventure	1962-01-01	Columbia Pictures
66	The Kid	Chaplin	Charles	Comedy	1921-01-01	First National Pictures Inc.
67	A Clockwork Orange	Kubrick	Stanley	Drama	1971-01-01	Warner Bros.
68	Amadeus	Forman	Milos	Drama	1984-01-01	Warner Bros. Pictures
69	To Kill a Mockingbird	Mulligan	Robert	Drama	1962-01-01	Universal International Pictur
70	Eternal Sunshine of the Spotless Mind	Gondry	Michel	Drama	2004-01-01	Focus Features
71	Singin in the Rain	Donen	Stanley	Comedy	1952-01-01	MGM
72	Taxi Driver	Scorsese	Martin	Drama	1976-01-01	Columbia Pictures
73	The Sting	Roy-Hill	George	Comedy	1973-01-01	Universal Pictures
74	Toy Story 3	Unkrich	Lee	Adventure	2010-01-01	Walt Disney Pictures
75	2001: A Space Odyssey	Kubrick	Stanley	Adventure	1968-01-01	Warner Bros. Pictures
76	Full Metal Jacket	Kubrick	Stanley	Drama	1987-01-01	Warner Bros.
77	Baby Driver	Wright	Edgar	Action	2017-01-01	Sony Pictures
78	Toy Story	Lasseter	John	Adventure	1995-01-01	Buena Vista
79	Inglourious Basterds	Tarantino	Quentin	Adventure	2009-01-01	The Weinstein Company
80	Snatch	Ritchie	Guy	Comedy	2000-01-01	Columbia Pictures
81	The Apartment	Wilder	Billy	Comedy	1960-01-01	United Artists
82	All About Eve	L.Mankiewicz	Joseph	Drama	1950-01-01	20th Century Fox
83	Monty Python and the Holy Grail	Gilliam	Terry	Adventure	1975-01-01	Almi Cinema 5
84	Scarface	De-Palma	Brian	Drama	1983-01-01	Universal Films
85	L.A. Confidential	Hanson	Curtis	Drama	1997-01-01	Warner Bros. Pictures
86	Good Will Hunting	Van-Sant	Gus	Drama	1997-01-01	Miramax Films
87	The Treasure of the Sierra Madre	Huston	John	Adventure	1948-01-01	WARNER BROTHERS PICTURES
88	Indiana Jones and the Last Crusade	Spielberg	Steven	Action	1989-01-01	Paramount Pictures
89	Some Like It Hot	Wilder	Billy	Comedy	1959-01-01	United Artists
90	Batman Begins	Nolan	Christopher	Action	2005-01-01	Warner Bros. Pictures
91	Up	Docter	Pete	Adventure	2009-01-01	Walt Disney Pictures
92	The Third Man	Reed	Carol	Drama	1949-01-01	Rialto Pictures
93	Unforgiven	Eastwood	Clint	Drama	1992-01-01	Warner Bros. Pictures
94	Judgment at Nuremberg	Kramer	Stanley	Drama	1961-01-01	United Artists
95	Raging Bull	Scorsese	Martin	Drama	1980-01-01	United Artists
96	The Great Escape	Sturges	John	Adventure	1963-01-01	VCI
97	Heat	Mann	Michael	Action	1995-01-01	Warner Bros.
98	Die Hard	McTiernan	John	Action	1988-01-01	20th Century Fox
99	The Gold Rush	Chaplin	Charles	Adventure	1925-01-01	Janus Films
100	Chinatown	Polanski	Roman	Drama	1974-01-01	Paramount Pictures
101	On the Waterfront	Kazan	Elia	Drama	1954-01-01	Sony Pictures
102	Mr. Smith Goes to Washington	Capra	Frank	Comedy	1939-01-01	ITVS
103	The General	Bruckman	Clyde	Action	1926-01-01	United Artists Films
104	Inside Out	Docter	Pete	Adventure	2015-01-01	Disney/Pixar
105	The Bridge on the River Kwai	Lean	David	Adventure	1957-01-01	Columbia Pictures
106	Room	Abrahamson	Lenny	Drama	2015-01-01	Element Pictures
107	La La Land	Chazelle	Damien	Comedy	2016-01-01	Liongate Films
108	Logan	Mangold	James	Action	2017-01-01	20th Century Fox
109	Blade Runner	Scott	Ridley	Science Fiction	1982-01-01	Warner Bros. Pictures
110	Lock, Stock and Two Smoking Barrels	Ritchie	Guy	Comedy	1998-01-01	Gramercy Pictures
111	Casino	Scorsese	Martin	Drama	1995-01-01	Universal Pictures
112	A Beautiful Mind	Howard	Ron	Drama	2001-01-01	Universal Pictures
113	The Elephant Man	Lynch	David	Drama	1980-01-01	Paramount
114	Warrior	OConnor	Gavin	Action	2011-01-01	Lionsgate
115	V for Vendetta	McTeigue	James	Action	2005-01-01	Warner Bros. Pictures
116	The Wolf of Wall Street	Scorsese	Martin	Comedy	2013-01-01	Paramount Studios
117	Dial M for Murder	Hitchcock	Alfred	Drama	1954-01-01	Warner Bros. Pictures
118	Hacksaw Ridge	Gibson	Mel	Drama	2016-01-01	Summit Entertainment
119	Gone with the Wind	Fleming	Victor	Drama	1939-01-01	Loews Inc.
120	The Message	Akkad	Moustapha	Adventure	1976-01-01	Anchor Bay Entertainment
121	Rebecca	Hitchcock	Alfred	Drama	1940-01-01	United Artists
122	Trainspotting	Boyle	Danny	Drama	1996-01-01	Miramax Films
123	The Deer Hunter	Cimino	Michael	Drama	1978-01-01	Universal Pictures
124	Cool Hand Luke	Rosenberg	Stuart	Drama	1967-01-01	Warner Bros.
125	Gran Torino	Eastwood	Clint	Drama	2008-01-01	Warner Bros. Pictures/Village Roadshow
126	The Big Lebowski	Coen	E&J	Comedy	1998-01-01	Gramercy Pictures
127	The Thing	Carpenter	John	Horror	1982-01-01	Universal Pictures
128	It Happened One Night	Capra	Frank	Comedy	1934-01-01	Sony Pictures Home Entertainment
129	Fargo	Coen	E&J	Drama	1996-01-01	MGM
130	The Sixth Sense	Shyamalan	M.Night	Drama	1999-01-01	Hollywood/Buena Vista
131	Finding Nemo	Stanton	Andrew	Adventure	2003-01-01	Walt Disney Pictures
132	Mary and Max	Elliot	Adam	Adventure	2009-01-01	IFC Films
133	No Country for Old Men	Coen	E&J	Drama	2007-01-01	Miramax Films
134	How to Train Your Dragon	DeBlois	Dean	Adventure	2010-01-01	Paramount/DWA
135	There Will Be Blood	Anderson	Paul	Drama	2007-01-01	Paramount Vantage
136	Into the Wild	Penn	Sean	Adventure	2007-01-01	Paramount Vantage
137	Kill Bill: Vol. 1	Tarantino	Quentin	Action	2003-01-01	Miramax Films
138	Gone Girl	Fincher	David	Drama	2014-01-01	20th Century Fox
139	Life of Brian	Jones	Terry	Comedy	1979-01-01	Warner Bros. Pictures
140	Network	Lumet	Sidney	Drama	1976-01-01	MGM/United Artists
141	Shutter Island	Scorsese	Martin	Drama	2010-01-01	Paramount Studios
142	In the Name of the Father	Sheridan	Jim	Drama	1993-01-01	Universal Pictures
143	Rush	Howard	Ron	Action	2013-01-01	Universal Pictures
144	Hotel Rwanda	George	Terry	Drama	2004-01-01	MGM
145	Platoon	Stone	Oliver	Drama	1986-01-01	Orion Pictures
146	Song of the Sea	Moore	Tomm	Adventure	2014-01-01	GKIDS
147	Ben-Hur	Wyler	William	Adventure	1959-01-01	MGM
148	Stand by Me	Reiner	Rob	Adventure	1986-01-01	MCA Universal Home Video
149	Hachi: A Dogs Tale	Hallström	Lasse	Drama	2009-01-01	Inferno Entertainment
150	Kind Hearts and Coronets	Hamer	Robert	Comedy	1949-01-01	Ealing Studios
151	The Maltese Falcon	Huston	John	Drama	1941-01-01	Warner Bros.
152	Butch Cassidy and the Sundance Kid	Roy-Hill	George	Drama	1969-01-01	20th Century Fox
153	The Legend of 1900	Tornatore	Giuseppe	Drama	1998-01-01	Fine Line Features
154	Spotlight	McCarthy	Tom	Drama	2015-01-01	Open Road Films
155	Brief Encounter	Lean	David	Drama	1945-01-01	Universal Pictures
156	The Best Years of Our Lives	Wyler	William	Drama	1946-01-01	RKO Radio Pictures
157	The Grapes of Wrath	Ford	John	Drama	1940-01-01	Twentieth Century Fox Home Entertainment
158	12 Years a Slave	McQueen	Steve	Drama	2013-01-01	Fox Searchlight
159	The Grand Budapest Hotel	Anderson	Wes	Adventure	2014-01-01	Fox Searchlight
160	Mad Max: Fury Road	Miller	George	Action	2015-01-01	Warner Bros.
161	The Princess Bride	Reiner	Rob	Adventure	1987-01-01	20th Century Fox
162	What Ever Happened to Baby Jane?	Aldrich	Robert	Drama	1962-01-01	Warner Bros. Pictures
163	Million Dollar Baby	Eastwood	Clint	Drama	2004-01-01	Warner Bros. Pictures
164	Jurassic Park	Spielberg	Steven	Adventure	1993-01-01	Universal City Studios
165	Touch of Evil	Welles	Orson	Drama	1958-01-01	October Films
166	Spider-Man: Homecoming	Watts	Jon	Action	2017-01-01	Sony Pictures
167	Before Sunrise	Linklater	Richard	Drama	1995-01-01	Sony Pictures Home Entertainment
168	The Truman Show	Weir	Peter	Comedy	1998-01-01	Paramount Pictures
169	Harry Potter and the Deathly Hallows: Part 2	Yates	David	Adventure	2011-01-01	Warner Bros. Pictures
170	Star Warz: The Force Awakens	Abrams	J.J.	Action	2015-01-01	Walt Disney Pictures
171	Paris, Texas	Wenders	Wim	Drama	1984-01-01	20th Century Fox
172	The Last Picture Show	Bogdanovich	Peter	Drama	1971-01-01	Columbia Pictures
173	Gandhi	Attenborough	Richard	Drama	1982-01-01	Columbia Pictures
174	Barry Lyndon	Kubrick	Stanley	Adventure	1975-01-01	Warner Bros.
175	Rocky	G.Avildsen	John	Drama	1976-01-01	United Artists
176	Annie Hall	Allen	Woody	Comedy	1977-01-01	United Artists
177	Prisoners	Villeneuve	Denis	Drama	2013-01-01	Warner Bros.
178	Donnie Darko	Kelly	Richard	Drama	2001-01-01	Newmarket Film Group
179	Catch Me If You Can	Spielberg	Steven	Drama	2002-01-01	DreamWorks SKG
180	The Man Who Shot Liberty Valance	Ford	John	Action	1962-01-01	Paramount Home Video
181	Laura	Preminger	Otto	Drama	1944-01-01	20th Century Fox
182	Monsters, Inc.	Docter	Pete	Adventure	2001-01-01	Buena Vista Distribution Compa
183	The Bourne Ultimatum	Greengrass	Paul	Action	2007-01-01	Universal Pictures
184	Cat on a Hot Tin Roof	Brooks	Richard	Drama	1958-01-01	MGM Home Entertainment
185	The Wizard of Oz	Fleming	Victor	Adventure	1939-01-01	Warner Bros. Pictures
186	Sleuth	L.Mankiewicz	Joseph	Drama	1972-01-01	20th Century Fox Film Corporation
187	Roman Holiday	Wyler	William	Comedy	1953-01-01	Paramount Pictures
188	Out of the Past	Tourneur	Jacques	Drama	1947-01-01	WARNER BROTHERS PICTURES
189	Anatomy of a Murder	Preminger	Otto	Drama	1959-01-01	Sony Pictures Home Entertainment
190	Whos Afraid of Virginia Woolf?	Nichols	Mike	Drama	1966-01-01	Warner Home Video
191	The Terminator	Cameron	James	Action	1984-01-01	Orion Pictures Corporation
192	Groundhog Day	Ramis	Harold	Comedy	1993-01-01	Columbia Pictures
193	The Help	Taylor	Tate	Drama	2011-01-01	DreamWorks Studios
194	Strangers on a Train	Hitchcock	Alfred	Drama	1951-01-01	Warner Home Video
195	The Night of the Hunter	Laughton	Charles	Drama	1955-01-01	United Artists
196	All Quiet on the Western Front	Milestone	Lewis	Drama	1930-01-01	Universal Pictures
197	Beauty and the Beast	Trousdale	Gary	Adventure	1991-01-01	Buena Vista
198	Lion	Davis	Garth	Drama	2016-01-01	See-Saw Films
199	Twelve Monkeys	Gilliam	Terry	Science Fiction	1995-01-01	Universal Pictures
200	Guardians of the Galaxy	Gunn	James	Action	2014-01-01	Walt Disney Pictures
201	Dog Day Afternoon	Lumet	Sidney	Drama	1975-01-01	WARNER BROTHERS PICTURES
202	Jaws	Spielberg	Steven	Adventure	1975-01-01	Universal Pictures
203	Zootopia	Bush	Jared	Adventure	2016-01-01	Walt Disney Animation Studios
204	Guardians of the Galaxy Vol. 2	Gunn	James	Action	2017-01-01	Walt Disney Pictures
205	Pirates of the Caribbean: The Curse of the Black Pearl	Verbinski	Gore	Action	2003-01-01	Buena Vista Pictures
206	Before Sunset	Linklater	Richard	Drama	2004-01-01	Warner Independent Pictures
207	The Imitation Game	Tyldum	Morten	Drama	2014-01-01	The Weinstein Company
208	Young Frankenstein	Brooks	Mel	Comedy	1974-01-01	20th Century Fox
209	Stalag 17	Wilder	Billy	Drama	1953-01-01	Paramount Home Video
210	Dogville	von-Trier	Lars	Drama	2003-01-01	Lions Gate Films
211	Dead Poets Society	Weir	Peter	Comedy	1989-01-01	Buena Vista Pictures
212	High Noon	Zinnemann	Fred	Drama	1952-01-01	United Artists
213	Papillon	J.Schaffner	Franklin	Drama	1973-01-01	Twentieth Century Fox Home Entertainment
214	A Streetcar Named Desire	Kazan	Elia	Drama	1951-01-01	Warner Bros. Pictures
215	Arsenic and Old Lace	Capra	Frank	Comedy	1944-01-01	Warner Bros. Pictures
216	Sin City	Miller	Frank	Drama	2005-01-01	Dimension Films
217	The Hustler	Rossen	Robert	Drama	1961-01-01	Fox
218	A Night at the Opera	Wood	Sam	Comedy	1935-01-01	MGM
219	The Killing	Kubrick	Stanley	Drama	1956-01-01	United Artists
220	The Avengers	Whedon	Joss	Action	2012-01-01	Walt Disney Pictures
221	Notorious	Hitchcock	Alfred	Drama	1946-01-01	RKO Radio Pictures
222	Harvey	Koster	Henry	Comedy	1950-01-01	MCA Universal Home Video
223	The Martian	Scott	Ridley	Adventure	2015-01-01	20th Century Fox
224	The Exorcist	Friedkin	William	Horror	1973-01-01	Warner Bros. Pictures
225	Rio Bravo	Hawks	Howard	Action	1959-01-01	Xenon
226	The Philadelphia Story	Cukor	George	Comedy	1940-01-01	MGM
227	Rope	Hitchcock	Alfred	Drama	1948-01-01	Warner Bros. Pictures
228	The Big Sleep	Hawks	Howard	Drama	1946-01-01	Warner Bros. Pictures
229	Pink Floyd: The Wall	Parker	Alan	Adventure	1982-01-01	United Artists
230	The Kings Speech	Hooper	Tom	Drama	2010-01-01	The Weinstein Company
231	A Christmas Story	Clark	Bob	Comedy	1983-01-01	MGM
232	The Graduate	Nichols	Mike	Comedy	1967-01-01	Embassy Pictures/Rialto Pictures
233	JFK	Stone	Oliver	Drama	1991-01-01	Warner Bros.
234	Sling Blade	Thornton	Billy	Drama	1996-01-01	Miramax Films
235	Blood Diamond	Zwick	Edward	Adventure	2006-01-01	Warner Bros. Pictures
236	Magnolia	Anderson	Paul	Drama	1999-01-01	New Line Cinema
237	Rain Man	Levinson	Barry	Drama	1988-01-01	MGM
238	The Revenant	Iñárritu	Alejandro	Adventure	2015-01-01	20th Century Fox
239	The Nightmare Before Christmas	Selick	Henry	Horror	1993-01-01	Touchstone Pictures
240	The Manchurian Candidate	Frankenheimer	John	Drama	1962-01-01	MGM/UA Classics
241	Deadpool	Miller	Tim	Action	2016-01-01	20th Century Fox
242	The Wild Bunch	Peckinpah	Sam	Action	1969-01-01	Warner Bros. Pictures
243	Aladdin	Clements	Ron	Adventure	1992-01-01	Buena Vista Distribution Compa
244	Big Fish	Burton	Tim	Adventure	2003-01-01	Sony Pictures
245	Patton	J.Schaffner	Franklin	Drama	1970-01-01	Twentieth Century Fox Home Entertainment
246	The Lost Weekend	Wilder	Billy	Drama	1945-01-01	Paramount Pictures
247	Short Term 12	Cretton	Destin	Drama	2013-01-01	Cinedigm
248	His Girl Friday	Hawks	Howard	Comedy	1940-01-01	Columbia Pictures
249	The Straight Story	Lynch	David	Drama	1999-01-01	Buena Vista Pictures
250	Slumdog Millionaire	Boyle	Danny	Drama	2008-01-01	Fox Searchlight Pictures
\.


--
-- Data for Name: shipments; Type: TABLE DATA; Schema: public; Owner: bneupan2
--

COPY public.shipments (shipment_id, customer_id, movie_id, media_type, shipment_date) FROM stdin;
2	4	10	DVD	2021-08-09
3	10	227	Stream-Media	2010-07-14
11	6	98	DVD	2021-12-07
20	6	224	Blu-Ray	2011-01-30
21	7	184	Blu-Ray	2001-05-18
22	10	229	Stream-Media	2006-12-30
26	8	166	Stream-Media	2019-08-17
29	10	10	Stream-Media	2020-11-12
30	10	180	Stream-Media	2000-11-07
43	8	229	Blu-Ray	2001-01-06
44	9	212	Blu-Ray	2014-10-04
49	10	218	Stream-Media	2011-09-15
56	6	101	Blu-Ray	2015-06-23
63	8	43	Blu-Ray	2006-12-04
66	5	43	Blu-Ray	2001-09-19
68	6	124	Stream-Media	2000-07-05
70	10	151	DVD	2008-02-13
73	5	183	Stream-Media	2003-05-01
81	7	182	Stream-Media	2017-09-12
113	4	190	DVD	2000-05-19
123	1	109	Stream-Media	2016-01-14
126	5	36	DVD	2009-01-04
130	7	219	Stream-Media	2010-05-20
134	7	10	Stream-Media	2017-04-11
135	1	88	Stream-Media	2021-10-20
147	7	164	Stream-Media	2013-06-26
149	2	48	Blu-Ray	2004-07-02
155	9	67	DVD	2002-08-11
156	1	174	Blu-Ray	2010-11-06
159	5	142	Blu-Ray	2011-07-21
161	3	54	Stream-Media	2016-08-21
165	4	98	Blu-Ray	2020-02-13
177	1	10	DVD	2003-05-07
184	4	97	Stream-Media	2000-09-07
187	6	180	Stream-Media	2007-11-23
190	8	232	DVD	2015-12-22
191	8	10	Blu-Ray	2014-01-22
192	9	74	Stream-Media	2006-08-13
195	1	174	Blu-Ray	2001-08-29
198	9	162	DVD	2021-07-05
203	1	125	Blu-Ray	2018-02-09
208	1	185	Stream-Media	2017-05-06
209	4	165	Stream-Media	2002-11-15
210	9	200	Blu-Ray	2011-08-13
212	9	10	Blu-Ray	2019-01-02
214	9	84	DVD	2018-11-25
216	3	138	Blu-Ray	2021-05-01
228	5	189	DVD	2007-01-11
236	1	61	DVD	2004-12-20
242	8	214	DVD	2010-01-11
245	3	227	Stream-Media	2000-03-30
250	8	26	Blu-Ray	2017-06-20
258	7	173	DVD	2017-11-21
263	1	216	DVD	2003-11-15
266	6	224	DVD	2011-02-23
269	5	159	Blu-Ray	2020-04-09
274	6	213	DVD	2021-06-04
275	7	138	Blu-Ray	2001-12-17
278	3	25	Blu-Ray	2010-01-05
282	4	14	Blu-Ray	2020-04-12
283	1	67	DVD	2020-02-07
287	2	162	Stream-Media	2016-08-27
290	4	62	Stream-Media	2017-08-11
293	8	68	Stream-Media	2013-01-09
299	7	119	Blu-Ray	2002-01-20
301	8	5	Blu-Ray	2021-04-21
\.


--
-- Data for Name: stock; Type: TABLE DATA; Schema: public; Owner: bneupan2
--

COPY public.stock (movie_id, media_type, cost_price, retail_price, current_stock) FROM stdin;
212	Blu-Ray	21.95	29.95	4
97	Stream-Media	17.45	24.95	Infinity
183	Stream-Media	17.45	24.95	Infinity
98	DVD	13.95	19.95	3
249	Stream-Media	17.45	24.95	Infinity
82	Stream-Media	17.45	24.95	Infinity
179	Stream-Media	17.45	24.95	Infinity
189	DVD	13.95	19.95	3
9	Stream-Media	17.45	24.95	Infinity
5	Stream-Media	17.45	24.95	Infinity
5	Blu-Ray	21.95	29.95	3
111	DVD	13.95	19.95	4
187	DVD	13.95	19.95	7
162	Stream-Media	17.45	24.95	Infinity
26	Blu-Ray	21.95	29.95	6
67	Blu-Ray	21.95	29.95	1
196	Blu-Ray	21.95	29.95	9
55	Blu-Ray	21.95	29.95	3
77	DVD	13.95	19.95	0
77	Stream-Media	17.45	24.95	Infinity
183	DVD	13.95	19.95	8
208	Stream-Media	17.45	24.95	Infinity
55	DVD	13.95	19.95	0
244	Stream-Media	17.45	24.95	Infinity
162	DVD	13.95	19.95	9
167	DVD	13.95	19.95	3
246	Blu-Ray	21.95	29.95	5
182	Stream-Media	17.45	24.95	Infinity
173	DVD	13.95	19.95	2
124	Stream-Media	17.45	24.95	Infinity
229	Blu-Ray	21.95	29.95	2
227	Stream-Media	17.45	24.95	Infinity
46	Stream-Media	17.45	24.95	Infinity
224	Blu-Ray	21.95	29.95	1
128	Stream-Media	17.45	24.95	Infinity
62	Stream-Media	17.45	24.95	Infinity
143	Stream-Media	17.45	24.95	Infinity
43	Blu-Ray	21.95	29.95	3
193	DVD	13.95	19.95	2
182	DVD	13.95	19.95	6
126	DVD	13.95	19.95	7
88	Stream-Media	17.45	24.95	Infinity
123	DVD	13.95	19.95	6
205	Blu-Ray	21.95	29.95	6
101	Blu-Ray	21.95	29.95	5
68	Stream-Media	17.45	24.95	Infinity
190	DVD	13.95	19.95	9
191	Blu-Ray	21.95	29.95	6
154	DVD	13.95	19.95	5
86	Stream-Media	17.45	24.95	Infinity
127	Blu-Ray	21.95	29.95	8
217	DVD	13.95	19.95	6
74	Stream-Media	17.45	24.95	Infinity
114	Stream-Media	17.45	24.95	Infinity
123	Blu-Ray	21.95	29.95	4
41	DVD	13.95	19.95	0
54	Stream-Media	17.45	24.95	Infinity
193	Blu-Ray	21.95	29.95	8
83	DVD	13.95	19.95	9
245	Stream-Media	17.45	24.95	Infinity
22	Blu-Ray	21.95	29.95	7
94	Blu-Ray	21.95	29.95	8
38	Stream-Media	17.45	24.95	Infinity
179	DVD	13.95	19.95	8
250	Stream-Media	17.45	24.95	Infinity
192	Stream-Media	17.45	24.95	Infinity
25	Blu-Ray	21.95	29.95	7
8	Stream-Media	17.45	24.95	Infinity
111	Blu-Ray	21.95	29.95	3
10	Stream-Media	17.45	24.95	Infinity
108	DVD	13.95	19.95	3
149	Stream-Media	17.45	24.95	Infinity
115	Stream-Media	17.45	24.95	Infinity
216	DVD	13.95	19.95	3
221	DVD	13.95	19.95	5
99	Stream-Media	17.45	24.95	Infinity
194	DVD	13.95	19.95	7
152	Blu-Ray	21.95	29.95	7
219	Stream-Media	17.45	24.95	Infinity
200	Blu-Ray	21.95	29.95	3
9	Blu-Ray	21.95	29.95	3
135	Blu-Ray	21.95	29.95	4
137	Blu-Ray	21.95	29.95	1
186	Blu-Ray	21.95	29.95	4
185	Stream-Media	17.45	24.95	Infinity
243	Blu-Ray	21.95	29.95	6
14	Blu-Ray	21.95	29.95	2
240	DVD	13.95	19.95	0
242	Blu-Ray	21.95	29.95	3
132	DVD	13.95	19.95	3
161	Stream-Media	17.45	24.95	Infinity
47	Stream-Media	17.45	24.95	Infinity
214	DVD	13.95	19.95	0
70	Blu-Ray	21.95	29.95	7
212	Stream-Media	17.45	24.95	Infinity
201	Blu-Ray	21.95	29.95	6
57	Blu-Ray	21.95	29.95	6
125	Blu-Ray	21.95	29.95	0
142	Blu-Ray	21.95	29.95	7
69	DVD	13.95	19.95	9
30	Blu-Ray	21.95	29.95	7
54	Blu-Ray	21.95	29.95	2
213	DVD	13.95	19.95	6
138	Blu-Ray	21.95	29.95	7
199	Blu-Ray	21.95	29.95	3
81	DVD	13.95	19.95	5
73	Blu-Ray	21.95	29.95	6
40	Stream-Media	17.45	24.95	Infinity
159	Blu-Ray	21.95	29.95	3
170	Stream-Media	17.45	24.95	Infinity
187	Blu-Ray	21.95	29.95	4
92	Stream-Media	17.45	24.95	Infinity
232	DVD	13.95	19.95	8
118	DVD	13.95	19.95	6
175	Stream-Media	17.45	24.95	Infinity
21	DVD	13.95	19.95	9
151	DVD	13.95	19.95	2
36	DVD	13.95	19.95	7
212	DVD	13.95	19.95	6
124	DVD	13.95	19.95	9
184	Blu-Ray	21.95	29.95	8
216	Blu-Ray	21.95	29.95	1
99	Blu-Ray	21.95	29.95	0
231	DVD	13.95	19.95	8
79	Blu-Ray	21.95	29.95	7
24	Stream-Media	17.45	24.95	Infinity
4	Blu-Ray	21.95	29.95	6
155	Stream-Media	17.45	24.95	Infinity
181	DVD	13.95	19.95	2
198	Blu-Ray	21.95	29.95	1
165	DVD	13.95	19.95	4
220	Blu-Ray	21.95	29.95	4
155	DVD	13.95	19.95	9
119	Blu-Ray	21.95	29.95	1
218	Stream-Media	17.45	24.95	Infinity
82	Blu-Ray	21.95	29.95	2
122	DVD	13.95	19.95	5
116	DVD	13.95	19.95	7
25	DVD	13.95	19.95	9
43	DVD	13.95	19.95	0
227	Blu-Ray	21.95	29.95	4
56	DVD	13.95	19.95	5
164	Stream-Media	17.45	24.95	Infinity
48	Blu-Ray	21.95	29.95	7
108	Blu-Ray	21.95	29.95	6
224	DVD	13.95	19.95	4
109	Stream-Media	17.45	24.95	Infinity
98	Blu-Ray	21.95	29.95	5
166	Stream-Media	17.45	24.95	Infinity
10	DVD	13.95	19.95	8
40	DVD	13.95	19.95	2
165	Stream-Media	17.45	24.95	Infinity
180	Stream-Media	17.45	24.95	Infinity
229	Stream-Media	17.45	24.95	Infinity
135	Stream-Media	17.45	24.95	Infinity
57	DVD	13.95	19.95	6
163	Stream-Media	17.45	24.95	Infinity
239	Blu-Ray	21.95	29.95	2
24	Blu-Ray	21.95	29.95	7
93	DVD	13.95	19.95	2
39	DVD	13.95	19.95	8
228	Stream-Media	17.45	24.95	Infinity
247	Stream-Media	17.45	24.95	Infinity
200	DVD	13.95	19.95	5
66	Blu-Ray	21.95	29.95	9
229	DVD	13.95	19.95	5
174	Blu-Ray	21.95	29.95	5
61	DVD	13.95	19.95	2
99	DVD	13.95	19.95	1
231	Blu-Ray	21.95	29.95	7
230	Stream-Media	17.45	24.95	Infinity
84	DVD	13.95	19.95	2
218	Blu-Ray	21.95	29.95	9
67	DVD	13.95	19.95	4
69	Blu-Ray	21.95	29.95	2
228	Blu-Ray	21.95	29.95	9
83	Blu-Ray	21.95	29.95	8
120	DVD	13.95	19.95	1
10	Blu-Ray	21.95	29.95	5
174	Stream-Media	17.45	24.95	Infinity
\.


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (customer_id);


--
-- Name: movies movies_pkey; Type: CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.movies
    ADD CONSTRAINT movies_pkey PRIMARY KEY (movie_id);


--
-- Name: shipments shipments_pkey; Type: CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT shipments_pkey PRIMARY KEY (shipment_id);


--
-- Name: stock stock_pkey; Type: CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_pkey PRIMARY KEY (movie_id, media_type);


--
-- Name: shipments shipments_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT shipments_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: shipments shipments_movie_id_media_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.shipments
    ADD CONSTRAINT shipments_movie_id_media_type_fkey FOREIGN KEY (movie_id, media_type) REFERENCES public.stock(movie_id, media_type) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: stock stock_movie_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: bneupan2
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_movie_id_fkey FOREIGN KEY (movie_id) REFERENCES public.movies(movie_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

