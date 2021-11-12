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
import com.units.*;

import java.util.Scanner;

public class Game {

    private static final int LEFT_POSITION = 0;
    public static final int RIGHT_POSITION = 3; //максимальная позиция, которую юнит может занимать на карте по горизонтали
    private static final int MAX_ROUND_NO_ATTACK = 4;     //максимальное количество ходов без атак

    private static final String VERSION = "4.22";
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
    private static final String CMD_GAME_OVER = "END";
    private static final String CMD_GO_RIGHT = ">";
    private static final String CMD_GO_LEFT = "<";
    private static final String CMD_JOKE = "$";
    private static final String CMD_PRINT_ALL_JOKES = "~";
    private static final String CMD_SKIP = "%";

    private static final String NAME_PLAYER1 = "Карл IV Великолепный";
    private static final String NAME_PLAYER2 = "Барон Свиное Рыло";
    private static final char EMPTY_SYMBOL = ' ';

    String MESSAGE_NO_WAY = "туда ходить нельзя";

    private final Player player1;
    private final Player player2;
    private Player playerCurrent;
    private Player playerOther;

    private int cntNoAttack; // счетчик ходов без атак

    Scanner scanner;
    String command;

    public Game() {
        player1 = new Player(NAME_PLAYER1);
        player1.addUnit(new Tower(LEFT_POSITION));
        player1.addUnit(new Knight(LEFT_POSITION));
        player1.addUnit(new Archer(LEFT_POSITION));
        player1.addUnit(new Dangler(LEFT_POSITION));
        player1.addUnit(new Magic(LEFT_POSITION));

        player2 = new Player(NAME_PLAYER2);
        player2.addUnit(new Tower(RIGHT_POSITION));
        player2.addUnit(new Knight(RIGHT_POSITION));
        player2.addUnit(new Archer(RIGHT_POSITION));
        player2.addUnit(new Dangler(RIGHT_POSITION));
        player2.addUnit(new Magic(RIGHT_POSITION));

        scanner = new Scanner(System.in);
    }




