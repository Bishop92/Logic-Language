package Lexer;

import Common.Symbol;
import Common.SymbolsTable;

import java.util.HashMap;
import java.util.Map;

public class Lexer {
    //The code to analyze
    private String SourceCode_;

    //The table of all the symbols
    private SymbolsTable SymbolsTable_;

    //The map used to map the keyword to the related tags
    private Map<String, Tag> KeywordTags_;

    //The current position of the code to analyze
    private int Peek_;

    //The current position of the lookahead to analyze the code
    private int LookAhead_;

    //The current line that the lexer is parsing
    private int Line_;

    //The current char that the lexer is parsing
    private int Char_;

    public Lexer(String SourceCode_i) {
        SourceCode_ = SourceCode_i;
        SymbolsTable_ = new SymbolsTable();
	    KeywordTags_ = new HashMap<>();
        Peek_ = 0;
        LookAhead_ = 0;
        Line_ = 0;
        Char_ = 0;

        RegisterKeywords();
    }

    private void RegisterKeywords() {
        RegisterKeyword("def", Tag.DEF);
        RegisterKeyword("as", Tag.AS);
        RegisterKeyword("int", Tag.INTTYPE);
        RegisterKeyword("float", Tag.FLOATTYPE);
        RegisterKeyword("bool", Tag.BOOLTYPE);
        RegisterKeyword("string", Tag.STRINGTYPE);

        /*MySymbolsTable.AddReservedKeyword("isa");
        MySymbolsTable.AddReservedKeyword("ax");
        MySymbolsTable.AddReservedKeyword("rule");
        MySymbolsTable.AddReservedKeyword("true");
        MySymbolsTable.AddReservedKeyword("false");
        MySymbolsTable.AddReservedKeyword("and");
        MySymbolsTable.AddReservedKeyword("or");
        MySymbolsTable.AddReservedKeyword("not");
        MySymbolsTable.AddReservedKeyword("xor");
        MySymbolsTable.AddReservedKeyword("properties");
        MySymbolsTable.AddReservedKeyword("on");
        MySymbolsTable.AddReservedKeyword("event");*/
    }

    private void RegisterKeyword(String Keyword_i, Tag KeywordTag_i) {

	    Keyword_i = Keyword_i.toLowerCase();

        KeywordTags_.put(Keyword_i, KeywordTag_i);
        SymbolsTable_.AddReservedKeyword(Keyword_i);

    }

    public Token GetNextToken() {
        SkipSpacesAndComments();

        char CurrentChar = GetCurrentChar();
        if (Character.isLetter(CurrentChar)) {
            //It's an identifier
            return ParseIDToken();
        } else if (Character.isDigit(CurrentChar)) {
            //It's a number
            return ParseNumToken();
        } else if (CurrentChar == '<') {
            //It's <, <=, <=>
            return ParseLowerToken();
        } else if (CurrentChar == '>') {
            //It's >, >=
            return ParseGreaterToken();
        } else if (CurrentChar == '=') {
            //It's =, ==, =>
            return ParseEqualToken();
        } else if (CurrentChar == ':') {
            //It's :
            return ParseColonToken();
        } else if (CurrentChar == '(' || CurrentChar == ')') {
            //It's (, )
            return ParseParenthesesToken();
        } else if (CurrentChar == '{' || CurrentChar == '}') {
            //It's {, }
            return ParseBracesToken();
        }  else if (CurrentChar == ',') {
            //It's ,
            return ParseCommaToken();
        }  else if (CurrentChar == ';') {
            //It's ;
            return ParseSemiColonToken();
        } else if (CurrentChar == '+') {
            //It's +
            return ParseAddToken();
        } else if (CurrentChar == '-') {
            //It's -
            return ParseMinusToken();
        } else if (CurrentChar == '*') {
            //It's *
            return ParseMultiplyToken();
        } else if (CurrentChar == '/') {
            //It's /
            return ParseDivideToken();
        } else if (Peek_ < SourceCode_.length()) {
            System.out.println("Error on line: " + Line_ + " at char: " + Char_ + ". Unexpected character found: " + CurrentChar + "(" + (int)CurrentChar + ")");
        }

	    if(Peek_ == SourceCode_.length()) {
		    return new Token(Tag.EOF, Char_, Line_, 0);
	    }

        return null;
    }

