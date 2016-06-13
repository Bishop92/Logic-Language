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

		//BasicType -> int
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddTerminalSymbol(Tag.INTTYPE);

		//BasicType -> float
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddTerminalSymbol(Tag.FLOATTYPE);

		//BasicType -> bool
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddTerminalSymbol(Tag.BOOLTYPE);

		//BasicType -> string
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddTerminalSymbol(Tag.STRINGTYPE);
	}
}
