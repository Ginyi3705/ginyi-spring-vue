package ginyi.server.web;

import ginyi.common.config.app.ApplicationInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        ApplicationInfo.beforeApplication();
        ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
        ApplicationInfo.afterApplication(context);
    }
}
