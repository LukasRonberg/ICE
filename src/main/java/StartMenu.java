import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StartMenu {
    protected DBConnector dbConnector = new DBConnector();
    protected TextUI textUI = new TextUI();
    protected User currentUser;
    private ArrayList<String> favoriteProducts = new ArrayList<>();
    private ArrayList<String> favoriteRecipes = new ArrayList<>();

    StartMenu() {
    }

    public void display() {
        boolean choosingAction = true;

        while(choosingAction) {
            this.textUI.displayMessage("\nSTART MENU:\n1. Login\n2. Create User\n3. Delete User\n0. Exit\n");
            String input = this.chooseMenuOption();

            try {
                int menuOption = Integer.parseInt(input);
                switch (menuOption) {
                    case 1:
                        this.loginOrDelete("login");
                        break;
                    case 2:
                        this.createUser();
                        break;
                    case 3:
                        this.loginOrDelete("delete");
                        break;
                    case 0:
                        System.exit(0);
                        break;
                        //this.errorNotAnOption();
                }

                if (this.currentUser != null) {
                    choosingAction = false;
                }
            } catch (NumberFormatException var4) {
                //this.errorNotANumber();
            }
        }

    }


    protected String chooseMenuOption()
    {
       return textUI.getInput("Choose menu option : ");
    }

    /** this method can either login a user by creating a instance of the user-class called currentUser
     * or delete a user by calling the method deleteUser in the DBConnector-class
     * @param action can contain either delete og login
     */
    private void loginOrDelete(String action)
    {
        this.dbConnector = new DBConnector();
        boolean userTableExist = this.dbConnector.countAllUsers();
        boolean isValidatingUserData = true;
        boolean userExist;

        if (userTableExist) {
            while(isValidatingUserData) {
                String typedUsername = this.textUI.getInput("\nInput username or go back to start menu (q): ");
                if (typedUsername.equalsIgnoreCase("q")) {
                    isValidatingUserData = false;
                } else {
                    userExist = this.dbConnector.checkUsername(typedUsername);
                    if (userExist) {
                        boolean isValidatingPassword = true;
                        while(isValidatingPassword) {
                            String typedPassword = this.textUI.getInput("\nInput password or go back to start menu (q): ");
                            if (!typedPassword.equalsIgnoreCase("q")) {
                                boolean checkUserPassword = this.dbConnector.checkUserPassword(typedUsername, typedPassword);
                                if (checkUserPassword) {
                                    if(action.equals("delete")) {
                                        boolean deleteUser = dbConnector.deleteUser(typedUsername);
                                        if(deleteUser) {
                                            this.textUI.displayErrorMessage("\n"+typedUsername+" has now been deleted from our Database");
                                            isValidatingPassword = false;
                                            isValidatingUserData = false;
                                        }
                                    } else if(action.equals("login")) {
                                        ArrayList<String> favoriteProducts = dbConnector.getFavoriteList(typedUsername,"products");
                                        ArrayList<String> favoriteRecipes = dbConnector.getFavoriteList(typedUsername, "recipes");
                                        this.currentUser = new User(typedUsername,typedPassword,favoriteProducts,favoriteRecipes);
                                        isValidatingPassword = false;
                                        isValidatingUserData = false;
                                    }
                                } else if (typedPassword.equalsIgnoreCase("q")) {
                                    isValidatingPassword = false;
                                } else {
                                    this.textUI.displayErrorMessage("\nPassword did not match!");
                                }
                            } else {
                                isValidatingPassword = false;
                                isValidatingUserData = false;
                            }
                        }
                    } else {
                        this.textUI.displayErrorMessage("\nCould not find user.");
                    }
                }
            }
        } else {
            this.textUI.displayErrorMessage("\nThere's no user accounts.");
        }

    }

    public User getUserAccount()
    {
        return this.currentUser;
    }

    /** we call the method createUsername() that returns a username if it is valid and is not used. If the username is valid then the string will
     * not be empty and we will call the method createPassword with the username as parameter
     */

    private void createUser()
    {
        String username = this.createUsername();
        if (!username.isEmpty()) {
            this.createPassword(username);
        }
    }

    /** we ask for a username and checks if it begins with a letter by using th method isDigit() from the Character-class that takes a char-character
     * if that is not true we call the method checkUsername in the DBConnector-class that returns a boolean. Is the boolean true then there is no one else
     * using that username and we return the userName to the createUser-method. if it is not true then we return a empty string.
     * @return a String containing a valid username
     */

    private String createUsername()
    {
        String username = "";
        boolean isCreatingUsername = true;
        while(isCreatingUsername) {
            String typedUsername = this.textUI.getInput("\nCreate username (Must begin with a letter) or go back to start menu (q): ");
            char firstCharacter = typedUsername.charAt(0);
            if (typedUsername.equalsIgnoreCase("q")) {
                isCreatingUsername = false;
            } else if (!Character.isDigit(firstCharacter)) {
                boolean userExists = this.dbConnector.checkUsername(typedUsername);
                if (!userExists) {
                    username = typedUsername;
                    isCreatingUsername = false;
                } else {
                    this.textUI.displayErrorMessage("\nUsername exist in our database, try another one!");
                }
            } else {
                this.textUI.displayErrorMessage("\nUsername must begin with a letter!");
            }
        }
        return username;
    }

    /** this method ask for a password and checks if it contains at least a number, a letter and a symbol and it has to be at least 8 character long
     * by using the method matches() from the classes Pattern and Matcher.
     * if the password meets our requirement we create a user in the user-table in our online-database by calling the method createUser in the DBConnector-class
     * if it is a succes we create a instance of a user called currentUser
     * @param username the typedUsername from the createUsername-method
     */
    private void createPassword(String username)
    {
        boolean isCreatingPassword = true;
        while(isCreatingPassword)
        {
            String password = this.textUI.getInput("\nCreate a password (Minimum 8 characters long and has to contain at least, a letter, a number and a symbol) or (q) to go back to start menu: ");
            String number = ".*\\d+.*";
            String letter = ".*[a-zA-Z]+.*";
            String symbol = ".*[^\\w\\s]+.*";

            Pattern numberPattern = Pattern.compile(number);
            Pattern letterPattern = Pattern.compile(letter);
            Pattern symbolPattern = Pattern.compile(symbol);

            Matcher numberMatcher = numberPattern.matcher(password);
            Matcher letterMatcher = letterPattern.matcher(password);
            Matcher symbolMatcher = symbolPattern.matcher(password);

            boolean containNumber = numberMatcher.matches();
            boolean containLetter = letterMatcher.matches();
            boolean containSymbol = symbolMatcher.matches();

            if (password.length() >= 8 && containNumber && containLetter && containSymbol)
            {
                this.dbConnector = new DBConnector();
                boolean userSavedToFile = this.dbConnector.createUser(username, password);
                if (!userSavedToFile) {
                    this.textUI.displayMessage("\nCould not create a user account. Try again later!");
                } else {
                    String option = this.textUI.getInput("\nYou have now created a user account. Do you want to log in Y/N ?");
                    if (option.equalsIgnoreCase("y")) {
                        this.currentUser = new User(username, password, favoriteProducts, favoriteRecipes);
                    }
                }
                isCreatingPassword = false;
            } else if (password.equalsIgnoreCase("q")) {
                isCreatingPassword = false;
            } else {
                this.textUI.displayErrorMessage("\nThe specified password does not meet our requirements, try again!");
            }
        }
    }

}


