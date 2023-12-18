public class AppFlow {
    private final DBConnector dbConnector = new DBConnector();
    private final TextUI textUI = new TextUI();
    private final MainMenu mainMenu = new MainMenu();

    private final StartMenu startMenu = new StartMenu();

    private User currentUser = null;
    public void start() {
        startMenu.display(dbConnector);
        currentUser = startMenu.getUserAccount();

        mainMenu.allProducts = dbConnector.getProducts();
        mainMenu.allRecipes = dbConnector.getRecipes();



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
                    mainMenu.searchProducts(textUI, currentUser);
                    break;
                case "2":
                    mainMenu.searchRecipes(textUI, currentUser);
                    break;
                case "3":
                    mainMenu.searchByIngredients(textUI, dbConnector, currentUser);
                    break;
                case "4":
                    mainMenu.searchRecipesByBudget(textUI, currentUser);
                    break;
                case "5":
                    mainMenu.getSavedProducts(currentUser, textUI);
                    break;
                case "6":
                    mainMenu.getSavedRecipes(currentUser, textUI);
                    break;
                case "0":
                    dbConnector.saveFavoriteLists(currentUser.getUsername(), currentUser.getSavedProducts(), currentUser.getSavedRecipes());
                    currentUser = null;
                    choosingAction = false;
                    break;
            }
        }
    }
}