    private void SkipSpacesAndComments() {
        char CurrentChar = GetCurrentChar();
        boolean CommentsFound = true;
        while (CommentsFound) {

            CommentsFound = false;

            while (IsASpace(CurrentChar) || IsATab(CurrentChar) || IsAReturn(CurrentChar)) {
                if(IsASpecialReturn(CurrentChar)) {
                    ++Peek_;
                }
                if (IsAReturn(CurrentChar)) {
                    ++Line_;
                    Char_ = 0;
                } else {
                    ++Char_;
                }

                ++Peek_;
                CurrentChar = GetCurrentChar();
            }

            if (CurrentChar == '/') {
                char NextChar = GetLookAhead();
                if (NextChar == '/') {
                    CommentsFound = true;
                    while (Peek_ < SourceCode_.length() && !IsAReturn(CurrentChar)) {
                        ++Peek_;
                        CurrentChar = GetCurrentChar();
                    }
                    if (Peek_ < SourceCode_.length()) {
                        ++Line_;
                        Char_ = 0;
                        ++Peek_;
                        CurrentChar = GetCurrentChar();
                    }
                } else if (NextChar == '*') {
                    Peek_ += 2;
                    Char_ += 2;
                    CommentsFound = true;

                    int CommentLine = Line_;
                    int CommentChar = Char_ - 2;

                    CurrentChar = GetCurrentChar();
                    while (Peek_ < SourceCode_.length() && !(CurrentChar == '*' && GetLookAhead() == '/')) {
                        if (IsAReturn(CurrentChar)) {
                            ++Line_;
                            Char_ = 0;
                        } else {
                            ++Char_;
                        }
                        ++Peek_;
                        CurrentChar = GetCurrentChar();
                    }

                    if (Peek_ == SourceCode_.length()) {
                        System.err.println("Missing */ for comment open at line: " + CommentLine + " char: " + CommentChar);
                    }

                    Peek_ += 2;
                    Char_ += 2;
                    CurrentChar = GetCurrentChar();
                }
            }
        }
    }

    private Token ParseIDToken() {
        String Name = "";
        char CurrentChar = GetCurrentChar();

        int Position = Peek_;

        while (Character.isLetterOrDigit(CurrentChar) || CurrentChar == '_') {
            Name += CurrentChar;
            ++Peek_;
            ++Char_;
            CurrentChar = GetCurrentChar();
        }

	    Name = Name.toLowerCase();

        if(KeywordTags_.containsKey(Name)) {
	        return new IDToken(SymbolsTable_.GetSymbol(Name), KeywordTags_.get(Name), Position, Line_);
        } else {
	        Symbol SymbolInTable = SymbolsTable_.GetSymbol(Name);
	        if(SymbolInTable == null) SymbolInTable = SymbolsTable_.AddSymbol(Name);

            return new IDToken(SymbolInTable, Position, Line_);
        }
    }

    private Token ParseNumToken() {
        String Value = "";
        char CurrentChar = GetCurrentChar();

        int Position = Peek_;

        while (Character.isDigit(CurrentChar)) {
            Value += CurrentChar;
            ++Peek_;
            ++Char_;
            CurrentChar = GetCurrentChar();
        }

        if (CurrentChar == '.') {
            Value += CurrentChar;
            ++Peek_;
            ++Char_;
            CurrentChar = GetCurrentChar();

            while (Character.isDigit(CurrentChar)) {
                Value += CurrentChar;
                ++Peek_;
                ++Char_;
                CurrentChar = GetCurrentChar();
            }

            return new FloatToken(Float.parseFloat(Value), Position, Line_, Value.length());
        } else {
            return new IntegerToken(Integer.parseInt(Value), Position, Line_, Value.length());
        }
    }

