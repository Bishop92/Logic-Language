package Parser.Productions;

import Lexer.Tag;

public class DeclarationTypeProduction extends Production {

	public DeclarationTypeProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "DeclarationType";
	}

	@Override
	protected void CreateProductionsImpl() {

		//DeclarationType -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.SEMICOLON);

		//DeclarationType -> TypeDefinition
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.OBRACE);
		Production.AddNonTerminalSymbol(TypeDefinitionProduction.GetStaticName());

		//DeclarationType -> as BaseType
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.AS);
		Production.AddTerminalSymbol(Tag.AS);
		Production.AddNonTerminalSymbol(BaseTypeProduction.GetStaticName());
	}
}
