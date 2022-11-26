package ginyi.server.web.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试模块")
@RestController
@RequestMapping("/api")
@Slf4j
public class test1 {

    @GetMapping("/test1")
    public String test1(){
        return "hello world";
    }

}
