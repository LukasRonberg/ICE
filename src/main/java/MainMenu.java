import java.sql.SQLException;
import java.util.*;

public class MainMenu {
    public ArrayList<Recipe> allRecipes;

    ArrayList<Product> allProducts;
    TextUi ui = new TextUi();

    DBConnector dbConnector = new DBConnector();

    User currentUser;

    public void startUp() {
        // Get recipes
        allRecipes = dbConnector.getRecipes();
        if (allRecipes == null) allRecipes = new ArrayList<>();
        // Get products
        allProducts = dbConnector.getProducts();
    }

    public void searchProducts() {
    }

    public void searchRecipes() {
    }

    // TODO: 11-12-2023 tilføj funktionalitet til at søge på et delvist ord og spørg om det var dette der blev ment 
    public void searchByIngrediens() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a desired ingredient: ");
        String ingredient = scanner.nextLine();
        List<Recipe> searchedList = dbConnector.getRecipeByIngredient(ingredient);

        for (Recipe r : searchedList) {
            System.out.println(r);
        }
    }

    public static ArrayList<Product> generateDataset(int numEntries) {
        ArrayList<Product> dataset = new ArrayList<>();
        Random random = new Random();

        String[] mad = new String[]{"hakket oksekød", "letmælk", "smør", "revet mozzarella", "peberfrugt", "ris",
                "kyllingebryst", "æg", "tomater", "løg", "gulerødder", "kartofler", "hvedemel", "brød", "bananer",
                "æbler", "appelsiner", "spinat", "broccoli", "laks", "tunge fløde", "parmesan", "spaghetti",
                "olivenolie", "hvidløg", "agurk", "ærter", "kiks", "yoghurt", "honning", "avocado", "svinekød", "majs",
                "grønne bønner", "pølser", "jordnøddesmør", "risengrød", "rosiner", "havregryn", "kaffe", "te",
                "chokolade", "rugbrød", "solsikkefrø", "is", "rugbrødschips", "koriander", "tomatsauce", "mel", "bønner"
                , "mayonnaise", "citron", "ost", "æggepasta", "hakket kylling", "taco-skaller", "vaniljeis", "friske bær"
                , "hakket svinekød", "fløde", "mælk", "kyllingelever", "rugbrødstoast", "frossen pizza", "frossen grøntsagsblanding",
                "citronsaft", "appelsinjuice", "fiskesauce", "sojasauce", "pasta", "gær", "balsamicoeddike", "røde bønner",
                "mørk chokolade", "sukker", "kanel", "jordbærmarmelade", "ærtepuré", "kyllingefond", "tun på dåse", "frisk ingefær",
                "muskatnød", "flødeost", "rucola", "paprika", "rødvin", "hvidvin",
                "letmælk", "revet mozzarella", "peberfrugt", "agurk", "kiks", "honning", "avocado", "grønne bønner",
                "pølser", "jordnøddesmør", "rugbrød", "solsikkefrø", "rugbrødschips", "is", "friske bær", "fløde", "mælk",
                "kyllingelever", "rugbrødstoast", "frossen pizza", "frossen grøntsagsblanding", "citronsaft", "appelsinjuice",
                "fiskesauce", "sojasauce", "gær", "balsamicoeddike", "røde bønner", "sukker", "jordbærmarmelade", "ærtepuré",
                "kyllingefond", "tun på dåse", "frisk ingefær", "muskatnød", "rucola", "paprika"};

        for (int i = 0; i < numEntries; i++) {
            String name = mad[random.nextInt(0, mad.length)];
            double weight = random.nextInt(50, 1000);
            double price = random.nextInt(5, 50);
            Enums.ProductType productType = Enums.ProductType.MEAT;
            Enums.StoreType storeType = Enums.StoreType.values()[random.nextInt(Enums.StoreType.values().length)]; // Vælg en tilfældig butikstype

            Product product = new Product(name, (int) weight, price, null, productType, storeType);
            dataset.add(product);
        }

        return dataset;
    }

    public void searchRecipesByBudget() {
        //double totalPrice = 0;
        double userBudget = Double.parseDouble(ui.getInput("Enter your budget:"));

        for (Recipe recipe : allRecipes) {
            recipe.calculateTotalPrice(allProducts);
        }

        for (int i = 0; i < allRecipes.size(); i++) {
            if (userBudget >= allRecipes.get(i).TotalPrice) {
                ui.displayMessage((i + 1) + ") " + allRecipes.get(i).getName() + " - " + allRecipes.get(i).TotalPrice + " DKK");
            }
        }
        int choice = userChoice(allRecipes.size());

        if (choice != 0) {
            Recipe selected = allRecipes.get(choice - 1);
            recipeChoices(selected);
        }
    }

    private int userChoice(int maxChoice) {
        int choice = 0;
        do {
            try {
                choice = intParser(ui.getInput("List of recipes\nEnter a number to choose:"));
                if (choice < 0 || choice > maxChoice) {
                    ui.displayMessage("Invalid. Please pick a number between 1 and " + maxChoice);
                }
            } catch (NumberFormatException e) {
                ui.displayMessage("Invalid. Please enter a valid number");
            }
        } while (choice < 0 || choice > maxChoice);
        return choice;
    }

    private int intParser(String stringToParse) {
        try {
            return Integer.parseInt(stringToParse);
        } catch (NumberFormatException e) {
            return intParser(ui.getInput("Input wasn't a number please try again: "));

        }
    }

    private void recipeChoices(Recipe selected) {
        boolean exists = false;
        String selectResponse;

        for (Recipe recipe : currentUser.getSavedRecipes()) {
            if (recipe.getName().equals(selected.getName())) {
                exists = true;
                break;
            }
        }
        label:
        while(true) {
            if (exists) {
                selectResponse = ui.getInput("\t" + selected.getName()
                        + "Ingredients:"
                        + selected.ingredients
                        + " \n1) Show cheapest products"
                        + " \n2) Show cheapest store to shop"
                        + " \n3) Remove recipe from saved recipes"
                        + " \n4) Return to main menu");
            } else {
                selectResponse = ui.getInput("\t" + selected.getName()
                        + "Ingredients:"
                        + selected.ingredients
                        + " \n1) Show cheapest products"
                        + " \n2) Show cheapest store to shop"
                        + " \n3) Save recipe to saved recipes"
                        + " \n4) Return to main menu");
            }
            switch (selectResponse) {
                case "1":
                    showCheapestProducts(selected);
                    break;
                case "2":
                    showCheapestStore(selected);
                    break;
                case "3":
                    if (!exists) {
                        ui.displayMessage("Added recipe to favorites");
                        currentUser.addRecipeToFavorites(selected);
                    } else {
                        ui.displayMessage("Removed recipe from favorites");
                        currentUser.removeRecipeFromFavorites(selected);
                    }
                    break;
                case "4":
                    ui.displayMessage("Returning to main menu...");
                    break label;
            }
        }
    }


    public void showCheapestStore(Recipe selected){
        // TODO: 11-12-2023 skal have mulighed for at vælge en butik og se produkter
        ArrayList<Recipe.Store> stores = selected.findCheapestStore(allProducts);
        for (Recipe.Store store : stores) {
            ui.displayMessage(store.toString());
        }
    }

    public void showCheapestProducts(Recipe selected){
        // TODO: 11-12-2023 skal have mulighed for at vælge et produkt og gemme osv.
        selected.findCheapestProductsNewAndBetter(allProducts, null);
        for (Product p : selected.getProductList()) {
            ui.displayMessage(p.toString());
        }

    }
    public void getSavedProducts() {
    }

    public void getSavedRecipes() {
    }
}
