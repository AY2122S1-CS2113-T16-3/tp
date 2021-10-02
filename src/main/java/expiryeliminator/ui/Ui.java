package expiryeliminator.ui;

import java.util.Scanner;

public class Ui {
    //@@author bernardboey-reused
    // Reused from https://github.com/bernardboey/ip/blob/master/src/main/java/duke/ui/Ui.java
    // with minor modifications
    private static final String INDENTED_HORIZONTAL_LINE = " ".repeat(4) + "_".repeat(60);
    private static final String LINE_PREFIX = " ".repeat(5);
    /** Platform independent line separator. */
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Reads input commands from the user.
     * Ignores blank lines and trims input command.
     *
     * @return Trimmed input command.
     */
    public String getUserInput() {
        String line = SCANNER.nextLine();
        // Ignore blank lines
        while (line.trim().isEmpty()) {
            line = SCANNER.nextLine();
        }
        return line.trim();
    }

    /**
     * Prints out the specified text formatted as a block.
     * Horizontal lines will be printed before and after the
     * specified text, and the text will be indented.
     *
     * @param text Text to be printed out.
     */
    public void showToUser(String text) {
        System.out.println(INDENTED_HORIZONTAL_LINE);
        System.out.println(addPrefixAndReplaceNewline(text));
        System.out.println(INDENTED_HORIZONTAL_LINE);
        System.out.println();
    }

    /**
     * Prints out greeting message.
     */
    public void showGreeting() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What is your name?");
    }

    /**
     * Adds {@link #LINE_PREFIX} to the start of each line of {@code text}, and replaces newline characters with the
     * platform independent line separator ({@link #LINE_SEPARATOR}).
     *
     * @param text Text to be processed.
     * @return Processed text.
     */
    private String addPrefixAndReplaceNewline(String text) {
        String[] lines = text.split("\n");
        for (int i = 0; i < lines.length; i++) {
            lines[i] = LINE_PREFIX + lines[i];
        }
        return String.join(LINE_SEPARATOR, lines);
    }
    //@@author
}