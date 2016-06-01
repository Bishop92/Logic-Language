package Lexer;

import Common.Symbol;
import IDE.PrettyPrinter.Visitor;

public class IDToken extends Token {
    private Symbol Symbol_;

    public IDToken(Symbol Symbol_i, int Position_i, int Line_i) {
        super(Tag.ID, Position_i, Line_i, Symbol_i.GetName().length());
        Symbol_ = Symbol_i;
    }

    public Symbol GetSymbol() {
        return Symbol_;
    }

    public void Print() {
        System.out.println("<ID," + Symbol_.GetName() + ">");
    }

    public void AcceptVisitor(Visitor Visitor_i) {
        Visitor_i.VisitToken(this);
    }
}
