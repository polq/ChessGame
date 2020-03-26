package org.buzevych.web.rest.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Class that inherits from a Dispatcher Servlet abstraction and defines mapping it will be
 * responsible for, as well as the root config class.
 */
public class RestWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class[] {RestSecurityConfig.class};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class[0];
  }

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/rest/*"};
  }

  @Override
  protected String getServletName() {
    return "restInitializer2";
  }
}
