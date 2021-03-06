package Parser;

import Parser.Productions.*;

import java.util.HashMap;

public class ProductionFactory {

	private HashMap<String, Production> Productions_;

	private static ProductionFactory OurInstance;

	public static ProductionFactory GetInstance() {
		if(OurInstance == null) {
			OurInstance = new ProductionFactory();
			OurInstance.CreateProductions();
		}

		return OurInstance;
	}

	private ProductionFactory() {
		Productions_ = new HashMap<>();
	}

	private void CreateProductions() {
		AddProduction(new BaseTypeProduction());
		AddProduction(new BasicTypeProduction());
		AddProduction(new CallbackProduction());
		AddProduction(new DeclarationProduction());
		AddProduction(new DeclarationTypeProduction());
		AddProduction(new EmptyDeclarationListProduction());
		AddProduction(new EmptyParentTypeListProduction());
		AddProduction(new EmptyTypeListProduction());
		AddProduction(new ReturnTypeListProduction());
		AddProduction(new StatementProduction());
		AddProduction(new StatementsProduction());
		AddProduction(new TypeDefinitionProduction());
		AddProduction(new TypeListProduction());
		AddProduction(new TypeProduction());
		AddProduction(new TypeStatementsProduction());

		Productions_.forEach((ProductionName, CurrentProduction) -> CurrentProduction.CreateProductions());
	}

	public Production GetProduction(String ProductionName_i) {
		return Productions_.get(ProductionName_i);
	}

	private void AddProduction(Production ProductionToAdd_i) {
		Productions_.put(ProductionToAdd_i.GetName(), ProductionToAdd_i);
	}
}
