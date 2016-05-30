package Common;

public class Symbol {
    //The name of the symbol
    private String Name_;

    //The type of the symbol
    private String Type_;

    public Symbol(String Name_i) {
        Name_ = Name_i;
    }

    public String GetName() {
        return Name_;
    }

    public void SetType(String Type_i) {
        Type_ = Type_i;
    }

}
