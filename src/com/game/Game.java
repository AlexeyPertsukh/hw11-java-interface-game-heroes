// Юникоды:
// https://forum.tamirov.ru/viewtopic.php?f=27&t=38
// https://wp-kama.ru/id_10136/100-yunikod-simvolov-kotorye-mozhno-ispolzovat-v-html-css-js-php.html
// https://saney.ru/tools/symbols.html
// ▲ ◈ ✠ ◉ ♦ ⛨
package com.game;

import com.interfaces.Attackable;
import com.interfaces.Jokable;
import com.interfaces.Medicinable;
import com.interfaces.Movable;
import com.units.Build;
import com.units.Unit;

import java.util.Scanner;

public class Game {

    private static final int LEFT_MAP_POSITION = 0;
    public static final int RIGHT_MAP_MAX_POSITION = 3; //максимальная позиция, которую юнит может занимать на карте по горизонтали
    private static final int MAX_ROUND_NO_ATTACK = 4;     //максимальное количество ходов без атак

    private static final String VERSION = "4.02";
    private static final String COPYRIGHT = "JAVA A01 \"ШАГ\", Запорожье 2021";
    private static final String AUTHOR =  "Перцух Алексей";

    //Цвета в программе
    private static final String COLOR_VICTORY = Color.ANSI_GREEN;   //победа
    private static final String COLOR_DRAW = Color.ANSI_CYAN;       //ничья
    private static final String COLOR_FOCUS = Color.ANSI_GREEN;
    private static final String COLOR_HEADER = Color.ANSI_PURPLE;
    private static final String COLOR_FOOTER = Color.ANSI_BLUE;
    private static final String COLOR_HELP = Color.ANSI_BLUE;
    private static final String COLOR_KILL = Color.ANSI_RED;

    private static final char KEY_CMD_ATTACK = '*';
    private static final char KEY_CMD_KILL = '#';
    private static final char KEY_CMD_CURE = '@';

    private static final String CMD_HELP = "?";
    private static final String CMD_GAME_OVER = "0";
    private static final String CMD_RUN_RIGHT = ">";
    private static final String CMD_RUN_LEFT = "<";
    private static final String CMD_JOKE = "$";
    private static final String CMD_PRINT_ALL_JOKE_STORIES = "~";
    private static final String CMD_SKIP = "%";

    private static final boolean NEED_PRINT_PAGE = true;
    private static final boolean NO_NEED_PRINT_PAGE = false;

    private static final String NAME_PLAYER1 = "Карл IV Великолепный";
    private static final String NAME_PLAYER2 = "Барон Свиное Рыло";

    private final Player player1;
    private final Player player2;
    private Player playerCurrent;
    private Player playerOther;

    private int cntNoAttack; // счетчик ходов без атак

    Scanner scanner;
    String command;

    public Game() {
        player1 = new Player(NAME_PLAYER1, LEFT_MAP_POSITION);
        player2 = new Player(NAME_PLAYER2, RIGHT_MAP_MAX_POSITION);
        scanner = new Scanner(System.in);
    }

    //========= основной блок ===========================
    public void go() {
        System.out.println("ver." + VERSION + " Dedicated to the Heroes of Might and Magic II  ");
        playerFirstFocus();
        printPage();

        boolean needPrintPage;
        while(true) {
            inputCommand();
            if(isExitCommand()) {
                break;
            }

            needPrintPage = processCommand();
            if(needPrintPage) {
                printPage();
            }

            //Кто-то победил?
            if (checkWin()) {
                printOnWin();
                break;
            }

            //ничья?
            if(checkDraw()) {
                printOnDraw();
                break;
            }
        }

        //конец игры
        System.out.println();
        System.out.println(COPYRIGHT);
        System.out.println(AUTHOR);
    }
    //===================================================


    //Распечатываем главную страницу игры
    private void printPage() {

        printHeader();

        printNamePlayersOnBattleField();
        printUnitsOnBattleField();

        printFooter();
    }

