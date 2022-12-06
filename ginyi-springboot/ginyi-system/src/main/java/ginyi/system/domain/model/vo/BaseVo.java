package ginyi.system.domain.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseVo<T> {
    @ApiModelProperty("列表数据")
    private List<T> list;

    @ApiModelProperty("总条数")
    private Integer count;
}
