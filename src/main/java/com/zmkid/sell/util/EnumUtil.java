package com.zmkid.sell.util;

import com.zmkid.sell.enums.CodeEnum;
import java.util.Objects;

/**
 * @author zhang man
 * @Description:
 * @date 2021/7/7
 */
public class EnumUtil {
    public static <T extends CodeEnum> T  getCode(int code, Class<T> clazz){
        for ( T one:clazz.getEnumConstants() ) {
            if(Objects.equals(code, one.getCode())){
                return one;
            }
        }
        return null;
    }
}
