// Part 5 of the Assignment
import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MenuSearcher {
    // fields
    private final static String appName = "Eets 4 Gobbledy-Geeks";
    private final static String filepath = "src/menu.txt";
    private final static String iconPath = "./gobbledy_geek.png";
    /**
     * main method used to allow the user to search inventory database of Burger, and place an order request
     *
     * @param args none required
     */
    public static void main(String[] args) {
        int result = JOptionPane.showConfirmDialog(null, "Welcome to Super Geek Auto!\n\tDo you want to run this application.", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        if (!(result == JOptionPane.YES_OPTION)) {
            JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
        // Requests user input for type, theme and price range,
        Type userType = (Type) JOptionPane.showInputDialog(null, "Please select your preferred type:", appName, JOptionPane.QUESTION_MESSAGE, icon, Type.values(), Type.U30);
        if (userType == null) {
            JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
        Set<Type> typeToCast = Collections.singleton(userType);
        // request user input for theme
        String userTheme;
        String[] themeValues = {"Star Wars", "Coding", "The Matrix", "The Office", "Marvel Comics", "Breaking Bad"};
        do {
            userTheme = (String) JOptionPane.showInputDialog(null, "Please select your preferred theme:", appName, JOptionPane.QUESTION_MESSAGE, icon, themeValues, themeValues[0]);
            if (userTheme == null) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            } //if the user closes/cancels the dialog, exit normally
        } while (userTheme.length() == 0);
        // request user input for price range i.e. minimum price and maximum price
        double minPrice = -1, maxPrice = -1;
        while (minPrice == -1) {
            try {
                minPrice = Double.parseDouble((String) JOptionPane.showInputDialog(null, "Please enter the lowest price", appName, JOptionPane.QUESTION_MESSAGE, icon, null, ""));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again. Minimum price should be greater than or equal to 0.", appName, JOptionPane.PLAIN_MESSAGE, icon);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
            if (minPrice < 0)
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again. Minimum price should be greater than or equal to 0.", appName, JOptionPane.PLAIN_MESSAGE, icon);
        }
        while (maxPrice < minPrice) {
            try {
                maxPrice = Double.parseDouble((String) JOptionPane.showInputDialog(null, "Please enter the highest price", appName, JOptionPane.PLAIN_MESSAGE, icon, null, ""));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again. Maximum price should be greater than or equal to 0.", appName, JOptionPane.PLAIN_MESSAGE, icon);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
            if (maxPrice < minPrice)
                JOptionPane.showMessageDialog(null, "Maximum price must be greater than Minimum price.", appName, JOptionPane.PLAIN_MESSAGE, icon);
        }
        // using the above data to initialise a Burger object
        Burger burgerCriteria = new Burger("", "", 0, userTheme, typeToCast, null);
        burgerCriteria.setMinPrice(minPrice);
        burgerCriteria.setMaxPrice(maxPrice);

        /*
         * generates JOptionPanes requesting user input for car seat cover types, theme, minPrice and maxPrice
         *
         * @return a Burger object representing the user's desired carSeatCover criteria
         */
        // The Burger object representing the user's filters is used to search for a real Burger (stored in the Menu object) that matches the user's preferences
        // Uses the method described in Step 1.1 to create an Menu object
        Menu menu = loadInventory();
        // The Burger object representing the user's filters should be used to search for a real Burger (stored in the Menu object) that matches the user's preferences.
        List<Burger> potentialMatches = menu.findMatch(burgerCriteria);
        // The program should account for THREE situations:
        // Situation 1 : Matching items are found and user selects ONE.
        if (potentialMatches.size() > 0) {
            Map<String, Burger> options = new HashMap<>();
            // The user should be presented with items that match their criteria in the form of a drop-down list, allowing them to select ONE item.
            StringBuilder infoToShow = new StringBuilder("Matches found!!! The following car seat covers meet your criteria:\n");
            for (Burger potentialMatch : potentialMatches) {
                infoToShow.append("\n").append(potentialMatch.productDescription().replace("Product Description","Caption")).append("\n");
                options.put(potentialMatch.getMenuItemName() + " (" + potentialMatch.getMenuItemID() + ")", potentialMatch);
            }
            // The user is presented with items that match their criteria in the form of a drop-down list, allowing them to select ONE item.
            String order = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which car seat cover you'd like to order:", appName, JOptionPane.QUESTION_MESSAGE, icon, options.keySet().toArray(), "");
            // Situation 3 : The user closes the dialog
            if (order == null) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            } else {
                // if the user selects a matching item,
                // the user's info should be requested
                // and used to create a Geek object,
                // which should then be passed (along with the chosen item)
                // into the method described in Step 1.2
                Burger chosenBurger = options.get(order);
                Geek geek = getUserDetails();
                writeOrderRequestToFile(geek, chosenBurger, userType);
                JOptionPane.showMessageDialog(null, "Thank you! Your order request has been submitted.\nOne of our friendly staff will be in touch shortly.", appName, JOptionPane.QUESTION_MESSAGE, icon);
            }
        }
        // Situation 2 : No matching items are found.
        else {
            int confirm = JOptionPane.showConfirmDialog(null, "Sorry! no matches found. Do you want to start again from the beginning?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
        }
    }

    // A method to load the data from the Burger.txt file.
    // This method should have a String file path parameter,
    // and return an instance of the Menu class,
    // containing all the Burger objects.

    /**
     * method to load all carSeatCover data from file, storing it as Burger objects in an instance of Menu
     *
     * @return an Menu object - functions as database of Burger, with associated methods
     */
    public static Menu loadInventory() {
        Menu menu = new Menu();
        Path path = Path.of(filepath);
        List<String> carSeatCoverData = null;
        try {
            carSeatCoverData = Files.readAllLines(path);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Couldn't load the file. \nError message: " + io.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
        for (int i = 1; i < carSeatCoverData.size(); i++) {
            String[] items = carSeatCoverData.get(i).split("\\[");
            String[] elements = items[0].split(",");
            String name = elements[0];
            String productCode = elements[1];
            double price = Double.parseDouble(elements[2]);
            String theme = elements[3];
            String description = items[2].replace("]", "");
            String[] userTypes = items[1].replace("]", "").split(",");
            for (String enumTypes : userTypes) {
                Set<Type> types = new HashSet<>();
                types.add(Type.valueOf(enumTypes.toUpperCase().strip()));
                Burger burger = new Burger(name, productCode, price, theme, types, description);
                menu.addCarSeatCover(burger);
            }
        }
        return menu;
    }

    /**
     * method to get user to input name, phone and email, with appropriate input validation
     *
     * @return a Geek object representing the user of the program
     */
    private static Geek getUserDetails() {
        String name;
        do {
            name = (String) JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (name == null) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            } //if the user closes/cancels the dialog, exit normally
        } while (!name.contains(" ")); //full name contains whitespace

        long phoneNumber = 0;
        String phoneNumberInput;
        do {
            phoneNumberInput = (String) JOptionPane.showInputDialog(null, "Please enter your phone number. Format: 0412838475", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (phoneNumberInput == null) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
            try {
                phoneNumber = Long.parseLong(phoneNumberInput);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Invalid entry. Please enter your 10 digit phone number.");
            }
        } while (phoneNumberInput.length() != 10);

        String email;
        do {
            email = (String) JOptionPane.showInputDialog(null, "Please enter your email address.", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (email == null) {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            } //if the user closes/cancels the dialog, exit normally
        } while (!email.contains("@")); //email must contain @

        //use those details to create a Person object
        return new Geek(name, phoneNumber, email);
    }

    // A method to write the Geek user's order information to a text file in the format specified by the Greek Geek
    // The method should have Geek, Burger and Type parameters,

    /**
     * provides Super Geek Auto with a file containing the user's order request
     *
     * @param geek         a Geek object representing the user
     * @param burger a Burger object representing the Burger that the user wants to order
     * @param type         a Type Object representing the type user has selected
     */
    private static void writeOrderRequestToFile(Geek geek, Burger burger, Type type) {
        // The name of the file should be the geek's name, followed by the item product code and type, with no whitespaces
        String filePath = geek.getName().replace(" ", "_") + "_" + burger.getMenuItemID() + "_" + type.name() + ".txt";
        // using the above String, create a Path object
        Path path = Path.of(filePath);
        // create the String which will be written to the file
        String lineToWrite = "Order Details :\n\tName : " + geek.getName() + "\n\tPhone Number : " + geek.getPhone() + "\n\tItem : " + burger.getMenuItemName() + "(" + burger.getMenuItemID() + ")\n\tSize : $" + type.name();
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) { //handle exceptions appropriately
            JOptionPane.showMessageDialog(null, "File could not be written. \nError message: " + io.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
    }
}