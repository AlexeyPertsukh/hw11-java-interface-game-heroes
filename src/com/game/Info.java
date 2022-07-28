package com.game;

import com.interfaces.Attackable;
import com.interfaces.Jokable;
import com.interfaces.Medicinable;
import com.units.Build;
import com.units.Man;

public class Info {
    private Info() {
    }

    public static void printHeader(String color) {
        Color.setTextColor(color);
        System.out.println("*************************************************************************************************************");
        System.out.println("                         ⛓✠⛓✠⛓✠⛓       HEROES OF JAVA CONSOLE      ⚔✠⚔✠⚔✠⚔                         ");
        System.out.println("*************************************************************************************************************");
        Color.resetTextColor();
    }

    public static void printFooter(String color) {
        Color.setTextColor(color);
        String text = String.format("%s %s   | %s%s %s   | %s%s  | %s%s  | %s %s  | %s %s",
                Command.CMD_HELP, "помощь",
                Command.CMD_GO_LEFT, Command.CMD_GO_RIGHT, "идти",
                Command.CMD_ATTACK, "номер_врага атаковать",
                Command.CMD_CURE, "номер_союзника  лечить",
                Command.CMD_JOKE, "шутить",
                Command.CMD_GAME_OVER, "выход"
        );
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        System.out.println(text);
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        Color.resetTextColor();
    }

    public static void printHelp(String color) {
        Color.setTextColor(color);
        System.out.println("---");
        System.out.println("Пошаговая битва воинов по мотивам игры 'Heroes of Might and Magic II'");
        System.out.println("Количество юнитов у игроков может быть разным");
        System.out.println("---");
        System.out.println("ПРАВИЛА");
        System.out.println("Люди умеют ходить, а здания нет");
        System.out.println("Пехотинцы могут атаковать врагов только на своей вертикальной линии");
        System.out.println("Стрелки могут обстреливать врага из любой точки");
        System.out.println("Маг атакует издалека фаерболами, поэтому тоже стрелок");
        System.out.println("Маг может лечить");
        System.out.println("Лечение возможно только для живых существ");
        System.out.println("Тунеядец умеет шутить");
        System.out.println("Ничья, если прошло " + Game.MAX_ROUND_NO_ATTACK + " раунда без атаки");
        System.out.println("---");
        System.out.println("Дополнительная команда для распечатки всех шуток: " + Command.CMD_PRINT_ALL_JOKES);
        System.out.println("Дополнительная чит-команда убить вражеского юнита сразу: " + Command.CMD_KILL + "номер_врага");
        System.out.println("---");
        System.out.println("ПРИМЕРЫ КОМАНД");
        System.out.println("Идти влево: " + Command.CMD_GO_LEFT);
        System.out.println("Идти вправо: " + Command.CMD_GO_RIGHT);
        System.out.println("Атаковать врага под номером 5: " + Command.CMD_ATTACK + "5");
        System.out.println("Лечить союзника под номером 2: " + Command.CMD_CURE + "2");
        System.out.println("Чит-команда убить сразу врага под номером 4: " + Command.CMD_KILL + "4");
        System.out.println("---");
        System.out.println("ОБОЗНАЧЕНИЯ");
        System.out.println(Man.CHAR_HP + " здоровье");
        System.out.println(Build.BUILD_HP + " прочность строения");
        System.out.println(Attackable.CHAR_ATTACK + " наносимый урон");
        System.out.println(Medicinable.CHAR_CURE + " уровень лечения");
        System.out.println(Jokable.LABEL_JOKE + " рассказывает шутки");
        System.out.println("---");
        System.out.println("https://github.com/AlexeyPertsukh/hw11-java-interface-game-heroes");
        System.out.println("---");
        Color.resetTextColor();
    }

    public static void printOnWin(Player playerWin, String color, String colorErr) {
        if(playerWin == null) {
            Color.printlnColor(">>>ОШИБКА В ОПРЕДЕЛЕНИИ ПОБЕДИТЕЛЯ", colorErr);
        } else {
            Color.printlnColor("⚑⚑⚑ ПОБЕДИЛ " + playerWin.getName() + " !!! ", color);
        }

    }

    public static void printOnStart(String version) {
        System.out.println("ver." + version + " Dedicated to the Heroes of Might and Magic II  ");
    }

    public static void printOnEnd(String... args) {
        System.out.println();
        for (String s : args) {
            System.out.println(s);
        }
    }

    public static void printOnDraw(int maxRoundNoAttack, String color) {
        Color.printlnColor("⛨⛨⛨ НИЧЬЯ: " + maxRoundNoAttack + " раунда без атак.", color);
    }


}
