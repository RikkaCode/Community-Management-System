package com.itmk.utils;

import com.itmk.status.StatusCode;

/**
 * 数据返回工具类
 */
public class ResultUtils {
    /**
     * 无参数返回
     * @return
     */
    public static ResultVo succcess(String s) {
        return Vo(null, StatusCode.SUCCESS_CODE, null);
    }
    public static ResultVo success(String msg){
        return Vo(msg,StatusCode.SUCCESS_CODE,null);
    }
    /**
     * 返回带参数
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo success(String msg,Object data){
        return Vo(msg,StatusCode.SUCCESS_CODE,data);
    }
    public static ResultVo success(String msg,int code,Object data){
        return Vo(msg,code,data);
    }
    public static ResultVo Vo(String msg, int code, Object data) {
        return new ResultVo(msg, code, data);
    }

    /**
     * 错误返回
     * @return
     */
    public static ResultVo error(){
        return Vo(null,StatusCode.ERROR_CODE,null);
    }
    public static ResultVo error(String msg){
        return Vo(msg,StatusCode.ERROR_CODE,null);
    }
    public static ResultVo error(String msg,int code,Object data){
        return Vo(msg,code,data);
    }
    public static ResultVo error(String msg,int code){
        return Vo(msg,code,null);
    }
    public static ResultVo error(String msg,Object data){
        return Vo(msg, StatusCode.ERROR_CODE,data);
    }
}