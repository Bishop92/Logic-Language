package Lexer;

public class FloatToken extends Token {

    private float Value_;

    public FloatToken(float Value_i)
    {
        super(Tag.FLOAT);
        Value_ = Value_i;
    }
}
