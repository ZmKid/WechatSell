package com.zmkid.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhang man
 * @Description:  日志框架测试类
 * @date 2021/5/30
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
@Slf4j
public class LogTest {

//    private final Logger logger = LoggerFactory.getLogger(LogTest2.class);

    @Test
    public void  test1(){
        String name= "李狗子";
        String password = "gansidan";
        log.debug("debug...");
        log.info("info...");
        log.error("error...");
        log.info("name: {},password: {}",name,password);

    }
}
