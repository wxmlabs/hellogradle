package com.wxmlabs.hellogradle;

import com.alibaba.fastjson.JSONObject;

public interface Echo {
    String echo(String input);

    String echoJsonCmd(JSONObject jsonCmd);
}
