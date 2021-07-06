package com.zmkid.sell.controller;

import com.zmkid.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/23
 */
@Controller
@RequestMapping("/weichat")
@Slf4j
public class WechatController {

    private WxMpService wxMpService;

    @Autowired
    public void setWxMpService(WxMpService wxMpService) {
        this.wxMpService = wxMpService;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) throws IOException {
        WxOAuth2Service wxOAuth2Service = wxMpService.getOAuth2Service();
        /*用户同意授权后重定向的URL*/
        String url = "http://zmsell.natapp1.cc/sell/weichat/userInfo";
        String redirectUrl = wxOAuth2Service.buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, returnUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code, @RequestParam("state") String state) {
        try{
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
            String openid = userInfo.getOpenid();
            boolean valid = wxMpService.getOAuth2Service().validateAccessToken(accessToken);
            log.info("【微信网页授权】 {}",userInfo);
            return "redirect:" + state + "?openid=" + openid;
        }catch (WxErrorException e){
            log.error("【微信网页授权】",e);
            throw new SellException(90000,e.getError().getErrorMsg());
        }
    }
}
