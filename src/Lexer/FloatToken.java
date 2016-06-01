package Lexer;

import IDE.PrettyPrinter.Visitor;

public class FloatToken extends Token {

    private float Value_;

    public FloatToken(float Value_i, int Position_i, int Line_i, int Length_i) {
        super(Tag.FLOAT, Position_i, Line_i, Length_i);
        Value_ = Value_i;
    }

    public void Print() {
        System.out.println("<Num," + Value_ + ">");
    }

    public void AcceptVisitor(Visitor Visitor_i) {
        Visitor_i.VisitToken(this);
    }
}
