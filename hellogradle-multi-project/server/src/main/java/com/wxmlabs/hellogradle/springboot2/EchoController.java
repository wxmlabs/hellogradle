package com.wxmlabs.hellogradle.springboot2;

import com.alibaba.fastjson.JSONObject;
import com.wxmlabs.hellogradle.Echo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/")
public class EchoController {
    private final Echo echo;

    public EchoController(Echo echo) {
        this.echo = echo;
    }

    @RequestMapping
    public String echo(@RequestParam(value = "input", required = false) String input, @RequestBody(required = false) Map<String, Object> jsonCmd) {
        if (input != null) {
            return echo.echo(input);
        } else {
            return echo.echoJsonCmd(new JSONObject(jsonCmd));
        }
    }
}
