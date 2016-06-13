package Parser.Productions;

import Lexer.Tag;

public class EmptyParentTypeListProduction extends Production {

	public EmptyParentTypeListProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "EmptyParentTypeList";
	}

	@Override
	protected void CreateProductionsImpl() {

		//EmptyParentTypeList -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.SEMICOLON);
		Production.AddSymbolToFirst(Tag.OBRACE);

		//EmptyParentTypeList -> , id EmptyParentTypeList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.COMMA);
		Production.AddTerminalSymbol(Tag.COMMA);
		Production.AddTerminalSymbol(Tag.ID);
		Production.AddNonTerminalSymbol(GetStaticName());
	}
}
