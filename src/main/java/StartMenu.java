import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

class StartMenu {
    protected DBConnector dbConnector = new DBConnector();
    protected TextUi textUI = new TextUi();
    protected User user;
    protected final String exit = "exit";
    protected final String goBack = "q";
    protected final String confirm = "y";

    StartMenu() {
    }
    public void run() {

        while (true) {

            StartMenu startMenu = new StartMenu();

            startMenu.display();

            User user = startMenu.getUserAccount();



            //MainMenu mainMenu = new MainMenu(user);

            //mainMenu.display();

        }

    }
    public void display() {
        boolean choosingAction = true;

        while(choosingAction) {
            this.textUI.displayMessage("\nSTART MENU:\n1. Login\n2. Create Account\n3. Exit\n");
            String input = this.chooseMenuOption();

            try {
                int menuOption = Integer.parseInt(input);
                switch (menuOption) {
                    case 1:
                        this.login();
                        break;
                    case 2:
                        this.createAccount();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        this.errorNotAnOption();
                }

                if (this.user != null) {
                    choosingAction = false;
                }
            } catch (NumberFormatException var4) {
                this.errorNotANumber();
            }
        }

    }

    private void login() {
        this.dbConnector = new DBConnector();
        ArrayList<String> data = this.dbConnector.loadAllUsers("");
        if (!data.isEmpty()) {
            String[] userData = this.validateUsername(data);
            if (userData != null) {
                this.validatePassword(userData);
            }
        } else {
            this.textUi.displayErrorMessage("\nThere's no user accounts.");
        }

    }

    private String[] validateUsername(ArrayList<String> data) {
        boolean isValidatingUsername = true;
        String[] validUser = null;

        while(true) {
            while(isValidatingUsername) {
                String typedUsername = this.textUI.getInput("\nInput username or back to start menu (q): ");
                if (typedUsername.equalsIgnoreCase("q")) {
                    isValidatingUsername = false;
                } else {
                    String[] userdata = this.getUserData(data, typedUsername);
                    if (!userdata[0].isEmpty() && !userdata[1].isEmpty()) {
                        validUser = new String[]{userdata[0], userdata[1]};
                        isValidatingUsername = false;
                    } else {
                        this.textUI.displayErrorMessage("\nCould not find user.");
                    }
                }
            }

            return validUser;
        }
    }

    private String[] getUserData(ArrayList<String> data, String typedUsername) {
        String username = "";
        String password = "";
        Iterator var5 = data.iterator();

        while(var5.hasNext()) {
            String s = (String)var5.next();
            String[] row = s.split(",");
            String usernameData = row[0];
            String passwordData = row[1];
            if (usernameData.equalsIgnoreCase(typedUsername)) {
                username = usernameData;
                password = passwordData;
            }
        }

        return new String[]{username, password};
    }

    private void validatePassword(String[] userData) {
        boolean isValidatingPassword = true;

        while(isValidatingPassword) {
            String typedPassword = this.textUI.getInput("\nInput password or back to start menu (q): ");
            if (userData[1].equals(typedPassword)) {
                this.user = new User(userData[0], userData[1]);
                isValidatingPassword = false;
            } else if (typedPassword.equalsIgnoreCase("q")) {
                isValidatingPassword = false;
            } else {
                this.textUI.displayErrorMessage("\nPassword did not match!");
            }
        }

    }

    private void createAccount() {
        this.dbConnector = new DBConnector();
        ArrayList<String> data = this.dbConnector.loadAllUsers("");
        String username = this.createUsername(data);
        if (!username.isEmpty()) {
            this.createPassword(username);
        }

    }

    private String createUsername(ArrayList<String> data) {
        String username = "";
        boolean isCreatingUsername = true;

        while(isCreatingUsername) {
            String typedUsername = this.textUI.getInput("\nCreate username (Must begin with a letter) or back to start menu (q): ");
            char firstCharacter = typedUsername.charAt(0);
            if (typedUsername.equalsIgnoreCase("q")) {
                isCreatingUsername = false;
            } else if (!Character.isDigit(firstCharacter)) {
                boolean userExists = this.doesUserExists(data, typedUsername);
                if (!userExists) {
                    username = typedUsername;
                }

                isCreatingUsername = false;
            } else {
                this.textUI.displayErrorMessage("\nMust begin with a letter!");
            }
        }

        return username;
    }

    private void createPassword(String username) {
        boolean isCreatingPassword = true;

        while(isCreatingPassword) {
            String password = this.textUI.getInput("\nCreate password (Minimum 8 characters) or back to start menu (q): ");
            if (password.length() >= 8) {
                this.dbConnector = new DBConnector();
                boolean userSavedToFile = this.dbConnector.saveUserData("", username, password);
                if (!userSavedToFile) {
                    this.textUI.displayMessage("\nCould not create an account.");
                } else {
                    String answer = this.textUI.getInput("\nYou have now created an account. Log in? Y/N: ");
                    if (answer.equalsIgnoreCase("y")) {
                        this.user = new User(username, password);
                    }
                }

                isCreatingPassword = false;
            } else if (password.equalsIgnoreCase("q")) {
                isCreatingPassword = false;
            } else {
                this.textUI.displayErrorMessage("\nMust be minimum 8 characters long!");
            }
        }

    }

    private boolean doesUserExists(ArrayList<String> data, String username) {
        boolean userExists = false;
        String[] userData = this.getUserData(data, username);
        if (!userData[0].isEmpty()) {
            userExists = true;
        }

        if (userExists) {
            String answer = this.textUI.getInput("\nUsername already exists. Do you want to login? Y/N: ");
            if (answer.equalsIgnoreCase("y")) {
                this.validatePassword(userData);
            } else {
                this.textUI.displayMessage("\nReturning you to Start Menu.");
            }
        }

        return userExists;
    }

    public User getUserAccount() {
        return this.user;
    }

    protected String chooseMenuOption() {
        return this.textUI.getInput("Choose menu option : ");
    }

    protected String chooseOption() {
        return this.textUI.getInput("\nChoose option or go back (q): ");
    }

    protected void errorNotAnOption() {
        this.textUI.displayErrorMessage("\nNot an option!");
    }

    protected void errorNotANumber() {
        this.textUI.displayErrorMessage("\nChoose a number!");
    }
}



