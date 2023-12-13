import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private ArrayList<String> savedProducts;

    private ArrayList<Recipe> savedRecipes = new ArrayList<>();

    public User(String username, String password, ArrayList<String> savedProducts) {
        this.username = username;
        this.password = password;
        this.savedProducts = savedProducts;
    }
    public String getUsername()
    {
        return this.username;
    }

    public void addRecipeToFavorites(Recipe recipe)
    {
    }

    public void removeRecipeFromFavorites(Recipe recipe)
    {
    }

    public ArrayList<Recipe> getSavedRecipes()
    {
        return savedRecipes;
    }

    public ArrayList<String> getSavedProducts()
    {
        return savedProducts;
    }
}
