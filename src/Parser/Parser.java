package Parser;

import Lexer.Lexer;
import Parser.Productions.Production;
import Parser.Productions.StatementsProduction;

public class Parser {

	public void Parse(Lexer Lexer_i) {
		Production CurrentProduction = ProductionFactory.GetInstance().GetProduction(StatementsProduction.GetStaticName());

		CurrentProduction.Parse(Lexer_i, Lexer_i.GetNextToken());
	}
}
