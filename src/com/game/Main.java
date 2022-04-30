/*
ДОМАШНЕЕ ЗАДАНИЕ #11
------------------------------------
Создать игру Битва Воинов.
Абстрактный класс: персонаж.
Касты: варвары, лучники...
Класс Игра.
Простой вариант: два против двух.

*Интерфейс Целитель.
 */

package com.game;

public class Main {

    public static void main(String[] args) {
//        Command command = new Command("44");
//        String[] ss = command.getArgs();
//        for (String s: ss
//             ) {
//            System.out.println(s);
//        }

        Game game = new Game();
        game.go();
    }
}
