import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class DBConnector {
    private final String DB_URL = "jdbc:mysql://mysql47.unoeuro.com:3306/thegreenway_dk_db";
    private final String USER = "thegreenway_dk";
    private final String PASS = "TheBlueMan45";

    public DBConnector() {
    }

    public boolean checkUsername(String typedUserName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean userExist = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM user";

            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column username
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
        boolean correctPassword = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM user";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column username
                String user = rs.getString("username");
                String password = rs.getString("password");
                if (typedUserName.equals(user) && typedPassword.equals(password)) {
                    correctPassword = true;
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
        return correctPassword;
    }

    public boolean loadAllUsers() {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM user";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
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

        return false;
    }

    public boolean saveUserData(String username, String password) {
        boolean userCreated = false;
        Connection conn = null;
        PreparedStatement stmt = null;

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

    public boolean deleteUser(String typedUsername) {
        boolean userDeleted = false;
        Connection conn = null;
        PreparedStatement stmt = null;

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

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM product";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                //int productID = rs.getInt("productID");
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

    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "SELECT * FROM recipe";
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //Retrieve by column name
                String name = rs.getString("name");
                String ingredientsData = rs.getString("ingredients");
                ArrayList<String> ingredients = new ArrayList<>();
                String[] ingredientsAfterSplit = ingredientsData.split(",", -1);
                for (String i : ingredientsAfterSplit) {
                    ingredients.add(i.trim());
                }
                recipes.add(new Recipe(name, ingredients));
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
    public List<Recipe> getRecipeByIngredient(String ingredients) throws SQLException {
        List<Recipe> recipeList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            /* Prøver at erklære et statement, som udfører forbindelsen med SQL, hvilket giver mulighed for, at kunne
             søge på specifikke ingredienser, som kan forbindes til nøgleord af bestemte ingredienser, og kan
             sammenlignes med opskrifter mm. Og "?" symbolet skal erstattes med værdien af den søgte ingrediens,
             for at undgå forvirring */
            stmt = conn.prepareStatement("SELECT * FROM recipe WHERE ingredients "
                    + "LIKE ?");

    /* Symbolet "%" giver anledning til, at kunne matche enhver karakter i vores string af ingredienser, og "1" er vores
    indeks af vores parameter, som vi vil forbinde værdien af vores valgte ingrediens */
            stmt.setString(1, "%" + ingredients + "%");

            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                String name = results.getString("name");
                List<String> ingredient = Arrays.asList(results.getString("ingredients").split(", "));
                // du skal bruge disse to variabler

                Recipe recipes = new Recipe(name, ingredient); //hernede
                recipeList.add(recipes);
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

    public boolean insertFoodProducts(Product productToInsert) {
        boolean insertCompleted = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO product (name,store, weight, price, image, type) VALUES ('" + productToInsert.name + "', '"
                    + productToInsert.storeType + "', '" +productToInsert.weight + "', '" + productToInsert.price +
                    "', '" + productToInsert.image + "', '" + productToInsert.productType + "')";
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


}