    private void printNamePlayersOnBattleField() {
        String colorPlayer1 = getColorPlayer(player1);
        String colorPlayer2 = getColorPlayer(player2);

        String str = "⚑  " + player1.getName();
        str = String.format("%-44s", str);
        Color.printColor(str, colorPlayer1);

        str =  "-----ПОЛЕ БОЯ------";
        str = String.format("%-27s", str);
        Color.printColor(str, COLOR_HEADER);

        str = "⚑  " + player2.getName();
        str = String.format("%-38s", str);
        Color.printColor(str, colorPlayer2);

        System.out.println();
    }

    private void printUnitsOnBattleField() {
        String format;

        int max = Math.max(player1.getUnitsSize(), player2.getUnitsSize());

        for (int i = 0; i < max; i++) {
            if(player1.getUnitShortInfo(i) == null || player2.getUnitShortInfo(i) == null) {
                break;
            }

            //юнит игрока1
            format = "%d. %-35s    ";
            printOneUnitOnBattleField(player1, i, format);

            //рисуем линию тактической карты
            printTacticMapLine(i);

            //юнит игрока2
            format = "       %d. %-35s ";
            printOneUnitOnBattleField(player2, i, format);

            System.out.println();
        }
    }

    private void printOneUnitOnBattleField(Player player, int numUnit, String format) {
        String colorUnit = getColorUnit(player, numUnit);
        String shortInfo = player.getUnitShortInfo(numUnit);
        shortInfo = String.format(format, numUnit + 1, shortInfo);
        Color.printColor(shortInfo, colorUnit);
    }

    private void printHeader() {
        Color.setTextColor(COLOR_HEADER);
        System.out.println("*************************************************************************************************************");
        System.out.println("                         ⛓✠⛓✠⛓✠⛓       HEROES OF JAVA CONSOLE      ⚔✠⚔✠⚔✠⚔                         ");
        System.out.println("*************************************************************************************************************");
        Color.resetTextColor();
    }


    private void printFooter() {
        Color.setTextColor(COLOR_FOOTER);
        String str = String.format("%s %s   | %s%s %s   | %c%s  | %c%s  | %s %s  | %s %s",
                CMD_HELP, "помощь",
                CMD_RUN_LEFT, CMD_RUN_RIGHT, "идти",
                KEY_CMD_ATTACK, "номер_врага атаковать",
                KEY_CMD_CURE, "номер_союзника  лечить",
                CMD_JOKE, "шутить",
                CMD_GAME_OVER, "выход"
                );
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        System.out.println(str);
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        Color.resetTextColor();
    }

    private void printHelp() {
        Color.setTextColor(COLOR_HELP);
        System.out.println("---");
        System.out.println("Правила");
        System.out.println("Пехотинцы могут атаковать врагов только на своей вертикальной линии");
        System.out.println("Стрелки могут обстреливать врага из любой точки");
        System.out.println("Маг атакует издалека фаерболами, поэтому тоже стрелок");
        System.out.println("Маг может лечить");
        System.out.println("Лечение возможно только для живых существ");
        System.out.println("Тунеядец умеет шутить");
        System.out.println("Ничья, если прошло " + MAX_ROUND_NO_ATTACK + " раунда без атаки");
        System.out.println("---");
        System.out.println("Дополнительная команда для распечатки всех шуток: " + CMD_PRINT_ALL_JOKE_STORIES);
        System.out.println("Дополнительная чит-команда убить вражеского юнита сразу: " + KEY_CMD_KILL + "номер_врага");
        System.out.println("---");
        System.out.println("Примеры команд");
        System.out.println("Идти влево: " + CMD_RUN_LEFT);
        System.out.println("Идти вправо: " + CMD_RUN_RIGHT);
        System.out.println("Атаковать врага под номером 5: " + KEY_CMD_ATTACK + "5");
        System.out.println("Лечить союзника под номером 2: " + KEY_CMD_CURE + "2");
        System.out.println("Чит-команда убить сразу врага под номером 4: " + KEY_CMD_KILL + "4");
        System.out.println("---");
        System.out.println("Обозначения");
        System.out.println(Unit.CHAR_HP + " здоровье");
        System.out.println(Build.CHAR_BUILD_HP + " прочность строения");
        System.out.println(Attackable.CHAR_ATTACK + " наносимый урон");
        System.out.println(Medicinable.CHAR_CURE + " уровень лечения");
        System.out.println(Jokable.LABEL_JOKE + " рассказывает шутки");
        System.out.println("---");
        System.out.println("https://github.com/AlexeyPertsukh/hw11-java-interface-game-heroes");
        System.out.println("---");
        Color.resetTextColor();
    }


