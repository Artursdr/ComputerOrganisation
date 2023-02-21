package AssamblerHack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private BufferedReader reader;
    private String currentLine;
    private String nextLine;

    // Opens the input file/stream and gets ready.
    public Parser(File source) throws IOException {
        if (source == null) {
            throw new NullPointerException("source");
        }
        if (!source.exists()) {
            throw new FileNotFoundException(source.getAbsolutePath());
        }

        this.reader = new BufferedReader(new FileReader(source));
        this.currentLine = null;
        this.nextLine = this.getNextLine();
    }

    private String getNextLine() throws IOException {
        String nextLine;

        do {
            nextLine = this.reader.readLine();

            if (nextLine == null) {
                return null;
            }
        } while (nextLine.trim().isEmpty() || this.isComment(nextLine));

        int commentIndex = nextLine.indexOf("//");
        if (commentIndex != -1) {
            nextLine = nextLine.substring(0, commentIndex - 1);
        }

        return nextLine;
    }

    private boolean isComment(String input) {
        return input.trim().startsWith("//");
    }

    @Override
    public void finalize() {
        this.close();
    }

    // Close the input file/stream and release the resource.
    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            // No operation.
        }
    }

    // Checks if there are more commands in the input?
    public boolean hasMoreCommands() {
        return (this.nextLine != null);
    }

    // Reads the next command from 	the input and makes it the current command.
    // Should be called only if hasMoreCommands() is true. Initially there is no
    // current command.
    public void advance() throws IOException {
        this.currentLine = this.nextLine;
        this.nextLine = this.getNextLine();
    }

    // Returns current commands.

    public CommandType commandType() {
        String trimmedLine = this.currentLine.trim();

        if (trimmedLine.startsWith("(") && trimmedLine.endsWith(")")) {
            return CommandType.L_COMMAND;
        } else if (trimmedLine.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    // Returns the symbol or decimal.
    public String symbol() {
        String trimmedLine = this.currentLine.trim();

        if (this.commandType().equals(CommandType.L_COMMAND)) {
            return trimmedLine.substring(1, this.currentLine.length() - 1);
        } else if (this.commandType().equals(CommandType.A_COMMAND)) {
            return trimmedLine.substring(1);
        } else {
            return null;
        }
    }

    // Returns the dest mnemonic of the current C-command (8 possibilities).
    public String dest() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");

        if (destIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(0, destIndex);
        }
    }

    // Returns the comp mnemonic of the current C-command (28 possibilities).
    public String comp() {
        String trimmedLine = this.currentLine.trim();
        int destIndex = trimmedLine.indexOf("=");
        if (destIndex != -1) {
            trimmedLine = trimmedLine.substring(destIndex + 1);
        }
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return trimmedLine;
        } else {
            return trimmedLine.substring(0, compIndex);
        }
    }

    // Returns the jump mnemonic of the current C-command (28 possibilities).
    public String jump() {
        String trimmedLine = this.currentLine.trim();
        int compIndex = trimmedLine.indexOf(";");

        if (compIndex == -1) {
            return null;
        } else {
            return trimmedLine.substring(compIndex + 1);
        }
    }
}
