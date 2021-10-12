// Юникоды:
// https://forum.tamirov.ru/viewtopic.php?f=27&t=38
// https://wp-kama.ru/id_10136/100-yunikod-simvolov-kotorye-mozhno-ispolzovat-v-html-css-js-php.html
// https://saney.ru/tools/symbols.html
// ▲ ◈ ✠ ◉ ♦ ⛨

import java.util.Scanner;

public class Game {

    private static final int LEFT_MAP_POSITION = 0;
    public static final int RIGHT_MAP_MAX_POSITION = 3; //максимальная позиция, которую юнит может занимать на карте по горизонтали
    private static final int MAX_ROUND_NO_ATTACK = 4;     //максимальное количество ходов без атак

    //    private static final boolean PAUSE_ON = false;
    private static final boolean PAUSE_ON = true;
    private static final int PAUSE_ANIMATION = 2000;
    private static final int PAUSE_JOKE = 5000;         //время, что бы прочитать шутку

    private static final String VERSION = "3.71";

    //Цвета в программе
    private static final String COLOR_VICTORY = My.ANSI_GREEN;   //победа
    private static final String COLOR_DRAW = My.ANSI_CYAN;       //ничья
    private static final String COLOR_FOCUS = My.ANSI_GREEN;
    private static final String COLOR_HEADER = My.ANSI_PURPLE;
    private static final String COLOR_FOOTER = My.ANSI_BLUE;
    public static final String COLOR_HELP = My.ANSI_BLUE;
    private static final String COLOR_KILL = My.ANSI_RED;

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

    //    private Player[] players;
    private final Player player1;
    private final Player player2;
    private Player playerCurrent;
    private Player playerOther;

    private boolean exit;
    private int cntNoAttack; // счетчик ходов без атак
    private boolean draw;    //ничья
    private final Scanner scannerEnter;

    public Game() {
        player1 = new Player("Карл IV Великолепный", LEFT_MAP_POSITION);
        player2 = new Player("Барон Свиное Рыло", RIGHT_MAP_MAX_POSITION);
        scannerEnter = new Scanner(System.in);
    }

    //========= основной блок ===========================
    public void go() {
        Scanner sc = new Scanner(System.in);
        String cmd;

        System.out.println("ver." + VERSION + " Dedicated to the Heroes of Might and Magic II  ");
        playerFirstFocus();
        printPage();

        do {
            System.out.printf("[%s] %s, введите команду: ", playerCurrent.getName(), playerCurrent.getUnitCurrent().getName().toLowerCase());
            cmd = playerCurrent.nextCmd(sc);
            inputCmd(cmd);

            //Кто-то победил?
            if (player1.isAllUnitsDead() || player2.isAllUnitsDead()) {
                Player playerWin = (player1.isAllUnitsDead()) ? player2 : player1;
                My.printlnColor("⚑⚑⚑ ПОБЕДИЛ " + playerWin.getName() + " !!! ", COLOR_VICTORY);
                exit = true;
            }

        } while (!exit);

        if (draw) {
            My.printlnColor("⛨⛨⛨ НИЧЬЯ: " + MAX_ROUND_NO_ATTACK + " раунда без атак.", COLOR_DRAW);
        }

        //конец игры
        System.out.println();
        System.out.println("JAVA A01 \"ШАГ\", Запорожье 2021");
        System.out.println("Перцух Алексей");
    }
    //===================================================


    //Распечатываем главную страницу игры
    private void printPage() {
        String colorPlayer1 = getColorPlayer(player1);
        String colorPlayer2 = getColorPlayer(player2);

        printHeader();

        String str1 = colorPlayer1 + "⚑  " + player1.getName() + My.ANSI_RESET;
        str1 = String.format("%-51s", str1);
        String str2 = COLOR_HEADER + "-----ПОЛЕ БОЯ------";
        str2 = String.format("%-30s", str2);
        String str3 = colorPlayer2 + "⚑  " + player2.getName();
        str3 = String.format("%-38s", str3);

        System.out.printf("%s  %s  %s    \n", str1, str2, str3);

        String shortInfo = "";
        //распечатка отрядов
        int i = 0;
        do {
            shortInfo = player1.getUnitShortInfo(i);
            if (shortInfo == null) {
                break;
            }

            String colorUnit1 = getColorUnit(player1, i);
            String colorUnit2 = getColorUnit(player2, i);

            shortInfo = String.format("%d. %-35s    ", i + 1, shortInfo);
            My.printColor(shortInfo, colorUnit1);

            //рисуем карту
            System.out.print(getStrMap(i, colorPlayer1, colorPlayer2));

            shortInfo = player2.getUnitShortInfo(i);
            shortInfo = String.format("       %d. %-35s ", i + 1, shortInfo);
            My.printlnColor(shortInfo, colorUnit2);
            i++;
        } while (true);

        printFooter();
    }

