package Parser.Productions;

import Lexer.Tag;

public class StatementsProduction extends Production {

	public StatementsProduction() {
		super(GetStaticName());
	}

	public static String GetStaticName() {
		return "Stmts";
	}

	@Override
	protected void CreateProductionsImpl() {

		//Stmts -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.CBRACE);
		Production.AddSymbolToFirst(Tag.EOF);

		//Stmts -> { Stmts }
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.OBRACE);
		Production.AddTerminalSymbol(Tag.OBRACE);
		Production.AddNonTerminalSymbol(GetStaticName());
		Production.AddTerminalSymbol(Tag.CBRACE);

		//Stmts -> Stmt Stmts
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.DEF);
		Production.AddNonTerminalSymbol(StatementProduction.GetStaticName());
		Production.AddNonTerminalSymbol(GetStaticName());
	}
}
