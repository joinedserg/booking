DROP TABLE IF EXISTS BOOKING;

CREATE TABLE BOOKING (
  ID BIGINT AUTO_INCREMENT  PRIMARY KEY,
  FILM_ID VARCHAR(50) NOT NULL,
  DATE_OF_BOOKING DATE NOT NULL,
  SEAT INT NOT NULL
);

CREATE UNIQUE INDEX UNIQUE_BOOKING ON BOOKING(
    FILM_ID, DATE_OF_BOOKING, SEAT
)