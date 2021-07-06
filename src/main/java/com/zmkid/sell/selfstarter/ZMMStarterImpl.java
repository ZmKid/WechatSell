package com.zmkid.sell.selfstarter;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/4
 */
public class ZMMStarterImpl implements ZMMStarter{

    @Autowired
    ZMMProperties zmmProperties;

    @Override
    public void hello() {
        String name = zmmProperties.getName();
        System.out.println( name + "hello，zmm first starter");
    }
}
