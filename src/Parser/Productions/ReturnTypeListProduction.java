package Parser.Productions;

import Lexer.Tag;

public class ReturnTypeListProduction extends Production {

	public ReturnTypeListProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "ReturnTypeList";
	}

	@Override
	protected void CreateProductionsImpl() {

		//ReturnTypeList -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.COMMA);
		Production.AddSymbolToFirst(Tag.SEMICOLON);
		Production.AddSymbolToFirst(Tag.CPAR);

		//ReturnTypeList -> : Type TypeList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.COLON);
		Production.AddTerminalSymbol(Tag.COLON);
		Production.AddNonTerminalSymbol(TypeProduction.GetStaticName());
		Production.AddNonTerminalSymbol(TypeListProduction.GetStaticName());
	}
}
