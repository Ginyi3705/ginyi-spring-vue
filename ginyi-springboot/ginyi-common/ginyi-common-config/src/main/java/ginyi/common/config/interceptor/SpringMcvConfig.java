package ginyi.common.config.interceptor;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMvc配置类
 */
@Configuration
public class SpringMcvConfig implements WebMvcConfigurer {

    @Autowired
    private WebInterceptor webSignatureInterceptor;

    @Autowired
    private UploadInterceptor uploadInterceptor;

    @Value("${interceptor.resources.exclude}")
    private String[] resourcesExclude;

    @Value("${interceptor.api.exclude}")
    private String[] apiExclude;

    @Value("${interceptor.file}")
    private String[] fileInclude;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * web拦截器
         */
        registry.addInterceptor(webSignatureInterceptor)
                /* 对所有请求路径都进行拦截 */
                .addPathPatterns("/**")
                /* 以下请求路径发起请求时，不拦截，给予通行 */
                .excludePathPatterns(ArrayUtil.addAll(resourcesExclude, apiExclude, fileInclude));

        /**
         * 上传下载拦截器
         */
        registry.addInterceptor(uploadInterceptor).addPathPatterns(fileInclude);

    }
}
