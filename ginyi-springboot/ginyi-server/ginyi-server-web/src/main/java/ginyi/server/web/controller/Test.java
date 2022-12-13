package ginyi.server.web.controller;


import ginyi.common.result.CommonResult;
import ginyi.framework.core.annotation.Anonymous;
import ginyi.framework.websocket.WebSocket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
public class Test {

    @Resource
    private WebSocket webSocket;

    @Anonymous
    @GetMapping("/test/{userId}")
    public CommonResult test(@PathVariable(value = "userId") String userId) {
        webSocket.sendOneMessage(userId, String.valueOf(new Date().getTime()));
        return CommonResult.success(userId);
    }


}
