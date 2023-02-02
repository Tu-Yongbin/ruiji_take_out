package com.itheima.ruiji.common;

/**
 * @author tyb
 * @version 1.0
 * @ClassName: BaseContext
 * @description:基于ThreadLocal封装的工具类，用户保存和获取当前登录用户的id
 * @date 2022/12/18 17:15
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
