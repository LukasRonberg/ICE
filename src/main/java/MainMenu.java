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
        String input = ui.getInput("Enter product name:");
        ArrayList<String> uniqueProductNames = new ArrayList<>();

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(input.toLowerCase())) {
                String productName = product.getName();
                if (!uniqueProductNames.contains(productName)) {
                    uniqueProductNames.add(productName);
                }
            }
        }

        if (uniqueProductNames.isEmpty()) {
            ui.displayMessage("No matching products found");
        } else {
            ui.displayMessage("Matching products:");
            int productNumber = 1;
            for (String productName : uniqueProductNames) {
                ui.displayMessage(productNumber + ") " + productName);
                productNumber++;
            }
            productOptions(uniqueProductNames);
        }
    }


    private void productOptions(ArrayList<String> listToChooseFrom){
        int choice = userChoice(listToChooseFrom.size(), true);
        if (choice != 0) {
            String selected = listToChooseFrom.get(choice - 1);
            while (true) {
                int showPriceOrSave = 0;
                boolean productIsSaved = false;
                ui.displayMessage(selected);
                if(currentUser.getSavedProducts().contains(selected)){
                    showPriceOrSave = intParser(ui.getInput("1) Show prices and stores\n2) Remove product search\n0) Return"));
                    productIsSaved = true;
                } else showPriceOrSave = intParser(ui.getInput("1) Show prices and stores\n2) Save product search\n0) Return"));
                switch (showPriceOrSave) {
                    case 1:
                        ui.displayMessage("Stores and Prices for " + selected + ":");
                        for (Product product : allProducts) {
                            if (product.getName().equalsIgnoreCase(selected)) {
                                ui.displayMessage(/*"Price: " + product.getPrice() + " - Store: " + product.getStoreType()*/product.toString());
                            }
                        }
                        break;
                    case 2:
                        if (productIsSaved) currentUser.getSavedProducts().remove(selected);
                        else currentUser.getSavedProducts().add(selected);

                        break;
                    case 0:
                        return;
                }
            }
        }
    }

    public void searchRecipes() {
        String input = ui.getInput("Enter recipe name: ");

        ArrayList<Recipe> matchedRecipe = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (recipe.getName().toLowerCase().contains(input.toLowerCase())) {
                matchedRecipe.add(recipe);
            }
        }
        if (!matchedRecipe.isEmpty()) {
            for (int i = 0; i < matchedRecipe.size(); i++) {
                ui.displayMessage((i + 1) + ") " + matchedRecipe.get(i).getName());
            }
            int choice = userChoice(matchedRecipe.size(), false);
            Recipe selected = matchedRecipe.get(choice - 1);
            recipeOptions(selected);
        } else {
            ui.displayMessage("Try again");
        }


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
        int choice = userChoice(allRecipes.size(), false);

        if (choice != 0) {
            Recipe selected = allRecipes.get(choice - 1);
            recipeOptions(selected);
        }
    }

    private int userChoice(int maxChoice, boolean isProduct) {
        int choice = 0;
        do {
            try {
                if(!isProduct)choice = intParser(ui.getInput("0) Return \nList of recipes\nEnter a number to choose:"));
                else choice = intParser(ui.getInput("0) Return \nList of products\nEnter a number to choose:"));

                if (choice < 0 || choice > maxChoice) {
                    ui.displayMessage("Invalid. Please pick a number between 0 and " + maxChoice);
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

    /*private void productChoices(Product selected) {
        boolean exists = false;
        String selectResponse;

        for (Product product : currentUser.getSavedProducts()) {
            if (product.getName().equals(selected.getName())) {
                exists = true;
                break;
            }
        }
        label:
        while(true) {
            if (exists) {
                selectResponse = ui.getInput("\t" + selected.getName()
                        + "Type: "
                        + selected.getProductType()
                        + " \n1) Show cheapest store to shop"
                        + " \n2) Remove recipe from saved recipes"
                        + " \n3) Return to main menu");
            } else {
                selectResponse = ui.getInput("\t" + selected.getName()
                        + "Type: "
                        + selected.getProductType()
                        + " \n1) Show cheapest store to shop"
                        + " \n2) Save recipe to saved recipes"
                        + " \n3) Return to main menu");
            }
            switch (selectResponse) {
                case "1":
                    showCheapestStore(selected);
                    break;
                case "2":
                    if (!exists) {
                        ui.displayMessage("Added recipe to favorites");
                        currentUser.addProductToFavorites(selected);
                    } else {
                        ui.displayMessage("Removed recipe from favorites");
                        currentUser.removeProductFromFavorites(selected);
                    }
                    break;
                case "3":
                    ui.displayMessage("Returning to main menu...");
                    break label;
            }
        }
    }*/
    private void recipeOptions(Recipe selected) {
        boolean exists = false;
        String selectResponse;

        for (String recipe : currentUser.getSavedRecipes()) {
            if (recipe.equals(selected.getName())) {
                exists = true;
                break;
            }
        }
        label:
        while (true) {
            if (exists) {
                selectResponse = ui.getInput(selected.getName()
                        + ": Ingredients include:"
                        + selected.ingredients
                        + " \n1) Show cheapest products"
                        + " \n2) Show cheapest store to shop"
                        + " \n3) Remove recipe from saved recipes"
                        + " \n0) Return to main menu");
            } else {
                selectResponse = ui.getInput(selected.getName()
                        + ": Ingredients include: "
                        + selected.ingredients
                        + " \n1) Show cheapest products"
                        + " \n2) Show cheapest store to shop"
                        + " \n3) Save recipe to saved recipes"
                        + " \n0) Return to main menu");
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
                        currentUser.getSavedRecipes().add(selected.getName());
                        exists = true;
                    } else {
                        ui.displayMessage("Removed recipe from favorites");
                        currentUser.getSavedRecipes().remove(selected.getName());
                        exists = false;
                    }
                    break;
                case "0":
                    ui.displayMessage("Returning to main menu...");
                    break label;
            }
        }
    }

    public static ArrayList<Product> generateNewProductList() {
        DBConnector dbConnector = new DBConnector();
        ArrayList<Product> productList = new ArrayList<>();
        ArrayList<Product> oldProductList = dbConnector.getNewProducts();
        Random random = new Random();

        for (Product p: oldProductList) {
            for(int j = 0; j < Enums.StoreType.values().length; j++) {
                String name = p.name;
                int weight = p.weight;
                int price1 = (int) (((p.price*20/100)+1)*-1);
                int price2 = (int) (p.price*20/100)+1;
                double price = p.price-(random.nextInt(price1,price2));
                Enums.ProductType productType = p.productType;
                Enums.StoreType storeType = Enums.StoreType.values()[j];
                Product product = new Product(name, (int) weight, price, null, productType, storeType);
                productList.add(product);
            }
        }
        return productList;
    }


    public void showCheapestStore(Recipe selected) {
        // TODO: 11-12-2023 skal have mulighed for at vælge en butik og se produkter
        ArrayList<Recipe.Store> stores = selected.findCheapestStore(allProducts);

        for (int i = 1; i < stores.size(); i++) {
            ui.displayMessage(i + ") " + stores.get(i-1).toString());
        }

        String selection = ui.getInput("0) Return \nType the number of the store you want to visit");
        if(selection.equals("0")) return;
        while (true) {
            try {
                var store = stores.get(Integer.parseInt(selection));
                for (Product p: store.getProducts()) {
                    ui.displayMessage(p.toString());
                };
                System.out.println();
                break;
            } catch (NumberFormatException e) {
                //throw new RuntimeException(e);
                ui.getInput("Number wasn't valid, try again:");
            }
        }
    }

    public void showCheapestProducts(Recipe selected) {
        // TODO: 11-12-2023 skal have mulighed for at vælge et produkt og gemme osv.
        selected.findCheapestProductsNewAndBetter(allProducts, null);
        int counter = 1;
        for (Product p : selected.getProductList()) {
            ui.displayMessage(counter+") "+p.toString());
            counter ++;
        }

        productOptions((ArrayList<String>) selected.ingredients);
    }

    public void getSavedProducts() {
        int counter = 1;
        for (String productName : currentUser.getSavedProducts()){
            ui.displayMessage(counter+") " + productName);
            counter++;
        }
        productOptions(currentUser.getSavedProducts());
    }

    public void getSavedRecipes() {
        int counter = 1;
        for (String recipeName : currentUser.getSavedRecipes()){
            ui.displayMessage(counter+") "+recipeName);
            counter++;
        }

        int choice = userChoice(currentUser.getSavedRecipes().size(), false);
        if(choice == 0) return;
        Recipe recipeChosen = null;
        for (Recipe recipe: allRecipes){
            if(recipe.getName().equals(currentUser.getSavedRecipes().get(choice-1))){
                recipeChosen = recipe;
                break;
            }
        }

        recipeOptions(recipeChosen);
    }
}
