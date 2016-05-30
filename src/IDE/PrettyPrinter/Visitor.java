package IDE.PrettyPrinter;

import Lexer.IDToken;
import Lexer.Token;

public interface Visitor {

    void VisitToken(Token Token_i);
    void VisitToken(IDToken Token_i);
}
