package Parser.Productions;

import Lexer.*;
import Parser.NonTerminalSymbol;
import Parser.Symbol;
import Parser.TerminalSymbol;

import java.util.Vector;

public abstract class Production {

	//The name of the production
	private String Name_;

	//The list of productions available
	private Vector<SingleProduction> Productions_;

	/**
	 * @param Name_i
	 */
	public Production(String Name_i) {
		Name_ = Name_i;
		Productions_ = new Vector<>();
	}

	public String GetName() {
		return Name_;
	}

	abstract protected void CreateProductionsImpl();

	public void CreateProductions() {
		CreateProductionsImpl();
	}

	public SingleProduction CreateSingleProduction() {
		SingleProduction NewProduction = new SingleProduction();
		Productions_.add(NewProduction);
		return NewProduction;
	}

	public SingleProduction GetProduction(Tag Symbol_i) {
		for(SingleProduction Production : Productions_) {
			if(Production.IsSymbolInFirst(Symbol_i)) {
				return Production;
			}
		}

		return null;
	}

	public Token Parse(Lexer Lexer_i, Token CurrentToken_i) {

		SingleProduction Production = GetProduction(CurrentToken_i.GetTag());
		if(Production != null) {
			Vector<Symbol> Symbols = Production.GetSymbols();
			for (Symbol CurrentSymbol : Symbols) {
				if (CurrentSymbol instanceof NonTerminalSymbol) {
					CurrentToken_i = ((NonTerminalSymbol) CurrentSymbol).CallProduction(Lexer_i, CurrentToken_i);
				} else if (CurrentToken_i.GetTag() == ((TerminalSymbol) CurrentSymbol).GetSymbol()) {
					System.out.println("Matched symbol " + CurrentToken_i.GetTag() + " in production " + Name_);
					CurrentToken_i = Lexer_i.GetNextToken();
				} else {
					System.out.println("Missing token " + ((TerminalSymbol) CurrentSymbol).GetSymbol());
				}
			}
		} else {
			System.out.println("Wrong symbol '" + CurrentToken_i.GetTag() + "' found at " + CurrentToken_i.GetPosition());
		}

		return CurrentToken_i;
	}
}
