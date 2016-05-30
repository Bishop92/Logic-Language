package IDE.PrettyPrinter;

import Lexer.FloatToken;
import Lexer.IDToken;
import Lexer.IntegerToken;
import Lexer.Token;

public interface Visitor {

    void VisitToken(Token Token_i);
    void VisitToken(IDToken Token_i);
    void VisitToken(IntegerToken Token_i);
    void VisitToken(FloatToken Token_i);
}
