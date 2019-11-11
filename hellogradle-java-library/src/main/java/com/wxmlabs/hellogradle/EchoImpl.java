package com.wxmlabs.hellogradle;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class EchoImpl implements Echo {
    private final Cache<String, String> cache;

    public EchoImpl() {
        cache = CacheBuilder.newBuilder().build();
    }

    @Override
    public String echo(String input) {
        String key = cacheKey(input);
        String output = cache.getIfPresent(key);
        if (output == null) {
            output = transfer(input);
            cache.put(key, output);
        }
        return output;
    }

    @Override
    public String echoJsonCmd(JSONObject jsonCmd) {
        if (jsonCmd != null && "echo".equals(jsonCmd.getString("cmd"))) {
            return echo(jsonCmd.getString("content"));
        }
        // exception
        String jsonCmdString = toString(jsonCmd);
        throw new IllegalArgumentException(jsonCmdString);
    }

    private String cacheKey(String input) {
        return toString(input.trim());
    }

    private String transfer(String input) {
        return toString(input.trim() + "\r\n");
    }

    private static String toString(Object obj) {
        return String.valueOf(obj == null ? "null" : obj.toString());
    }
}
