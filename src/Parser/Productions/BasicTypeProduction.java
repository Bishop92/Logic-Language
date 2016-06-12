package Parser.Productions;

import Lexer.Tag;

public class BasicTypeProduction extends Production {

	public BasicTypeProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "BasicType";
	}

	@Override
	protected void CreateProductionsImpl() {

		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddTerminalSymbol(Tag.INTTYPE);

		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddTerminalSymbol(Tag.FLOATTYPE);

		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddTerminalSymbol(Tag.BOOLTYPE);

		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddTerminalSymbol(Tag.STRINGTYPE);
	}
}
