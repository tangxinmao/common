package com.socool.soft.common.util;

import com.google.common.base.Strings;
import com.socool.soft.exception.ErrorCode;
import com.socool.soft.exception.SystemException;

/**
 * 参数校验工具类
 */
public class CheckUtil {

    /**
     * 校验参数长度, 不符合则抛出异常
     * @param param 需要校验的参数
     * @param maxLength 最大长度
     * @param paramStr 提示字段
     * @throws SystemException
     */
    public static void tooLong(String param, int maxLength, String paramStr) throws SystemException {
        if (!Strings.isNullOrEmpty(param) && param.length() > maxLength) {
            throw new SystemException(ErrorCode.PARAMETER_ERROR, paramStr + " was too long.");
        }
    }

    /**
     * 校验参数是否为空, 是则抛出异常
     * @param param 需要校验的参数
     * @param paramStr 提示字段
     * @throws SystemException
     */
    public static void isBlank(String param, String paramStr) throws SystemException {
        if(Strings.isNullOrEmpty(param)) {
            throw new SystemException(ErrorCode.PARAMETER_ERROR, paramStr + " can't be empty.");
        }
    }

    public static void isBlank(Object param, String paramStr) throws SystemException {
        if(param == null) {
            throw new SystemException(ErrorCode.PARAMETER_ERROR, paramStr + " can't be empty.");
        }
    }
}
