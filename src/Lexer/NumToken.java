package Lexer;

public class NumToken extends Token
{
    private int Value_;

    public NumToken(int Value_i)
    {
        super(Tag.NUM);
        Value_ = Value_i;
    }
}
