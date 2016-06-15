package Parser.Productions;

import Lexer.Tag;
import Parser.NonTerminalSymbol;
import Parser.ProductionFactory;
import Parser.Symbol;
import Parser.TerminalSymbol;

import java.util.Vector;

public class SingleProduction {

	//
	private Vector<Tag> First_;

	//
	private Vector<Symbol> Symbols_;

	public SingleProduction() {
		First_ = new Vector<>();
		Symbols_ = new Vector<>();
	}

	public Vector<Symbol> GetSymbols() {
		return Symbols_;
	}

	public void AddSymbolToFirst(Tag Symbol_i) {
		First_.add(Symbol_i);
	}

	public void AddTerminalSymbol(Tag TerminalSymbol_i) {
		Symbols_.add(new TerminalSymbol(TerminalSymbol_i));
	}

	public void AddNonTerminalSymbol(String NonTerminalSymbol_i) {
		Production ProductionToAdd = ProductionFactory.GetInstance().GetProduction(NonTerminalSymbol_i);
		Symbols_.add(new NonTerminalSymbol(ProductionToAdd));
	}

	public boolean IsSymbolInFirst(Tag Symbol_i) {
		return First_.contains(Symbol_i);
	}

	public boolean HasTerminalForSynchronization() {
		if(Symbols_.lastElement() instanceof TerminalSymbol) {
			return ((TerminalSymbol) Symbols_.lastElement()).GetSymbol() == Tag.SEMICOLON;
		}

		return false;
	}
}
