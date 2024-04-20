package com.liangshou.llmsrefactor.common;

/**
 * 返回工具类
 * @author X-L-S
 */
public class ResultUtils {
    /**
     * 成功
     *
     * @param data 数据
     * @param <T> 泛型——指定具体数据类型
     * @return 通用返回对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 通用返回对象
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code code
     * @param message msg
     * @return 通用返回对象
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 通用返回对象
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}
