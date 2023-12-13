import java.sql.SQLException;
import java.util.ArrayList;

public class AppFlow {
    DBConnector dbConnector = new DBConnector();
    TextUi textUI = new TextUi();
    MainMenu mainMenu = new MainMenu();
    User currentUser;

    public void start(User currentUser) throws SQLException {
        this.currentUser = currentUser;
        /*while (true) {
            startMenu.display();
            if (startMenu.getUserAccount() != null) {
                currentUser = startMenu.getUserAccount();
                break;
            }
        }*/

        mainMenu.allProducts = dbConnector.getProducts();
        mainMenu.allRecipes = dbConnector.getRecipes();
        mainMenu.currentUser = currentUser; //new User("Lars", "Tissemand");
        textUI.displayMessage("\n*** Welcome " + currentUser.getUsername() + "! ***");

        boolean choosingAction = true;
        while (choosingAction) {
            String userInput = textUI.getInput("Press Any of the following keys:" +
                    "\n1. Search by product name" +
                    "\n2. Search recipes by name" +
                    "\n3. Search recipes by ingredient" +
                    "\n4. Search recipes by budget" +
                    "\n5. View saved products" +
                    "\n6. View saved recipes" +
                    "\n7. Quit");

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
                    mainMenu.getSavedProducts(currentUser.getUsername());
                    break;
                case "6":
                    mainMenu.getSavedRecipes();
                    break;
                case "7":
                    currentUser = null;
                    choosingAction = false;
                    break;
                default:
                    //errorIsNotAnOption();
                    break;
            }
        }

    }

}
