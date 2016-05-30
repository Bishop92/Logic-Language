package Lexer;

import IDE.PrettyPrinter.Visitor;

public class Token {

    //The tag that represents the token
    private Tag Tag_;

    public Token(Tag Tag_i) {
        Tag_ = Tag_i;
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
