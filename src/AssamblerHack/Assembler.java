package AssamblerHack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

public class Assembler {
    private File assemblyCode;
    private BufferedWriter machineCode;
    private Code encoder;
    private SymbolTable symbolTable;

    public Assembler(File source, File target) throws IOException {
        this.assemblyCode = source;

        // Buffered writer.
        FileWriter fw = new FileWriter(target);
        this.machineCode = new BufferedWriter(fw);

        // Assembler components.
        this.encoder = new Code();
        this.symbolTable = new SymbolTable();
    }

    // Translate assembly file -> machine language.
    public void translate() throws IOException {
        this.recordLabelAddress();
        this.parse();
    }

    // Parse source file.
    private void recordLabelAddress() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            CommandType commandType = parser.commandType();

            if (commandType.equals(CommandType.L_COMMAND)) {
                String symbol = parser.symbol();
                int address = this.symbolTable.getProgramAddress();
                this.symbolTable.addEntry(symbol, address);
            } else {
                this.symbolTable.incrementProgramAddress();
            }
        }
        parser.close();
    }

    private void parse() throws IOException {
        Parser parser = new Parser(this.assemblyCode);
        while (parser.hasMoreCommands()) {
            parser.advance();

            CommandType commandType = parser.commandType();
            String instruction = null;

            if (commandType.equals(CommandType.A_COMMAND)) {

                // Format A-Instruction.
                String symbol = parser.symbol();
                Character firstCharacter = symbol.charAt(0);
                boolean isSymbol = (!Character.isDigit(firstCharacter));

                String address = null;
                if (isSymbol) {
                    boolean symbolExists = this.symbolTable.contains(symbol);

                    // Record address if symbol does not exist.
                    if (!symbolExists) {
                        int dataAddress = this.symbolTable.getDataAddress();
                        this.symbolTable.addEntry(symbol, dataAddress);
                        this.symbolTable.incrementDataAddress();
                    }

                    // Address  of symbol.
                    address = Integer.toString(
                            this.symbolTable.getAddress(symbol));
                } else {
                    address = symbol;
                }

                instruction = this.formatAInstruction(address);
            } else if (commandType.equals(CommandType.C_COMMAND)) {

                // C-Instruction
                String comp = parser.comp();
                String dest = parser.dest();
                String jump = parser.jump();
                instruction = this.formatCInstruction(comp, dest, jump);
            }

            if (!commandType.equals(CommandType.L_COMMAND)) {

                // Binary instruction to file.
                this.machineCode.write(instruction);
                this.machineCode.newLine();
            }
        }


        parser.close();
        this.machineCode.flush();
        this.machineCode.close();
    }

    // Machine-format an A-Instruction.
    private String formatAInstruction(String address) {
        String formattedNumber = this.encoder.formatNumberAsBinary(address);
        return "0" + formattedNumber;
    }

    // Machine-format a C-Instruction.
    private String formatCInstruction( String comp, String dest, String jump) {
        StringWriter instruction = new StringWriter();
        instruction.append("111");
        instruction.append(this.encoder.comp(comp));
        instruction.append(this.encoder.dest(dest));
        instruction.append(this.encoder.jump(jump));
        return instruction.toString();
    }
}
