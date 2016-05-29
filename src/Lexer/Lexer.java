package Lexer;

import Common.SymbolsTable;

public class Lexer
{
    //The code to analyze
    private String SourceCode_;

    //The table of all the symbols
    private SymbolsTable SymbolsTable_;

    //The current position of the code to analyze
    private int Peek_;

    //The current line that the lexer is parsing
    private int Line_;

    //The current char that the lexer is parsing
    private int Char_;

    public Lexer(String SourceCode_i, SymbolsTable SymbolsTable_i)
    {
        SourceCode_ = SourceCode_i;
        SymbolsTable_ = SymbolsTable_i;
        Peek_ = 0;
        Line_ = 0;
        Char_ = 0;
    }

    public Token GetNextToken()
    {
        SkipSpacesAndComments();

        char CurrentChar = GetCurrentChar();
        if(Character.isLetter(CurrentChar))
        {
            //It's an identifier
            return ParseIDToken();
        }
        else if(Character.isDigit(CurrentChar))
        {
            //It's a number
            return ParseNumToken();
        }
        else if(Peek_ < SourceCode_.length())
        {
            System.out.println("Error on line: " + Line_ + " at char: " + Char_ + ". Unexpected character found: " + CurrentChar);
        }

        return null;
    }

    private void SkipSpacesAndComments()
    {
        char CurrentChar = GetCurrentChar();
        while(IsASpace(CurrentChar) || IsATab(CurrentChar) || IsAReturn(CurrentChar))
        {
            if(IsAReturn(CurrentChar))
            {
                ++Line_;
                Char_ = 0;
            }
            else
            {
                ++Char_;
            }

            ++Peek_;
            CurrentChar = GetCurrentChar();
        }
    }

    private Token ParseIDToken()
    {
        String Name = "";
        char CurrentChar = GetCurrentChar();
        while(Character.isLetterOrDigit(CurrentChar) || CurrentChar == '_')
        {
            Name += CurrentChar;
            ++Peek_;
            ++Char_;
            CurrentChar = GetCurrentChar();
        }

        return new IDToken(SymbolsTable_.AddSymbol(Name));
    }

    private Token ParseNumToken()
    {
        String Value = "";
        char CurrentChar = GetCurrentChar();
        while(Character.isDigit(CurrentChar))
        {
            Value += CurrentChar;
            ++Peek_;
            ++Char_;
            CurrentChar = GetCurrentChar();
        }

        return new NumToken(Integer.parseInt(Value));
    }

    private boolean IsASpace(char Char_i)
    {
        return Char_i == ' ';
    }

    private boolean IsATab(char Char_i)
    {
        return Char_i == '\t';
    }

    private boolean IsAReturn(char Char_i)
    {
        return Char_i == '\n';
    }

    private char GetCurrentChar()
    {
        if(Peek_ < SourceCode_.length())
            return SourceCode_.charAt(Peek_);

        return 0;
    }
}
