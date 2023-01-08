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

    public static final int MAX_ROUND_NO_ATTACK = 4;     //максимальное количество ходов без атак

    private static final String VERSION = "5.0";
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
    private static final String COLOR_ERR = Color.ANSI_RED;

    private static final char EMPTY_SYMBOL = ' ';
    private static final char BATTLE_FIELD_BORDER_CHAR = '\'';

    private static final String MESSAGE_NO_WAY = "туда ходить нельзя";

    private static final int ONE_STEP_LEFT = -1;
    private static final int ONE_STEP_RIGHT = 1;

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    private final Board board;

    private int cntNoAttack; // счетчик ходов без атак

    private final Scanner scanner;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board(player1.getUnits(), player2.getUnits());

        scanner = new Scanner(System.in);
    }

    //========= основной блок ===========================
    public void go() {
        Info.printOnStart(VERSION);
        focusFirstPlayer();
        printPage();

        while (true) {
            Command command = readCommand();
            if (command.isEnd()) {
                break;
            }

            boolean result = executeCommand(command);
            if (!result) {
                continue;
            }

            if (isNeedPressForContinue(command)) {
                Util.pressEnterForContinue();
            }

            if (isNeedFocusNextUnit(command)) {
                focusNextUnit();
            }

            if (isNeedUpdatePage(command)) {
                printPage();
            }

            //Кто-то победил?
            if (isWin()) {
                Info.printOnWin(getWinPlayer(), COLOR_VICTORY, COLOR_ERR);
                break;
            }

            //ничья?
            if (isDraw()) {
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

        for (int i = 0; i < board.length(); i++) {

            //юнит игрока1
            format = "%d. %-11s %-22s   ";      // напр: "4. ⬦ Тунеядец  (♥15, :))"
            Unit[] units = board.line(i);

            Unit unit = units[0];
            printOneUnitOnBattleField(unit, i, format, getColorUnit(unit));

            //рисуем линию тактической карты
            printBattleFieldLine(i);

            //юнит игрока2
            format = "       " + format;
            unit = units[1];
            printOneUnitOnBattleField(unit, i, format, getColorUnit(unit));

            System.out.println();
        }
    }

    private void printOneUnitOnBattleField(Unit unit, int numUnit, String format, String color) {
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

        Color.printColor(info, color);
    }

    private Player getOtherPlayer() {
        return currentPlayer == player1 ? player2 : player1;
    }

    // фокус на первого игрока
    private void focusFirstPlayer() {
        focusPlayer(player1);
    }

    private void focusSecondPlayer() {
        focusPlayer(player2);
    }

    private void focusPlayer(Player player) {
        currentPlayer = player;
        currentPlayer.focusFirstLivingUnit();
    }

    //фокус на следующего игрокa
    private void focusNextPlayer() {
        if (getOtherPlayer().isAllUnitsDead()) { //враг убит полностью- фокус на самого себя
            focusPlayer(currentPlayer);
            return;
        }

        if (currentPlayer == player1) {
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

        if (player == currentPlayer) {
            return COLOR_FOCUS;
        } else {
            return Color.ANSI_RESET;
        }
    }

    //Цвет, каким распечатывать юнита (цветным- когда юнит в фокусе)
    private String getColorUnit(Unit unit) {
        Player player = null;
        if (player1.contain(unit)) {
            player = player1;
        } else if (player2.contain(unit)) {
            player = player2;
        }

        if (unit.isDead()) {
            return COLOR_KILL;
        }

        if (player == currentPlayer && player.getUnitCurrent() == unit) {
            return COLOR_FOCUS;
        } else {
            return Color.ANSI_RESET;
        }
    }

    //ввод команды
    private Command readCommand() {
        System.out.printf("[%s] %s, введите команду: ", currentPlayer.getName(), currentPlayer.getUnitCurrent().getNameLowerCase());
        String text = scanner.next();
        return new Command(text);
    }

    //обработка команд
    private boolean executeCommand(Command command) {
        if (command.isGoRight()) {
            return moveUnitRight();
        }

        if (command.isGoLeft()) {
            return moveUnitLeft();
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
        System.out.printf("[%s] неизвестная команда \n", currentPlayer.getName());
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
        if (currentPlayer.currentUnitIsLastInLine()) {
            focusNextPlayer();
        } else {
            currentPlayer.focusNextUnit();
        }
    }

    //атака на противника
    private boolean attack(int numEnemy) {
        Unit unit = currentPlayer.getUnitCurrent();
        if (!isAttackable(unit)) {
            System.out.printf("[%s] %s не умеет атаковать \n", currentPlayer.getName(), unit.getNameLowerCase());
            return false;
        }

        Unit enemy = getOtherPlayer().getUnit(numEnemy);
        if (enemy == null) {
            System.out.printf("[%s] неправильный номер для атаки, попробуйте еще раз \n", currentPlayer.getName());
            return false;
        }

        int attackResult = ((Attackable) unit).attack(enemy, board.getPosition(unit), board.getPosition(enemy));
        if (attackResult < 0) {
            printMessageAttackFail(unit, attackResult);
            return false;
        }

        cntNoAttack = 0; //сбрасываем счетчик ходов без атак

        System.out.printf("[%s] %s атакует: враг %s(%d) получил урон %d ед. \n", currentPlayer.getName(),
                unit.getNameLowerCase(),
                enemy.getNameLowerCase(),
                numEnemy + 1,
                attackResult);

        return true;
    }

    private void printMessageAttackFail(Unit unit, int codeMessage) {
        switch (codeMessage) {
            case Attackable.CODE_TOO_FAR:
                System.out.printf("[%s] %s атакует только в ближнем бою, подойдите к врагу вплотную \n", currentPlayer.getName(), unit.getNameLowerCase());
                break;
            case Attackable.CODE_ENEMY_DEAD:
                System.out.printf("[%s] нельзя атаковать убитого \n", currentPlayer.getName());
                break;
            default:
                System.out.printf("[%s] атака невозможна по неизвестной причине \n", currentPlayer.getName());
                break;
        }
    }

    private boolean killEnemy(int numEnemy) {
        Unit enemy = getOtherPlayer().getUnit(numEnemy);
        if (enemy == null) {
            System.out.printf("[%s] неправильный номер для моментального убийства, попробуйте еще раз \n", currentPlayer.getName());
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
        Unit unit = currentPlayer.getUnitCurrent();
        if (!isMedicinable(unit)) {
            System.out.printf("[%s] %s не умеет лечить \n", currentPlayer.getName(), unit.getNameLowerCase());
            return false;
        }

        Unit patient = currentPlayer.getUnit(numPatient);
        if (patient == null) {
            System.out.printf("[%s] неправильный номер для лечения \n", currentPlayer.getName());
            return false;
        }

        int cureResult = ((Medicinable) (unit)).cure(patient);
        if (cureResult < 0) {
            printMessageCureFail(patient, cureResult);
            return false;
        }

        System.out.printf("[%s] %s подлечил раненого, %s(%d) восстановил %d ед. здоровья   \n", currentPlayer.getName(),
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
                System.out.printf("[%s] %s, убитому не помочь    \n", currentPlayer.getName(), errMessage);
                break;

            case Medicinable.CODE_PATIENT_HP_MAX:
                System.out.printf("[%s] %s, %s полностью здоров    \n", currentPlayer.getName(),
                        errMessage, patient.getNameLowerCase());
                break;

            case Medicinable.CODE_PATIENT_NO_MAN:
                System.out.printf("[%s] %s, %s не является живым существом   \n", currentPlayer.getName(),
                        errMessage, patient.getNameLowerCase());
                break;

//            case Medicinable.CODE_IS_THIS:
//                System.out.printf("[%s] лечение невозможно, нельзя лечить самого себя   \n", playerCurrent.getName());
//                break;

            default:
                System.out.printf("[%s] %s по неизвестной причине   \n", currentPlayer.getName(), errMessage);
                break;
        }
    }

    private boolean isMedicinable(Unit unit) {
        return unit instanceof Medicinable;
    }

    //пошутить
    public boolean randomJoke() {
        Unit unit = currentPlayer.getUnitCurrent();

        if (!isJokable(unit)) {
            System.out.printf("[%s] %s не умеет шутить \n", currentPlayer.getName(), unit.getNameLowerCase());
            return false;
        }

        String story = ((Jokable) unit).randomJoke();
        System.out.printf("[%s] %s шутит: ", currentPlayer.getName(), unit.getNameLowerCase());
        Color.printlnColor(story, COLOR_HELP);

        return true;
    }

    private boolean isJokable(Unit unit) {
        return unit instanceof Jokable;
    }

    //для отрисовывки карты битвы построчно
    public void printBattleFieldLine(int num) {
        System.out.print(BATTLE_FIELD_BORDER_CHAR);

        for (int cell = 0; cell < Board.COLUMNS; cell++) {

            printUnitIconOrEmptyInCellBattleField(player1, num, cell);

            System.out.print("  ");    //разделитель между вражескими юнитами, когда они станут в одну ячейку

            printUnitIconOrEmptyInCellBattleField(player2, num, cell);

            System.out.print(BATTLE_FIELD_BORDER_CHAR);
        }
    }

    private void printUnitIconOrEmptyInCellBattleField(Player player, int num, int cell) {
        Unit unit = player.getUnit(num);

        if (unit == null || board.getPosition(unit) != cell) {
            System.out.print(EMPTY_SYMBOL);
            return;
        }

        char coat = unit.getIcon();
        String color;

        if (unit.isDead()) {
            color = COLOR_KILL;
        } else {
            color = getColorPlayer(player);
        }

        String coatString = String.valueOf(coat);
        Color.printColor(coatString, color);
    }

    private boolean moveUnit(int direction) {
        Unit unit = currentPlayer.getUnitCurrent();
        if (!isMovable(unit)) {
            System.out.printf("[%s] %s не умеет ходить \n", currentPlayer.getName(), unit.getNameLowerCase());
            return false;
        }
        int newPosition = board.getPosition(unit) + direction;

        boolean code = board.updatePosition(unit, newPosition);
        if (!code) {
            System.out.printf("[%s] %s \n", currentPlayer.getName(), MESSAGE_NO_WAY);
            return false;
        }

        return true;

    }

    public boolean moveUnitRight() {
        return moveUnit( ONE_STEP_RIGHT);
    }

    public boolean moveUnitLeft() {
        return moveUnit(ONE_STEP_LEFT);
    }

    private boolean isMovable(Unit unit) {
        return unit instanceof Movable;
    }

    private boolean isWin() {
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

    private boolean isDraw() {
        return (cntNoAttack > MAX_ROUND_NO_ATTACK);
    }

    //Если бы на этом этапе учебы мы знали про enum, я бы использовал enum для доп. атрибутирования команд,
    //но пока справляюсь как могу- checkNeedUpdatePage etc.
    private boolean isNeedUpdatePage(Command command) {
        return command.isGo() || command.isJoke() || command.isAttack() || command.isKill() || command.isCure();
    }

    private boolean isNeedFocusNextUnit(Command command) {
        return command.isGo() || command.isJoke() || command.isAttack() || command.isCure();
    }

    private boolean isNeedPressForContinue(Command command) {
        return command.isJoke() || command.isPrintAllJokes() || command.isAttack() || command.isKill() || command.isCure();
    }


}
