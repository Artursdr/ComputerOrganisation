package AssamblerHack;

import java.util.Hashtable;

public class SymbolTable {
    private static final int DATA_STARTING_ADDRESS = 16;
    private static final int DATA_ENDING_ADDRESS = 16384;
    private static final int PROGRAM_STARTING_ADDRESS = 0;
    private static final int PROGRAM_ENDING_ADDRESS = 32767;

    private Hashtable<String, Integer> symbolAddressMap;
    private int programAddress;
    private int dataAddress;

    public SymbolTable() {
        this.initializeSymbolTable();
        this.programAddress = PROGRAM_STARTING_ADDRESS;
        this.dataAddress = DATA_STARTING_ADDRESS;
    }

    private void initializeSymbolTable() {
        this.symbolAddressMap = new Hashtable<String, Integer>();
        this.symbolAddressMap.put("SP", Integer.valueOf(0));
        this.symbolAddressMap.put("LCL", Integer.valueOf(1));
        this.symbolAddressMap.put("ARG", Integer.valueOf(2));
        this.symbolAddressMap.put("THIS", Integer.valueOf(3));
        this.symbolAddressMap.put("THAT", Integer.valueOf(4));
        this.symbolAddressMap.put("R0", Integer.valueOf(0));
        this.symbolAddressMap.put("R1", Integer.valueOf(1));
        this.symbolAddressMap.put("R2", Integer.valueOf(2));
        this.symbolAddressMap.put("R3", Integer.valueOf(3));
        this.symbolAddressMap.put("R4", Integer.valueOf(4));
        this.symbolAddressMap.put("R5", Integer.valueOf(5));
        this.symbolAddressMap.put("R6", Integer.valueOf(6));
        this.symbolAddressMap.put("R7", Integer.valueOf(7));
        this.symbolAddressMap.put("R8", Integer.valueOf(8));
        this.symbolAddressMap.put("R9", Integer.valueOf(9));
        this.symbolAddressMap.put("R10", Integer.valueOf(10));
        this.symbolAddressMap.put("R11", Integer.valueOf(11));
        this.symbolAddressMap.put("R12", Integer.valueOf(12));
        this.symbolAddressMap.put("R13", Integer.valueOf(13));
        this.symbolAddressMap.put("R14", Integer.valueOf(14));
        this.symbolAddressMap.put("R15", Integer.valueOf(15));
        this.symbolAddressMap.put("SCREEN", Integer.valueOf(16384));
        this.symbolAddressMap.put("KBD", Integer.valueOf(24576));
    }

    // Adds the pair (symbol, address) to the table.
    public void addEntry(String symbol, int address) {
        this.symbolAddressMap.put(symbol, Integer.valueOf(address));
    }

    // Checks if the symbol table has the given symbol?
    public boolean contains(String symbol) {
        return this.symbolAddressMap.containsKey(symbol);
    }

    //  Returns the address from the symbol.
    public int getAddress(String symbol) {
        return this.symbolAddressMap.get(symbol);
    }

    public void incrementProgramAddress() {
        this.programAddress++;
        if (this.programAddress > PROGRAM_ENDING_ADDRESS) {
            throw new RuntimeException();
        }
    }

    public void incrementDataAddress() {
        this.dataAddress++;
        if (this.dataAddress > DATA_ENDING_ADDRESS) {
            throw new RuntimeException();
        }
    }

    public int getProgramAddress() {
        return this.programAddress;
    }

    public int getDataAddress() {
        return this.dataAddress;
    }
}
