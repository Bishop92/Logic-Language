package Common;

import java.util.HashMap;
import java.util.Vector;

//The symbol table that store all the symbols found
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

    /**
     * @param Owner_i The symbol table that own this symbol table. It is used while searching a symbol that is not in the current symbol table
     */
    public SymbolsTable(SymbolsTable Owner_i) {
        SymbolsTable_ = new HashMap<>();
        Blocks_ = new Vector<>();
        ReservedKeywords_ = new Vector<>();
        Owner_ = Owner_i;
        if (Owner_ != null) {
            Owner_i.Blocks_.addElement(this);
        }
    }

    /**
     * Return the symbol table owner of this symbol table
     *
     * @return The symbol table owner of this symbol table
     */
    public SymbolsTable GetOwner() {
        return Owner_;
    }

    /**
     * Add a reserved keyword to the symbol table
     *
     * @param Keyword_i The name of the keyword to add to the symbol table
     */
    public void AddReservedKeyword(String Keyword_i) {

        Keyword_i = Keyword_i.toLowerCase();

        if (!ReservedKeywords_.contains(Keyword_i)) {
            //The reserved keyword is not yet present in the set of reserved keywords
            ReservedKeywords_.add(Keyword_i);
            //Create a new symbol for the reserved keyword and add it to the symbol table
            Symbol ReservedKeyword = new Symbol(Keyword_i, true);
            SymbolsTable_.put(Keyword_i, ReservedKeyword);
        }
    }

    /**
     * Add a symbol to the symbol table with the given name
     *
     * @param Name_i The name of the symbol
     * @return The symbol created or the existing one if the symbol was already defined
     */
    public Symbol AddSymbol(String Name_i) {

        Name_i = Name_i.toLowerCase();

        if (IsAReservedKeyword(Name_i)) {
            //The symbol is a reserved keyword, so return it
            return GetSymbol(Name_i);
        }

        //Get the symbol from the symbol table
        //It's necessary to check only the symbol table associated to the current block.
        Symbol CurrentSymbol = SymbolsTable_.get(Name_i);
        if (CurrentSymbol == null) {
            //The symbol is not present in the symbol table, so create and add it
            //The symbol eventually override all the other symbols present in the parent symbols tables
            CurrentSymbol = new Symbol(Name_i);
            SymbolsTable_.put(Name_i, CurrentSymbol);
        }

        //Return the symbol created
        return CurrentSymbol;
    }

    /**
     * Retrieve the symbol from the symbol table, searching in all the parent symbols tables
     *
     * @param Name_i The name of the symbol to find
     * @return The symbol found, null if no symbol with the given name is presents
     */
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

    /**
     * Returns true if the given name correspond to a reserved keyword, false otherwise
     *
     * @param Name_i The name of the symbol to find
     * @return True if is a reserved keyword, false otherwise
     */
    public boolean IsAReservedKeyword(String Name_i) {
        if (Owner_ == null) {
            //It's the root of all the symbols table
            Name_i = Name_i.toLowerCase();
            //Check if the given name is present in the set of the reserved keywords
            return ReservedKeywords_.contains(Name_i);
        }

        //Recursive call on the parent until the root is reached
        return Owner_.IsAReservedKeyword(Name_i);
    }
}