package com.zmkid.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhang man
 * @Description: 微信
 * @date 2021/6/23
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXintController {

    @GetMapping("/auth")
    public void  auth(@RequestParam("code") String code){
        log.info("进入auth方法");
        log.info("code={}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxac32ee48d2a93049&secret=3299ae440d3f36256fc17b4a61537375&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        log.info("response={}",forObject);
    }


}
