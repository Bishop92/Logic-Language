package Lexer;

public class IntegerToken extends Token
{
    private int Value_;

    public IntegerToken(int Value_i)
    {
        super(Tag.INTEGER);
        Value_ = Value_i;
    }
}
