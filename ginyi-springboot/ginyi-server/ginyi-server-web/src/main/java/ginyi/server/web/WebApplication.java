package ginyi.server.web;

import ginyi.common.config.app.ApplicationInfo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import javax.sql.DataSource;

@Slf4j
// 扫描系统服务的包以及自身所在模块的包
@SpringBootApplication(scanBasePackages = {"ginyi.system", "ginyi.server.web"})
@MapperScan({"ginyi.system.**.mapper", "ginyi.server.web.**.mapper"})
public class WebApplication {

    private static DataSource dataSource;
    private static RedisTemplate redisTemplate;

    public static void main(String[] args) {
        try {
            ApplicationInfo.beforeApplication();
            ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
            if(context != null){
                log.info("正在进行服务连接...");
                dataSource = (DataSource) context.getBean("dataSource");
                redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
                ApplicationInfo.createMysqlConnection(dataSource);
                ApplicationInfo.createRedisConnection(redisTemplate);
                ApplicationInfo.afterApplication(context);

            }
        } catch (Exception e) {
            log.error("启动程序时发生异常 ===>>> " + e.getMessage());
        }
    }
}
