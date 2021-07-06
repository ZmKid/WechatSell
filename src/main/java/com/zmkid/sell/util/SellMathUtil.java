package com.zmkid.sell.util;

/**
 * @author zhang man
 * @Description: 金额工具类
 * @date 2021/7/1
 */
public class SellMathUtil {

    public static boolean equals(double d1, double d2){
        double res = Math.abs(d1 - d2);
        double MONEY_RANGE = 0.01;
        return res < MONEY_RANGE;
    }
}
