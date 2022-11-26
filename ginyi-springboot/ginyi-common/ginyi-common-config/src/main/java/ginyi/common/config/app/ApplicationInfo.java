package ginyi.common.config.app;

import ginyi.common.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.fusesource.jansi.Ansi;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
public class ApplicationInfo {

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
     * 启动成功之后，打印项目信息
     */
    public static void afterApplication(ConfigurableApplicationContext context) {
        ConfigurableEnvironment environment = context.getEnvironment();
        // 项目名称
        String projectFinalName = environment.getProperty("project-info.project-name");
        // 项目版本
        String projectVersion = environment.getProperty("project-info.project-version");
        // 项目路径
        String contextPath = environment.getProperty("server.servlet.context-path");
        // 项目端口
        String port = environment.getProperty("server.port");

        log.info("项目名称: {}", projectFinalName);
        log.info("项目版本: {}", projectVersion);
        log.info("项目路径: {}", contextPath);
        log.info("项目端口: {}", port);

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
        log.info("\n{}", Ansi.ansi().eraseScreen().fg(Ansi.Color.BLUE).a(startSuccess).reset().toString());

    }

}
