package Parser.Productions;

import Lexer.Tag;

public class CallbackProduction extends Production {

	public CallbackProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "Callback";
	}

	@Override
	protected void CreateProductionsImpl() {

		//Callback -> Callback()
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CALLBACK);
		Production.AddTerminalSymbol(Tag.CALLBACK);
		Production.AddTerminalSymbol(Tag.OPAR);
		Production.AddNonTerminalSymbol(EmptyTypeListProduction.GetStaticName());
		Production.AddTerminalSymbol(Tag.CPAR);
		Production.AddNonTerminalSymbol(ReturnTypeListProduction.GetStaticName());
	}
}
