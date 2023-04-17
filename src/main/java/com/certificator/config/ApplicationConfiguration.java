package com.certificator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public TemplateEngine rawTemplateEngine() {
    ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
    classLoaderTemplateResolver.setCacheable(false);
    classLoaderTemplateResolver.setTemplateMode(TemplateMode.TEXT);

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(classLoaderTemplateResolver);

    return templateEngine;
  }
}