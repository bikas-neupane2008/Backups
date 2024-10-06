import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CarAccessorySearcher {
    private static final String filePath = "./inventory_v3.txt";
    private static final String appName = "SuperGeek Auto";
    private static final String[] args = null;
    private static Inventory inventory;

    /**
     * main method
     * calls loadInventory() by passing file path as parameter and saves in inventory.
     * calls getFilters() and saves as CarAccessorySpecs
     * calls processSearchResults() by passing saved CarAccessorySpecs Object
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
        CarAccessorySpecs desiredCarSeatCover = getFilters();
        processSearchResults(desiredCarSeatCover);
        System.exit(0);
    }

    /**
     * method to get user search criteria
     *
     * @return a CarAccessorySpecs object representing the user search criteria
     */
    public static CarAccessorySpecs getFilters() {
        Map<Filter, Object> desiredFeatures = new HashMap<>();
        AccessoryType accessoryType = (AccessoryType) JOptionPane.showInputDialog(null, "Please select your preferred accessory type:", appName, JOptionPane.QUESTION_MESSAGE, null, AccessoryType.values(), AccessoryType.CAR_SEAT_COVER);
        if (accessoryType == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
        desiredFeatures.put(Filter.ACCESSORY_TYPE, accessoryType);
        Materials materials;
        materials = (Materials) JOptionPane.showInputDialog(null, "Please select your preferred materials:", appName, JOptionPane.QUESTION_MESSAGE, null, Materials.values(), Materials.NA);
        if (materials == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        } else if (materials.equals(Materials.NA)) {
            JOptionPane.showMessageDialog(null, "You skipped the selection of materials.");
        }
        if (!(materials == null || materials.equals(Materials.NA))) desiredFeatures.put(Filter.MATERIAL, materials);
        String theme = (String) JOptionPane.showInputDialog(null, "Please select your preferred theme:", appName, JOptionPane.QUESTION_MESSAGE, null, inventory.getAllThemes().toArray(), "NA");
        if (theme == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        } else if (theme.equals("NA")) {
            JOptionPane.showMessageDialog(null, "You skipped the selection of theme.");
        } else desiredFeatures.put(Filter.THEME, theme);
        if (accessoryType == AccessoryType.CAR_SEAT_COVER) {
            Type type = (Type) JOptionPane.showInputDialog(null, "Please select your preferred type:", appName, JOptionPane.QUESTION_MESSAGE, null, Type.values(), Type.NA);
            if (type == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            } else if (type.equals(Type.NA)) {
                JOptionPane.showMessageDialog(null, "You skipped the selection of type.");
            } else desiredFeatures.put(Filter.TYPE, type);
        } else if (accessoryType == AccessoryType.STEERING_WHEEL_COVER) {
            SteeringWheelDiameter steeringWheelDiameter = (SteeringWheelDiameter) JOptionPane.showInputDialog(null, "Please select your preferred steering wheel diameter:", appName, JOptionPane.QUESTION_MESSAGE, null, SteeringWheelDiameter.values(), SteeringWheelDiameter._NA);
            if (steeringWheelDiameter == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            } else if (steeringWheelDiameter.equals(SteeringWheelDiameter._NA)) {
                JOptionPane.showMessageDialog(null, "You skipped the selection of steering wheel diameter.");
            } else desiredFeatures.put(Filter.SIZE, steeringWheelDiameter);
        }
        double minPrice = -1, maxPrice = -1;
        while (minPrice < 0) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter the lowest price", appName, JOptionPane.QUESTION_MESSAGE);
            if (userInput == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
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
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
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
        return new CarAccessorySpecs(desiredFeatures, minPrice, maxPrice);
    }

    /**
     * process the search and displays the result as per the search criteria
     *
     * @param desiredCarAccessory a CarAccessorySpecs object representing the user's criteria to search
     */
    public static void processSearchResults(CarAccessorySpecs desiredCarAccessory) {
        List<CarAccessory> matchingSeatCovers = inventory.findMatch(desiredCarAccessory);
        if (matchingSeatCovers.size() > 0) {
            Map<String, CarAccessory> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following car accessory meet your criteria: \n");
            for (CarAccessory matchingCover : matchingSeatCovers) {
                infoToShow.append(matchingCover.getCarAccessoryInformation());
                options.put(matchingCover.getProductName(), matchingCover);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which car accessory you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, null, options.keySet().toArray(), "");
            if (choice == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            }
            CarAccessory chosenCover = options.get(choice);
            submitOrder(getUserContactInfo(), chosenCover);
            JOptionPane.showMessageDialog(null, "Thank you! Your order has been submitted. " + "One of our friendly staff will be in touch shortly.", appName, JOptionPane.INFORMATION_MESSAGE);
            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to start again from the beginning?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(null, "Unfortunately none of our car accessory meet your criteria. Do you want to start again from the beginning?", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
    }

    /**
     * method to get user to input name and phone, with appropriate input validation
     *
     * @return a Geek object representing the user of the program
     */
    public static Geek getUserContactInfo() {
        String name = JOptionPane.showInputDialog(null, "Please enter your full name.", appName, JOptionPane.QUESTION_MESSAGE);
        if (name == null) {
            int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            if (confirm == JOptionPane.YES_OPTION) {
                main(args);
            } else {
                JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                System.exit(0);
            }
        }
        long phoneNumber = 0;
        String phoneNumberInput;
        do {
            phoneNumberInput = (String) JOptionPane.showInputDialog(null, "Please enter your phone number. Format: 0000000000", appName, JOptionPane.QUESTION_MESSAGE, null, null, "");
            if (phoneNumberInput == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "You cancelled the program in between. \nDo you want to start again from the beginning?\nClick \"Yes\" to begin new program.\nClick \"No\" to exit the program.", appName, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    main(args);
                } else {
                    JOptionPane.showMessageDialog(null, "Good Bye!", appName, JOptionPane.PLAIN_MESSAGE, null);
                    System.exit(0);
                }
            } else {
                try {
                    phoneNumber = Long.parseLong(phoneNumberInput);
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, "Invalid entry. Please enter your 10 digit phone number.");
                }
            }
        } while (Objects.requireNonNull(phoneNumberInput).length() != 10);
        return new Geek(name, phoneNumber);
    }

    /**
     * provides SuperGeek Auto with a file containing the user's order request
     *
     * @param geek         a Geek object representing the user
     * @param carAccessory a CarAccessory object representing the carAccessory that the user wants to order
     */
    public static void submitOrder(Geek geek, CarAccessory carAccessory) {
        String filePath = geek.getName().replace(" ", "_") + "_" + carAccessory.getProductCode() + ".txt";
        Path path = Path.of(filePath);
        StringBuilder lineToWrite = new StringBuilder("Order details:\n\t" + "Name: " + geek.getName() + "\n\tPhone number: " + geek.getPhoneNumber() + "\n\tItem: " + carAccessory.getProductName() + " (" + carAccessory.getProductCode() + ")" + "\n\tAccessory Type: " + carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.ACCESSORY_TYPE));
        if (carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.ACCESSORY_TYPE).equals(AccessoryType.STEERING_WHEEL_COVER))
            lineToWrite.append("\n\tDiameter: ").append(carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.SIZE));
        if (carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.ACCESSORY_TYPE).equals(AccessoryType.CAR_SEAT_COVER))
            lineToWrite.append("\n\tType: ").append(carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.TYPE));
        try {
            Files.writeString(path, lineToWrite);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Order could not be placed. \nError message: " + io.getMessage());
            System.exit(0);
        }
    }

    /**
     * method to load all carAccessory data from file, storing it as CarAccessory objects in an instance of Inventory
     *
     * @return an Inventory object - functions as database of CarAccessory, with associated methods
     */
    public static Inventory loadInventory(String filePath) {
        Inventory inventory = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "File could not be found");
            System.exit(0);
        }
        for (int i = 1; i < fileContents.size(); i++) {
            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");
            String materialsRaw = info[1].replace("]", "").replace(" ", "");
            String typesRaw = info[2].replace("]", "");
            String description = info[3].replace("]", "");
            String name = singularInfo[0];
            Map<Filter, Object> carAccessoryMap = new HashMap<>();
            long productCode = 0;
            try {
                productCode = Long.parseLong(singularInfo[1]);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Error in file. Product code could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            double price = 0;
            try {
                price = Double.parseDouble(singularInfo[2]);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Error in file. Price could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }
            String theme = singularInfo[3];
            SteeringWheelDiameter steeringWheelDiameter = SteeringWheelDiameter.valueOf("_" + singularInfo[4].toUpperCase());
            Set<Materials> materials = new HashSet<>();
            Materials material = null;
            for (String m : materialsRaw.split(",")) {
                try {
                    material = Materials.valueOf(m.toUpperCase());
                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(null, "Error in file. Materials data could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
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
                    JOptionPane.showMessageDialog(null, "Error in file. Type data could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                    System.exit(0);
                }
                types.add(type);
            }
            carAccessoryMap.put(Filter.MATERIAL, materials);
            carAccessoryMap.put(Filter.THEME, theme);
            if (!steeringWheelDiameter.equals(SteeringWheelDiameter._NA)) {
                carAccessoryMap.put(Filter.ACCESSORY_TYPE, AccessoryType.STEERING_WHEEL_COVER);
                carAccessoryMap.put(Filter.SIZE, steeringWheelDiameter);
            }
            if (!types.contains(Type.NA)) {
                carAccessoryMap.put(Filter.ACCESSORY_TYPE, AccessoryType.CAR_SEAT_COVER);
                carAccessoryMap.put(Filter.TYPE, types);
            }
            CarAccessorySpecs carAccessorySpecs = new CarAccessorySpecs(carAccessoryMap);
            CarAccessory carAccessory = new CarAccessory(name, productCode, price, description, carAccessorySpecs);
            inventory.addItem(carAccessory);
        }
        return inventory;
    }
}