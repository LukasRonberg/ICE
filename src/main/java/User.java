import java.util.ArrayList;

public class User {

    String Username;

    String Password;

    private ArrayList<Recipe> savedRecipes = new ArrayList<>();

    private ArrayList<String> savedProducts = new ArrayList<>();


    public void addRecipeToFavorites(Recipe recipe)
    {
    }

    public void removeRecipeFromFavorites(Recipe recipe)
    {
    }

    public ArrayList<Recipe> getSavedRecipes() {
        return savedRecipes;
    }
}
