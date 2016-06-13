package Common;

//A symbol in the symbol table
public class Symbol {

    //The name of the symbol
    private String Name_;

    //The type of the symbol
    private String Type_;

    //True if the symbol represents a reserved keyword, false otherwise
    private boolean IsReserved_;

    /**
     * Constructor for a non reserved symbol
     *
     * @param Name_i The name of the symbol
     */
    public Symbol(String Name_i) {
        Name_ = Name_i;
        IsReserved_ = false;
    }

    /**
     * Constructor for a potentially non reserved
     *
     * @param Name_i     The name of the symbol
     * @param Reserved_i True if the symbol is reserved, false otherwise
     */
    public Symbol(String Name_i, boolean Reserved_i) {
        Name_ = Name_i;
        IsReserved_ = Reserved_i;
    }

    /**
     * Return the name of the symbol
     *
     * @return The name of the symbol
     */
    public String GetName() {
        return Name_;
    }

    /**
     * Set the type of the symbol
     *
     * @param Type_i The type of the symbol
     */
    public void SetType(String Type_i) {
        Type_ = Type_i;
    }

    /**
     * Return true if the symbol is reserved, false otherwise
     *
     * @return True if the symbol is reserved, false otherwise
     */
    public boolean IsReserved() {
        return IsReserved_;
    }
}
