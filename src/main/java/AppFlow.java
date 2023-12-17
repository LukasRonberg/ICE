import java.sql.SQLException;
import java.util.ArrayList;

public class AppFlow {
    private DBConnector dbConnector = new DBConnector();
    private TextUi textUI = new TextUi();
    private MainMenu mainMenu = new MainMenu();
    private User currentUser;

    public void start() throws SQLException {

        StartMenu startMenu = new StartMenu();
        startMenu.display();

        currentUser = startMenu.getUserAccount();
        mainMenu.allProducts = dbConnector.getProducts();
        mainMenu.allRecipes = dbConnector.getRecipes();
        mainMenu.currentUser = currentUser;
        textUI.displayMessage("\n*** Welcome " + currentUser.getUsername() + "! ***");

        boolean choosingAction = true;
        while (choosingAction) {
            String userInput = textUI.getInput("\nPress Any of the following keys:" +
                    "\n1. Search products by name" +
                    "\n2. Search recipes by name" +
                    "\n3. Search recipes by ingredient" +
                    "\n4. Search recipes by budget" +
                    "\n5. View saved products" +
                    "\n6. View saved recipes" +
                    "\n0. Quit");

            switch (userInput) {
                case "1":
                    mainMenu.searchProducts();
                    break;
                case "2":
                    mainMenu.searchRecipes();
                    break;
                case "3":
                    mainMenu.searchByIngrediens();
                    break;
                case "4":
                    mainMenu.searchRecipesByBudget();
                    break;
                case "5":
                    mainMenu.getSavedProducts();
                    break;
                case "6":
                    mainMenu.getSavedRecipes();
                    break;
                case "0":
                    dbConnector.saveFavoriteLists(currentUser.getUsername(), currentUser.getSavedProducts(), currentUser.getSavedRecipes());
                    currentUser = null;
                    choosingAction = false;
                    break;
                //default:
                //errorIsNotAnOption();
                //break;
            }
        }
    }
}
