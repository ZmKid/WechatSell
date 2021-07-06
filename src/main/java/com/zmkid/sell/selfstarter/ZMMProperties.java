package com.zmkid.sell.selfstarter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/4
 */
@Data
@ConfigurationProperties(prefix = "spring.zmm")
public class ZMMProperties {

    private String name;

    private int age;

}
