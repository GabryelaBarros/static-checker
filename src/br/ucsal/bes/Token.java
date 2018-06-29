package br.ucsal.bes;

import java.util.regex.Pattern;

public enum Token {
    Bool("Bool", "A01", TokenType.Reserved),
    While("While", "A02", TokenType.Reserved),
    Break("Break", "A03", TokenType.Reserved),
    Void("Void", "A04", TokenType.Reserved),
    Char("Char", "A05", TokenType.Reserved),
    True("True", "A06", TokenType.Reserved),
    Else("Else", "A07", TokenType.Reserved),
    String("String", "A08", TokenType.Reserved),
    End("End", "A09", TokenType.Reserved),
    Return("Return", "A10", TokenType.Reserved),
    False("False", "A11", TokenType.Reserved),
    Program("Program", "A12", TokenType.Reserved),
    Float("Float", "A13", TokenType.Reserved),
    Int("Int", "A14", TokenType.Reserved),
    If("If", "A15", TokenType.Reserved),
    NotEqual("!=", "B01", TokenType.Reserved),
    HashTag("#", "B02", TokenType.Reserved),
    Ampersand("&", "B03", TokenType.Reserved),
    OpenParentheses("(", "B04", TokenType.Reserved),
    Slash("/", "B05", TokenType.Reserved),
    Semicolon(";", "B06", TokenType.Reserved),
    OpenBracket("[", "B07", TokenType.Reserved),
    OpenBrace("{", "B08", TokenType.Reserved),
    Plus("+", "B09", TokenType.Reserved),
    LesserThanEqual("<=", "B10", TokenType.Reserved),
    Equal("=", "B11", TokenType.Reserved),
    GreaterThanEqual(">=", "B12", TokenType.Reserved),
    ExclamationPoint("!", "B13", TokenType.Reserved),
    PercentSign("%", "B14", TokenType.Reserved),
    CloseParentheses(")", "B15", TokenType.Reserved),
    Asterisk("*", "B16", TokenType.Reserved),
    Comma(",", "B17", TokenType.Reserved),
    CloseBracket("]", "B18", TokenType.Reserved),
    Pipe("|", "B19", TokenType.Reserved),
    CloseBrace("}", "B20", TokenType.Reserved),
    LesserThan("<", "B21", TokenType.Reserved),
    DoubleEqual("==", "B22", TokenType.Reserved),
    GreaterThan(">", "B23", TokenType.Reserved),
    Minus("-", "B24", TokenType.Reserved),
    Character("'[A-Z]'", "C01", TokenType.CH),
    ConstantString("\"([A-Z\\s0-9$_.])+\"", "C02", TokenType.CS),
    FloatNumber("[0-9]+\\.[0-9]+(E(-|\\+)*[0-9]+)?", "C03", TokenType.FL),
    Identifier("([A-Z_])([A-Z0-9_])*", "C05", TokenType.ID),
    Function("[A-Z]|(([A-Z_])([A-Z0-9_])*)[A-Z0-9]", "C04", TokenType.FN),
    IntegerNumber("[0-9]+\\.?", "C06", TokenType.IN);
//    SubMachine("", "M01", TokenType.Identifiable);
	

    private String pattern;
    private String code;
    private TokenType type;

    Token(String stringPattern, String code, TokenType type){
        this.pattern = TokenType.Reserved.equals(type) ? Pattern.quote(stringPattern) : stringPattern;
        this.code = code;
        this.type = type;
    }

    public Boolean isIdentifiable(){
        return !TokenType.Reserved.equals(this.type);
    }

    public static Token tokenOf(String lexeme){
        Token[] tokens = Token.values();

        for (Token token: tokens) {

            if(token.isIdentifiable() && lexeme.toUpperCase().matches(token.pattern)){
                return token;
            }else if(lexeme.toUpperCase().matches(token.pattern.toUpperCase())){
                return token;
            }
        }

        return null;
    }

    @Override
    public java.lang.String toString() {
        return "Token{" +
                "name='" + this.name() + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                '}';
    }
}
