import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MenuSearcher {
    private final static String appName = "Eets 4 Gobbledy-Geeks";
    private final static String filePath = "./menu.txt";
    private final static String iconPath = "./gobbledy_geek.png";
    private final static ImageIcon icon = new ImageIcon(iconPath);
    private static Menu menu;

    public static void main(String[] args) {
        int start = JOptionPane.showOptionDialog(null, "Welcome to Eets 4 Gobbledy-Geeks!\n\tTo start, click OK.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[]{"OK"}, "OK");
        if (!(start == JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
        menu = loadMenu(filePath);
        Burger burgerCriteria = getUserCriteria();
        List<Burger> potentialMatches = menu.findMatch(burgerCriteria);
        if (potentialMatches.size() > 0) {
            Map<String, Burger> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following burgers meet your criteria: \n");
            for (Burger potentialMatch : potentialMatches) {
                infoToShow.append(potentialMatch.productDescription());
                options.put(potentialMatch.getMenuItemName() + " (" + potentialMatch.getMenuItemID() + ")", potentialMatch);
            }
            String order = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which (if any) burger you'd like to order:", appName, JOptionPane.QUESTION_MESSAGE, icon, options.keySet().toArray(), "");
            if (order == null) System.exit(0);
            else {
                Burger chosenBurger = options.get(order);
                Geek geek = getUserDetails();
                writeOrderRequestToFile(geek, chosenBurger);
                JOptionPane.showMessageDialog(null, "Thank you! Your order has been submitted. Please wait for your name to be called out... ", appName, JOptionPane.QUESTION_MESSAGE, icon);
            }
        } else {
            int customOrder = JOptionPane.showOptionDialog(null, "Unfortunately none of our burgers meet your criteria :(\n\tWould you like to place a custom order?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[]{"Yes", "No"}, "Yes");
            if (customOrder == JOptionPane.YES_OPTION) {
                Geek geek = getUserDetails();
                writeCustomOrderRequestToFile(geek, burgerCriteria);
                JOptionPane.showMessageDialog(null, "Thank you! Your order has been submitted. Please wait for your name to be called out... ", appName, JOptionPane.QUESTION_MESSAGE, icon);
            } else System.exit(0);
        }
    }

    private static Geek getUserDetails() {
        String name;
        do {
            name = (String) JOptionPane.showInputDialog(null, "Please enter your full name for the order.", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (name == null) System.exit(0);
        } while (!name.contains(" "));
        String phoneNumberInput;
        do {
            phoneNumberInput = (String) JOptionPane.showInputDialog(null, "Please enter your phone number.\nIt will be used as your order number.", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (phoneNumberInput == null) System.exit(0);
            if (!phoneNumberInput.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, "Invalid entry. Please enter your 10-digit phone number in the format 0412123345.");
            }
        } while (!phoneNumberInput.matches("\\d{10}"));
        return new Geek(name, phoneNumberInput);
    }

    private static Burger getUserCriteria() {
        String bun = (String) JOptionPane.showInputDialog(null, "Please select your preferred bun type:", "Eets 4 Gobbledy-Geeks", JOptionPane.QUESTION_MESSAGE, icon, menu.getAllBunTypes().toArray(), "");
        if (bun == null) System.exit(0);
        Meat meat = (Meat) JOptionPane.showInputDialog(null, "Please select your preferred patty meat:", appName, JOptionPane.QUESTION_MESSAGE, icon, Meat.values(), Meat.BEEF);
        if (meat == null) System.exit(0);
        Set<Sauces> selectedSauces = new HashSet<>();
        do {
            Sauces sauces = (Sauces) JOptionPane.showInputDialog(null, "Please select your preferred sauce:", appName, JOptionPane.QUESTION_MESSAGE, icon, Sauces.values(), Sauces.TOMATO);
            if (sauces == null) {
                System.exit(0);
            }
            selectedSauces.add(sauces);
            int response = JOptionPane.showOptionDialog(null, "Do you want to add more sauce?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[]{"Yes", "No"}, "No");
            if (!(response == JOptionPane.YES_OPTION)) {
                break;
            }
        } while (true);
        boolean cheeseValue = false, picklesValue = false;
        int cheeseSelection = JOptionPane.showOptionDialog(null, "Would you like cheese?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[]{"Yes", "No"}, "No");
        if (cheeseSelection == JOptionPane.YES_OPTION) {
            cheeseValue = true;
        }
        int picklesSelection = JOptionPane.showOptionDialog(null, "Would you like pickles?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[]{"Yes", "No"}, "No");
        if (picklesSelection == JOptionPane.YES_OPTION) {
            picklesValue = true;
        }
        double minPrice = -1.0, maxPrice = -1.0;
        while (minPrice == -1) {
            String minPriceInput = (String) JOptionPane.showInputDialog(null, "Please enter the lowest price", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (minPriceInput == null) {
                System.exit(0);
            }
            try {
                minPrice = Double.parseDouble(minPriceInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.PLAIN_MESSAGE, icon);
            }
        }
        while (maxPrice < minPrice) {
            String maxPriceInput = (String) JOptionPane.showInputDialog(null, "Please enter the highest price", appName, JOptionPane.QUESTION_MESSAGE, icon, null, "");
            if (maxPriceInput == null) {
                System.exit(0);
            }
            try {
                maxPrice = Double.parseDouble(maxPriceInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.PLAIN_MESSAGE, icon);
            }
            if (maxPrice < minPrice)
                JOptionPane.showMessageDialog(null, "Max price must be >= min price.", appName, JOptionPane.PLAIN_MESSAGE, icon);
        }
        Burger burgerCriteria = new Burger("", "", -1, bun, meat, selectedSauces, cheeseValue, picklesValue, "");
        burgerCriteria.setMinPrice(minPrice);
        burgerCriteria.setMaxPrice(maxPrice);
        return burgerCriteria;
    }

    public static Menu loadMenu(String filePath) {
        Menu menu = new Menu();
        Path path = Path.of(filePath);
        List<String> burgerData = null;
        try {
            burgerData = Files.readAllLines(path);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Couldn't load the file. \nError message: " + io.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
        for (int i = 1; i < burgerData.size(); i++) {
            String[] items = burgerData.get(i).split("\\[");
            String[] elements = items[0].split(",");
            String itemID = elements[0];
            String itemName = elements[1];
            double price = 0;
            try {
                price = Double.parseDouble(elements[2]);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Error in file. Price data could not be loaded for burger on line " + (i + 1) + ". System Exited. \nError message: " + n.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
            String bun = elements[3].substring(0, 1).toUpperCase() + elements[3].substring(1);
            Meat meat = null;
            try {
                meat = Meat.valueOf(elements[4].toUpperCase());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Error in file. Meat data could not be loaded for burger on line " + (i + 1) + ". System Exited. \nError message: " + e.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
                System.exit(0);
            }
            String cheese = elements[5];
            boolean cheeseValue = false, picklesValue = false;
            if (cheese.equals("yes")) cheeseValue = true;
            String pickles = elements[6];
            if (pickles.equals("yes")) picklesValue = true;
            String description = items[2].replace("]", "");
            String saucesSet = items[1].replace("]", "");
            Set<Sauces> sauces = new HashSet<>();
            Sauces sauce = null;
            for (String singleSauce : saucesSet.replace(" ", "").split(",")) {
                try {
                    sauce = Sauces.valueOf(singleSauce.toUpperCase());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error in file. Sauces data could not be loaded for burger on line " + (i + 1) + ". System Exited. \nError message: " + e.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
                    System.exit(0);
                }
                sauces.add(sauce);
            }
            Burger burger = new Burger(itemName, itemID, price, bun, meat, sauces, cheeseValue, picklesValue, description);
            menu.addBurger(burger);
        }
        return menu;
    }

    private static void writeOrderRequestToFile(Geek geek, Burger burger) {
        String filePath = geek.getName().replaceAll(" ", "_") + burger.getMenuItemID();
        Path path = Path.of(filePath);
        String lineToWrite = "Order Details:\n\tName: " + geek.getName() + " (" + geek.getPhone() + ")" + "\n\tItem: " + burger.getMenuItemName() + " (" + burger.getMenuItemID() + ")";
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "File could not be written. \nError message: " + io.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
    }

    private static void writeCustomOrderRequestToFile(Geek geek, Burger burger) {
        String filePath = geek.getName().replaceAll(" ", "_") + burger.getMenuItemID();
        Path path = Path.of(filePath);
        String lineToWrite = "Order Details:\n\tName: " + geek.getName() + " (" + geek.getPhone() + ")" + "\n\nCustom Order...\n\n************************************\nIngredients:\n\tBun Type: " + burger.getBun() + "\n\tMeat: " + burger.getPatties() + "\n\tSauce/s: " + burger.getSauces() + "\n\tOther: ";
        if (burger.isCheese()) lineToWrite = lineToWrite.concat("Cheese ");
        if (burger.isPickles()) lineToWrite = lineToWrite.concat("Pickle ");
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "File could not be written. \nError message: " + io.getMessage(), appName, JOptionPane.PLAIN_MESSAGE, icon);
            System.exit(0);
        }
    }
}