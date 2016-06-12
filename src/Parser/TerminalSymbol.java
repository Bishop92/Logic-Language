package Parser;

import Lexer.Tag;

public class TerminalSymbol implements Symbol {

	private Tag Symbol_;

	public TerminalSymbol(Tag Symbol_i) {
		Symbol_ = Symbol_i;
	}

	public Tag GetSymbol() {
		return Symbol_;
	}

}
