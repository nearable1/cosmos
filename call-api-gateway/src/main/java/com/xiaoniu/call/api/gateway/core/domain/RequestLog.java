package com.xiaoniu.call.api.gateway.core.domain;

import java.io.Serializable;

/**
 * 请求日志记录
 *
 * @author guozhengshui
 * @date 2018-11-05 15:44
 */
public class RequestLog implements Serializable {
	private static final long serialVersionUID = 6029456863406811658L;

	private String requestUrl;

	private String requestHeader;

	private String requestParam;

	private String responseBody;

	private Long totalTime;

	public RequestLog() {
	}

	public RequestLog(String requestUrl, String requestHeader, String requestParam, String responseBody, Long totalTime) {
		this.requestUrl = requestUrl;
		this.requestHeader = requestHeader;
		this.requestParam = requestParam;
		this.responseBody = responseBody;
		this.totalTime = totalTime;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	@Override
	public String toString() {
		return "RequestLog{" +
				"requestUrl='" + requestUrl + '\'' +
				", requestHeader='" + requestHeader + '\'' +
				", requestParam='" + requestParam + '\'' +
				", responseBody='" + responseBody + '\'' +
				", totalTime=" + totalTime +
				'}';
	}
}