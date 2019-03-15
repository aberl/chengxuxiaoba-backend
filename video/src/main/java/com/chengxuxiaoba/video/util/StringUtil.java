package com.chengxuxiaoba.video.util;

import java.util.Random;

public class StringUtil {
    private static String numberConstant = "0123456789";
    private static String letterConstant = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 判断是否是empty或null
     * @param str
     * @return
     */
    public static Boolean isNullOrEmpty(String str) {
        if (str == null)
            return true;

        if (str.equals(""))
            return true;

        return false;
    }

    /**
     * 随机生成指定位数的数字字符串
     * @param size
     * @return
     */
    public static String randomGenerateNumberStr(int size) {
        String code = randomGenerateStr(numberConstant, size);

        return code;
    }

    /**
     * 随机生成指定位数的字母字符串
     * @param size
     * @return
     */
    public static String randomGenerateLetterStr(int size) {
        String code = randomGenerateStr(letterConstant, size);
        return code;
    }

    private  static  String randomGenerateStr(String referenceStr, int size)
    {
        if(StringUtil.isNullOrEmpty(referenceStr))
            return  null;

        if(referenceStr.length() < size)
            return  referenceStr;

        String code = "";
        char ch;
        for (int i = 0; i < size; i++) {
            ch = referenceStr.charAt(new Random().nextInt(referenceStr.length()));
            code += ch;
        }
        return code;
    }
}
