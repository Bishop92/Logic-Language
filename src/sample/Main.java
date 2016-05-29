package sample;

import Common.SymbolsTable;
import Lexer.Lexer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        String Code = "     Hello90 Im the best he___re good night\nsiamo| tutti piu belli qua";

        SymbolsTable MySymbolsTable = new SymbolsTable();
        Lexer MyLexer = new Lexer(Code, MySymbolsTable);

        int TokensFound = 0;
        while(MyLexer.GetNextToken() != null)
        {
            ++TokensFound;
        }

        System.out.println(TokensFound + " tokens have been found");

        launch(args);
    }
}
