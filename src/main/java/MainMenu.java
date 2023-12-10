import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
    public ArrayList<Recipe> allRecipes;



    ArrayList<Product> allProducts;
    TextUi ui = new TextUi();

    DBConnector dbConnector = new DBConnector();

    User currentUser;

    public void startUp(){
        // Get recipes
        allRecipes = dbConnector.getRecipes();
        if(allRecipes == null) allRecipes = new ArrayList<>();
        // Get products
        allProducts = dbConnector.getProducts();
    }

    public void searchProducts()
    {
    }

    public void searchRecipes()
    {
    }

    public void searchByIngrediens()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a desired ingredient: ");
        String ingredient = scanner.nextLine();
        dbConnector.getRecipeByIngredient(ingredient);
    }

    public void searchRecipesByBudget()
    {
        double totalPrice = 0;
        double userBudget = Double.parseDouble(ui.getInput("Enter your budget:"));

        for(Recipe recipe : allRecipes){
            for(Product product : recipe.getProductList()){
            totalPrice += product.getPrice();
            }

                for(int i = 0;i < allRecipes.size() ;i++){
                    if(userBudget >= totalPrice){
                        ui.displayMessage((i + 1) + ") " + allRecipes.get(i).getName() + " - " + totalPrice + " DKK");
                    }
                }
                int choice = userChoice(allRecipes.size());

                if (choice!=0){
                    Recipe selected = allRecipes.get(choice - 1);
                    recipeChoices(selected);
            }

        }

    }

    private int userChoice(int maxChoice)
    {
        int choice = 0;
        do
        {
            try
            {
                choice = intParser(ui.getInput("List of recipes\nEnter a number to choose:"));
                if (choice < 0 || choice > maxChoice)
                {
                    ui.displayMessage("Invalid. Please pick a number between 1 and " + maxChoice);
                }
            } catch (NumberFormatException e) {
                ui.displayMessage("Invalid. Please enter a valid number");
            }
        } while (choice < 0 || choice > maxChoice);
        return choice;
    }
    private int intParser(String stringToParse)
    {
        try
        {
            return Integer.parseInt(stringToParse);
        } catch (NumberFormatException e)
        {
            return intParser(ui.getInput("Input wasn't a number please try again: "));

        }
    }
    private void recipeChoices(Recipe selected)
    {
        boolean exists = false;
        String selectResponse;

        for (Recipe recipe: currentUser.getSavedRecipes())
        {
            if (recipe.getName().equals(selected.getName()))
            {
                exists = true;
                break;
            }
        }

        if(exists)
        {
            selectResponse = ui.getInput("\t"+selected.getName()
                    + "Ingredients:"
                    + selected.ingredients
                    + " \n1) Return to main menu"
                    + " \n2) Remove from favorite recipes");
        }
        else
        {
            selectResponse = ui.getInput("\t"+selected.getName()
                    + "Ingredients:"
                    + selected.ingredients
                    + " \n1) Return to main menu"
                    + " \n2) Add to favorite recipes");
        }
        if(selectResponse.equals("1"))
        {
            ui.displayMessage("Returning to main menu...");
        } else if (selectResponse.equals("2")){
            if(!exists){
                ui.displayMessage("Added recipe to favorites");
                currentUser.addRecipeToFavorites(selected);
            } else {
                ui.displayMessage("Removed recipe from favorites");
                currentUser.removeRecipeFromFavorites(selected);
            }
        }
    }



}
