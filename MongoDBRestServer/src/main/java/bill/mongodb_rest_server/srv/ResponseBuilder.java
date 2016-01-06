package bill.mongodb_rest_server.srv;

import bill.mongodb_rest_server.model.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


@Component
public class ResponseBuilder {
	Logger logger =LoggerFactory.getLogger(ResponseBuilder.class);
	
	@Autowired
	private ApplicationContext context;
	
	Map<String,String> responseMap=new HashMap<String, String>();
	
	public ResponseBuilder(){
	}
	
//	 @PostConstruct
//	protected void initializeResponseCache(){
//		responseMap.put("A", loadResponse("ResponseA.xml"));
//		responseMap.put("D", loadResponse("ResponseD.xml"));
//		logger.trace("ResponseMsp:{}",responseMap);
//	}
	
	
	public String getResponse(String requestMessage){
		String response;
		if (requestMessage.indexOf(".authorize")>=0)
			response=responseMap.get("A");
		else 
			response= responseMap.get("D");
		logger.debug("Request:{},response:{}",requestMessage,response);
		return response;
	}

	protected String loadResponse(String resourceName) {
		Resource resource = context.getResource("classpath:" + resourceName);
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream is = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			br.close();

		} catch (IOException e) {
			throw new RuntimeException("Error reading resource " + resourceName
					+ e);
		}
		//logger.debug("Got Response"+buffer.toString());
		return buffer.toString();

	}
	
	public RestResponse buildErrorResponse(String errorMessage){
		return new RestResponse(false,errorMessage);
				//String.format("{isSuccess:false,error:%s}", errorMessage);
	}
	
	public RestResponse buildErrorResponse(String errorMessage,String details){
		return new RestResponse(false,errorMessage,details);
	}

	
	public RestResponse buildSuccessResponse(String message){
		return new RestResponse(true,message);
	}
	

}
