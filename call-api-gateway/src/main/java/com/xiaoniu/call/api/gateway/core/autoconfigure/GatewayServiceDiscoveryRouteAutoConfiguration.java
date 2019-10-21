
package com.xiaoniu.call.api.gateway.core.autoconfigure;

import com.xiaoniu.call.api.gateway.core.support.GatewayConstants;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.cloud.gateway.handler.predicate.PathRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.DispatcherHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.handler.predicate.RoutePredicateFactory.PATTERN_KEY;
import static org.springframework.cloud.gateway.support.NameUtils.normalizeFilterFactoryName;
import static org.springframework.cloud.gateway.support.NameUtils.normalizeRoutePredicateName;

/**
 *
 * 重写服务发现路由自动配置
 *
 * @author guoqiang
 * @date 2018-09-11 11:35 PM
 */
@Configuration
@ConditionalOnProperty(name = "spring.cloud.gateway.enabled", matchIfMissing = true)
@AutoConfigureBefore(GatewayAutoConfiguration.class)
@AutoConfigureAfter(CompositeDiscoveryClientAutoConfiguration.class)
@ConditionalOnClass({DispatcherHandler.class, DiscoveryClient.class})
@EnableConfigurationProperties
public class GatewayServiceDiscoveryRouteAutoConfiguration {

	@Bean
	@ConditionalOnBean(DiscoveryClient.class)
	@ConditionalOnProperty(name = "spring.cloud.gateway.discovery.locator.customize.enabled")
	public ServiceDiscoveryRouteDefinitionLocator serviceDiscoveryRouteDefinitionLocator(
			DiscoveryClient discoveryClient, DiscoveryLocatorProperties serviceDiscoveryLocatorProperties) {
		return new ServiceDiscoveryRouteDefinitionLocator(discoveryClient, serviceDiscoveryLocatorProperties);
	}

	@Bean
	@Primary
	public DiscoveryLocatorProperties serviceDiscoveryLocatorProperties(Environment environment) {
		DiscoveryLocatorProperties properties = new DiscoveryLocatorProperties();
		String springApplicationName = getSpringApplicationName(environment);
		properties.setPredicates(initPredicates(springApplicationName));
		properties.setFilters(initFilters());
		return properties;
	}

	private static String getSpringApplicationName(Environment environment){
		return environment.getRequiredProperty(GatewayConstants.SPRING_APPLICATION_NAME_KEY);
	}

	private static List<PredicateDefinition> initPredicates(String springApplicationName) {
		ArrayList<PredicateDefinition> definitions = new ArrayList<>();
		// add a predicate that matches the url at /gateway/serviceId/**
		PredicateDefinition predicate = new PredicateDefinition();
		predicate.setName(normalizeRoutePredicateName(PathRoutePredicateFactory.class));
		predicate.addArg(PATTERN_KEY, getPredicatePath(springApplicationName));
		definitions.add(predicate);
		return definitions;
	}

    private static String getPredicatePath(String springApplicationName) {
        return "'/" + springApplicationName + "/' + serviceId + '/**'";
    }

    private static List<FilterDefinition> initFilters() {
		ArrayList<FilterDefinition> definitions = new ArrayList<>();
		// add a StripPrefixFilter that removes /gateway/serviceId by default
		FilterDefinition stripPrefixFilter = new FilterDefinition();
		stripPrefixFilter.setName(normalizeFilterFactoryName(StripPrefixGatewayFilterFactory.class));
		stripPrefixFilter.addArg(StripPrefixGatewayFilterFactory.PARTS_KEY,
				GatewayConstants.STRIP_PREFIX_FILTER_PARTS_VALUE);
		definitions.add(stripPrefixFilter);
		// add a default HystrixFilter that use fallback
		/*FilterDefinition hystrixFilter = new FilterDefinition();
		hystrixFilter.setName(normalizeFilterFactoryName(HystrixGatewayFilterFactory.class));
		hystrixFilter.addArg(HystrixGatewayFilterFactory.NAME_KEY,
				"'" + GatewayConstants.DEFAULT_HYSTRIX_COMMAND_NAME + "'");
		hystrixFilter.addArg(HystrixGatewayFilterFactory.FALLBACK_URI,
				"'forward:" + GatewayConstants.DEFAULT_GLOBAL_FALLBACK_URI + "'");
		definitions.add(hystrixFilter);*/
		return definitions;
	}

}

