package Parser.Productions;

import Lexer.Tag;

public class DeclarationProduction extends Production {

	public DeclarationProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "Declaration";
	}

	@Override
	protected void CreateProductionsImpl() {

		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.DEF);
		Production.AddTerminalSymbol(Tag.DEF);
		Production.AddTerminalSymbol(Tag.ID);
		Production.AddNonTerminalSymbol(DeclarationTypeProduction.GetStaticName());
		Production.AddTerminalSymbol(Tag.SEMICOLON);
	}
}
