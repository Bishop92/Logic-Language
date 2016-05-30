package Common;

public class Symbol {
    //The name of the symbol
    private String Name_;

    //The type of the symbol
    private String Type_;

    //True if the symbol represents a reserved keyword, false otherwise
    private boolean IsReserved_;

    public Symbol(String Name_i) {
        Name_ = Name_i;
        IsReserved_ = false;
    }

    public Symbol(String Name_i, boolean Reserved_i) {
        Name_ = Name_i;
        IsReserved_ = Reserved_i;
    }

    public String GetName() {
        return Name_;
    }

    public void SetType(String Type_i) {
        Type_ = Type_i;
    }

    public boolean IsReserved() {
        return IsReserved_;
    }

}
