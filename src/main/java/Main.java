import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //generateProductList();

        AppFlow app = new AppFlow();
        app.start();
    }

    /** this methode generates a new table of Products by calling the method generateNewProductList in the MainMenu-class and creating a new ArrayList(newProductList)
     *  of the class Product. Then we send the newProductList to a method in DBConnector-class that puts it into a table in our Database.
     */
    public static void generateProductList() {
        TextUI textUI = new TextUI();
        long start, end;
        start = System.nanoTime();
        DBConnector dbConnector = new DBConnector();
        MainMenu mainMenu = new MainMenu();
        ArrayList<Product> newProductList = mainMenu.generateNewProductList();
        dbConnector.insertAllFoodProducts(newProductList);
        end = System.nanoTime();
        textUI.displayMessage("Product list created in "+(int)((end-start)/1000000000)+" seconds");
    }
}