    // фокус на перволго игрока
    private void playerFirstFocus() {
        playerFocus(player1);
    }

    private void playerSecondFocus() {
        playerFocus(player2);
    }

    private void playerFocus(Player player) {
        playerCurrent = (player == player1) ? player1 : player2;
        playerOther = (player == player1) ? player2 : player1;

        playerCurrent.focusFirstUnit();
    }


    //фокус на следующего игрокa
    private void playerNextFocus() {

        if (playerOther.isAllUnitsDead()) { //враг убит полностью- фокус на самого себя
            playerFocus(playerCurrent);
            return;
        }

        if (playerCurrent == player1) {
            playerSecondFocus();
        } else {
            playerFirstFocus();
        }

        cntNoAttack++;
    }

    //Цвет, каким распечатывать игрока (цветным- когда игрок в фокусе)
    private String getColorPlayer(Player player) {

        if (player.isAllUnitsDead()) {
            return COLOR_KILL;
        }

        if (player == playerCurrent) {
            return COLOR_FOCUS;
        }
        return Color.ANSI_RESET;
    }

    //Цвет, каким распечатывать юнита (цветным- когда юнит в фокусе)
    private String getColorUnit(Player player, int num) {
        if (player.getUnitByNum(num).isDead()) {
            return COLOR_KILL;
        }

        if (player == playerCurrent && player.getUnitCurrent() == player.getUnitByNum(num)) {
            return COLOR_FOCUS;
        }
        return Color.ANSI_RESET;
    }

    //ввод команды
    private void inputCommand() {
        System.out.printf("[%s] %s, введите команду: ", playerCurrent.getName(), playerCurrent.getUnitCurrent().getName().toLowerCase());
        command = playerCurrent.nextCmd(scanner);
    }

    //обработка команд
    private boolean processCommand() {
        boolean needPrintPage = NO_NEED_PRINT_PAGE;
        boolean ok;

        //простые команды без параметров
        switch (command) {
            case CMD_RUN_RIGHT:         //идти вправо
                ok = goRight();
                if (ok) {
                    focusNextUnit();
                    needPrintPage = NEED_PRINT_PAGE;
                }
                return needPrintPage;

            case CMD_RUN_LEFT:          //идти влево
                ok = goLeft();
                if (ok) {
                    focusNextUnit();
                    needPrintPage = NEED_PRINT_PAGE;
                }
                return needPrintPage;

            case CMD_HELP:              //помощь
                printHelp();
                return needPrintPage;

//            case CMD_SKIP:
//                System.out.println("пропуск хода");
//                focusNextUnit();
//                printPage();
//                return;

            case CMD_JOKE:
                ok = joke();
                if (ok) {
                    focusNextUnit();
                    needPrintPage = NEED_PRINT_PAGE;
                }
                return needPrintPage;

            case CMD_PRINT_ALL_JOKE_STORIES:       //распечатать все шутки
                printAllJoke();
                return needPrintPage;

            default:
                break;
        }

        //команды с параметрами

        //атака
        int num = Util.getIntFromCmdStr(command, KEY_CMD_ATTACK);

        if (num != Util.CODE_NOT_OK) {
            num--;
            ok = attack(num);
            if (ok) {
                Util.pressEnterForContinue();
                focusNextUnit();
                needPrintPage = NEED_PRINT_PAGE;
            }
            return needPrintPage;
        }

        //убить сразу
        num = Util.getIntFromCmdStr(command, KEY_CMD_KILL);
        if (num != Util.CODE_NOT_OK) {
            num--;
            ok = attack(num, 1000);
            if (ok) {
                Util.pressEnterForContinue();
                focusNextUnit();
                needPrintPage = NEED_PRINT_PAGE;
            }
            return needPrintPage;
        }

        //лечение
        num = Util.getIntFromCmdStr(command, KEY_CMD_CURE);
        if (num != Util.CODE_NOT_OK) {
            num--;
            ok = cure(num);
            if (ok) {
                Util.pressEnterForContinue();
                focusNextUnit();
                needPrintPage = NEED_PRINT_PAGE;
            }
            return needPrintPage;
        }
        //
        System.out.printf("[%s] неизвестная команда \n", playerCurrent.getName());
        return needPrintPage;
    }

