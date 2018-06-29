package br.ucsal.bes;

import java.util.LinkedHashSet;
import java.util.Set;

public class SymbolEntry {

    private String lexeme;
    private Token token;
    private Integer symbolTableIndex;
    private Integer lengthBeforeTruncate;
    private Integer lengthAfterTruncate;
    private Set<Integer> appearsInLines;

    public SymbolEntry(String lexeme, Token token, Integer symbolTableIndex, Integer lengthBeforeTruncate, Integer lengthAfterTruncate) {
        this.lexeme = lexeme;
        this.token = token;
        this.symbolTableIndex = symbolTableIndex;
        this.lengthBeforeTruncate = lengthBeforeTruncate;
        this.lengthAfterTruncate = lengthAfterTruncate;
        this.appearsInLines =  new LinkedHashSet<>();
    }

    public Token getToken() {
        return token;
    }

    public Integer getSymbolTableIndex() {
        return symbolTableIndex;
    }

    public void addLineNumber(Integer lineNumber, Integer maxLines){
        if(appearsInLines.size() < maxLines){
            appearsInLines.add(lineNumber);
        }
    }

    @Override
    public String toString() {
        return "SymbolEntry{" +
                "lexeme='" + lexeme + '\'' +
                ", token=" + token +
                ", symbolTableIndex=" + symbolTableIndex +
                ", lengthBeforeTruncate=" + lengthBeforeTruncate +
                ", lengthAfterTruncate=" + lengthAfterTruncate +
                ", appearsInLines=" + appearsInLines +
                '}';
    }
}
