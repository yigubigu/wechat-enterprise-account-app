package com.aug.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aug.handler.MsgHandler;
import com.aug.handler.LogHandler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;

/**
 * wechat cp configuration
 *
 * @author lory hou
 */
@Configuration
@ConditionalOnClass(WxCpService.class)
@EnableConfigurationProperties(WechatCpProperties.class)
public class WechatCpConfiguration {
    @Autowired
    private WechatCpProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxCpConfigStorage configStorage() {
        WxCpInMemoryConfigStorage configStorage = new WxCpInMemoryConfigStorage();
        configStorage.setCorpId(this.properties.getCorpId());
        configStorage.setCorpSecret(this.properties.getCorpSecret());
        configStorage.setAgentId(this.properties.getAgentId());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpService wxMpService(WxCpConfigStorage configStorage) {
        WxCpService wxMpService = new WxCpServiceImpl();
        wxMpService.setWxCpConfigStorage(configStorage);
        return wxMpService;
    }

    @Bean 
    public WxCpMessageRouter router(WxCpService wxCpService) {
        final WxCpMessageRouter newRouter = new WxCpMessageRouter(wxCpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();


        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
    
    @Autowired
    protected LogHandler logHandler;

    @Autowired
    protected MsgHandler msgHandler;
    

    
    

}
