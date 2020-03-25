package org.buzevych.web.mvc.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Class is used to start Filter chain that will be used to secure pages according to the rules,
 * specified in {@link WebSecurityConfig} class
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {}
