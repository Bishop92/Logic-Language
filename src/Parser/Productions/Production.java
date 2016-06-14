package Parser.Productions;

import Lexer.*;
import Parser.NonTerminalSymbol;
import Parser.Symbol;
import Parser.TerminalSymbol;
import Utils.ErrorUtils;

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

	private boolean IsSymbolInFirst(Tag Symbol_i) {
		for(SingleProduction Production : Productions_) {
			if(Production.IsSymbolInFirst(Symbol_i)) {
				return true;
			}
		}

		return false;
	}

	private boolean AllowEmptyProduction() {
		for(SingleProduction Production : Productions_) {
			if(Production.GetSymbols().isEmpty()) {
				return true;
			}
		}

		return false;
	}

	public Token Parse(Lexer Lexer_i, Token CurrentToken_i) {

		SingleProduction Production = GetProduction(CurrentToken_i.GetTag());
		Vector<Symbol> Symbols = Production.GetSymbols();

		boolean ErrorAlreadyLogged = false;

		for (Symbol CurrentSymbol : Symbols) {
			if (CurrentSymbol instanceof NonTerminalSymbol) {
				//It's a non terminal symbol, so check if the current symbol match with its FIRST
				Production NextProduction = ((NonTerminalSymbol) CurrentSymbol).GetProduction();
				if (NextProduction.IsSymbolInFirst(CurrentToken_i.GetTag())) {
					ErrorAlreadyLogged = false;
					//The symbol match, so call the parsing of the next tokens
					CurrentToken_i = NextProduction.Parse(Lexer_i, CurrentToken_i);
				} else if(!ErrorAlreadyLogged && NextProduction.AllowEmptyProduction()) {
					//The symbol does not mach with a production, so log an error
					//System.out.println("Syntax error: unexpected symbol found '" + ErrorUtils.GetSymbolFromTag(CurrentToken_i.GetTag()) + "' at " + ErrorUtils.GetErrorLocation(CurrentToken_i));
					ErrorAlreadyLogged = true;
				} else if(!ErrorAlreadyLogged) {
					//The symbol does not mach with a production, so log an error
					System.out.println("Syntax error: expected '" + NextProduction.GetName() + "' at " + ErrorUtils.GetErrorLocation(CurrentToken_i));
					ErrorAlreadyLogged = true;
				}
			} else if (CurrentToken_i.GetTag() == ((TerminalSymbol) CurrentSymbol).GetSymbol()) {
				ErrorAlreadyLogged = false;
				CurrentToken_i = Lexer_i.GetNextToken();
			} else if(!ErrorAlreadyLogged) {

				String WrongTag = ErrorUtils.GetSymbolFromTag(CurrentToken_i.GetTag());

				Tag ExpectedTag = ((TerminalSymbol) CurrentSymbol).GetSymbol();
				String ExpectedTagError = ErrorUtils.GetSymbolFromTag(ExpectedTag);

				System.out.println("Syntax error: unexpected symbol '" + WrongTag + "' at " + ErrorUtils.GetErrorLocation(CurrentToken_i) + ". Expected '" + ExpectedTagError + "'");

				while(CurrentToken_i.GetTag() != Tag.SEMICOLON && CurrentToken_i.GetTag() != Tag.EOF) {
					CurrentToken_i = Lexer_i.GetNextToken();
				}

				if(Production.HasTerminalForSynchronization()) {
					CurrentToken_i = Lexer_i.GetNextToken();
				}

				return CurrentToken_i;
			}
		}

		return CurrentToken_i;
	}
}
