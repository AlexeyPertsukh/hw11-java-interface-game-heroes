/*
Цвета: http://surl.li/mrnv
 */
package com.game;

import java.util.Scanner;

public class Util {

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

    //Возвращает пустую строку того же размера
    //in: '9 symbols', out: '         '
    public static String spacedString(String string) {
        String out = "%" + (string.length() + 1) + "s";
        out = String.format(out, "");
        return out;
    }

}
