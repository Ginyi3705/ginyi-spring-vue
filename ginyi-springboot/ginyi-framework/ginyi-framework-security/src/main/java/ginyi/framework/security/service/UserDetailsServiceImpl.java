package ginyi.framework.security.service;

import ginyi.common.exception.BusinessException;
import ginyi.common.result.MessageConstants;
import ginyi.common.result.StateCode;
import ginyi.common.utils.StringUtils;
import ginyi.common.utils.enums.UserStatus;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.LoginUser;
import ginyi.system.service.ISysPasswordService;
import ginyi.system.service.ISysPermissionService;
import ginyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysPasswordService passwordService;

    @Autowired
    private ISysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            // 不存在
            throw new BusinessException(StateCode.ERROR_UNAUTHENTICATION, MessageConstants.USER_NOT_EXIST);
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            // 被删除
            throw new BusinessException(StateCode.ERROR_UNAUTHENTICATION, MessageConstants.USER_IS_DELETED);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            // 被停用
            throw new BusinessException(StateCode.ERROR_UNAUTHENTICATION, MessageConstants.USER_IS_FORBIDDEN);
        }

        passwordService.validate(user);

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
