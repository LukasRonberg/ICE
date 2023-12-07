import java.util.ArrayList;

public class MainMenu {
    ArrayList<Recipe> allRecipes;
    ArrayList<Product> allProducts;

    DBConnector dbConnector = new DBConnector();

    User currentUser;

    public void startUp(){
        // Get recipes
        allRecipes = dbConnector.getRecipes();
        // Get products
        allProducts = dbConnector.getProducts();
    }

    public void searchProducts()
    {

    }

    public void searchRecipes()
    {
    }

    public void searchByIngredients()
    {
    }

    public void searchByBudget()
    {
    }










}
