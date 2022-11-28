package ginyi.common.config.app;

import ginyi.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
public class ApplicationInfo {

    private static boolean mysqlConnection = false;
    private static boolean redisConnection = false;

    /**
     * 彩色打印字体
     */
    public static String colorPrint(String s, Ansi.Color color) {
        return Ansi.ansi().eraseScreen().fg(color).a(s).reset().toString();
    }

    /**
     * 执行之前，打印前置条件提示
     */
    public static void beforeApplication() {
        String tip = "======================================================================================\n" +
                "                               !!!准备工作!!!                                      \n" +
                " 1.请先启动Redis服务                        \n" +
                " 2.请先启动MySQL服务                        \n" +
                "                                                                                  \n" +
                "======================================================================================";
        log.info("\n{}", Ansi.ansi().eraseScreen().fg(Ansi.Color.YELLOW).a(tip).reset().toString());
    }

    /**
     * 创建数据库连接
     */
    public static void createMysqlConnection(DataSource dataSource) throws SQLException {
        mysqlConnection = dataSource.getConnection() != null;
    }


    /**
     * 创建redis连接
     */
    public static void createRedisConnection(RedisTemplate redisTemplate) {
        redisConnection = redisTemplate.execute(RedisConnectionCommands::ping) != null;
    }


    /**
     * 启动成功之后，打印项目信息
     */
    public static void afterApplication(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        // 项目名称
        String projectFinalName = environment.getProperty("project.project-name");
        // 项目版本
        String projectVersion = environment.getProperty("project.project-version");
        // 项目路径
        String contextPath = environment.getProperty("server.servlet.context-path");
        // 项目端口
        String port = environment.getProperty("server.port");

        log.info("项目路径: {}", contextPath);
        log.info("项目端口: {}", port);
        log.info("项目名称: {}", projectFinalName);
        log.info("项目版本: {}", projectVersion);
        log.info("服务状态: 数据库{} Redis{}",
                colorPrint(mysqlConnection ? " ● 已连接" : " ✗ 未连接", mysqlConnection ? Ansi.Color.GREEN : Ansi.Color.RED),
                colorPrint(redisConnection ? " ● 已连接" : " ✗ 未连接", redisConnection ? Ansi.Color.GREEN : Ansi.Color.RED));

        String startSuccess = "   _____   _______              _____    _______      _____   _    _    _____    _____   ______    _____    _____ \n" +
                "  / ____| |__   __|     /\\     |  __ \\  |__   __|    / ____| | |  | |  / ____|  / ____| |  ____|  / ____|  / ____|\n" +
                " | (___      | |       /  \\    | |__) |    | |      | (___   | |  | | | |      | |      | |__    | (___   | (___  \n" +
                "  \\___ \\     | |      / /\\ \\   |  _  /     | |       \\___ \\  | |  | | | |      | |      |  __|    \\___ \\   \\___ \\ \n" +
                "  ____) |    | |     / ____ \\  | | \\ \\     | |       ____) | | |__| | | |____  | |____  | |____   ____) |  ____) |\n" +
                " |_____/     |_|    /_/    \\_\\ |_|  \\_\\    |_|      |_____/   \\____/   \\_____|  \\_____| |______| |_____/  |_____/ 启动成功...\n" +
                "                                                                                                                  ";

        String homeUrl = "http://" + IpUtil.getLocalhostIp() + ":" + port + contextPath;
        String swaggerUrl = homeUrl + "swagger-ui.html";
        String knife4jUrl = homeUrl + "doc.html";
        log.info("服务地址: {}", homeUrl);
        log.info("Swagger接口文档: {}", swaggerUrl);
        log.info("knife4j接口文档: {}", knife4jUrl);
        log.info("\n{}", colorPrint(startSuccess, Ansi.Color.BLUE));

    }

}
