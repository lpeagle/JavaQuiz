package bill.mongodb_rest_server.srv;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import bill.mongodb_rest_server.model.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bill.mongodb_rest_server.model.RestResponse;
import bill.mongodb_rest_server.data.DataManager;

@RestController
public class EntryService {
	Logger logger = LoggerFactory.getLogger(EntryService.class);

	@Autowired
	private ApplicationContext context;

	@Autowired
	private ResponseBuilder responseBuilder;

	@Autowired
	private DataManager dataManger;

	@Value("${responseDelay}")
	private int responseDelay;

	private AtomicLong count = new AtomicLong(0);
	private AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());
	private AtomicLong newTime = new AtomicLong(0);
	ObjectMapper jsonMapper = new ObjectMapper();

	@PostConstruct
	protected void initialize() {
		dataManger.connectDB("localhost", 27017);
		logger.info("Using delay time:{}", responseDelay);

	}

	@RequestMapping("/")
	String home() {
		return "MongoDB Rest Service";
	}

	@RequestMapping(value = "/restAPI/items/{id}", method = RequestMethod.POST)
	RestResponse updateEntry(@PathVariable long id, @RequestBody Entry entry) {
		logger.debug("request to update entry [{}]", id);
		logger.debug("Request:{}", entry);
		try {
				dataManger.updateEntry(entry);
				String info=String.format("entry [%s] is updated", id);
				logger.debug(info);
				return responseBuilder.buildSuccessResponse(info);

		} catch (Exception e) {
			String errorMsg = String.format("Error updating entry %s", id);
			logger.warn(errorMsg);
			return responseBuilder.buildErrorResponse(errorMsg,e.toString());
		}
	}

	@RequestMapping(value = "/restAPI/items", method = { RequestMethod.PUT, RequestMethod.POST })
	RestResponse createEntry(@RequestBody Entry entry) {
		logger.debug("Request:{}", entry);
		try {
			dataManger.addEntry(entry);
			String info=String.format("[%s] created", entry);
			logger.debug(info);
			return responseBuilder.buildSuccessResponse(info);
		} catch (Exception e) {
			String errorMsg = String.format("Error creating [%s]", entry);
			logger.warn(errorMsg,e);
			return responseBuilder.buildErrorResponse(errorMsg,e.toString());
		}
	}
	
	@RequestMapping(value = "/restAPI/items/{id}", method = RequestMethod.GET)
	RestResponse getEntry(@PathVariable long id) {
		logger.debug("request to get entry [{}]", id);
		Entry entry = dataManger.getEntry(id);
		RestResponse response = new RestResponse(true, null);
		try {
			if (entry == null)
				response.setMessage("entry not exist");
			else
				response.setMessage(jsonMapper.writeValueAsString(entry));
		} catch (JsonProcessingException e) {
			response.setSuccess(false);
			response.setMessage("Json error getting entry info");
		}
		return response;
	}

	@RequestMapping(value = "/restAPI/items/{id}", method = RequestMethod.DELETE)
	RestResponse removeEntry(@PathVariable long id) {
		logger.debug("request to remove entry [{}]", id);
		//logger.debug("Request:{}", request);
		dataManger.removeEntry(new Entry(id,""));
		return new RestResponse(true,"Entry ["+id+"] removed");
	}

}