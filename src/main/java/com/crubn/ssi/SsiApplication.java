package com.crubn.ssi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Configuration
//@Profile("production")
public class SsiApplication extends SpringBootServletInitializer {

//        private static ApplicationContext applicationContext;

        public static void main(String[] args) {

//            applicationContext =
//                    new AnnotationConfigApplicationContext(SsiApplication.class);
//
//            for (String beanName : applicationContext.getBeanDefinitionNames()) {
//                System.out.println(beanName);
//            }
            SpringApplication.run(SsiApplication.class, args);


        }
        @Override
        protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
            return application.sources(SsiApplication.class);
        }
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**");
                }
            };
        }

//    public static void main(String[] args) {
//        SpringApplication.run(SsiApplication.class, args);
//    }

}
