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

public class Main {

    public static void main(String[] args) {
        //чит код #НОМЕР_ЮНИТА убивает юнита сразу, напр. #2  - убить юнит 2
        Game game = new Game();
        game.go();
    }
}