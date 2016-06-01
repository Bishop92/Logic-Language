package IDE;

import Common.SymbolsTable;
import IDE.PrettyPrinter.PrettyPrintTokenVisitor;
import Lexer.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IDE extends JFrame {
    private JPanel MainPanel;
    private JButton BtnCompile;
    private JTextPane TxtEditor;

    public IDE() {

        setSize(600, 600);

        add(MainPanel);

        BtnCompile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                String Code = TxtEditor.getText();

                SymbolsTable MySymbolsTable = new SymbolsTable();
                Lexer MyLexer = new Lexer(Code, MySymbolsTable);

                MySymbolsTable.AddReservedKeyword("def");
                MySymbolsTable.AddReservedKeyword("as");
                MySymbolsTable.AddReservedKeyword("isa");
                MySymbolsTable.AddReservedKeyword("ax");
                MySymbolsTable.AddReservedKeyword("rule");
                MySymbolsTable.AddReservedKeyword("true");
                MySymbolsTable.AddReservedKeyword("false");
                MySymbolsTable.AddReservedKeyword("and");
                MySymbolsTable.AddReservedKeyword("or");
                MySymbolsTable.AddReservedKeyword("not");
                MySymbolsTable.AddReservedKeyword("xor");


                Token CurrToken = MyLexer.GetNextToken();
                while (CurrToken != null) {
                    CurrToken.Print();
                    CurrToken = MyLexer.GetNextToken();
                }
            }
        });

        setVisible(true);

        TxtEditor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                String Code = TxtEditor.getText();

                SymbolsTable MySymbolsTable = new SymbolsTable();
                Lexer MyLexer = new Lexer(Code, MySymbolsTable);

                MySymbolsTable.AddReservedKeyword("def");
                MySymbolsTable.AddReservedKeyword("as");
                MySymbolsTable.AddReservedKeyword("isa");
                MySymbolsTable.AddReservedKeyword("ax");
                MySymbolsTable.AddReservedKeyword("rule");
                MySymbolsTable.AddReservedKeyword("true");
                MySymbolsTable.AddReservedKeyword("false");
                MySymbolsTable.AddReservedKeyword("and");
                MySymbolsTable.AddReservedKeyword("or");
                MySymbolsTable.AddReservedKeyword("not");
                MySymbolsTable.AddReservedKeyword("xor");
                MySymbolsTable.AddReservedKeyword("properties");
                MySymbolsTable.AddReservedKeyword("on");

                PrettyPrintTokenVisitor Visitor = new PrettyPrintTokenVisitor(TxtEditor.getStyledDocument());

                Token CurrToken = MyLexer.GetNextToken();
                while (CurrToken != null) {
                    CurrToken.AcceptVisitor(Visitor);
                    CurrToken.Print();
                    CurrToken = MyLexer.GetNextToken();
                }

            }
        });
    }
}
