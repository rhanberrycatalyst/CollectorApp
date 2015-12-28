package xmen.collectorapp.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;


public class InitializerConfig extends AbstractDispatcherServletInitializer {
	

	@Override
	protected WebApplicationContext createServletApplicationContext() {
		DispatcherServlet dispatcher = new DispatcherServlet();

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		dispatcher.setApplicationContext(applicationContext);
		applicationContext.register(ServletConfig.class);
		
		return applicationContext;

	}

	@Override
	protected String[] getServletMappings() {
		 return new String[]{"/*", "/pdfs/**", "/html/**", "/images/**"};
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(JpaContextConfig.class);
		return applicationContext;

	}
	
	
}