    //========= основной блок ===========================
    public void go() {
        printOnStart();
        focusFirstPlayer();
        printPage();
        boolean cmdResult;

        while(true) {
            inputCommand();
            if(checkExitCommand()) {
                break;
            }

            cmdResult = processCommand();
            processNeedActions(cmdResult);

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

        printOnEnd();
    }
    //===================================================

    private void printOnStart() {
        System.out.println("ver." + VERSION + " Dedicated to the Heroes of Might and Magic II  ");
    }

    private void printOnEnd() {
        System.out.println();
        System.out.println(COPYRIGHT);
        System.out.println(AUTHOR);
    }


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

        String text = "⚑  " + player1.getName();
        text = String.format("%-44s", text);
        Color.printColor(text, colorPlayer1);

        text =  "-----ПОЛЕ БОЯ------";
        text = String.format("%-27s", text);
        Color.printColor(text, COLOR_HEADER);

        text = "⚑  " + player2.getName();
        text = String.format("%-38s", text);
        Color.printColor(text, colorPlayer2);

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
        String text = String.format("%s %s   | %s%s %s   | %c%s  | %c%s  | %s %s  | %s %s",
                CMD_HELP, "помощь",
                CMD_GO_LEFT, CMD_GO_RIGHT, "идти",
                KEY_CMD_ATTACK, "номер_врага атаковать",
                KEY_CMD_CURE, "номер_союзника  лечить",
                CMD_JOKE, "шутить",
                CMD_GAME_OVER, "выход"
                );
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        System.out.println(text);
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
        System.out.println("Дополнительная команда для распечатки всех шуток: " + CMD_PRINT_ALL_JOKES);
        System.out.println("Дополнительная чит-команда убить вражеского юнита сразу: " + KEY_CMD_KILL + "номер_врага");
        System.out.println("---");
        System.out.println("Примеры команд");
        System.out.println("Идти влево: " + CMD_GO_LEFT);
        System.out.println("Идти вправо: " + CMD_GO_RIGHT);
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
    private void focusFirstPlayer() {
        focusPlayer(player1);
    }

    private void focusSecondPlayer() {
        focusPlayer(player2);
    }

    private void focusPlayer(Player player) {

        if(player == player1) {
            playerCurrent = player1;
            playerOther = player2;
        } else {
            playerCurrent = player2;
            playerOther = player1;
        }

        playerCurrent.focusFirstLivingUnit();
    }


    //фокус на следующего игрокa
    private void focusNextPlayer() {

        if (playerOther.isAllUnitsDead()) { //враг убит полностью- фокус на самого себя
            focusPlayer(playerCurrent);
            return;
        }

        if (playerCurrent == player1) {
            focusSecondPlayer();
        } else {
            focusFirstPlayer();
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
        } else {
            return Color.ANSI_RESET;
        }
    }

    //Цвет, каким распечатывать юнита (цветным- когда юнит в фокусе)
    private String getColorUnit(Player player, int num) {
        Unit unit = player.getUnitByNum(num);

        if (unit.isDead()) {
            return COLOR_KILL;
        }

        if (player == playerCurrent && player.getUnitCurrent() == unit) {
            return COLOR_FOCUS;
        } else {
            return Color.ANSI_RESET;
        }
    }

    //ввод команды
    private void inputCommand() {
        System.out.printf("[%s] %s, введите команду: ", playerCurrent.getName(), playerCurrent.getUnitCurrent().getNameLowerCase());
        command = playerCurrent.nextCmd(scanner);
    }

    //обработка команд
    private boolean processCommand() {

        if(checkBasicCommand(CMD_GO_RIGHT)) {
            return goRight();
        }

        if(checkBasicCommand(CMD_GO_LEFT)) {
            return goLeft();
        }

        if(checkBasicCommand(CMD_HELP)) {
            printHelp();
            return true;
        }

        if(checkBasicCommand(CMD_JOKE)) {
            return randomJoke();
        }

        if(checkBasicCommand(CMD_PRINT_ALL_JOKES)) {
            printAllJokes();
            return true;
        }

        //команды с параметрами
        //атака
        if (checkCommandAttack()) {
            int num = Util.getIntFromCommandStr(command, KEY_CMD_ATTACK) - 1;
            return attack(num);
        }

        //убить сразу
        if (checkCommandKill()) {
            int num = Util.getIntFromCommandStr(command, KEY_CMD_KILL) - 1;
            return killEnemy(num);
        }

        //лечение
        if (checkCommandCure()) {
            int num = Util.getIntFromCommandStr(command, KEY_CMD_CURE) - 1;
            return cure(num);
        }

        //
        System.out.printf("[%s] неизвестная команда \n", playerCurrent.getName());
        return false;
    }

    private void printAllJokes() {
        Color.setTextColor(COLOR_HELP);
        System.out.println("Репертуар шутника, достаточный для получения бесплатной кружки пива на любом магистратском балагане:");
        System.out.println("----");
        for (String joke : Jokable.JOKE_STORIES) {
            System.out.println(joke);
        }
        System.out.println("----");
        Color.resetTextColor();
    }

    //фокус на следующего юнита, если все юниты отыграли - передаем ход следующему игроку
    private void focusNextUnit() {
        if(playerCurrent.currentUnitIsLastInLine()) {
            playerCurrent.focusFirstLivingUnit();
            focusNextPlayer();
        } else {
            playerCurrent.focusNextUnit();
        }
    }

    //атака на противника
    private boolean attack(int numEnemy) {

        Unit unit = playerCurrent.getUnitCurrent();
        Unit enemy = playerOther.getUnitByNum(numEnemy);

        if (!isAttackable(unit)) {
            System.out.printf("[%s] %s не умеет атаковать \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        if (enemy == null) {
            System.out.printf("[%s] неправильный номер для атаки, попробуйте еще раз \n", playerCurrent.getName());
            return false;
        }

        int attackResult  = ((Attackable) unit).attack(enemy);

        if(attackResult < 0) {
            printMessageAttackFail(unit, attackResult);
            return false;
        }

        cntNoAttack = 0; //сбрасываем счетчик ходов без атак

        System.out.printf("[%s] %s атакует: враг %s(%d) получил урон %d ед. \n", playerCurrent.getName(),
                unit.getNameLowerCase(),
                enemy.getNameLowerCase(),
                numEnemy + 1,
                attackResult);

        return true;

    }

    private void printMessageAttackFail(Unit unit, int codeMessage) {
        switch (codeMessage) {
            case Attackable.CODE_TOO_FAR:
                System.out.printf("[%s] %s атакует только в ближнем бою, подойдите к врагу вплотную \n", playerCurrent.getName(), unit.getNameLowerCase());
                break;
            case Attackable.CODE_IS_KILLED:
                System.out.printf("[%s] нельзя атаковать убитого \n", playerCurrent.getName());
                break;
            default:
                System.out.printf("[%s] атака невозможна по неизвестной причине   \n", playerCurrent.getName());
                break;
        }
    }

    private boolean killEnemy(int numEnemy) {
        Unit enemy = playerOther.getUnitByNum(numEnemy);
        if (enemy == null) {
            System.out.printf("[%s] неправильный номер для моментального убийства, попробуйте еще раз \n", playerCurrent.getName());
            return false;
        } else {
            enemy.kill();
            System.out.println("чит-команда выполнена");

            return true;
        }
    }

    private boolean isAttackable(Unit unit) {
        return unit instanceof Attackable;
    }

    //лечение
    public boolean cure(int numPatient) {

        Unit patient = playerCurrent.getUnitByNum(numPatient);
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMedicinable(unit)) {
            System.out.printf("[%s] %s не умеет лечить    \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        if (patient == null) {
            System.out.printf("[%s] неправильный номер для лечения \n", playerCurrent.getName());
            return false;
        }

        int cureResult = ((Medicinable) (unit)).cureMan(patient);

        if(cureResult < 0) {
            printMessageCureFail(patient, cureResult);
            return false;
        }

        System.out.printf("[%s] %s подлечил раненого, %s(%d) восстановил %d ед. здоровья   \n", playerCurrent.getName(),
                unit.getNameLowerCase(),
                patient.getNameLowerCase(),
                numPatient + 1,
                cureResult);

        return true;
    }

    private void printMessageCureFail(Unit patient, int codeMessage) {
        final String errMessage = "лечение невозможно";

        switch (codeMessage) {
            case Medicinable.CODE_IS_KILLED:
                System.out.printf("[%s] %s, убитому не помочь    \n", playerCurrent.getName(), errMessage);
                break;

            case Medicinable.CODE_IS_FULL:
                System.out.printf("[%s] %s, %s полностью здоров    \n", playerCurrent.getName(),
                        errMessage, patient.getNameLowerCase());
                break;

            case Medicinable.CODE_IS_NO_MAN:
                System.out.printf("[%s] %s, %s не является живым существом   \n", playerCurrent.getName(),
                        errMessage, patient.getNameLowerCase());
                break;

//            case Medicinable.CODE_IS_THIS:
//                System.out.printf("[%s] лечение невозможно, нельзя лечить самого себя   \n", playerCurrent.getName());
//                break;

            default:
                System.out.printf("[%s] %s по неизвестной причине   \n", playerCurrent.getName(), errMessage);
                break;
        }

    }

    private boolean isMedicinable(Unit unit) {
        return unit instanceof Medicinable;
    }

    //пошутить
    public boolean randomJoke() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isJokable(unit)) {
            System.out.printf("[%s] %s не умеет шутить \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        String story = ((Jokable) unit).randomJoke();
        System.out.printf("[%s] %s шутит: ", playerCurrent.getName(), unit.getNameLowerCase());
        Color.printlnColor(story, COLOR_HELP);

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

        final char border = '\'';
        System.out.print(border);

        for (int cell = 0; cell < RIGHT_POSITION + 1; cell++) {

            printUnitCoatOrEmptyInCellMap(player1, num, cell);

            System.out.print("  ");    //разделитель между вражескими юнитами, когда они станут в одну ячейку

            printUnitCoatOrEmptyInCellMap(player2, num, cell);

            System.out.print(border);
        }
    }

    private void printUnitCoatOrEmptyInCellMap(Player player, int num, int cell) {
        String color = getColorPlayer(player);
        Unit unit = player.getUnitByNum(num);

        if(unit.getPosition() == cell && unit.isDead()) {
            color = COLOR_KILL;
        }

        char coat = EMPTY_SYMBOL;
        if(unit.getPosition() == cell) {
            coat = unit.getCoat();
        }
        String coatString = String.valueOf(coat);
        Color.printColor(coatString, color);
    }

    private void printOnWin() {
        Player playerWin = getWinPlayer();
        Color.printlnColor("⚑⚑⚑ ПОБЕДИЛ " + playerWin.getName() + " !!! ", COLOR_VICTORY);
    }

    private void printOnDraw() {
        Color.printlnColor("⛨⛨⛨ НИЧЬЯ: " + MAX_ROUND_NO_ATTACK + " раунда без атак.", COLOR_DRAW);
    }

    public boolean goRight() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        boolean code = ((Movable) unit).goRightOneStep();
        if (!code) {
            System.out.printf("[%s] %s \n", playerCurrent.getName(), MESSAGE_NO_WAY);
            return false;
        }

        return true;
    }

    public boolean goLeft() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        boolean code = ((Movable) unit).goLeftOneStep();
        if (!code) {
            System.out.printf("[%s] %s \n", playerCurrent.getName(), MESSAGE_NO_WAY);
            return false;
        }

        return true;
    }

