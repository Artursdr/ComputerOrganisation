package AssamblerHack;

import java.util.Hashtable;

public class Code {
    private Hashtable<String, String> destMnemonics;
    private Hashtable<String, String> compMnemonics;
    private Hashtable<String, String> jumpMnemonics;

    public Code() {
        this.jumpMnemonics = new Hashtable<String, String>();
        this.populateJumpMnemonics();
        this.compMnemonics = new Hashtable<String, String>();
        this.populateCompMnemonics();
        this.destMnemonics = new Hashtable<String, String>();
        this.populateDestMnemonics();
    }

    private void populateJumpMnemonics() {
        this.jumpMnemonics.put("NULL", "000");
        this.jumpMnemonics.put("JGT", "001");
        this.jumpMnemonics.put("JEQ", "010");
        this.jumpMnemonics.put("JGE", "011");
        this.jumpMnemonics.put("JLT", "100");
        this.jumpMnemonics.put("JNE", "101");
        this.jumpMnemonics.put("JLE", "110");
        this.jumpMnemonics.put("JMP", "111");
    }

    private void populateCompMnemonics() {
        this.compMnemonics.put("0", "0101010");
        this.compMnemonics.put("1", "0111111");
        this.compMnemonics.put("-1", "0111010");
        this.compMnemonics.put("D", "0001100");
        this.compMnemonics.put("A", "0110000");
        this.compMnemonics.put("M", "1110000");
        this.compMnemonics.put("!D", "0001101");
        this.compMnemonics.put("!A", "0110001");
        this.compMnemonics.put("!M", "1110001");
        this.compMnemonics.put("-D", "0001111");
        this.compMnemonics.put("-A", "0110011");
        this.compMnemonics.put("-M", "1110011");
        this.compMnemonics.put("D+1", "0011111");
        this.compMnemonics.put("A+1", "0110111");
        this.compMnemonics.put("M+1", "1110111");
        this.compMnemonics.put("D-1", "0001110");
        this.compMnemonics.put("A-1", "0110010");
        this.compMnemonics.put("M-1", "1110010");
        this.compMnemonics.put("D+A", "0000010");
        this.compMnemonics.put("D+M", "1000010");
        this.compMnemonics.put("D-A", "0010011");
        this.compMnemonics.put("D-M", "1010011");
        this.compMnemonics.put("A-D", "0000111");
        this.compMnemonics.put("M-D", "1000111");
        this.compMnemonics.put("D&A", "0000000");
        this.compMnemonics.put("D&M", "1000000");
        this.compMnemonics.put("D|A", "0010101");
        this.compMnemonics.put("D|M", "1010101");
    }

    private void populateDestMnemonics() {
        this.destMnemonics.put("NULL", "000");
        this.destMnemonics.put("M", "001");
        this.destMnemonics.put("D", "010");
        this.destMnemonics.put("MD", "011");
        this.destMnemonics.put("A", "100");
        this.destMnemonics.put("AM", "101");
        this.destMnemonics.put("AD", "110");
        this.destMnemonics.put("AMD", "111");
    }

    //  Returns the binary code of the dest mnemonic.
    public String dest(String mnemonic) {
        if (mnemonic == null || mnemonic.isEmpty()) {
            mnemonic = "NULL";
        }

        return this.destMnemonics.get(mnemonic);
    }

    //  Returns the binary code of the comp mnemonic.
    public String comp(String mnemonic) {
        return this.compMnemonics.get(mnemonic);
    }

    //  Returns the binary code of the jump mnemonic.
    public String jump(String mnemonic) {
        if (mnemonic == null || mnemonic.isEmpty()) {
            mnemonic = "NULL";
        }

        return this.jumpMnemonics.get(mnemonic);
    }

    // Format a number as a 15-bit.
    public String formatNumberAsBinary(String number) {
        int value = Integer.parseInt(number);
        String binaryNumber = Integer.toBinaryString(value);
        String formattedBinaryNumber =
                String.format("%15s", binaryNumber).replace(' ', '0');
        return formattedBinaryNumber;
    }
}
