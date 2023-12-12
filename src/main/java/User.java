import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }

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
