package IDE.PrettyPrinter;

import Common.Symbol;
import Lexer.FloatToken;
import Lexer.IDToken;
import Lexer.IntegerToken;
import Lexer.Token;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class PrettyPrintTokenVisitor implements Visitor {

    private StyledDocument Document_;

    public PrettyPrintTokenVisitor(StyledDocument Document_i) {
        Document_ = Document_i;
    }

    @Override
    public void VisitToken(Token Token_i) {
        SimpleAttributeSet KeyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(KeyWord, Color.BLACK);
        Document_.setCharacterAttributes(Token_i.GetPosition(), Token_i.GetLength(), KeyWord, true);
    }

    @Override
    public void VisitToken(FloatToken Token_i) {
        SimpleAttributeSet KeyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(KeyWord, Color.ORANGE);
        Document_.setCharacterAttributes(Token_i.GetPosition(), Token_i.GetLength(), KeyWord, true);
    }

    @Override
    public void VisitToken(IntegerToken Token_i) {
        SimpleAttributeSet KeyWord = new SimpleAttributeSet();
        StyleConstants.setForeground(KeyWord, Color.ORANGE);
        Document_.setCharacterAttributes(Token_i.GetPosition(), Token_i.GetLength(), KeyWord, true);
    }

    @Override
    public void VisitToken(IDToken Token_i) {
        Symbol TokenSymbol = Token_i.GetSymbol();
        SimpleAttributeSet KeyWord = new SimpleAttributeSet();
        if (TokenSymbol.IsReserved()) {
            StyleConstants.setForeground(KeyWord, new Color(86, 156, 214));
            StyleConstants.setBold(KeyWord, true);
        } else {
            StyleConstants.setForeground(KeyWord, Color.RED);
        }
        Document_.setCharacterAttributes(Token_i.GetPosition(), Token_i.GetLength(), KeyWord, true);
    }
}
