package Parser.Productions;

import Lexer.Tag;

public class EmptyTypeListProduction extends Production {

	public EmptyTypeListProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "EmptyTypeList";
	}

	@Override
	protected void CreateProductionsImpl() {

		//EmptyTypeList -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CPAR);

		//EmptyTypeList -> Type TypeList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.ID);
		Production.AddSymbolToFirst(Tag.CALLBACK);
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddNonTerminalSymbol(TypeProduction.GetStaticName());
		Production.AddNonTerminalSymbol(TypeListProduction.GetStaticName());
	}
}
