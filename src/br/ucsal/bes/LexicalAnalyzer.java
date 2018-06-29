package br.ucsal.bes;

import java.util.Arrays;

public class LexicalAnalyzer {

    private final String VALID_ALPHABET = "[A-Za-z0-9!=#&(/;\\[{+<>%)*,\\]|}\\-._\"\'\\s\\n]";
    private Character[] preparedSourceText;
    private Integer currentIndex;

    public LexicalAnalyzer(String sourceText) {
        this.preparedSourceText = prepareSource(sourceText);
        this.currentIndex = 0;
    }

    public LexEntry analyze(){
        LexEntry lexEntry =  null;
        String lexeme = "";

        while(!this.isFinished()){
            Character currChar = this.preparedSourceText[currentIndex];

            if(currChar.equals('\n') || currChar.equals('\r')){
                SynataticParser.incrementFileLine();
            }

            lexEntry = this.generateLexEntry(lexeme, lexeme + currChar);

            if(lexEntry != null) {
                this.currentIndex++;
                break;
            }

            if(this.isLastCharacter()){
                lexeme += currChar;
                Token token = Token.tokenOf(lexeme);
                if(token != null){
                    this.currentIndex++;
                    lexEntry = this.generateLexEntry(lexeme, token);
                    break;
                }
            }

            this.currentIndex++;

            if(!Character.isWhitespace(currChar) || lexeme.startsWith("\"")){
                lexeme += currChar;
            }

        }

        return lexEntry;
    }

    public Boolean isFinished(){
        return this.preparedSourceText.length == this.currentIndex;
    }

    private Boolean isLastCharacter(){
        return this.preparedSourceText.length == currentIndex + 1;
    }

    private LexEntry generateLexEntry(String lexeme, String nextLexeme){
        Token token = Token.tokenOf(lexeme);
        Token nextToken = Token.tokenOf(nextLexeme);

        if(token != null && nextToken == null){
            return this.generateLexEntry(lexeme, token);
        }

        return null;
    }

    private LexEntry generateLexEntry(String lexeme, Token token){

        if(token.isIdentifiable()){
            SymbolEntry symbolEntry = SynataticParser.addToOrGetFromSymbolTable(lexeme, token);
            return new LexEntry(lexeme, symbolEntry);
        }

        return new LexEntry(lexeme, token);
    }

    private Boolean isValidChar(Character charToTest){
        return charToTest.toString().matches(VALID_ALPHABET);
    }

    private Character[] prepareSource(String sourceText){
        return sourceText.chars()
                .filter(c -> this.isValidChar((char) c))
                .mapToObj(c -> (char) c).toArray(Character[]::new);
    }
}
