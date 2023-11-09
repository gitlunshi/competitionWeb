package com.henu.competition.common.util;

import java.util.regex.Pattern;

public class StringTools {
    //特殊字符
    public static final String SPEC_CHARACTERS = " !\"#$%&'()*+,-./:;<=>?@\\]\\[^_`{|}~";
    // 纯字母
    public static final String character = "[a-zA-Z]{1,}$";
    // 纯数字
    public static final String numberic = "[0-9]{1,}$";
    // 字母和数字
    public static final String number_and_character = "((^[a-zA-Z]{1,}[0-9]{1,}[a-zA-Z0-9]*)+)" +
            "|((^[0-9]{1,}[a-zA-Z]{1,}[a-zA-Z0-9]*)+)$";
    // 字母或数字
    public static final String number_or_character = "[a-zA-Z0-9]+$";
    // 字母数字下划线
    public static final String ncw = "\\w+$";

    //密码最短
    public static final int minL = 8;
    //密码最长
    public static final int maxL = 20;
    public static boolean checkPassword(String targetString) {
        if (targetString.length()<minL||targetString.length()>maxL){
            return false;
        }
        String opStr = targetString;
        boolean isLegal = false;
        boolean hasSpecChar = false;
        char[] charArray = opStr.toCharArray();
        for (char c : charArray) {
            if (SPEC_CHARACTERS.contains(String.valueOf(c))) {
                hasSpecChar = true;
                opStr = opStr.replace(c, ' ');
            }
        }
        String excSpecCharStr = opStr.replace(" ", "");
        boolean isPureNum = Pattern.compile(numberic).matcher(excSpecCharStr).matches();
        boolean isPureChar = Pattern.compile(character).matcher(excSpecCharStr).matches();
        boolean isNumAndChar = Pattern.compile(number_and_character).matcher(excSpecCharStr).matches();
        isLegal = ((isPureNum && hasSpecChar)
                || (isPureChar && hasSpecChar) || isNumAndChar && hasSpecChar) || isNumAndChar;
        return isLegal;
    }
    public static byte[] intToByteArrayBigEndian(int x) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (x >> 24);
        bytes[1] = (byte) (x >> 16);
        bytes[2] = (byte) (x >> 8);
        bytes[3] = (byte) x;
        return bytes;
    }


}