    private void printHeader() {
        My.setTextColor(COLOR_HEADER);
        System.out.println("*************************************************************************************************************");
        System.out.println("                         ⛓✠⛓✠⛓✠⛓       HEROES OF JAVA CONSOLE      ⚔✠⚔✠⚔✠⚔                         ");
        System.out.println("*************************************************************************************************************");
        My.resetTextColor();
    }


    private void printFooter() {
        My.setTextColor(COLOR_FOOTER);
        String str = String.format("%s помощь   | %s%s идти   | %cномер_врага атаковать  | %cномер_союзника  лечить  | %s шутить  | 0 выход", CMD_HELP, CMD_RUN_LEFT, CMD_RUN_RIGHT, KEY_CMD_ATTACK, KEY_CMD_CURE, CMD_JOKE);
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        System.out.println(str);
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        My.resetTextColor();
    }

    private void printHelp() {
        My.setTextColor(COLOR_HELP);
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
        System.out.println("Примеры команд");
        System.out.println("Идти влево: " + CMD_RUN_LEFT);
        System.out.println("Идти вправо: " + CMD_RUN_RIGHT);
        System.out.println("Атаковать врага под номером 5: " + KEY_CMD_ATTACK + "5");
        System.out.println("Лечить союзника под номером 2: " + KEY_CMD_CURE + "2");
//        System.out.println("Дополнительная команда для распечатки всех шуток: " + CMD_PRINT_ALL_JOKE_STORIES);
        System.out.println("---");
        System.out.println("Обозначения");
        System.out.println(Unit.CHAR_HP + " здоровье");
        System.out.println(Build.CHAR_BUILD_HP + " прочность строения");
        System.out.println(Attackable.CHAR_ATTACK + " наносимый урон");
        System.out.println(Medicinable.CHAR_CURE + " уровень лечения");
        System.out.println(Jokable.STR_JOKE + " рассказывает шутки");
        System.out.println("---");
        System.out.println("https://github.com/AlexeyPertsukh/hw11-java-interface-game-heroes");
        System.out.println("---");
        My.resetTextColor();
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
        if (cntNoAttack > MAX_ROUND_NO_ATTACK) {  //ничья
            draw = true;
            exit = true;
        }
    }

    //Цвет, каким распечатывать игрока (цветным- когда игрок в фокусе)
    private String getColorPlayer(Player player) {

        if (player.isAllUnitsDead()) {
            return COLOR_KILL;
        }

        if (player == playerCurrent) {
            return COLOR_FOCUS;
        }
        return My.ANSI_RESET;
    }

    //Цвет, каким распечатывать юнита (цветным- когда юнит в фокусе)
    private String getColorUnit(Player player, int num) {
        if (player.getUnitByNum(num).isDead()) {
            return COLOR_KILL;
        }

        if (player == playerCurrent && player.getUnitCurrent() == player.getUnitByNum(num)) {
            return COLOR_FOCUS;
        }
        return My.ANSI_RESET;
    }

    //обработка команд
    private void inputCmd(String cmd) {
        switch (cmd) {
            case CMD_RUN_RIGHT:         //идти вправо
                if (!goRight()) {
                    return;
                }
                focusNextUnit();
                printPage();
                return;
            case CMD_RUN_LEFT:          //идти влево
                if (!goLeft()) {
                    return;
                }
                focusNextUnit();
                printPage();
                return;
            case CMD_HELP:              //помощь
                printHelp();
                return;
//            case CMD_SKIP:
//                System.out.println("пропуск хода");
//                focusNextUnit();
//                printPage();
//                return;
            case CMD_JOKE:
                if (joke()) {
                    focusNextUnit();
                    printPage();
                }
                return;
            case CMD_PRINT_ALL_JOKE_STORIES:       //распечатать все шутки
                Dangler dangler = new Dangler(0);
                dangler.printStories();
                return;
            case CMD_GAME_OVER:         //выйти из игры
                exit = true;
                return;
            default:
                break;
        }

        //атака
        int num = My.getIntFromCmdStr(cmd, KEY_CMD_ATTACK);

        if (num != My.CODE_NOT_OK) {
            num--;
            if (attack(num)) {
                pressEnterForContinue();
                focusNextUnit();
                printPage();
            }
            return;
        }

        //убить сразу
        num = My.getIntFromCmdStr(cmd, KEY_CMD_KILL);
        if (num != My.CODE_NOT_OK) {
            num--;
            if (attack(num, 1000)) {
                pressEnterForContinue();
                focusNextUnit();
                printPage();
            }
            return;
        }

        //лечение
        num = My.getIntFromCmdStr(cmd, KEY_CMD_CURE);
        if (num != My.CODE_NOT_OK) {
            num--;
            if (cure(num)) {
                pressEnterForContinue();
                focusNextUnit();
                printPage();
            }
            return;
        }
        //
        String str = String.format("[%s] неизвестная команда ", playerCurrent.getName());
        System.out.println(str);
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

        String str = "";
        if (enemy == null) {
            str = String.format("[%s] неправильный номер для атаки, попробуйте еще раз ", playerCurrent.getName());
            System.out.println(str);
            return false;
        }

        boolean isAttacker = unit instanceof Attackable;
        if (!isAttacker) {
            str = String.format("[%s] %s не умеет атаковать ", playerCurrent.getName(), unit.getName().toLowerCase());
            System.out.println(str);
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
                str = String.format("[%s] %s атакует только в ближнем бою, подойдите к врагу вплотную ", playerCurrent.getName(), unit.getName().toLowerCase());
                System.out.println(str);
                return false;
            case Attackable.CODE_IS_KILLED:
                str = String.format("[%s] нельзя атаковать убитого ", playerCurrent.getName());
                System.out.println(str);
                return false;
            default:
                break;
        }

        if (code < 0) {
            System.out.printf("[%s] атака невозможна по неизвестной причине   \n", playerCurrent.getName());
            return false;
        }

        cntNoAttack = 0; //сбрасываем счетчик ходов без атак

        str = String.format("[%s] %s атакует: враг %s(%d) получил урон %d ед.", playerCurrent.getName(), unit.getName().toLowerCase(), enemy.getName().toLowerCase(), playerOther.getNumUnits(enemy), code);
        System.out.println(str);

        return true;
    }


