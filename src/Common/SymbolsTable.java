package Common;

import java.util.HashMap;
import java.util.Vector;

public class SymbolsTable
{
    //The table containing the symbols
    private HashMap<String, Symbol> SymbolsTable_;

    //The most specific symbols tables used for blocks
    private Vector<SymbolsTable> Blocks_;

    //The owner of that symbol table, used for retrieving symbols
    private SymbolsTable Owner_;

    public SymbolsTable()
    {
        this(null);
    }

    public SymbolsTable(SymbolsTable Owner_i)
    {
        SymbolsTable_ = new HashMap<String, Symbol>();
        Blocks_ = new Vector<SymbolsTable>();
        Owner_ = Owner_i;
        if(Owner_ != null)
        {
            Owner_i.Blocks_.addElement(this);
        }
    }

    public SymbolsTable GetOwner()
    {
        return Owner_;
    }

    public Symbol AddSymbol(String Name_i)
    {
        Symbol CurrentSymbol = SymbolsTable_.get(Name_i);
        if(CurrentSymbol == null)
        {
            System.out.println("New symbol found! His name is: " + Name_i);
            CurrentSymbol = new Symbol(Name_i);
            SymbolsTable_.put(Name_i, CurrentSymbol);
        }

        return CurrentSymbol;
    }

    public Symbol GetSymbol(String Name_i)
    {
        SymbolsTable CurrentOwner = this;
        while(CurrentOwner != null)
        {
            Symbol CurrentSymbol = CurrentOwner.SymbolsTable_.get(Name_i);
            if(CurrentSymbol != null)
            {
                return CurrentSymbol;
            }

            CurrentOwner = CurrentOwner.Owner_;
        }

        return null;
    }
}