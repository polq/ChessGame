package org.buzevych.web.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("org.buzevych.web.mvc")
@PropertySource("classpath:games.properties")
@EnableWebMvc
public class ApplicationConfig {

  @Autowired ApplicationContext context;

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix("WEB-INF/templates/");
    resolver.setSuffix(".html");
    resolver.setCharacterEncoding("UTF-8");
    return resolver;
  }

  @Bean
  ViewResolver viewResolver() {
    ThymeleafViewResolver resolver = new ThymeleafViewResolver();
    resolver.setTemplateEngine(engine());
    resolver.setCharacterEncoding("UTF-8");
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

  @Bean
  public MultipartResolver multipartResolver() {
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    commonsMultipartResolver.setMaxUploadSize(100000);
    commonsMultipartResolver.setDefaultEncoding("UTF-8");
    return commonsMultipartResolver;
  }
}
