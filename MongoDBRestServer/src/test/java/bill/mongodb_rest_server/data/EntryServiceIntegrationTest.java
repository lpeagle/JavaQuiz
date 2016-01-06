package bill.mongodb_rest_server.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.fail;

import java.io.IOException;

import bill.mongodb_rest_server.App;
import bill.mongodb_rest_server.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import bill.mongodb_rest_server.model.RestResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebIntegrationTest("server.port:0")
@net.jcip.annotations.NotThreadSafe 
public class EntryServiceIntegrationTest {
	RestTemplate restTemplate = new TestRestTemplate();
	ObjectMapper jsonMapper = new ObjectMapper();
	String url=null;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		url = "http://localhost:" + port + "/restAPI/items";
		//remove entry if already exist
		restTemplate.delete(url+"/10");
	}

	@Test
	public void canAddEntryTest() {
		Entry entry = new Entry(10,"car");
		RestResponse result = restTemplate.postForObject(url, entry, RestResponse.class);
		result = restTemplate.getForObject(url+"/10", RestResponse.class);
		assertThat("{\"id\":10,\"name\":\"car\"}", is(result.getMessage()));
	}

	@Test
	public void canUpdateEntryTest() {
		Entry entry = new Entry(10,"car");
		RestResponse result = restTemplate.postForObject(url, entry, RestResponse.class);
		result = restTemplate.postForObject(url+"/10", new Entry(10,"suv"), RestResponse.class);
		result = restTemplate.getForObject(url+"/10", RestResponse.class);
		assertThat("{\"id\":10,\"name\":\"suv\"}", is(result.getMessage()));
	}

	@Test
	public void canRemoveEntryTest() {
		Entry entry = new Entry(10,"car");
		RestResponse result = restTemplate.postForObject(url, entry, RestResponse.class);
		result = restTemplate.getForObject(url+"/10", RestResponse.class);
		assertThat("{\"id\":10,\"name\":\"car\"}", is(result.getMessage()));
		restTemplate.delete(url+"/10");
		result = restTemplate.getForObject(url+"/10", RestResponse.class);
		assertThat("entry not exist", is(result.getMessage()));
	}


}
