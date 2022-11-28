package ginyi.server.system.dto.params;

import ginyi.server.system.entity.SysUser;

public class SysUserParams extends SysUser {

    /**
     * 起始索引
     */
    private Integer pageNum;

    /**
     * 每页的条数
     */
    private Integer pageSize;

}
