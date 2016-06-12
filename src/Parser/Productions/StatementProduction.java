package Parser.Productions;

import Lexer.Tag;

public class StatementProduction extends Production {

	public StatementProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "Stmt";
	}

	@Override
	protected void CreateProductionsImpl() {
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.DEF);
		Production.AddNonTerminalSymbol(DeclarationProduction.GetStaticName());
	}
}
