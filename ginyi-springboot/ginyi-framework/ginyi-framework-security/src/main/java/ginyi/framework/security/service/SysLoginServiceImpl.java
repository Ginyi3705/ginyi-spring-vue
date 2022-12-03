package ginyi.framework.security.service;

import ginyi.common.exception.BusinessException;
import ginyi.common.exception.UserPasswordNotMatchException;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.DateUtils;
import ginyi.common.utils.MessageUtils;
import ginyi.common.utils.ServletUtils;
import ginyi.common.utils.constants.Constants;
import ginyi.common.utils.ip.IpUtils;
import ginyi.framework.security.context.AuthenticationContextHolder;
import ginyi.framework.security.manager.AsyncManager;
import ginyi.framework.security.manager.factory.AsyncFactory;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.LoginUser;
import ginyi.system.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLoginServiceImpl implements ISysLoginService {

    @Resource
    private ISysConfigService configService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private ISysUserService userService;

    @Resource
    private ITokenService tokenService;

    @Resource
    private IVerifyService verifyService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @return 结果
     */
    @Override
    public String login(String username, String password, String code) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 图片验证码开关
        if (captchaEnabled) {
            verifyService.checkImgCode(code);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new BusinessException(StateCode.ERROR_SYSTEM);
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
