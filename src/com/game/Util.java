/*
Цвета: http://surl.li/mrnv
 */
package com.game;

import java.util.Scanner;

public class Util {
    public static final int CODE_RESULT_NOT_OK = -1;

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

    //возвращает положительное число в строке-команде или -1 если это не команда
    //например, команда (str = "+3", key ='+') вернет число 3,
    //например, команда (str = "+3", key ='@') вернет число -1, потому что '@' не входит в "+3"

    public static int getIntFromCommandStr(String str, char key) {

        if(str.length() < 2) {
            return CODE_RESULT_NOT_OK;
        }

        if(str.charAt(0) != key) {
            return CODE_RESULT_NOT_OK;
        }

        str = str.substring(1);
        if(!Util.isInteger(str)) {
            return CODE_RESULT_NOT_OK;
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

    public static void pressEnterForContinue() {
        System.out.println("...");
        System.out.print("для продолжения нажмите <enter>");
        //не выносить инициализацию этого сканера отсюда, не передавать на вход метода!
        //постоянно пересоздаем сканнер в этом методе из-за глюков при переводе фокуса ввода(курсора) из консоли в код и обратно
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

}
