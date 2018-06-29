package br.ucsal.bes;

public class LexEntry {

    private String lexeme;
    private Token token;
    private Integer symbolTableIndex;

    public LexEntry(String lexeme, SymbolEntry symbolEntry) {
        this.lexeme = lexeme;
        this.token = symbolEntry.getToken();
        this.symbolTableIndex = symbolEntry.getSymbolTableIndex();
    }

    public LexEntry(String lexeme, Token token) {
        this.lexeme = lexeme;
        this.token = token;
        this.symbolTableIndex = null;
    }

    @Override
    public String toString() {
        return "LexEntry{" +
                "lexeme='" + lexeme + '\'' +
                ", token=" + token +
                ", symbolTableIndex=" + symbolTableIndex +
                '}';
    }
}
