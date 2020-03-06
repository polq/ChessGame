package org.buzevych.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("org.buzevych")
@EnableWebMvc
public class ApplicationConfig {

  @Autowired ApplicationContext context;

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("WEB-INF/templates/");
    resolver.setSuffix(".html");
    return resolver;
  }

  @Bean
  ViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(engine());
    ViewResolverRegistry resolverRegistry = new ViewResolverRegistry(null, context);
    resolverRegistry.viewResolver(resolver);
    return resolver;
  }

  @Bean
  public SpringTemplateEngine engine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(templateResolver());
    return engine;
  }
}
