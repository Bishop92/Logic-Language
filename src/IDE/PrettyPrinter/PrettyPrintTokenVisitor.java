package IDE.PrettyPrinter;

import Common.Symbol;
import Common.SymbolsTable;
import Lexer.IDToken;
import Lexer.Token;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class PrettyPrintTokenVisitor implements Visitor {

    private StyledDocument Document_;

    private SymbolsTable SymbolsTable_;

    public PrettyPrintTokenVisitor(SymbolsTable SymbolsTable_i, StyledDocument Document_i) {
        SymbolsTable_ = SymbolsTable_i;
        Document_ = Document_i;
    }

    @Override
    public void VisitToken(Token Token_i) {
        try {
            Document_.insertString(Document_.getLength(), Token_i.toString() + ' ', null);
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }

    @Override
    public void VisitToken(IDToken Token_i) {
        try {
            Symbol TokenSymbol = Token_i.GetSymbol();
            if(SymbolsTable_.IsAReservedKeyword(TokenSymbol.GetName()))
            {
                SimpleAttributeSet KeyWord = new SimpleAttributeSet();
                StyleConstants.setForeground(KeyWord, Color.BLUE);
                Document_.insertString(Document_.getLength(), Token_i.GetSymbol().GetName() + ' ', KeyWord);
            }
            else {
                Document_.insertString(Document_.getLength(), Token_i.GetSymbol().GetName() + ' ', null);
            }
        } catch (Exception exc) {
            System.out.println(exc);
        }
    }
}
