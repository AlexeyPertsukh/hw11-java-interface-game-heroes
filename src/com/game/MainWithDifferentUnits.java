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

import com.units.*;

public class MainWithDifferentUnits {

    private static final String NAME_PLAYER1 = "Карл IV Великолепный";
    private static final String NAME_PLAYER2 = "Барон Свиное Рыло";

    public static void main(String[] args) {
        Player player1 = new Player(NAME_PLAYER1, createUnits());
        Player player2 = new Player(NAME_PLAYER2, createUnits());
        player2.addUnit(new Archer());
        player2.addUnit(new Dangler());

        Game game = new Game(player1, player2);
        game.go();
    }

    private static Unit[] createUnits() {
        return new Unit[]{new Tower(),
                new Knight(),
                new Archer(),
                new Dangler(),
                new Magic()
        };
    }
}
