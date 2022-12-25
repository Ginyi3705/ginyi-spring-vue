package ginyi.system.service;

import ginyi.system.domain.SysDept;
import ginyi.system.domain.model.dto.DeptDto;
import ginyi.system.domain.model.vo.BaseVo;

/**
 * 系统部门
 */
public interface ISysDeptService {

    /**
     * 获取部门列表
     * @param deptDto
     * @param page
     * @param pageSize
     */
    public BaseVo<SysDept> list(DeptDto deptDto, Long page, Long pageSize);
}
