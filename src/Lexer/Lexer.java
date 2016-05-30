package Lexer;

import Common.SymbolsTable;

public class Lexer {
    //The code to analyze
    private String SourceCode_;

    //The table of all the symbols
    private SymbolsTable SymbolsTable_;

    //The current position of the code to analyze
    private int Peek_;

    //The current position of the lookahead to analyze the code
    private int LookAhead_;

    //The current line that the lexer is parsing
    private int Line_;

    //The current char that the lexer is parsing
    private int Char_;

    public Lexer(String SourceCode_i, SymbolsTable SymbolsTable_i) {
        SourceCode_ = SourceCode_i;
        SymbolsTable_ = SymbolsTable_i;
        Peek_ = 0;
        LookAhead_ = 0;
        Line_ = 0;
        Char_ = 0;
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
            //It's {, }
            return ParseCommaToken();
        }  else if (CurrentChar == ';') {
            //It's {, }
            return ParseSemiColonToken();
        } else if (Peek_ < SourceCode_.length()) {
            System.out.println("Error on line: " + Line_ + " at char: " + Char_ + ". Unexpected character found: " + CurrentChar + "(" + (int)CurrentChar + ")");
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

        return new IDToken(SymbolsTable_.AddSymbol(Name), Position - Line_);
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

            return new FloatToken(Float.parseFloat(Value), Position - Line_, Value.length());
        } else {
            return new IntegerToken(Integer.parseInt(Value), Position - Line_, Value.length());
        }
    }

    private Token ParseLowerToken() {
        char NextChar = GetLookAhead();
        if(NextChar == '=') {
            NextChar = GetLookAhead();
            if(NextChar == '>') {
                Peek_ += 3;
                Char_ += 3;
                return new Token(Tag.IIF, Peek_ - Line_ - 3, 3);
            }
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.LET, Peek_ - Line_ - 2, 2);
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.LT, Peek_ - Line_ - 1, 1);
    }

    private Token ParseGreaterToken() {
        char NextChar = GetLookAhead();
        if (NextChar == '=') {
            Peek_ += 2;
            Char_ += 2;
            return new Token(Tag.GET, Peek_ - Line_ - 2, 2);
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.GT, Peek_ - Line_ - 1, 1);
    }

    private Token ParseEqualToken() {
        char NextChar = GetLookAhead();
        switch (NextChar) {
            case '=':
                Peek_ += 2;
                Char_ += 2;
                return new Token(Tag.EQ, Peek_ - 2, 2);
            case '>':
                Peek_ += 2;
                Char_ += 2;
                return new Token(Tag.IMPLY, Peek_ - 2, 2);
            default:
        }
        ++Peek_;
        ++Char_;
        return new Token(Tag.ASSIGN, Peek_ - 1, 1);
    }

    private Token ParseColonToken() {
        ++Peek_;
        ++Char_;
        return new Token(Tag.COLON, Peek_ - 1, 1);
    }

    private Token ParseParenthesesToken() {
        char CurrentChar = GetCurrentChar();
        ++Peek_;
        ++Char_;
        if(CurrentChar == '(') {
            return new Token(Tag.OPAR, Peek_ - 1, 1);
        }
        return new Token(Tag.CPAR, Peek_ - 1, 1);
    }

    private Token ParseBracesToken() {
        char CurrentChar = GetCurrentChar();
        ++Peek_;
        ++Char_;
        if(CurrentChar == '{') {
            SymbolsTable_ = new SymbolsTable(SymbolsTable_);
            return new Token(Tag.OBRACE, Peek_ - 1, 1);
        }

        if(SymbolsTable_.GetOwner() != null) {
            SymbolsTable_ = SymbolsTable_.GetOwner();
        }

        return new Token(Tag.CBRACE, Peek_ - 1, 1);
    }

    private Token ParseCommaToken() {
        ++Peek_;
        ++Char_;
        return new Token(Tag.COMMA, Peek_ - 1, 1);
    }

    private Token ParseSemiColonToken() {
        ++Peek_;
        ++Char_;
        return new Token(Tag.SEMICOLON, Peek_ - 1, 1);
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
