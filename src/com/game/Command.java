package com.game;

public class Command {

    public static final String CMD_ATTACK = "*";
    public static final String CMD_KILL = "#";
    public static final String CMD_CURE = "@";

    public static final String CMD_HELP = "?";
    public static final String CMD_GAME_OVER = "END";
    public static final String CMD_GO_RIGHT = ">";
    public static final String CMD_GO_LEFT = "<";
    public static final String CMD_JOKE = "$";
    public static final String CMD_PRINT_ALL_JOKES = "~";
    public static final String CMD_SKIP = "%";

    public static final int CODE_ERR = -1;

    private final String text;

    public Command(String text) {
        this.text = text;
    }

    public boolean isEnd() {
        return text.equalsIgnoreCase(CMD_GAME_OVER);
    }

    public boolean isGoRight() {
        return text.equalsIgnoreCase(CMD_GO_RIGHT);
    }

    public boolean isGoLeft() {
        return text.equalsIgnoreCase(CMD_GO_LEFT);
    }

    public boolean isGo() {
        return isGoRight() || isGoLeft();
    }

    public boolean isJoke() {
        return text.equalsIgnoreCase(CMD_JOKE);
    }

    public boolean isHelp() {
        return text.equalsIgnoreCase(CMD_HELP);
    }

    public boolean isPrintAllJokes() {
        return text.equalsIgnoreCase(CMD_PRINT_ALL_JOKES);
    }

    public boolean isKill() {
        return isComplexCommand(CMD_KILL);
    }

    public boolean isAttack() {
        return isComplexCommand(CMD_ATTACK);
    }

    public boolean isCure() {
        return isComplexCommand(CMD_CURE);
    }


    private boolean isComplexCommand(String typeCmd) {
        String[] args = getArgs();
        if(args.length < 2) {
            return false;
        }

        String arg1 = args[0];
        String arg2 = args[1];

        return arg1.equalsIgnoreCase(typeCmd) && Util.isInteger(arg2);
    }


    public int getPositiveNumOrErrCode() {
        String[] args = getArgs();

        if(args.length > 1 && Util.isInteger(args[1])) {
            int num = Integer.parseInt(args[1]);
            if (num >= 0) {
                return num;
            }
        }
        return CODE_ERR;
    }

    // "text" -> "text"
    // "text123" -> "text", "123"
    public String[] getArgs() {
        char[] chars= text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if(c == '-' || (c >= '0' && c <= '9')) {
                return new String[]{text.substring(0, i), text.substring(i)};
            }
        }

        return new String[]{text};
    }

}
