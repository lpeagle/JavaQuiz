package bill.mongodb_rest_server.model;

//import org.yaml.snakeyaml.representer.Representer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponse {
	private boolean isSuccess;
	private String message;
	private String details;

	public RestResponse() {
	}

	public RestResponse(boolean isSuccess, String message) {
		this(isSuccess, message, null);
	}

	public RestResponse(boolean isSuccess, String message, String details) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.details = details;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "RestResult [isSuccess=" + isSuccess + ", message=" + message + ", error=" + details + "]";
	}

}
