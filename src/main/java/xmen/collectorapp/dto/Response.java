package xmen.collectorapp.dto;

import org.springframework.http.HttpStatus;

public class Response {
	private HttpStatus statusCode;
	private Object responseObject;

	/**
	 * @return the statusCode
	 */
	public HttpStatus getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @return the responseObject
	 */
	public Object getResponseObject() {
		return responseObject;
	}

	/**
	 * @param responseObject
	 *            the responseObject to set
	 */
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", responseObject="
				+ responseObject + "]";
	}

	/**
	 * @param statusCode
	 * @param responseObject
	 */
	public Response(HttpStatus statusCode, Object responseObject) {
		this.statusCode = statusCode;
		this.responseObject = responseObject;
	}

	/**
	 * 
	 */
	public Response() {
	}

}
