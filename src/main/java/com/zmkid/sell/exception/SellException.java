package com.zmkid.sell.exception;

import com.zmkid.sell.enums.ResultEnum;

/**
 * @author zhang man
 * @Description: 异常
 * @date 2021/6/7
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException(Integer code, String massage){
        super(massage);
        this.code = code;
    }


}
