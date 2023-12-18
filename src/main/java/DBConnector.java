import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DBConnector {
    private final String DB_URL = "jdbc:mysql://mysql47.unoeuro.com:3306/thegreenway_dk_db";
    private final String DB2_URL = "jdbc:mysql://mysql47.unoeuro.com:3306/thegreenway_dk_db_sikkerhed";
    private final String USER = "thegreenway_dk";
    private final String PASS = "TheBlueMan45";

    MainMenu mainMenu = new MainMenu();
    public DBConnector() {
    }

    public boolean countAllUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean userTableExist = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT COUNT(*) AS userCount FROM user";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int userCount = rs.getInt("userCount");
                if (userCount > 0) {
                    userTableExist = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        }
        return userTableExist;
    }

    public boolean checkUsername(String typedUserName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean userExist = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT username FROM user";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String user = rs.getString("username");
                if (typedUserName.equals(user)) {
                    userExist = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        }
        return userExist;
    }

    public boolean checkUserPassword(String typedUserName, String typedPassword) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isPasswordCorrect = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT username,password FROM user";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String user = rs.getString("username");
                String password = rs.getString("password");
                if (typedUserName.equals(user) && typedPassword.equals(password)) {
                    isPasswordCorrect = true;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        }
        return isPasswordCorrect;
    }

    /** creates a user in our user-table in our Database
     * @param username the typedUsername
     * @param password the typedPassword
     * @return a boolean that is true if the action is a success
     */

    public boolean createUser(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean userCreated = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO user (username,password) VALUES ('" + username + "', '" + password + "')";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            userCreated = true;
        } catch (SQLException var23) {
            var23.printStackTrace();
        } catch (Exception var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var22) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        }
        return userCreated;
    }

    /** this method deletes a user in our user-table in our Database
     * @param typedUsername is the user that shall be deleted
     * @return true if the action is a success and falls if not
     */
    public boolean deleteUser(String typedUsername) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean userDeleted = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM user WHERE username = '" + typedUsername + "'";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            userDeleted = true;
        } catch (SQLException var23) {
            var23.printStackTrace();
        } catch (Exception var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var22) {
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        }
        return userDeleted;
    }

    /** this method creates an ArrayList of Products by importing the products from our product-table in our Database
     * @return an ArrayList of Products
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM products";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                Enums.StoreType storeType = Enums.StoreType.valueOf(rs.getString("store"));
                int weight = rs.getInt("weight");
                double price = rs.getInt("price");
                String image = rs.getString("image");
                Enums.ProductType productType = Enums.ProductType.valueOf(rs.getString("type"));
                products.add(new Product(name, weight, price, image, productType, storeType));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        }
        return products;
    }

    /** This method imports all our products from our basic-table in another Database.
     * @return an ArrayList of products that is used by the generateNewProductList() in the MainMenu-class
     */
    public ArrayList<Product> getNewProducts() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Product> products = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB2_URL, USER, PASS);
            String sql = "SELECT * FROM product";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                Enums.StoreType storeType = Enums.StoreType.valueOf(rs.getString("store"));
                int weight = rs.getInt("weight");
                double price = rs.getInt("price");
                String image = rs.getString("image");
                Enums.ProductType productType = Enums.ProductType.valueOf(rs.getString("type"));
                products.add(new Product(name, weight, price, image, productType, storeType));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
        }
        return products;
    }

    /** this method creates a ArrayList of Recipes by importing them from the recipe-table in our Database
     * @return an ArrayList of Recipes
     */
    public ArrayList<Recipe> getRecipes() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM recipe";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String ingredientsData = rs.getString("ingredients");
                if (ingredientsData != null) {
                    ArrayList<String> ingredients = new ArrayList<>();
                    String[] ingredientsAfterSplit = ingredientsData.split(",");
                    for (String i : ingredientsAfterSplit) {
                       ingredients.add(i.trim());
                    }
                    recipes.add(new Recipe(name, ingredients));
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException var25) {
            var25.printStackTrace();
        } catch (Exception var26) {
            var26.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var24) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var23) {
                var23.printStackTrace();
            }
            return recipes;
        }
    }


    // Liste over vores madvarer, i form af en ArrayList
    //TODO Listen skal præsenteret ordentligt overfor brugeren, så vedkommende kan vælge, ligesom i search recipes
    public ArrayList<Recipe> getRecipeByIngredient(String ingredient) throws SQLException {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            /* Prøver at erklære et statement, som udfører forbindelsen med SQL, hvilket giver mulighed for, at kunne
             søge på specifikke ingredienser, som kan forbindes til nøgleord af bestemte ingredienser, og kan
             sammenlignes med opskrifter mm. Og "?" symbolet skal erstattes med værdien af den søgte ingrediens,
             for at undgå forvirring */
            String sql = "SELECT * FROM recipe WHERE ingredients LIKE '%"+ingredient+"%'";
            stmt = conn.prepareStatement(sql);

    /* Symbolet "%" giver anledning til, at kunne matche enhver karakter i vores string af ingredienser, og "1" er vores
    indeks af vores parameter, som vi vil forbinde værdien af vores valgte ingrediens */
            //stmt.setString(1, "%" + ingredients + "%");
            ResultSet results = stmt.executeQuery(sql);

            if (results.next()) {
                while (results.next()) {
                    String name = results.getString("name");
                    System.out.println(name);
                    String ingredientsData = results.getString("ingredients");
                    ArrayList<String> ingredientList = new ArrayList<>();
                    String[] ingredientsAfterSplit = ingredientsData.split(",");
                    for (String i : ingredientsAfterSplit) {
                        ingredientList.add(i.trim());
                    }
                    recipeList.add(new Recipe(name, ingredientList));
                }

                //TODO Denne del prøver at opsætte vores recipes ud fra users valg, men derimod så forsvinder
                // ingredienserne fra vores valgmuligheder(?)
                /*
                System.out.println("Select a recipe from the following options:");
                for (int i = 0; i < recipeList.size(); i++) {
                    System.out.println(i + 1 + ". " + recipeList.get(i).getName());
                }
                int choice = Integer.parseInt(System.console().readLine());

                // Return the selected recipe
                return (List<Recipe>) recipeList.get(choice - 1);
                 */
            }
            results.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }  catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return recipeList;
    }
    public List<Recipe> recipeOptions(List<Recipe> recipeOptions) {
        return recipeOptions;
    }

    public boolean insertFoodProducts(Product productToInsert) {
        boolean insertCompleted = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO product (name,store, weight, price, image, type) VALUES ('" + productToInsert.getName() + "', '"
                    + productToInsert.storeType + "', '" +productToInsert.getWeight() + "', '" + productToInsert.getPrice() +
                    "', '" + productToInsert.getImage() + "', '" + productToInsert.productType + "')";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            insertCompleted = true;
        } catch (SQLException var23) {
            var23.printStackTrace();
        } catch (Exception var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var22) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        }
        return insertCompleted;
    }

    public boolean insertAllFoodProducts(ArrayList<Product> newProductList) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean productTableCreated = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("The product-table are being created.....");
            for(Product p: newProductList)
            {
               String sql = "INSERT INTO products (name, store, weight, price, image, type) VALUES ('" + p.getName() + "', '"
                        + p.storeType + "', '" + p.getWeight() + "', '" + p.getPrice() +
                        "', '" + p.getImage() + "', '" + p.productType + "')";
                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);
            }
            System.out.println("The product-table has been successfully created.....");
            stmt.close();
            conn.close();
            productTableCreated = true;
        } catch (SQLException var23) {
            var23.printStackTrace();
        } catch (Exception var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var22) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        }
        return productTableCreated;
    }


    public ArrayList<String> getFavoriteList(String userName, String list) {
            Connection conn = null;
            PreparedStatement stmt = null;
            ArrayList<String> favoriteList = new ArrayList<>();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                String sql = "SELECT username,favoriteProducts,favoriteRecipes FROM user";
                stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    String user = rs.getString("username");
                    String products = rs.getString("favoriteProducts");
                    String recipes = rs.getString("favoriteRecipes");
                    if(list.equals("products")) {
                        if (userName.equals(user) && products != null) {
                            String[] productsSplitted = products.split(",");
                            for (String p: productsSplitted) {
                                favoriteList.add(p.trim());
                            }
                        }
                    } else if(list.equals("recipes")) {
                        if (userName.equals(user) && recipes != null) {
                            String[] recipesSplitted = recipes.split(",");
                            for (String p: recipesSplitted) {
                                favoriteList.add(p.trim());
                            }
                        }
                    }
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException var25) {
                var25.printStackTrace();
            } catch (Exception var26) {
                var26.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException var24) {
                }

                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException var23) {
                    var23.printStackTrace();
                }

            }
            return favoriteList;
    }


    public boolean saveFavoriteLists(String username, ArrayList<String> favoriteproducts, ArrayList<String> favoriterecipes) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean favoriteListSaved = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String favoriteprod = "";
            String favoriterecip = "";
            if(!favoriteproducts.isEmpty()) {
                for (String p : favoriteproducts) {
                    if (!p.isEmpty()) {
                        favoriteprod = favoriteprod + p + ",";
                    }
                }
                String sql = "UPDATE user SET favoriteProducts = '"+favoriteprod+"' WHERE username = '"+username+"'";
                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }
            if(!favoriterecipes.isEmpty()) {
                for (String r : favoriterecipes) {
                    if (!r.isEmpty()) {
                        favoriterecip = favoriterecip + r + ",";
                    }
                }
                String sql = "UPDATE user SET favoriteRecipes = '"+favoriterecip+"' WHERE username = '"+username+"'";
                stmt = conn.prepareStatement(sql);
                stmt.executeUpdate(sql);
                stmt.close();
            }
            conn.close();
            favoriteListSaved  = true;
        } catch (SQLException var23) {
            var23.printStackTrace();
        } catch (Exception var24) {
            var24.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException var22) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        }
        return favoriteListSaved;
    }
}