package Parser;

import Lexer.*;
import Parser.Productions.Production;

public class NonTerminalSymbol implements Symbol {

	//
	private Production Production_;

	/**
	 * @param Production_i
	 */
	public NonTerminalSymbol(Production Production_i) {
		Production_ = Production_i;
	}

	public Token CallProduction(Lexer Lexer_i, Token CurrentToken_i) {
		return Production_.Parse(Lexer_i, CurrentToken_i);
	}
}
