package Parser.Productions;

import Lexer.Tag;

public class TypeListProduction extends Production {

	public TypeListProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "TypeList";
	}

	@Override
	protected void CreateProductionsImpl() {

		//EmptyTypeList -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CPAR);
		Production.AddSymbolToFirst(Tag.SEMICOLON);

		//EmptyTypeList -> Type TypeList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.COMMA);
		Production.AddTerminalSymbol(Tag.COMMA);
		Production.AddNonTerminalSymbol(TypeProduction.GetStaticName());
		Production.AddNonTerminalSymbol(TypeListProduction.GetStaticName());
	}
}
