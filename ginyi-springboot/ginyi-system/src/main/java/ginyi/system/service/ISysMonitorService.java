package ginyi.system.service;

import ginyi.system.domain.model.vo.BaseVo;
import ginyi.system.domain.model.vo.SessionUserVo;

import java.util.List;

public interface ISysMonitorService {

    /**
     * 当前在线用户列表
     */
    public BaseVo<SessionUserVo> getOnlineUserList(Long page, Long pageSize);
}
