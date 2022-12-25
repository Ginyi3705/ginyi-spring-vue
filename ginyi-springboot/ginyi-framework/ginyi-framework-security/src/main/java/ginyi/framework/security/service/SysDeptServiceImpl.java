package ginyi.framework.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import ginyi.common.constant.CacheConstants;
import ginyi.common.constant.MessageConstants;
import ginyi.common.exception.CommonException;
import ginyi.common.mysql.MyPage;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.system.domain.SysDept;
import ginyi.system.domain.model.dto.DeptDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.DeptVo;
import ginyi.system.mapper.SysDeptMapper;
import ginyi.system.service.ISysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysDeptServiceImpl implements ISysDeptService {

    @Resource
    private SysDeptMapper deptMapper;
    @Resource
    private RedisCache redisCache;

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
     * 根据部门id获取部门详情
     *
     * @param deptId
     * @return
     */
    @Override
    public DeptVo getDeptByDeptId(Long deptId) {
        // 检查缓存中是否存在空id
        if (redisCache.hasKey(CacheConstants.DEPT_NOT_EXIST_KEY + deptId)) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, deptId + MessageConstants.DEPT_NOT_EXIST);
        }
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getDeptId, deptId);
        SysDept dept = deptMapper.selectOne(queryWrapper);
        if (StringUtils.isNull(dept)) {
            redisCache.setCacheObject(CacheConstants.DEPT_NOT_EXIST_KEY + deptId, null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, deptId + MessageConstants.DEPT_NOT_EXIST);
        }
        redisCache.setCacheObject(CacheConstants.DEPT_DETAILS_BY_DEPTID_KEY + deptId, dept);
        DeptVo deptVo = new DeptVo();
        BeanUtils.copyProperties(dept, deptVo);
        return deptVo;
    }

    /**
     * 新增部门
     *
     * @param deptDto
     */
    @Override
    public void addDept(DeptDto deptDto) {
        Long parentId = StringUtils.isNull(deptDto.getParentId()) ? 0L : deptDto.getParentId();
        // 检查缓存中是否有标记同个分支下名称有被使用
        if (redisCache.hasKey(CacheConstants.DEPT_NAME_USED_KEY + parentId + deptDto.getDeptName())) {
            throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.DEPT_NAME_USED);
        }
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, parentId)
                .eq(SysDept::getDeptName, deptDto.getDeptName());
        SysDept dept = deptMapper.selectOne(queryWrapper);
        // 检查同个分支下名称是否被使用
        if (StringUtils.isNotNull(dept)) {
            redisCache.setCacheObject(CacheConstants.DEPT_NAME_USED_KEY + parentId + deptDto.getDeptName(), null);
            throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.DEPT_NAME_USED);
        }
        deptMapper.insertDept(deptDto);
        redisCache.removeCacheObject(CacheConstants.DEPT_KEY_PREFIX);
    }

    /**
     * 更新部门
     *
     * @param deptDto
     */
    @Override
    public void updateDept(DeptDto deptDto) {
        // 检查缓存中是否有标记空id
        if (redisCache.hasKey(CacheConstants.DEPT_NOT_EXIST_KEY + deptDto.getDeptId())) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, deptDto.getDeptId() + MessageConstants.DEPT_NOT_EXIST);
        }

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        Long parentId = StringUtils.isNull(deptDto.getParentId()) ? 0L : deptDto.getParentId();
        queryWrapper.eq(parentId != 0L, SysDept::getDeptId, parentId);
        List<SysDept> deptList = deptMapper.selectList(queryWrapper);
        // 检查上级部门是否存在
        if (deptList.size() == 0) {
            throw new CommonException(StateCode.ERROR_NOT_EXIST, MessageConstants.DEPT_PARENT_NOT_EXIST);
        }

        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getParentId, parentId)
                .eq(SysDept::getDeptName, deptDto.getDeptName());
        SysDept dept = deptMapper.selectOne(queryWrapper);
        // 检查同个分支下名称是否被使用
        if (StringUtils.isNotNull(dept)) {
            redisCache.setCacheObject(CacheConstants.DEPT_NAME_USED_KEY + parentId + deptDto.getDeptName(), null);
            throw new CommonException(StateCode.ERROR_EXIST, MessageConstants.DEPT_NAME_USED);
        }

        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getDeptId, deptDto.getDeptId());
        dept = deptMapper.selectOne(queryWrapper);
        // 检查是否存在
        if (StringUtils.isNull(dept)) {
            redisCache.setCacheObject(CacheConstants.DEPT_NOT_EXIST_KEY + deptDto.getDeptId(), null);
            throw new CommonException(StateCode.ERROR_NOT_EXIST, deptDto.getDeptId() + MessageConstants.DEPT_NOT_EXIST);
        }
        deptMapper.updateDept(deptDto);
        redisCache.removeCacheObject(CacheConstants.DEPT_KEY_PREFIX);
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
