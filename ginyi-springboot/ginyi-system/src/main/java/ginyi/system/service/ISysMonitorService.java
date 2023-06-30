package ginyi.system.service;

import ginyi.system.domain.SysServer;
import ginyi.system.domain.model.dto.CacheDto;
import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.CacheKeyVo;
import ginyi.system.domain.model.vo.CacheVo;

/**
 * 系统服务
 */
public interface ISysMonitorService {

    /**
     * 获取系统服务信息，如 cpu、内存 等
     */
    public SysServer getServerInfo() throws InterruptedException;

    /**
     * 获取缓存列表
     *
     * @return
     */
    public BaseVo<CacheKeyVo> getCacheList();

    /**
     * 获取缓存详情
     * @return
     */
    public CacheVo getCacheDetails(CacheDto cacheDto);
}
