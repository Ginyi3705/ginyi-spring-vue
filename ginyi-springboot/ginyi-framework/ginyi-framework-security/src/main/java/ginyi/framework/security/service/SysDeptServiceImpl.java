package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.mysql.MyPage;
import ginyi.system.domain.SysDept;
import ginyi.system.domain.model.dto.DeptDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.mapper.SysDeptMapper;
import ginyi.system.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Resource
    private SysDeptMapper deptMapper;

    /**
     * 获取部门列表
     *
     * @param deptDto
     * @param page
     * @param pageSize
     */
    @Override
    public BaseVo<SysDept> list(DeptDto deptDto, Long page, Long pageSize) {
        IPage<SysDept> list = deptMapper.list(deptDto, new MyPage(page, pageSize).getPage());
        // 转成树
        List<SysDept> deptList = list.getRecords().stream()
                .filter(dept -> dept.getParentId().equals(0L))
                .map(dept -> convertToDeptTree(dept, list.getRecords())).collect(Collectors.toList());
        BaseVo<SysDept> baseVo = new BaseVo<>();
        baseVo.setList(deptList);
        baseVo.setCount(deptList.size());
        return baseVo;
    }

    /**
     * 部门列表转换成部门树的结构
     *
     * @param dept
     * @param list
     * @return
     */
    public SysDept convertToDeptTree(SysDept dept, List<SysDept> list) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(dept, sysDept);
        List<SysDept> children = list.stream()
                .filter(subDept -> dept.getDeptId().equals(subDept.getParentId()))
                .map(subDept -> convertToDeptTree(subDept, list)).collect(Collectors.toList());
       sysDept.setChildren(children);
        return sysDept;
    }
}
