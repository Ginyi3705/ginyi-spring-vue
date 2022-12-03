package ginyi.framework.security.service;

import ginyi.common.exception.BusinessException;
import ginyi.common.exception.UserPasswordNotMatchException;
import ginyi.common.exception.UserPasswordRetryLimitExceedException;
import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.MessageConstants;
import ginyi.common.result.StateCode;
import ginyi.common.utils.DateUtils;
import ginyi.common.utils.ServletUtils;
import ginyi.common.utils.constants.Constants;
import ginyi.common.utils.constants.UserConstants;
import ginyi.common.utils.ip.IpUtils;
import ginyi.framework.security.context.AuthenticationContextHolder;
import ginyi.framework.security.manager.AsyncManager;
import ginyi.framework.security.manager.factory.AsyncFactory;
import ginyi.framework.security.utils.SecurityUtils;
import ginyi.system.domain.LoginUser;
import ginyi.system.domain.SysUser;
import ginyi.system.domain.model.dto.LoginDto;
import ginyi.system.domain.model.dto.RegisterDto;
import ginyi.system.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
     * @param loginDto
     * @return
     */
    @Override
    public String login(LoginDto loginDto) {

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        String code = loginDto.getCode();

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

            // 登录成功
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageConstants.LOGIN_SUCCESS));
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            recordLoginInfo(loginUser.getUserId());
            // 生成token
            return tokenService.createToken(loginUser);
        } catch (Exception e) {
            if (e instanceof AuthenticationException) {
                // 账户被锁定
                if (e.getCause() instanceof UserPasswordRetryLimitExceedException) {
                    UserPasswordRetryLimitExceedException userPasswordRetryLimitExceedException = (UserPasswordRetryLimitExceedException) e.getCause();
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageConstants.USER_IS_LOCKED));
                    throw new UserPasswordRetryLimitExceedException(
                            userPasswordRetryLimitExceedException.getState(),
                            userPasswordRetryLimitExceedException.getData()
                    );
                }
                // 密码不匹配
                if (e.getCause() instanceof UserPasswordNotMatchException) {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageConstants.USER_PASSWORD_NOT_MATCH));
                    throw new UserPasswordNotMatchException();
                }
            } else {
                // 其他的未知异常
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new BusinessException(StateCode.ERROR_SYSTEM);
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        // 其他的未知异常
        throw new BusinessException(StateCode.ERROR_SYSTEM, MessageConstants.SYS_ERROR);
    }


    /**
     * 用户注册
     *
     * @param registerDto
     */
    @Override
    public void register(RegisterDto registerDto) {
        // 图片验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            verifyService.checkImgCode(registerDto.getCode());
        }

        SysUser sysUser = new SysUser();
        sysUser.setUserName(registerDto.getUsername());

        // 当前注册的用户是否存在
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(sysUser))) {
            throw new BusinessException(StateCode.ERROR_EXIST, MessageConstants.USER_EXIST);
        } else {
            sysUser.setNickName(registerDto.getUsername());
            sysUser.setPassword(SecurityUtils.encryptPassword(registerDto.getPassword()));
            boolean regFlag = userService.registerUser(sysUser);
            if (!regFlag) {
                throw new BusinessException(StateCode.ERROR_SYSTEM, MessageConstants.SYS_ERROR);
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(registerDto.getUsername(), Constants.REGISTER, MessageConstants.REGISTER_SUCCESS));
            }
        }
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
