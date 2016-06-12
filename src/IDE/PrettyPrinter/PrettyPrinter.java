package IDE.PrettyPrinter;

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

				Lexer MyLexer = new Lexer(Code);

				PrettyPrintTokenVisitor Visitor = new PrettyPrintTokenVisitor(TextEditor_.getStyledDocument());

				Token CurrToken = MyLexer.GetNextToken();
				while (CurrToken.GetTag() != Tag.EOF) {
					CurrToken.AcceptVisitor(Visitor);
					CurrToken.Print();
					CurrToken = MyLexer.GetNextToken();
				}
			}
		});
	}
}
