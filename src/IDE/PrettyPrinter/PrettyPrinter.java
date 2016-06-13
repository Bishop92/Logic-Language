package IDE.PrettyPrinter;

import Lexer.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//A pretty printer for the language
public class PrettyPrinter {

	//The text editor that contains the text to format
	private JTextPane TextEditor_;

	/**
	 * @param TextEditor_i The text editor that contains the text to format
	 */
	public PrettyPrinter(JTextPane TextEditor_i) {
		TextEditor_ = TextEditor_i;

		//When the text editor is modified, update the format
		TextEditor_.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);

				//Get the text in the editor
				String Code = TextEditor_.getText();

				Lexer MyLexer = new Lexer(Code);

				//Crate a visitor for pretty print the tokens
				PrettyPrintTokenVisitor Visitor = new PrettyPrintTokenVisitor(TextEditor_.getStyledDocument());

				//Get the next token
				Token CurrToken = MyLexer.GetNextToken();
				while (CurrToken.GetTag() != Tag.EOF) {
					//Pass the token to the visitor and get the next token
					CurrToken.AcceptVisitor(Visitor);
					CurrToken.Print();
					CurrToken = MyLexer.GetNextToken();
				}
			}
		});
	}
}
