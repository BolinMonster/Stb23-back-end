CREATE TABLE stb (
  id SERIAL PRIMARY KEY, 
  titre VARCHAR(255), 
  version VARCHAR(10), 
  date TIMESTAMP, 
  description TEXT, 
  client_entity VARCHAR(255), 
  client_person_gender VARCHAR(10), 
  client_person_lastname VARCHAR(255), 
  client_mail VARCHAR(255)
);
CREATE TABLE member (
  id SERIAL PRIMARY KEY, 
  stb_id INTEGER REFERENCES stb(id), 
  gender VARCHAR(10), 
  lastname VARCHAR(255), 
  mail VARCHAR(255), 
  function VARCHAR(255)
);
CREATE TABLE feature (
  id SERIAL PRIMARY KEY, 
  stb_id INTEGER REFERENCES stb(id), 
  name VARCHAR(255), 
  number INTEGER, 
  section INTEGER, 
  description TEXT, 
  priority INTEGER
);