package ginyi.framework.security.service;

import ginyi.common.redis.cache.RedisCache;
import ginyi.common.result.StateCode;
import ginyi.common.utils.MessageUtils;
import ginyi.common.utils.constants.Constants;
import ginyi.common.result.CacheConstants;
import ginyi.framework.security.context.AuthenticationContextHolder;
import ginyi.common.exception.UserPasswordNotMatchException;
import ginyi.common.exception.UserPasswordRetryLimitExceedException;
import ginyi.framework.security.manager.AsyncManager;
import ginyi.framework.security.manager.factory.AsyncFactory;
import ginyi.framework.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import ginyi.system.domain.SysUser;
import ginyi.system.service.ISysPasswordService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SysPasswordServiceImpl implements ISysPasswordService {

    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;


    public void validate(SysUser user) {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.password.retry.limit.exceed", maxRetryCount, lockTime)));
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("密码输入错误").append(maxRetryCount).append("次，帐号将被系统锁定").append(lockTime).append("分钟");
            throw new UserPasswordRetryLimitExceedException(StateCode.ERROR_UNAUTHENTICATION, errorMessage);
        }

        if (!matches(user, password)) {
            retryCount = retryCount + 1;
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.count", retryCount)));
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        } else {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword) {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username) {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void clearLoginRecordCache(String loginName) {
        if (redisCache.hasKey(getCacheKey(loginName))) {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}
