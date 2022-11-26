package ginyi.server.admin.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@EnableSwagger2
@Configuration
public class Swagger2Config {

    /**
     * 标题
     */
    @Value("${swagger.title}")
    private String title;

    /**
     * web端 - 基本包
     */
    @Value("${swagger.base.package}")
    private String basePackage;


    /**
     * 描述
     */
    @Value("${swagger.description}")
    private String description;

    /**
     * URL
     */
    @Value("${swagger.url}")
    private String url;

    /**
     * 作者
     */
    @Value("${swagger.contact.name}")
    private String contactName;


    /**
     * 作者邮箱
     */
    @Value("${swagger.contact.email}")
    private String contactEmail;

    /**
     * 版本
     */
    @Value("${swagger.version}")
    private String version;

    @Bean
    public Docket createAdminApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin")
                .apiInfo(apiInfo())
                .select()
                .apis(basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    // 构建 api 文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(url)
                .contact(new Contact(contactName, null, contactEmail))
                .version(version)
                .build();
    }
}
