package ginyi.system.service;

import ginyi.system.domain.SysDept;
import ginyi.system.domain.model.dto.DeptDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.DeptVo;

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

    /**
     * 根据部门id获取部门详情
     * @param deptId
     * @return
     */
    public DeptVo getDeptByDeptId(Long deptId);
}
