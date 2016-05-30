package Common;

import java.util.HashMap;
import java.util.Vector;

public class SymbolsTable {
    //The table containing the symbols
    private HashMap<String, Symbol> SymbolsTable_;

    //The most specific symbols tables used for blocks
    private Vector<SymbolsTable> Blocks_;

    //The list of reserved keywords in the language
    private Vector<String> ReservedKeywords_;

    //The owner of that symbol table, used for retrieving symbols
    private SymbolsTable Owner_;

    public SymbolsTable() {
        this(null);
    }

    public SymbolsTable(SymbolsTable Owner_i) {
        SymbolsTable_ = new HashMap<String, Symbol>();
        Blocks_ = new Vector<SymbolsTable>();
        ReservedKeywords_ = new Vector<String>();
        Owner_ = Owner_i;
        if (Owner_ != null) {
            Owner_i.Blocks_.addElement(this);
        }
    }

    public SymbolsTable GetOwner() {
        return Owner_;
    }

    public void AddReservedKeyword(String Keyword_i) {
        Keyword_i = Keyword_i.toLowerCase();
        if(!ReservedKeywords_.contains(Keyword_i))
        {
            ReservedKeywords_.add(Keyword_i);
            Symbol ReservedKeyword = new Symbol(Keyword_i, true);
            SymbolsTable_.put(Keyword_i, ReservedKeyword);
        }
    }

    public Symbol AddSymbol(String Name_i) {

        Name_i = Name_i.toLowerCase();

        if(IsAReservedKeyword(Name_i)) {
            return GetSymbol(Name_i);
        }

        Symbol CurrentSymbol = SymbolsTable_.get(Name_i);
        if (CurrentSymbol == null) {
            CurrentSymbol = new Symbol(Name_i);
            SymbolsTable_.put(Name_i, CurrentSymbol);
        }

        return CurrentSymbol;
    }

    public Symbol GetSymbol(String Name_i) {

        Name_i = Name_i.toLowerCase();

        SymbolsTable CurrentOwner = this;
        while (CurrentOwner != null) {
            Symbol CurrentSymbol = CurrentOwner.SymbolsTable_.get(Name_i);
            if (CurrentSymbol != null) {
                return CurrentSymbol;
            }

            CurrentOwner = CurrentOwner.Owner_;
        }

        return null;
    }

    public boolean IsAReservedKeyword(String Name_i) {
        if(Owner_ == null) {
            Name_i = Name_i.toLowerCase();
            return ReservedKeywords_.contains(Name_i);
        }

        return Owner_.IsAReservedKeyword(Name_i);
    }
}