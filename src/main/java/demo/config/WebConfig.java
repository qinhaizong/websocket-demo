package demo.config;

import demo.controller.HelloController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by hasee on 2017/6/25.
 * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter}
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = HelloController.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/index.html");
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/WEB-INF/assets/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }
}
