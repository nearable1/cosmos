package com.xiaoniu.call.api.gateway.core.support;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 黑白名单过滤器辅助类
 *
 * @author guozhengshui
 * @date 2019-06-10 16:28
 */
public class FilterPathMatcherHelper {

	private static AntPathMatcher antPathMatcher = new AntPathMatcher();

	private FilterPathMatcherHelper() {
	}

	public static boolean anyMatch(List<String> patterns, String path) {
		Assert.notEmpty(patterns, "配置路径列表不能为空");
		Assert.hasLength(path, "请求路径不能为空");

		return patterns != null && patterns.stream().anyMatch(s -> antPathMatcher.match(s, path));
	}

	public static boolean noneMatch(List<String> patterns, String path) {
		Assert.notEmpty(patterns, "配置路径列表不能为空");
		Assert.hasLength(path, "请求路径不能为空");

		return patterns != null && patterns.stream().noneMatch(s -> antPathMatcher.match(s, path));
	}

}