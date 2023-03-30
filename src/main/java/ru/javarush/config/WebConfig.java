package ru.javarush.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("ru.javarush")
public class WebConfig implements WebMvcConfigurer {
    private final ApplicationContext context;

    public WebConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public SpringResourceTemplateResolver springResourceTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(context);
        resolver.setPrefix("/html/");
        resolver.setSuffix(".html");
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(springResourceTemplateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tasks");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/html/**").addResourceLocations("/html/");
        registry.addResourceHandler("/style/**").addResourceLocations("/style/");
        registry.addResourceHandler("/script/**").addResourceLocations("/script/");
    }
}
