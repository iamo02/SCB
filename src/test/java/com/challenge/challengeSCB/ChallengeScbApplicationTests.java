package com.challenge.challengeSCB;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.challengeSCB.bean.request.Request;
import com.challenge.challengeSCB.bean.response.ReturnObj;
import com.challenge.challengeSCB.exception.ValidationException;
import com.challenge.challengeSCB.service.ChallengeService;
import com.challenge.challengeSCB.service.impl.ChallengeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeScbApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ChallengeService challengeService;

	@Test
	public void success_with_challengescb() throws URISyntaxException {

		URI uri = new URI("http://localhost:8080/challengescb");
		Request request = new Request();
		request.setFunnyStr("VEhFIFdPUkxE");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);
		assertEquals("{\"encodedString\":\"ZGxyb3cxZWh0\"}", result.getBody());
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void fail_with_challengescb() throws URISyntaxException {

		URI uri = new URI("http://localhost:8080/challengescb");
		Request request = new Request();
		request.setFunnyStr("ZGxyb3cxZWh0");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<Request> httpEntity = new HttpEntity<>(request, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);
		assertEquals("Please enter english capital letters only.", result.getBody());
		assertEquals(400, result.getStatusCodeValue());
	}

	@Test
	public void workTest() {
		Request request = new Request();
		request.setFunnyStr("VEhFIFdPUkxE");
		ReturnObj response = challengeService.work(request);
		assertEquals("ZGxyb3cxZWh0", response.getEncodedString());
	}

	@Test
	public void workLowcaseExceptionTest() {
		Request request = new Request();
		request.setFunnyStr("ZGxyb3dlaHQ=");

		assertThrows(ValidationException.class, () -> challengeService.work(request),"Please enter english capital letters only.");
		
	}
	
	@Test
	public void workNumberExceptionTest() {
		Request request = new Request();
		request.setFunnyStr("VEhFMVdPUkxE");

		assertThrows(ValidationException.class, () -> challengeService.work(request),"Please enter english capital letters only.");
	}


	@Test
	public void decodeTest() {
		ChallengeServiceImpl challengeServiceImpl = new ChallengeServiceImpl();
		String stringDecode = challengeServiceImpl.decode("VEhFIFdPUkxE");
		assertEquals("THE WORLD", stringDecode);
	}

	@Test
	public void encodeTest() {
		ChallengeServiceImpl challengeServiceImpl = new ChallengeServiceImpl();
		String stringEncode = challengeServiceImpl.encode("dlrow1eht");
		assertEquals("ZGxyb3cxZWh0", stringEncode);
	}

	@Test
	public void addNumberTest() {
		ChallengeServiceImpl challengeServiceImpl = new ChallengeServiceImpl();
		String stringAddNumber = challengeServiceImpl.addNumber("the world");
		assertEquals("the1world", stringAddNumber);
	}

	@Test
	public void swapTest() {
		ChallengeServiceImpl challengeServiceImpl = new ChallengeServiceImpl();
		String stringSwap = challengeServiceImpl.swap("the1world");
		assertEquals("dlrow1eht", stringSwap);
	}

	@Test
	public void checkNumberTest() {
		ChallengeServiceImpl challengeServiceImpl = new ChallengeServiceImpl();
		boolean f = challengeServiceImpl.checkNumber("THE WORLD");
		boolean t = challengeServiceImpl.checkNumber("THE44WORLD");
		assertEquals(true, t);
		assertEquals(false, f);
	}
	
}
