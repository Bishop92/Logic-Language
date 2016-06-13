package Parser.Productions;

import Lexer.Tag;

public class TypeDefinitionProduction extends Production {

	public TypeDefinitionProduction() {
		super(GetStaticName());
	}

	protected static String GetStaticName() {
		return "TypeDefinition";
	}

	@Override
	protected void CreateProductionsImpl() {

		//TypeDefinition -> e
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.SEMICOLON);

		//TypeDefinition -> { }
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.OBRACE);
		Production.AddTerminalSymbol(Tag.OBRACE);
		Production.AddNonTerminalSymbol(DeclarationProduction.GetStaticName());
		Production.AddNonTerminalSymbol(EmptyDeclarationListProduction.GetStaticName());
		Production.AddTerminalSymbol(Tag.CBRACE);
	}
}
