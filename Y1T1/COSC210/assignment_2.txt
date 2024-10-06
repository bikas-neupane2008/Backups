-- COSC210 Practical Assignment Template

-- Please complete the assignment questions using the view templates
-- provided below.

-- *******************************************************************
--                           IMPORTANT
-- *******************************************************************

-- Make sure that you do not alter the names of the views or their 
-- attribute values. If you do your assignment will not work in the
-- auto-marking software and you may lose marks!

-- *******************************************************************


CREATE VIEW movie_summary(movie_title, release_date, media_type, retail_price)
AS 
SELECT m.movie_title, m.release_date, s.media_type, s.retail_price
FROM movies m
JOIN stock s ON m.movie_id = s.movie_id;


CREATE VIEW old_shipments(first_name, last_name, movie_id, shipment_id, shipment_date)
AS 
SELECT c.first_name, c.last_name, s.movie_id, s.shipment_id, s.shipment_date
FROM customers c
JOIN shipments s ON c.customer_id = s.customer_id
WHERE s.shipment_date < '2010-01-01';


CREATE VIEW trilogy(movie_title)
AS 
SELECT movie_title
FROM movies
WHERE movie_title LIKE '%Rings%' OR movie_title LIKE '%Wars%';


CREATE VIEW retail_price_hike(movie_id , retail_price, new_price)
AS 
SELECT movie_id, retail_price, Round((retail_price * 1.25)::numeric, 2)
FROM stock;


CREATE VIEW value_summary(total_stock_cost, total_retail_cost)
AS 
SELECT SUM(s.cost_price * s.current_stock),
       SUM(s.retail_price * s.current_stock)
FROM stock s
JOIN movies m ON s.movie_id = m.movie_id
WHERE s.media_type <> 'Stream-Media';


CREATE VIEW profits_from_movie(movie_id, movie_title, total_profit)
AS 
SELECT m.movie_id, m.movie_title, SUM(s.retail_price) - SUM(s.cost_price)
FROM movies m
JOIN stock s ON m.movie_id = s.movie_id
JOIN shipments sh ON s.movie_id = sh.movie_id AND s.media_type = sh.media_type
GROUP BY m.movie_id, m.movie_title;


CREATE VIEW followers_of_melkor AS
SELECT c.first_name, c.last_name
FROM customers c
WHERE c.customer_id NOT IN (
    SELECT s.customer_id
    FROM shipments s
    JOIN stock st ON s.movie_id = st.movie_id AND s.media_type = st.media_type
    JOIN movies m ON st.movie_id = m.movie_id
    WHERE m.movie_title = 'The Lord of the Rings: The Fellowship of the Ring'
);

                      
CREATE VIEW sole_angry_watcher(first_name, last_name)
AS 
SELECT c.first_name, c.last_name
FROM customers c
JOIN shipments s ON c.customer_id = s.customer_id
JOIN movies m ON s.movie_id = m.movie_id
WHERE m.movie_title = '12 Angry Men'
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING COUNT(*) = 1;
