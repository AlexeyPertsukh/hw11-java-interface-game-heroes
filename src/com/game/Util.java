/*
Цвета: http://surl.li/mrnv
 */
package com.game;
public class Util {
    public static final int CODE_NOT_OK = -1;

    private Util(){
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //возвращает положительное число в строке-коменде или -1 если это не команда
    //например, команда +3 - вернет число 3

    public static int getIntFromCmdStr(String str, char key) {
        int num = CODE_NOT_OK;

        if(str.length() < 2) {
            return num;
        }

        if(str.charAt(0) != key) {
            return num;
        }

        str = str.substring(1);
        if(!Util.isInteger(str)) {
            return num;
        }
        return Integer.parseInt(str);
    }

    public static int random(int min, int max) {
        if(min > max) {
            int tmp = min;
            min = max;
            max = tmp;
        }
        return (int) (Math.random() * (max - min)) + min;
    }

    public static int random(int max) {
        return random(0, max);
    }
}
