package ginyi.system.mapper;

import ginyi.system.domain.SysConfig;

public interface SysConfigMapper {

    /**
     * 查询参数配置信息
     *
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public SysConfig selectConfig(SysConfig config);
}