    private boolean isMovable(Unit unit) {
        return unit instanceof Movable;
    }

    private boolean checkWin() {
        return player1.isAllUnitsDead() || player2.isAllUnitsDead();
    }

    private Player getWinPlayer() {
        if(player1.isAllUnitsDead()) {
            return player2;

        } else  if(player2.isAllUnitsDead()) {
            return player1;

        } else {
            return null;
        }

    }

    private boolean checkDraw() {
        return  (cntNoAttack > MAX_ROUND_NO_ATTACK);
    }

    private boolean checkCommandAttack() {
        int num = Util.getIntFromCommandStr(command, KEY_CMD_ATTACK);
        return num != Util.CODE_RESULT_NOT_OK;
    }

    private boolean checkCommandKill() {
        int num = Util.getIntFromCommandStr(command, KEY_CMD_KILL);
        return num != Util.CODE_RESULT_NOT_OK;
    }

    private boolean checkCommandCure() {
        int num = Util.getIntFromCommandStr(command, KEY_CMD_CURE);
        return num != Util.CODE_RESULT_NOT_OK;
    }

    private boolean checkBasicCommand(String keyCommand) {
        return command.equalsIgnoreCase(keyCommand);
    }

    private boolean checkExitCommand() {
        return command.equalsIgnoreCase(CMD_GAME_OVER);
    }