    //лечение
    public boolean cure(int num) {

        Unit patient = playerCurrent.getUnitByNum(num);
        Unit unit = playerCurrent.getUnitCurrent();

        if (patient == null) {
            return false;
        }

        boolean isMedic = unit instanceof Medicinable;
        if (!isMedic) {
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

    //пошутить
    public boolean joke() {
        Unit unit = playerCurrent.getUnitCurrent();
        boolean isJocker = unit instanceof Jokable;


        if (!isJocker) {
            System.out.printf("[%s] %s не умеет шутить \n", playerCurrent.getName(), unit.getName().toLowerCase());
            return false;
        }

        String story = ((Jokable) unit).joke();
        System.out.printf("[%s] %s шутит: ", playerCurrent.getName(), unit.getName().toLowerCase());
        My.printlnColor(story, COLOR_HELP);
        pressEnterForContinue();
        return true;
    }

    //для отрисовывки карты битвы построчно
    public String getStrMap(int num, String color1, String color2) {
        if (num < 0 || num >= player1.getUnitsArrLength()) {
            return "";
        }

        char line = '\'';

        String str = "";

        str += line;
        for (int i = 0; i < RIGHT_MAP_MAX_POSITION + 1; i++) {
            color1 = (player1.getUnitByNum(num).getPosition() == i && player1.getUnitByNum(num).isDead()) ? COLOR_KILL : color1;

            str += color1;
            str += (player1.getUnitByNum(num).getPosition() == i) ? player1.getUnitByNum(num).getCoat() : " ";
            str += My.ANSI_RESET;

            str += "  ";

            color2 = (player2.getUnitByNum(num).getPosition() == i && player2.getUnitByNum(num).isDead()) ? COLOR_KILL : color2;
            str += color2;
            str += (player2.getUnitByNum(num).getPosition() == i) ? player2.getUnitByNum(num).getCoat() : " ";
            str += My.ANSI_RESET;
            str += line;
        }
        return str;
    }


    public boolean goRight() {
        Unit unit = playerCurrent.getUnitCurrent();
        String str = "";
        boolean isRunner = unit instanceof Movable;

        if (!isRunner) {
            str = String.format("[%s] %s не умеет ходить ", playerCurrent.getName(), unit.getName().toLowerCase());
            System.out.println(str);
            return false;
        }

        boolean res = ((Movable) unit).goRightOneStep();
        if (!res) {
            str = String.format("[%s] %s ", playerCurrent.getName(), Movable.MSG_NO_WAY);
            System.out.println(str);
        }
        return res;

    }

    public boolean goLeft() {
        Unit unit = playerCurrent.getUnitCurrent();
        String str = "";

        boolean isRunner = unit instanceof Movable;
        if (!isRunner) {
            str = String.format("[%s] %s не умеет ходить ", playerCurrent.getName(), unit.getName().toLowerCase());
            System.out.println(str);
            return false;
        }

        boolean res = ((Movable) unit).goLeftOneStep();
        if (!res) {
            str = String.format("[%s] %s ", playerCurrent.getName(), Movable.MSG_NO_WAY);
            System.out.println(str);
        }
        return res;
    }

    private void pressEnterForContinue() {
        System.out.print("для продолжения нажмите <enter> ");
        scannerEnter.nextLine();
    }


}
