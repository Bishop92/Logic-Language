package sample;

import Common.SymbolsTable;
import Lexer.Lexer;

public class Main {

    public static void main(String[] args) {

        String Code = "     Hello90 /* bonjour     */              /*Im the best he___re good night\nsiamo*/ in 10!!!";

        SymbolsTable MySymbolsTable = new SymbolsTable();
        Lexer MyLexer = new Lexer(Code, MySymbolsTable);

        int TokensFound = 0;
        while(MyLexer.GetNextToken() != null)
        {
            ++TokensFound;
        }

        System.out.println(TokensFound + " tokens have been found");
    }
}
