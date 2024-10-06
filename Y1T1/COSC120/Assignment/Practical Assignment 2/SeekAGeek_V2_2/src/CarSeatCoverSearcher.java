import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CarSeatCoverSearcher {
    private static final String filePath = "./inventory_v2.txt";
    private static final String appName = "SuperGeek Auto";
    private static final String[] args = null;
    private static Inventory inventory;

    /**
     * main method
     * calls loadInventory() by passing file path as parameter and saves in inventory.
     * calls getFilters() and saves as CarSeatCoverSpecs
     * calls processSearchResults() by passing saved CarSeatCoverSpecs Object
     *
     * @param args none required
     */
    public static void main(String[] args) {
        int result = JOptionPane.showConfirmDialog(null, "Welcome to Super Geek Auto!\n\tDo you want to run this application.", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
        if (!(result == JOptionPane.YES_OPTION)) {
            JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
            System.exit(0);
        }
        inventory = loadInventory(filePath);
        CarSeatCoverSpecs dreamSeatCover = getFilters();
        processSearchResults(dreamSeatCover);
        System.exit(0);
    }

    /**
     * method to get user search criteria
     *
     * @return a CarSeatCoverSpecs object representing the user search criteria
     */
    public static CarSeatCoverSpecs getFilters() {
        Type type = (Type) JOptionPane.showInputDialog(null, "Please select your preferred type:", appName, JOptionPane.QUESTION_MESSAGE, null, Type.values(), Type.U06H);
        if (type == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between.\nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
        Materials materials;
        materials = (Materials) JOptionPane.showInputDialog(null, "Please select your preferred materials:", appName, JOptionPane.QUESTION_MESSAGE, null, Materials.values(), Materials.ELASTANE);
        if (materials == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between.\nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
        String theme = (String) JOptionPane.showInputDialog(null, "Please select your preferred theme:", appName, JOptionPane.QUESTION_MESSAGE, null, inventory.getAllThemes().toArray(), "");
        if (theme == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between.\nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
        int minPrice = -1, maxPrice = -1;
        while (minPrice < 0) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter the lowest price", appName, JOptionPane.QUESTION_MESSAGE);
            if (userInput == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between.\nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            } else {
                try {
                    minPrice = Integer.parseInt(userInput);
                    if (minPrice < 0)
                        JOptionPane.showMessageDialog(null, "Price must be >= 0.", appName, JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        while (maxPrice < minPrice) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter the highest price", appName, JOptionPane.QUESTION_MESSAGE);
            if (userInput == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between.\nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            } else {
                try {
                    maxPrice = Integer.parseInt(userInput);
                    if (maxPrice < minPrice)
                        JOptionPane.showMessageDialog(null, "Price must be >= " + minPrice, appName, JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        CarSeatCoverSpecs carSeatCoverSpecs = new CarSeatCoverSpecs(new HashSet<>(), new HashSet<>(), theme);
        carSeatCoverSpecs.setDesiredType(type);
        carSeatCoverSpecs.setDesiredMaterials(materials);
        carSeatCoverSpecs.setMinPrice(minPrice);
        carSeatCoverSpecs.setMaxPrice(maxPrice);
        return carSeatCoverSpecs;
    }

    /**
     * process the search and displays the result as per the search criteria
     *
     * @param desiredCarSeatCover a CarSeatCoverSpecs object representing the user's criteria to search
     */
    public static void processSearchResults(CarSeatCoverSpecs desiredCarSeatCover) {
        List<CarSeatCover> matchingSeatCovers = inventory.findMatch(desiredCarSeatCover);
        if (matchingSeatCovers.size() > 0) {
            Map<String, CarSeatCover> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following car seat covers meet your criteria: \n");
            for (CarSeatCover matchingCover : matchingSeatCovers) {
                infoToShow.append(matchingCover.getCarSeatCoverInformation());
                options.put(matchingCover.getProductName(), matchingCover);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which car seat cover you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, null, options.keySet().toArray(), "");
            if (choice == null) System.exit(0);
            CarSeatCover chosenCover = options.get(choice);
            submitOrder(getUserContactInfo(), chosenCover, desiredCarSeatCover.getDesiredType().name());
            JOptionPane.showMessageDialog(null, "Thank you! Your order has been submitted. " + "One of our friendly staff will be in touch shortly.", appName, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Unfortunately none of our car seat covers meet your criteria :(" + "\n\tTo exit, click OK.", appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * method to get user to input name and phone, with appropriate input validation
     *
     * @return a Geek object representing the user of the program
     */
    public static Geek getUserContactInfo() {
        String name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
        if (name == null) System.exit(0);
        long phoneNumber = 0;
        while (phoneNumber == 0) {
            try {
                String userInput = JOptionPane.showInputDialog(null, "Please enter your phone number.", appName, JOptionPane.QUESTION_MESSAGE);
                if (userInput == null) System.exit(0);
                phoneNumber = Long.parseLong(userInput);
            } catch (NumberFormatException e) {
                phoneNumber = Long.parseLong(JOptionPane.showInputDialog(null, "Invalid entry. Please enter your phone number.", appName, JOptionPane.ERROR_MESSAGE));
            }
        }
        return new Geek(name, phoneNumber);
    }

    /**
     * provides Super Geek Auto with a file containing the user's order request
     *
     * @param geek         a Geek object representing the user
     * @param carSeatCover a CarSeatCover object representing the carSeatCover that the user wants to order
     * @param type         a Type Object representing the type user has selected
     */
    public static void submitOrder(Geek geek, CarSeatCover carSeatCover, String type) {
        String filePath = geek.getName().replace(" ", "_") + "_" + carSeatCover.getProductCode() + ".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" + "Name: " + geek.getName() + "\n\tPhone number: 0" + geek.getPhoneNumber() + "\n\tItem: " + carSeatCover.getProductName() + " (" + carSeatCover.getProductCode() + ")" + "\n\tType: " + type;
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            System.out.println("Order could not be placed. \nError message: " + io.getMessage());
            System.exit(0);
        }
    }

    /**
     * method to load all carSeatCover data from file, storing it as CarSeatCover objects in an instance of Inventory
     *
     * @return an Inventory object - functions as database of CarSeatCover, with associated methods
     */
    public static Inventory loadInventory(String filePath) {
        Inventory inventory = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println("File could not be found");
            System.exit(0);
        }
        for (int i = 1; i < fileContents.size(); i++) {
            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");
            String materialsRaw = info[1].replace("]", "").replace(" ", "");
            String typesRaw = info[2].replace("]", "");
            String description = info[3].replace("]", "");
            String name = singularInfo[0];
            long productCode = 0;
            try {
                productCode = Long.parseLong(singularInfo[1]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Product code could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            double price = 0;
            try {
                price = Double.parseDouble(singularInfo[2]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Price could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            String theme = singularInfo[3];
            Set<Materials> materials = new HashSet<>();
            Materials material = null;
            for (String m : materialsRaw.split(",")) {
                try {
                    material = Materials.valueOf(m.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in file. Materials data could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                    System.exit(0);
                }
                materials.add(material);
            }
            Set<Type> types = new HashSet<>();
            Type type = null;
            for (String s : typesRaw.split(",")) {
                try {
                    type = Type.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in file. Type data could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                    System.exit(0);
                }
                types.add(type);
            }
            CarSeatCoverSpecs carSeatCoverSpecs = new CarSeatCoverSpecs(types, materials, theme);
            CarSeatCover carSeatCover = new CarSeatCover(name, productCode, price, description, carSeatCoverSpecs);
            inventory.addItem(carSeatCover);
        }
        return inventory;
    }
}