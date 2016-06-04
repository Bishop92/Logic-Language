package IDE.PrettyPrinter;

import Common.SymbolsTable;
import Lexer.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PrettyPrinter {

	private JTextPane TextEditor_;

	public PrettyPrinter(JTextPane TextEditor_i) {

		TextEditor_ = TextEditor_i;

		TextEditor_.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);

				String Code = TextEditor_.getText();

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
				MySymbolsTable.AddReservedKeyword("event");

				PrettyPrintTokenVisitor Visitor = new PrettyPrintTokenVisitor(TextEditor_.getStyledDocument());

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
