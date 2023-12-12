import org.w3c.dom.ranges.Range;

import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        while (true)
        {
            StartMenu startMenu = new StartMenu();
            startMenu.display();

            User user = startMenu.getUserAccount();

            AppFlow app = new AppFlow();
            app.start(user);
        }

        //patrickTest();
    }

    public static void patrickTest()
    {
        ArrayList<Product> products = new ArrayList<>();
        DBConnector dbConnector = new DBConnector();
        TextUi textUI = new TextUi();
        GUI gui = new GUI();
        products = dbConnector.getProducts();
        //products = generateDataset(500);
        for (Product product: products) {
            //textUI.displayMessage();(product.toString());
            //dbConnector.insertFoodProducts(product);
        }

        //gui.start();


        textUI.displayMessage(products.size()+"");

        ArrayList<Recipe> recipes = dbConnector.getRecipes();

        /*for (Recipe r: recipes) {
            textUI.displayMessage();(r.toString());
        }*/

        Recipe recipe = new Recipe("Bolo", new ArrayList<String>(Arrays.asList("hakket oksekød","smør","letmælk", "revet mozzarella", "peberfrugt", "ris")));

        //Sorts products in the products arraylist by pricePerGram
        products.sort((Comparator.comparingDouble(Product::pricePerGram)));


        //textUI.displayMessage();();
        textUI.displayMessage("Test 1: Using Bolo");
        long start1 = System.nanoTime();
        var test1 = recipe.findCheapestProducts(products);
        textUI.displayMessage("Name: "+test1.get(0).name +", Price per 100g: "+test1.get(0).pricePerGram() +", Price: "+ test1.get(0).price+", Store: "+ test1.get(0).storeType);
        long end1 = System.nanoTime();
        textUI.displayMessage("Elapsed Time in nano seconds: "+ (end1-start1));


        //textUI.displayMessage();();
        textUI.displayMessage("Test 2: Using Bolo");
        start1 = System.nanoTime();
        var test2 = recipe.findCheapestProductsNewAndBetter(products,null);
        for (Product p: test2) {
            textUI.displayMessage("Name: "+p.name +", Price per 100g: "+ (Math.round(p.pricePerGram() * 100.0) / 100.0) +", Price: "+ p.price+", Store: "+ p.storeType);
        }
        textUI.displayMessage("Total price for this recipe is: "+ recipe.calculateTotalPrice(products));
        end1 = System.nanoTime();
        textUI.displayMessage("Elapsed Time in nano seconds: "+ (end1-start1));


        //textUI.displayMessage();();
        textUI.displayMessage("Test 3: Using Bolo");
        start1 = System.nanoTime();
        var test3 = recipe.findCheapestStore(products);
        test3.sort((Comparator.comparingDouble(Recipe.Store::getTotalStorePrice)));
        for (Recipe.Store store: test3) {
            if(store.getTotalStorePrice() > 0)

                textUI.displayMessage("Store name: "+store.storeName+", total price per 100g: "+ (Math.round(store.totalStorePrice * 100.0) / 100.0) + ", number of wares in store: "+ store.getProducts().size());
        }
        end1 = System.nanoTime();
        textUI.displayMessage("Elapsed Time in nano seconds: "+ (end1-start1));



    }

}
