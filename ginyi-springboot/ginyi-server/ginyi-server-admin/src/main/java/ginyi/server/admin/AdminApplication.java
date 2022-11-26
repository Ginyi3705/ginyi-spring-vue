package ginyi.server.admin;


import ginyi.common.config.app.ApplicationInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        ApplicationInfo.beforeApplication();
        ConfigurableApplicationContext context = SpringApplication.run(AdminApplication.class, args);
        ApplicationInfo.afterApplication(context);
    }
}
