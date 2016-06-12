package Parser.Productions;

import Lexer.Tag;

public class DeclarationTypeProduction extends Production {

	public DeclarationTypeProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "DeclarationType";
	}

	@Override
	protected void CreateProductionsImpl() {

		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.SEMICOLON);

		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.AS);
		Production.AddTerminalSymbol(Tag.AS);
		Production.AddNonTerminalSymbol(BaseTypeProduction.GetStaticName());
	}
}
