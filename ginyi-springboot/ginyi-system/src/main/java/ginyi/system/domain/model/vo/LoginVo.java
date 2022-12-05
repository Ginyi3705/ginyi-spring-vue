package ginyi.system.domain.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
@ApiModel("登录返回数据")
public class LoginVo {

    @ApiModelProperty(value = "Token令牌", required = true)
    private String token;

    @Value("${token.header}")
    @ApiModelProperty(value = "Token请求头Key", required = true)
    private String tokenHeader;

    @Value("${token.prefix}")
    @ApiModelProperty(value = "Token令牌前缀", required = true)
    private String tokenPrefix;
}
