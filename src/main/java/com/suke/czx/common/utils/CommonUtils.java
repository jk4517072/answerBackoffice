package com.suke.czx.common.utils;

import org.apache.commons.lang3.StringUtils;

public class CommonUtils {

    /**
     * 判断空值
     * @param value
     * @return
     */
    public static boolean isNull(String value){
        if(StringUtils.isBlank(value) || "null".equals(value)){
            return true;
        }
        return false;
    }
}
