package xmen.collectorapp.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {SettingsConfig.PROJECT_CONTROLLER_PACKAGE})
public class ServletConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ContentNegotiatingViewResolver getContentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
		contentNegotiatingViewResolver.setOrder(1);
		contentNegotiatingViewResolver
				.setContentNegotiationManager(getContentNegotiationManager());

		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(new MappingJackson2JsonView());
		defaultViews.add(new MarshallingView(getXStreamMarshaller()));
		contentNegotiatingViewResolver.setDefaultViews(defaultViews);
		return contentNegotiatingViewResolver;
	}

	@Bean
	public ContentNegotiationManager getContentNegotiationManager() {
		ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager(
				getPathExtensionContentNegotiationStrategy());
		return contentNegotiationManager;
	}

	/**
	 * Allows content to be of either xml or json type
	 * @return
	 */
	@Bean
	public PathExtensionContentNegotiationStrategy getPathExtensionContentNegotiationStrategy() {
		Map<String, MediaType> args = new HashMap<String, MediaType>();
		args.put("json", MediaType.APPLICATION_JSON);
		args.put("xml", MediaType.APPLICATION_XML);
		PathExtensionContentNegotiationStrategy pathExtensionContentNegotiationStrategy = new PathExtensionContentNegotiationStrategy(
				args);
		return pathExtensionContentNegotiationStrategy;
	}

	@Bean
	public XStreamMarshaller getXStreamMarshaller() {
		XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		xStreamMarshaller.setAutodetectAnnotations(true);
		return xStreamMarshaller;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**")
				.addResourceLocations("assets");
		registry.addResourceHandler("/pdfs/**").addResourceLocations("pdfs");
		registry.addResourceHandler("/html/**").addResourceLocations("/WEB-INF/html");

	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);

	}


	public SessionLocaleResolver getSessionLocaleResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(new Locale("en"));
		return sessionLocaleResolver;
	}


	public ResourceBundleMessageSource getResourceBundleMessageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("messages");
		return resourceBundleMessageSource;

	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/html/");
		internalResourceViewResolver.setSuffix(".html");
		internalResourceViewResolver.setOrder(1);
		return internalResourceViewResolver;
	}

	@Bean
	public BeanNameViewResolver getBeanNameViewResolver() {
		BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
		beanNameViewResolver.setOrder(0);
		return beanNameViewResolver;
	}

}