    private Token ParseLowerToken() {
        char NextChar = GetLookAhead();

        int Position = Peek_;

        if(NextChar == '=') {
            NextChar = GetLookAhead();
            if(NextChar == '>') {
                Peek_ += 3;
                Char_ += 3;
                return new Token(Tag.IIF, Position, Line_, 3);
            }
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.LET, Position, Line_, 2);
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.LT, Position, Line_, 1);
    }

    private Token ParseGreaterToken() {

        int Position = Peek_;

        char NextChar = GetLookAhead();
        if (NextChar == '=') {
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.GET, Position, Line_, 2);
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.GT, Position, Line_, 1);
    }

    private Token ParseEqualToken() {

        int Position = Peek_;

        char NextChar = GetLookAhead();
        switch (NextChar) {
            case '=':
                Peek_ += 2;
                Char_ += 2;
                return new Token(Tag.EQ, Position, Line_, 2);
            case '>':
                Peek_ += 2;
                Char_ += 2;
                return new Token(Tag.IMPLY, Position, Line_, 2);
            default:
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.ASSIGN, Position, Line_, 1);
    }

    private Token ParseColonToken() {

        int Position = Peek_;

        ++Peek_;
        ++Char_;
        return new Token(Tag.COLON, Position, Line_, 1);
    }

    private Token ParseParenthesesToken() {

        int Position = Peek_;

        char CurrentChar = GetCurrentChar();
        ++Peek_;
        ++Char_;
        if(CurrentChar == '(') {
            return new Token(Tag.OPAR, Position, Line_, 1);
        }
        return new Token(Tag.CPAR, Position, Line_, 1);
    }

    private Token ParseBracesToken() {

        int Position = Peek_;

        char CurrentChar = GetCurrentChar();

        ++Peek_;
        ++Char_;
        if(CurrentChar == '{') {
            SymbolsTable_ = new SymbolsTable(SymbolsTable_);
            return new Token(Tag.OBRACE, Position, Line_, 1);
        }

        if(SymbolsTable_.GetOwner() != null) {
            SymbolsTable_ = SymbolsTable_.GetOwner();
        }

        return new Token(Tag.CBRACE, Position, Line_, 1);
    }

    private Token ParseCommaToken() {

        int Position = Peek_;

        ++Peek_;
        ++Char_;
        return new Token(Tag.COMMA, Position, Line_, 1);
    }

    private Token ParseSemiColonToken() {

        int Position = Peek_;

        ++Peek_;
        ++Char_;
        return new Token(Tag.SEMICOLON, Position, Line_, 1);
    }

    private Token ParseAddToken() {

        int Position = Peek_;

        if(GetLookAhead() == '=') {
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.INCREMENT, Position, Line_ , 2);
        }

        ++Peek_;
        ++Char_;
        return new Token(Tag.ADD, Position, Line_, 1);
    }

    private Token ParseMinusToken() {

        int Position = Peek_;

        if(GetLookAhead() == '=') {
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.DECREMENT, Position, Line_ , 2);
        }

        ++Peek_;
        ++Char_;
        return new Token(Tag.MINUS, Position, Line_, 1);
    }

    private Token ParseMultiplyToken() {

        int Position = Peek_;

        ++Peek_;
        ++Char_;
        return new Token(Tag.MULTIPLY, Position, Line_, 1);
    }

    private Token ParseDivideToken() {

        int Position = Peek_;

        ++Peek_;
        ++Char_;
        return new Token(Tag.DIVIDE, Position, Line_, 1);
    }

    private boolean IsASpace(char Char_i) {
        return Char_i == ' ';
    }

    private boolean IsATab(char Char_i) {
        return Char_i == '\t';
    }

    private boolean IsASpecialReturn(char Char_i) {
        return Char_i == '\r';
    }

    private boolean IsAReturn(char Char_i) {
        return Char_i == '\n' || Char_i == '\r';
    }

    private char GetCurrentChar() {
        if (Peek_ < SourceCode_.length()) {
            return SourceCode_.charAt(Peek_);
        }
        return 0;
    }

    private char GetLookAhead() {
        if (LookAhead_ < Peek_) {
            LookAhead_ = Peek_;
        }

        ++LookAhead_;

        if (LookAhead_ < SourceCode_.length()) {
            return SourceCode_.charAt(LookAhead_);
        }

        return 0;
    }
}
