package org.javid.util;

import java.io.Console;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Screen {

    private static final Scanner SCANNER;
    private static final Console CONSOLE;
    public static final boolean IS_WINDOWS;
    public static final String USER_NAME;
    public static final String PC_NAME;
    public static final String SELECT_FROM_MENU;
    public static final String INVALID_CHOICE;
    private static final String INVALID_NUMBER;

    static {
        SCANNER = new Scanner(System.in);
        CONSOLE = System.console();
        IS_WINDOWS = System.getProperty("os.name").contains("Windows");
        USER_NAME = System.getProperty("user.name");
        PC_NAME = getComputerName();
        SELECT_FROM_MENU = "Select from menu: ";
        INVALID_CHOICE = "Invalid choice.";
        INVALID_NUMBER = "Invalid number.";
    }

    private Screen() {
        throw new IllegalStateException("Utility class");
    }

    private static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown-Computer");
    }

    public static int showMenu(String zeroItem, List<String> items) {
        return showMenu(SELECT_FROM_MENU, zeroItem, items);
    }

    public static int showMenu(String message, String zeroItem, List<String> items) {
        return showMenu(message, INVALID_CHOICE, zeroItem, items);
    }

    public static int showMenu(String message, String errorMessage, String zeroItem, List<String> items) {
        return showMenu("", message, errorMessage, zeroItem, items);
    }

    public static int showMenu(String header, String message, String errorMessage, String zeroItem, List<String> items) {
        return showMenu(header, "", message, errorMessage, zeroItem, items);
    }

    public static int showMenu(String header, String footer, String message, String errorMessage, String zeroItem, List<String> items) {
        int counter = 1;
        StringBuilder builder = new StringBuilder(header).append("\n");
        for (String item : items) {
            builder.append(String.format("%10s", counter + ") ")).append(item).append("\n");
            counter++;
        }
        builder.append(String.format("%10s", 0 + ") ")).append(zeroItem).append("\n");
        builder.append(footer).append("\n");
        System.out.println(builder);

        int userChoice = getInt(message, "Invalid number.");

        if (userChoice <= items.size() && userChoice >= 0) {
            return userChoice;
        }

        printError(errorMessage, 2000);
        return showMenu(header, footer, message, errorMessage, zeroItem, items);
    }

    public static String getStingDate(String message, String errorMessage) {
        Date date = getDate(message, errorMessage);
        return date == null ? null : date.toString();
    }

    public static Date getDate(String message, String errorMessage) {
        System.out.print(message);
        while (true) {
            System.out.println("Accepted format is yyyy-[m]m-[d]d");
            try {
                String s = SCANNER.nextLine().trim();
                if (s.isEmpty())
                    return null;
                return Date.valueOf(s);
            } catch (IllegalArgumentException e) {
                printError(errorMessage);
            }
        }
    }

    public static Date getFirstDayOfMonth(String message, String errorMessage) {
        System.out.print(message);
        while (true) {
            System.out.println("Accepted format is yyyy-[m]m");
            try {
                String s = SCANNER.nextLine().trim();
                if (s.isEmpty())
                    return null;
                return Date.valueOf(s + "-1");
            } catch (IllegalArgumentException e) {
                printError(errorMessage);
            }
        }
    }

    public static String getStringTime(String message, String errorMessage) {
        System.out.print(message);
        while (true) {
            System.out.println("Accepted format is hh:mm");
            try {
                String s = SCANNER.nextLine().trim();
                Time time = Time.valueOf(s + ":00");
                return time.toString();
            } catch (IllegalArgumentException e) {
                printError(errorMessage);
            }
        }
    }

    public static String getString(String message) {
        System.out.print(message);
        return SCANNER.nextLine().trim();
    }

    public static String getPassword(String message) {
        System.out.print(message);
        return CONSOLE == null ? new Scanner(System.in).nextLine()
                : String.valueOf(CONSOLE.readPassword());
    }

    public static int getInt(String message) {
        return getInt(message, INVALID_NUMBER);
    }

    public static int getInt(String message, String errorMessage) {
        System.out.print(message);
        while (true) {
            try {
                int i = SCANNER.nextInt();
                SCANNER.nextLine();
                return i;
            } catch (NumberFormatException | InputMismatchException e) {
                printError(errorMessage);
            }
            SCANNER.nextLine();
        }
    }

    public static long getLong(String message) {
        return getLong(message, INVALID_NUMBER);
    }

    public static long getLong(String message, String errorMessage) {
        System.out.print(message);
        while (true) {
            try {
                long l = SCANNER.nextLong();
                SCANNER.nextLine();
                return l;
            } catch (NumberFormatException | InputMismatchException e) {
                printError(errorMessage);
            }
            SCANNER.nextLine();
        }
    }

    public static void printError(String message) {
        printError(message, 500);
    }

    public static void printError(String message, int waitFor) {
        System.err.println(message);
        try {
            Thread.sleep(waitFor);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void clearScreen() {
        try {
            if (IS_WINDOWS) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}
