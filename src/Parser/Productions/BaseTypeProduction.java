package Parser.Productions;

import Lexer.Tag;

public class BaseTypeProduction extends Production {

	public BaseTypeProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "BaseType";
	}

	@Override
	protected void CreateProductionsImpl() {

		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddNonTerminalSymbol(BasicTypeProduction.GetStaticName());
	}
}
