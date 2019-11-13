package com.wxmlabs.hellogradle.springboot2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
public class AppTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void echo() throws Exception {
        mvc.perform(get("/").param("input", "hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello\r\n"));
    }

    @Test
    public void echoJsonCmd() throws Exception {
        String jsonCmd = "{\"cmd\":\"echo\",\"content\":\"世界，你好！\"}";
        mvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(jsonCmd))
                .andExpect(status().isOk())
                .andExpect(content().string("世界，你好！\r\n"));
    }
}
