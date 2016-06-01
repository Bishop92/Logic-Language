package Lexer;

import IDE.PrettyPrinter.Visitor;

public class Token {

    //The tag that represents the token
    private Tag Tag_;

    //The position of the token inside the code
    private int Position_;

    //The length of the token read
    private int Length_;

    public Token(Tag Tag_i, int Position_i, int Line_i, int Length_i) {
        Tag_ = Tag_i;
        Position_ = Position_i - Line_i;
        Length_ = Length_i;
    }

    public int GetPosition() {
        return Position_;
    }

    public int GetLength() {
        return Length_;
    }

    public String toString() {
        return "" + Tag_;
    }

    public void Print() {
        System.out.println("<" + Tag_ + ">");
    }

    public void AcceptVisitor(Visitor Visitor_i) {
        Visitor_i.VisitToken(this);
    }
}
