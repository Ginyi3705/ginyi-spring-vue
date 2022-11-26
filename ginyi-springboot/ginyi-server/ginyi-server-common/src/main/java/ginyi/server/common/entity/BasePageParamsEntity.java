package ginyi.server.common.entity;

import lombok.Data;

@Data
public class BasePageParamsEntity {

    /** 起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;
}
