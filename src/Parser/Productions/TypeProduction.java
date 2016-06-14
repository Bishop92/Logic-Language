package Parser.Productions;

import Lexer.Tag;

public class TypeProduction extends Production {

	public TypeProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "Type";
	}

	@Override
	protected void CreateProductionsImpl() {

		//Type -> id
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.ID);
		Production.AddTerminalSymbol(Tag.ID);

		//Type -> Callback
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CALLBACK);
		Production.AddNonTerminalSymbol(CallbackProduction.GetStaticName());

		//Type -> BasicType
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddNonTerminalSymbol(BasicTypeProduction.GetStaticName());
	}
}
