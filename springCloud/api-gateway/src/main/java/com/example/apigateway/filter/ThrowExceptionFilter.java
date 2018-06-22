package com.example.apigateway.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ThrowExceptionFilter extends ZuulFilter{
	
	private static Logger log= LoggerFactory.getLogger(ThrowExceptionFilter.class);

	@Override
	public Object run() {
		// TODO Auto-generated method stub
		log.info("This is a pre filter, it will七hrow a RuntimeException");
		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			doSomething();
		} catch (Exception e) {
			ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			ctx.set("error.exception", e); 
		}
		return null;
	}
	
	private void doSomething() {
		throw new RuntimeException("Exist some errors ... "); 
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}
	
}
