package com.example.apigateway.filter;

import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class ErrorExtFilter extends SendErrorFilter {
	@Override
	public String filterType () {
		return "error"; 
	}
	
	@Override
	public int filterOrder () {
		return 30; //大于 ErrorFilter 的值
	}
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		ZuulFilter failedFilter = (ZuulFilter)ctx.get("failed.fil七er");
		if(failedFilter != null && failedFilter.filterType().equals ("post")) {
			return true; 
		}
		return false;
	}
}
