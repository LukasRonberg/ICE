import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

class StartMenu {
    protected DBConnector dbConnector = new DBConnector();
    protected TextUi textUI = new TextUi();
    protected User currentUser;
    private ArrayList<String> favoriteProducts = new ArrayList<>();
    private ArrayList<String> favoriteRecipes = new ArrayList<>();
    protected final String exit = "exit";
    protected final String goBack = "q";
    protected final String confirm = "y";

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

    private void loginOrDelete(String action)
    {
        this.dbConnector = new DBConnector();
        boolean userTableExist = this.dbConnector.loadAllUsers();
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
                                        ArrayList<String> favoriteProducts = dbConnector.getFavoriteProducts(typedUsername);
                                        ArrayList<String> favoriteRecipes = dbConnector.getFavoriteRecipes(typedUsername);
                                        this.currentUser = new User(typedUsername, typedPassword,favoriteProducts, favoriteRecipes);
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


    private void createUser()
    {
        String username = this.createUsername();
        if (!username.isEmpty()) {
            this.createPassword(username);
        }
    }

    private String createUsername()
    {
        String username = "";
        boolean isCreatingUsername = true;
        while(isCreatingUsername) {
            String typedUsername = this.textUI.getInput("\nCreate username (Must begin with a letter) or back to start menu (q): ");
            char firstCharacter = typedUsername.charAt(0);
            if (typedUsername.equalsIgnoreCase("q")) {
                isCreatingUsername = false;
            } else if (!Character.isDigit(firstCharacter)) {
                boolean userExists = this.dbConnector.checkUsername(typedUsername);
                if (!userExists) {
                    username = typedUsername;
                    isCreatingUsername = false;
                } else if(userExists) {
                    this.textUI.displayErrorMessage("\n Username exist, try another one!");
                }
            } else {
                this.textUI.displayErrorMessage("\nUsername must begin with a letter!");
            }
        }
        return username;
    }

    private void createPassword(String username)
    {
        boolean isCreatingPassword = true;
        while(isCreatingPassword)
        {
            String password = this.textUI.getInput("\nCreate password (Minimum 8 characters) or (q) to go back to start menu: ");
            if (password.length() >= 8)
            {
                this.dbConnector = new DBConnector();
                boolean userSavedToFile = this.dbConnector.saveUserData(username, password);
                if (!userSavedToFile) {
                    this.textUI.displayMessage("\nCould not create an user-account. Try again later!");
                } else {
                    String option = this.textUI.getInput("\nYou have now created an account. Log in? Y/N: ");
                    if (option.equalsIgnoreCase("y")) {
                        this.currentUser = new User(username, password, favoriteProducts, favoriteRecipes);
                    }
                }
                isCreatingPassword = false;
            } else if (password.equalsIgnoreCase("q")) {
                isCreatingPassword = false;
            } else {
                this.textUI.displayErrorMessage("\nPassword must be minimum 8 characters long!");
            }
        }
    }

}


