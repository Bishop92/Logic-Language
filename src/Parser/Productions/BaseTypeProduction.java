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

		//BaseType -> id EmptyParentTypeList TypeDefinition
		SingleProduction Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.ID);
		Production.AddTerminalSymbol(Tag.ID);
		Production.AddNonTerminalSymbol(EmptyParentTypeListProduction.GetStaticName());
		Production.AddNonTerminalSymbol(TypeDefinitionProduction.GetStaticName());

		//BaseType -> BasicType
		Production = CreateSingleProduction();
		Production.AddSymbolToFirst(Tag.INTTYPE);
		Production.AddSymbolToFirst(Tag.FLOATTYPE);
		Production.AddSymbolToFirst(Tag.BOOLTYPE);
		Production.AddSymbolToFirst(Tag.STRINGTYPE);
		Production.AddNonTerminalSymbol(BasicTypeProduction.GetStaticName());
	}
}
