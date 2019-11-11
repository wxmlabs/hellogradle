package com.wxmlabs.hellogradle.springboot2;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class EchoController {

    @RequestMapping
    public String echo(@RequestParam(value = "input",required = false) String inputParam, @RequestBody(required = false) String requestBody) {
        return inputParam;
    }
}
