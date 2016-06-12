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
		AddProduction(new DeclarationProduction());
		AddProduction(new DeclarationTypeProduction());
		AddProduction(new StatementProduction());
		AddProduction(new StatementsProduction());

		Productions_.forEach((ProductionName, CurrentProduction) -> CurrentProduction.CreateProductions());
	}

	public Production GetProduction(String ProductionName_i) {
		return Productions_.get(ProductionName_i);
	}

	private void AddProduction(Production ProductionToAdd_i) {
		Productions_.put(ProductionToAdd_i.GetName(), ProductionToAdd_i);
	}
}
