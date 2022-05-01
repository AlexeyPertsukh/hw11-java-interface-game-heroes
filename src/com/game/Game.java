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

    private static final int LIMIT_LEFT = 0;
    public static final int LIMIT_RIGHT = 3; //максимальная позиция, которую юнит может занимать на карте по горизонтали
    public static final int MAX_ROUND_NO_ATTACK = 4;     //максимальное количество ходов без атак

    private static final String VERSION = "4.4";
    private static final String COPYRIGHT = "JAVA A01 \"ШАГ\", Запорожье 2021";
    private static final String AUTHOR = "Перцух Алексей";

    //Цвета в программе
    private static final String COLOR_VICTORY = Color.ANSI_GREEN;   //победа
    private static final String COLOR_DRAW = Color.ANSI_CYAN;       //ничья
    private static final String COLOR_FOCUS = Color.ANSI_YELLOW;
    private static final String COLOR_HEADER = Color.ANSI_PURPLE;
    private static final String COLOR_FOOTER = Color.ANSI_BLUE;
    private static final String COLOR_HELP = Color.ANSI_BLUE;
    private static final String COLOR_KILL = Color.ANSI_RED;

    private static final String NAME_PLAYER1 = "Карл IV Великолепный";
    private static final String NAME_PLAYER2 = "Барон Свиное Рыло";
    private static final char EMPTY_SYMBOL = ' ';
    private static final char BATTLE_FIELD_BORDER_CHAR = '\'';

    private static final String MESSAGE_NO_WAY = "туда ходить нельзя";

    private final Player player1;
    private final Player player2;
    private Player playerCurrent;
    private Player playerOther;

    private int cntNoAttack; // счетчик ходов без атак

    private final Scanner scanner;

    public Game() {
        //количество юнитов у игрока 1 и 2 может быть разным
        player1 = new Player(NAME_PLAYER1);
        player1.addUnit(new Tower(LIMIT_LEFT));
        player1.addUnit(new Knight(LIMIT_LEFT));
        player1.addUnit(new Archer(LIMIT_LEFT));
        player1.addUnit(new Dangler(LIMIT_LEFT));
        player1.addUnit(new Magic(LIMIT_LEFT));

        player2 = new Player(NAME_PLAYER2);
        player2.addUnit(new Tower(LIMIT_RIGHT));
        player2.addUnit(new Knight(LIMIT_RIGHT));
        player2.addUnit(new Archer(LIMIT_RIGHT));
        player2.addUnit(new Dangler(LIMIT_RIGHT));
        player2.addUnit(new Magic(LIMIT_RIGHT));
//        player2.addUnit(new Archer(RIGHT_POSITION));   //для проверки игры с разным количеством юнитов у игроков

        scanner = new Scanner(System.in);
    }

    //========= основной блок ===========================
    public void go() {
        Info.printOnStart(VERSION);
        focusFirstPlayer();
        printPage();
        boolean commandResult;

        while (true) {
            Command command = inputCommand();
            if (command.isEnd()) {
                break;
            }

            commandResult = processCommand(command);
            if (commandResult) {
                processNeedActions(command);
            }


            //Кто-то победил?
            if (checkWin()) {
                Info.printOnWin(getWinPlayer(), COLOR_VICTORY);
                break;
            }

            //ничья?
            if (checkDraw()) {
                Info.printOnDraw(MAX_ROUND_NO_ATTACK, COLOR_DRAW);
                break;
            }
        }

        Info.printOnEnd(COPYRIGHT, AUTHOR);
    }
    //===================================================

    //Распечатываем главную страницу игры
    private void printPage() {
        Info.printHeader(COLOR_HEADER);

        printNamePlayersOnBattleField();
        printUnitsOnBattleField();

        Info.printFooter(COLOR_FOOTER);
    }

    private void printNamePlayersOnBattleField() {
        String colorPlayer1 = getColorPlayer(player1);
        String colorPlayer2 = getColorPlayer(player2);

        final String playerFormat = "%-42s";
        final String beforeName = "⚑  ";

        String text = beforeName + player1.getName();
        text = String.format(playerFormat, text);
        Color.printColor(text, colorPlayer1);

        text = "-----ПОЛЕ БОЯ------";
        text = String.format("%-28s", text);
        Color.printColor(text, COLOR_HEADER);

        text = beforeName + player2.getName();
        text = String.format(playerFormat, text);
        Color.printColor(text, colorPlayer2);

        System.out.println();
    }

    private void printUnitsOnBattleField() {
        String format;

        int max = Math.max(player1.getUnitsSize(), player2.getUnitsSize());

        for (int i = 0; i < max; i++) {

            //юнит игрока1
            format = "%d. %-11s %-22s   ";      // напр: "4. ⬦ Тунеядец  (♥15, :))"
            printOneUnitOnBattleField(player1, i, format);

            //рисуем линию тактической карты
            printBattleFieldLine(i);

            //юнит игрока2
            format = "       " + format;
            printOneUnitOnBattleField(player2, i, format);

            System.out.println();
        }
    }

    private void printOneUnitOnBattleField(Player player, int numUnit, String format) {
        Unit unit = player.getUnitByNum(numUnit);
        if (unit == null) {
            String string = String.format(format, 0, "");
            string = Util.spacedString(string);
            System.out.print(string);
            return;
        }

        String infoSkills = "";
        if (!unit.isDead()) {
            infoSkills = String.format("(%s)", unit.infoSkills());
        }

        String shortInfo = unit.shortInfo();

        String info = String.format(format, numUnit + 1, shortInfo, infoSkills);
        if (unit.isDead()) {
            info += " ";    // потому что при выводе черепа появляются глюки при выводе текста
        }
        String colorUnit = getColorUnit(player, numUnit);

        Color.printColor(info, colorUnit);
    }


    // фокус на первого игрока
    private void focusFirstPlayer() {
        focusPlayer(player1);
    }

    private void focusSecondPlayer() {
        focusPlayer(player2);
    }

    private void focusPlayer(Player player) {

        if (player == player1) {
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
    private Command inputCommand() {
        System.out.printf("[%s] %s, введите команду: ", playerCurrent.getName(), playerCurrent.getUnitCurrent().getNameLowerCase());
        String text = playerCurrent.nextCmd(scanner);
        return new Command(text);
    }

    //обработка команд
    private boolean processCommand(Command command) {
        if (command.isGoRight()) {
            return goRight();
        }

        if (command.isGoLeft()) {
            return goLeft();
        }

        if (command.isHelp()) {
            Info.printHelp(COLOR_HELP);
            return true;
        }

        if (command.isJoke()) {
            return randomJoke();
        }

        if (command.isPrintAllJokes()) {
            printAllJokes();
            return true;
        }

        //команды с параметрами
        //атака
        if (command.isAttack()) {
            int num = command.getPositiveNumOrErrCode() - 1;
            return attack(num);
        }

        //убить сразу
        if (command.isKill()) {
            int num = command.getPositiveNumOrErrCode() - 1;
            return killEnemy(num);
        }

        //лечение
        if (command.isCure()) {
            int num = command.getPositiveNumOrErrCode() - 1;
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
        if (playerCurrent.currentUnitIsLastInLine()) {
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

        int attackResult = ((Attackable) unit).attack(enemy);

        if (attackResult < 0) {
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
            case Attackable.CODE_ENEMY_DEAD:
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

        if (cureResult < 0) {
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
            case Medicinable.CODE_PATIENT_DEAD:
                System.out.printf("[%s] %s, убитому не помочь    \n", playerCurrent.getName(), errMessage);
                break;

            case Medicinable.CODE_PATIENT_HP_MAX:
                System.out.printf("[%s] %s, %s полностью здоров    \n", playerCurrent.getName(),
                        errMessage, patient.getNameLowerCase());
                break;

            case Medicinable.CODE_PATIENT_NO_MAN:
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
    public void printBattleFieldLine(int num) {
        System.out.print(BATTLE_FIELD_BORDER_CHAR);

        for (int cell = 0; cell < LIMIT_RIGHT + 1; cell++) {

            printUnitCoatOrEmptyInCellBattleField(player1, num, cell);

            System.out.print("  ");    //разделитель между вражескими юнитами, когда они станут в одну ячейку

            printUnitCoatOrEmptyInCellBattleField(player2, num, cell);

            System.out.print(BATTLE_FIELD_BORDER_CHAR);
        }
    }

    private void printUnitCoatOrEmptyInCellBattleField(Player player, int num, int cell) {
        Unit unit = player.getUnitByNum(num);

        if (unit == null || unit.getPosition() != cell) {
            System.out.print(EMPTY_SYMBOL);
            return;
        }

        char coat = unit.getCoat();
        String color;

        if (unit.isDead()) {
            color = COLOR_KILL;
        } else {
            color = getColorPlayer(player);
        }

        String coatString = String.valueOf(coat);
        Color.printColor(coatString, color);
    }

    public boolean goRight() {
        Unit unit = playerCurrent.getUnitCurrent();

        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", playerCurrent.getName(), unit.getNameLowerCase());
            return false;
        }

        boolean code = ((Movable) unit).goRightOneStep(LIMIT_LEFT, LIMIT_RIGHT);
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

        boolean code = ((Movable) unit).goLeftOneStep(LIMIT_LEFT, LIMIT_RIGHT);
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
        if (player1.isAllUnitsDead()) {
            return player2;

        } else if (player2.isAllUnitsDead()) {
            return player1;

        } else {
            return null;
        }
    }

    private boolean checkDraw() {
        return (cntNoAttack > MAX_ROUND_NO_ATTACK);
    }


    private void processNeedActions(Command command) {
        if (checkNeedPressForContinue(command)) {
            Util.pressEnterForContinue();
        }

        if (checkNeedFocusNextUnit(command)) {
            focusNextUnit();
        }

        if (checkNeedUpdatePage(command)) {
            printPage();
        }
    }

    //Если бы на этом этапе учебы мы знали про enum, я бы использовал enum для доп. атрибутирования команд,
    //но пока справляюсь как могу- checkNeedUpdatePage etc.
    private boolean checkNeedUpdatePage(Command command) {
        return command.isGo() || command.isJoke() || command.isAttack() || command.isKill() || command.isCure();
    }

    private boolean checkNeedFocusNextUnit(Command command) {
        return command.isGo() || command.isJoke() || command.isAttack() || command.isCure();
    }

    private boolean checkNeedPressForContinue(Command command) {
        return command.isJoke() || command.isPrintAllJokes() || command.isAttack() || command.isKill() || command.isCure();
    }


}
