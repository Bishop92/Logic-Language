package Parser.Productions;

import Lexer.Tag;

public class TypeStatementsProduction extends Production {

	public TypeStatementsProduction() {
		super(GetStaticName());
	}

	public static String GetStaticName() {
		return "TypeStmts";
	}

	@Override
	protected void CreateProductionsImpl() {

		//TypeStmts -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CBRACE);

		//TypeStmts -> Declaration EmptyDeclarationList
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.DEF);
		Production.AddNonTerminalSymbol(DeclarationProduction.GetStaticName());
		Production.AddNonTerminalSymbol(EmptyDeclarationListProduction.GetStaticName());
	}
}