    private void processNeedActions(boolean cmdResult) {
        if(checkNeedPressForContinue(cmdResult)) {
            Util.pressEnterForContinue();
        }

        if(checkNeedFocusNextUnit(cmdResult)) {
            focusNextUnit();
        }

        if(checkNeedUpdatePage(cmdResult)) {
            printPage();
        }
    }

    //Если бы на этом этапе учебы мы знали про enum, я бы использовал enum для доп. атрибутирования команд,
    //но пока справляюсь как могу- checkNeedUpdatePage etc.
    private boolean checkNeedUpdatePage(boolean cmdResult) {
        boolean need = checkBasicCommand(CMD_GO_RIGHT) || checkBasicCommand(CMD_GO_LEFT) || checkBasicCommand(CMD_JOKE) ||
                checkCommandAttack() || checkCommandKill() || checkCommandCure();
        return cmdResult && need;
    }

    private boolean checkNeedFocusNextUnit(boolean cmdResult) {
        boolean need = checkBasicCommand(CMD_GO_RIGHT) || checkBasicCommand(CMD_GO_LEFT) || checkBasicCommand(CMD_JOKE) ||
                checkCommandAttack() || checkCommandCure();
        return cmdResult && need;
    }

    private boolean checkNeedPressForContinue(boolean cmdResult) {
        boolean need = checkBasicCommand(CMD_JOKE) || checkBasicCommand(CMD_PRINT_ALL_JOKES) ||
                checkCommandAttack() || checkCommandKill() || checkCommandCure();
        return cmdResult && need;
    }


}
