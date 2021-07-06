package com.zmkid.sell.vo;

import com.zmkid.sell.dto.OrderDTO;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/6
 */
@Data
public class ResultVO<T> {
    private static final long serialVersionUID = 3068837394742385883L;

    /*错误码*/
    private Integer code;

    /*提示信息*/
    private String msg;

    /*返回的具体内容*/
    private T data;


}
