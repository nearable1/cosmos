package com.example.apigateway.utils;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;

public class DidiErrorAttributes extends DefaultErrorAttributes {
	@Override
	public Map<String, Object> getErrorAttributes (RequestAttributes requestAttributes,
			boolean includeStackTrace) {
		Map<String, Object> result = super.getErrorAttributes(requestAttributes,includeStackTrace);
		result.remove("exception");
		return result; 
	}
}
