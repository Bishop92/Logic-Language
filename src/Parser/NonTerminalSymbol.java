package Parser;

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

	public Production GetProduction() {
		return Production_;
	}

}