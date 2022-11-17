package com.example.booking;

import com.example.booking.db.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class BookingApplicationTests {

	private String BASE = "booking/";

	private String GET_BY_ID = BASE + "getBooking/";
	private String CANCEL_BY_ID = BASE + "cancelBooking/";
	private String CREATE = BASE + "createBooking/";
	private String GET_ALL_BY_DATE = BASE + "listBookings/";

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private BookingRepository repository;

	@BeforeEach
	public void beforeEach() {
		repository.deleteAll();
	}

	@Test
	public void test_base() throws Exception {
		String url = "http://localhost:" + randomServerPort + "/";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		LocalDate curDate = LocalDate.now();
		LocalDate nextDate = LocalDate.now().plusDays(1);

		TestRestTemplate restTemplate = new TestRestTemplate();
		//
		ResponseEntity<List> respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + curDate, HttpMethod.GET, requestEntity, List.class);

		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 0);

		//1) add curDate
		ResponseEntity respCreate = restTemplate.exchange(url + CREATE + "1/" + curDate + "/" + 1, HttpMethod.POST, requestEntity, Void.class);
		assertEquals(respCreate.getStatusCode(), HttpStatus.OK);

		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + curDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 1);

		//add curDate (the same as at 1 step)
		respCreate = restTemplate.exchange(url + CREATE + "1/" + curDate + "/" + 1, HttpMethod.POST, requestEntity, Void.class);
		assertEquals(respCreate.getStatusCode(), HttpStatus.BAD_REQUEST);

		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + curDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 1);

		//add nextDate
		respCreate = restTemplate.exchange(url + CREATE + "1/" + nextDate + "/" + 1, HttpMethod.POST, requestEntity, Void.class);
		assertEquals(respCreate.getStatusCode(), HttpStatus.OK);

		//check cur date
		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + curDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 1);

		//check next date
		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + nextDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 1);

		//cancel cur date
		respCreate = restTemplate.exchange(url + CANCEL_BY_ID + 1, HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(respCreate.getStatusCode(), HttpStatus.OK);

		//cancel cur date again
		respCreate = restTemplate.exchange(url + CANCEL_BY_ID + 1, HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(respCreate.getStatusCode(), HttpStatus.NOT_FOUND);

		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + curDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 0);

		//check next date
		respGet = restTemplate.exchange(url + GET_ALL_BY_DATE + nextDate, HttpMethod.GET, requestEntity, List.class);
		assertEquals(respGet.getStatusCode(), HttpStatus.OK);
		assertEquals(respGet.getBody().size(), 1);

	}

}