    private void printAllJoke() {
        Color.setTextColor(COLOR_HELP);
        System.out.println("----");
        System.out.println("Репертуар шутника, достаточный для получения бесплатной кружки пива на любом магистратском балагане:");
        for (String joke : Jokable.JOKE_STORIES) {
            System.out.println(joke);
        }
        System.out.println("----");
        Color.resetTextColor();
    }

    //фокус на следующего юнита, если все юниты отыграли - передаем ход следующему игроку
    private void focusNextUnit() {
        playerCurrent.focusNextUnit();
        if (playerCurrent.isAllUnitsPlayed()) {  //все юниты сделали ход - передаем ход следующему
            playerNextFocus();
        }
    }

    //атака на противника
    private boolean attack(int... arr) {

        int num = arr[0];   //адрес вражеского юнита, который атакуем
        Unit unit = playerCurrent.getUnitCurrent();
        Unit enemy = playerOther.getUnitByNum(num);

        if (enemy == null) {
            System.out.printf("[%s] неправильный номер для атаки, попробуйте еще раз \n", playerCurrent.getName());
            return false;
        }

        if (!isAttackable(unit)) {
            System.out.printf("[%s] %s не умеет атаковать \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        int code;
        if (arr.length == 1) {
            code = ((Attackable) unit).attack(enemy);
        } else {
            code = ((Attackable) unit).attack(enemy, arr[1]);
        }

        switch (code) {
            case Attackable.CODE_TOO_FAR:
                System.out.printf("[%s] %s атакует только в ближнем бою, подойдите к врагу вплотную \n", playerCurrent.getName(), unit.getName().toLowerCase());
                return false;
            case Attackable.CODE_IS_KILLED:
                System.out.printf("[%s] нельзя атаковать убитого \n", playerCurrent.getName());
                return false;
            default:
                break;
        }

        if (code < 0) {
            System.out.printf("[%s] атака невозможна по неизвестной причине   \n", playerCurrent.getName());
            return false;
        }

        cntNoAttack = 0; //сбрасываем счетчик ходов без атак

        System.out.printf("[%s] %s атакует: враг %s(%d) получил урон %d ед. \n", playerCurrent.getName(), unit.getName().toLowerCase(), enemy.getName().toLowerCase(), playerOther.getNumUnits(enemy), code);

        return true;
    }

    private boolean isAttackable(Unit unit) {
        return unit instanceof Attackable;
    }

    //лечение
    public boolean cure(int num) {

        Unit patient = playerCurrent.getUnitByNum(num);
        Unit unit = playerCurrent.getUnitCurrent();

        if (patient == null) {
            return false;
        }

        if (!isMedicinable(unit)) {
            System.out.printf("[%s] %s не умеет лечить    \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        int code = ((Medicinable) (unit)).cure(patient);

        switch (code) {
            case Medicinable.CODE_IS_KILLED:
                System.out.printf("[%s] лечение не выполнено, убитому не помочь    \n", playerCurrent.getName());
                return false;
            case Medicinable.CODE_IS_FULL:
                System.out.printf("[%s] лечение не выполнено, %s полностью здоров    \n", playerCurrent.getName(), patient.getName().toLowerCase());
                return false;
            case Medicinable.CODE_IS_NO_MAN:
                System.out.printf("[%s] лечение невозможно, %s не является живым существом   \n", playerCurrent.getName(), patient.getName().toLowerCase());
                return false;
//            case Medicinable.CODE_IS_THIS:
//                System.out.printf("[%s] нельзя лечить самого себя   \n", playerCurrent.getName());
//                return false;
            default:
                break;
        }

        if (code < 0) {
            System.out.printf("[%s] лечение невозможно по неизвестной причине   \n", playerCurrent.getName());
            return false;
        }

        System.out.printf("[%s] %s подлечил раненого, %s(%d) восстановил %d ед. здоровья   \n", playerCurrent.getName(),
                unit.getName().toLowerCase(),
                patient.getName().toLowerCase(),
                playerCurrent.getNumUnits(patient),
                code);
        return true;
    }

    private boolean isMedicinable(Unit unit) {
        return unit instanceof Medicinable;
    }

    //пошутить
    public boolean joke() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isJokable(unit)) {
            System.out.printf("[%s] %s не умеет шутить \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        String story = ((Jokable) unit).joke();
        System.out.printf("[%s] %s шутит: ", playerCurrent.getName(), unit.getName().toLowerCase());
        Color.printlnColor(story, COLOR_HELP);
        Util.pressEnterForContinue();
        return true;
    }

    private boolean isJokable(Unit unit) {
        return unit instanceof Jokable;
    }

    //для отрисовывки карты битвы построчно
    public void printTacticMapLine(int num) {
        if (num < 0 || num >= player1.getUnitsSize()) {
            return;
        }

        char border = '\'';
        System.out.print(border);

        for (int cell = 0; cell < RIGHT_MAP_MAX_POSITION + 1; cell++) {

            printUnitCoatOrEmptyInCellMap(player1, num, cell);

            System.out.print("  ");    //разделитель между вражескими юнитами, когда они станут в одну ячейку

            printUnitCoatOrEmptyInCellMap(player2, num, cell);

            System.out.print(border);
        }
    }

    private void printUnitCoatOrEmptyInCellMap(Player player, int num, int cell) {
        String color = getColorPlayer(player);
        if(player.getUnitByNum(num).getPosition() == cell && player.getUnitByNum(num).isDead()) {
            color = COLOR_KILL;
        }

        String coat = " ";
        if(player.getUnitByNum(num).getPosition() == cell) {
            coat = String.valueOf(player.getUnitByNum(num).getCoat());
        }

        Color.printColor(coat, color);
    }


    public boolean goRight() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        boolean code = ((Movable) unit).goRightOneStep();
        if (!code) {
            System.out.printf("[%s] %s \n", playerCurrent.getName(), Movable.MSG_NO_WAY);
        }
        return code;
    }

    public boolean goLeft() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        boolean code = ((Movable) unit).goLeftOneStep();
        if (!code) {
            System.out.printf("[%s] %s \n", playerCurrent.getName(), Movable.MSG_NO_WAY);
        }
        return code;
    }

    private boolean isMovable(Unit unit) {
        return unit instanceof Movable;
    }

    private boolean checkWin() {
        return player1.isAllUnitsDead() || player2.isAllUnitsDead();
    }

    private Player getWinPlayer() {
        return  (player1.isAllUnitsDead()) ? player2 : player1;
    }

    private boolean checkDraw() {
        return  (cntNoAttack > MAX_ROUND_NO_ATTACK);
    }

    private boolean isExitCommand() {
        return command.equalsIgnoreCase(CMD_GAME_OVER);
    }

    private void printOnWin() {
        Player playerWin = getWinPlayer();
        Color.printlnColor("⚑⚑⚑ ПОБЕДИЛ " + playerWin.getName() + " !!! ", COLOR_VICTORY);
    }

    private void printOnDraw() {
        Color.printlnColor("⛨⛨⛨ НИЧЬЯ: " + MAX_ROUND_NO_ATTACK + " раунда без атак.", COLOR_DRAW);
    }

}
