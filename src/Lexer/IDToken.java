package Lexer;

import Common.Symbol;

public class IDToken extends Token
{
    private Symbol Symbol_;

    public IDToken(Symbol Symbol_i)
    {
        super(Tag.ID);
        Symbol_ = Symbol_i;
    }
}
