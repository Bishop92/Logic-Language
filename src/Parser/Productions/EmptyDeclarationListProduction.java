package Parser.Productions;

import Lexer.Tag;

public class EmptyDeclarationListProduction extends Production {

	public EmptyDeclarationListProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "EmptyDeclarationList";
	}

	@Override
	protected void CreateProductionsImpl() {

		//EmptyDeclarationList -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CBRACE);

		//EmptyDeclarationList -> Declaration EmptyDeclarationList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.DEF);
		Production.AddNonTerminalSymbol(DeclarationProduction.GetStaticName());
		Production.AddNonTerminalSymbol(EmptyDeclarationListProduction.GetStaticName());
	}
}
