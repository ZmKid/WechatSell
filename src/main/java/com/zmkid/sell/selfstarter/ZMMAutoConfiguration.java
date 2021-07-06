package com.zmkid.sell.selfstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/4
 */
@Configuration
@ConditionalOnClass(ZMMStarter.class)
@EnableConfigurationProperties(ZMMProperties.class)
public class ZMMAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ZMMStarter zmmStarter() {
        return new ZMMStarterImpl();
    }
}
