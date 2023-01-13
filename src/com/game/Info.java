package com.game;

import com.interfaces.Attackable;
import com.interfaces.Jokable;
import com.interfaces.Medicinable;
import com.units.Build;
import com.units.Man;

public class Info {
    private Info() {
    }

    public static String[] header() {
        return new String[]{
                "*************************************************************************************************************",
                "                         ⛓✠⛓✠⛓✠⛓       HEROES OF JAVA CONSOLE      ⚔✠⚔✠⚔✠⚔                         ",
                "*************************************************************************************************************",
        };
    }

    public static String[] footer() {
        String text = String.format("%s %s   | %s%s %s   | %s%s  | %s%s  | %s %s  | %s %s",
                Command.CMD_HELP, "помощь",
                Command.CMD_GO_LEFT, Command.CMD_GO_RIGHT, "идти",
                Command.CMD_ATTACK, "номер_врага атаковать",
                Command.CMD_CURE, "номер_союзника лечить",
                Command.CMD_JOKE, "шутить",
                Command.CMD_GAME_OVER, "выход"
        );
        return new String[]{
                ". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .",
                text,
                ". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . ."
        };
    }

    public static String[] help() {
        return new String[]{
                "---",
                "Пошаговая битва воинов по мотивам игры 'Heroes of Might and Magic II'",
                "Количество юнитов у игроков может быть разным",
                "---",
                "ПРАВИЛА",
                "Люди умеют ходить, а здания нет",
                "Пехотинцы могут атаковать врагов только на своей вертикальной линии",
                "Стрелки могут обстреливать врага из любой точки",
                "Маг атакует издалека фаерболами, поэтому тоже стрелок",
                "Маг может лечить",
                "Лечение возможно только для живых существ",
                "Тунеядец умеет шутить",
                "---",
                "Дополнительная команда для распечатки всех шуток: " + Command.CMD_PRINT_ALL_JOKES,
                "Дополнительная чит-команда убить вражеского юнита сразу: " + Command.CMD_KILL + "номер_врага",
                "---",
                "ПРИМЕРЫ КОМАНД",
                "Идти влево: " + Command.CMD_GO_LEFT,
                "Идти вправо: " + Command.CMD_GO_RIGHT,
                "Атаковать врага под номером 5: " + Command.CMD_ATTACK + "5",
                "Лечить союзника под номером 2: " + Command.CMD_CURE + "2",
                "Чит-команда убить сразу врага под номером 4: " + Command.CMD_KILL + "4",
                "---",
                "ОБОЗНАЧЕНИЯ",
                Man.SYMBOL_HP + " здоровье",
                Build.SYMBOL_HP + " прочность строения",
                Attackable.CHAR_ATTACK + " наносимый урон",
                Medicinable.CHAR_CURE + " уровень лечения",
                Jokable.LABEL_JOKE + " рассказывает шутки",
                "---",
                "https://github.com/AlexeyPertsukh/hw11-java-interface-game-heroes",
                "---",
        };
    }

    public static String onWin(Player playerWin) {
        return "⚑⚑⚑ ПОБЕДИЛ " + playerWin.getName() + " !!! ";
    }

    public static String onStart(String version) {
        return "ver." + version + " Dedicated to the Heroes of Might and Magic II  ";
    }


}
