import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //generateProductList();

        AppFlow app = new AppFlow();
        app.start();

        //patrickTest();
    }

    /** this methode generates a new table of Products by importing a raw product table of all our products
     * and generating a ransom price(-15 to 15% of the average price) for each of our stores
     */
    public static void generateProductList() {
        TextUi textUI = new TextUi();
        long start, end;
        start = System.nanoTime();
        DBConnector dbConnector = new DBConnector();
        MainMenu mainMenu = new MainMenu();
        ArrayList<Product> newProductList = mainMenu.generateNewProductList();
        dbConnector.insertAllFoodProducts(newProductList);
        end = System.nanoTime();
        textUI.displayMessage("Product list created in "+(end-start)+" nano seconds");
    }
}
