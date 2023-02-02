package com.itheima.ruiji.common;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: CustomException
 * @description:
 * @date 2022/12/18 19:36
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
