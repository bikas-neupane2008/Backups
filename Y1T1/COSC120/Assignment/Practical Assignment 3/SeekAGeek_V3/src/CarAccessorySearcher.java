import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

/**
 * A GUI application for searching and ordering car accessories.
 */
public class CarAccessorySearcher {

    private static final String appName = "SuperGeek Auto";
    private static final String iconFilePath = "./icon.png";
    private static final String filePath = "./inventory_v3.txt";
    private static JFrame mainWindow = null;
    private static JPanel searchView = null;
    private static JPanel userInformationView = null;
    private static CriteriaEntry criteriaEntry;
    private static ImageIcon icon = new ImageIcon(iconFilePath);
    private static Inventory inventory = null;

    /**
     * The main entry point of the CarAccessorySearcher application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        inventory = loadInventory(filePath);
        mainWindow = new JFrame(appName);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        icon = new ImageIcon(iconFilePath);
        mainWindow.setIconImage(icon.getImage());
        mainWindow.setMinimumSize(new Dimension(400, 200));
        searchView = enterCriteria();
        mainWindow.setContentPane(searchView);
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
    }

    /**
     * Creates and returns the search view panel where users can enter their search criteria.
     *
     * @return the search view panel
     */
    public static JPanel enterCriteria() {
        JPanel searchWindow = new JPanel();
        searchWindow.setLayout(new BorderLayout());
        criteriaEntry = new CriteriaEntry();
        searchWindow.add(criteriaEntry.generateSearchView(), BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        ActionListener actionListener = e -> conductSearch(criteriaEntry);
        searchButton.addActionListener(actionListener);
        searchWindow.add(searchButton, BorderLayout.SOUTH);
        searchWindow.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        searchWindow.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        return searchWindow;
    }

    /**
     * Conducts the search based on the user's criteria and displays the results.
     *
     * @param criteriaEntry the criteria entered by the user
     */
    public static void conductSearch(CriteriaEntry criteriaEntry) {
        Map<Filter, Object> specs = new HashMap<>();
        AccessoryType accessoryType = criteriaEntry.getAccessoryType();
        Set<String> theme = criteriaEntry.getThemes();
        Material material = criteriaEntry.getMaterial();
        double minPrice = criteriaEntry.getMinPrice();
        double maxPrice = criteriaEntry.getMaxPrice();
        Type type = criteriaEntry.getType();
        STWDiameter size = criteriaEntry.getSize();

        if (accessoryType.equals(AccessoryType.SELECT_ACCESSORY_TYPE)) {
            JOptionPane.showMessageDialog(mainWindow, "You MUST select an accessory type.\n", "Invalid search", JOptionPane.INFORMATION_MESSAGE, icon);
            return;
        }
        if (accessoryType.equals(AccessoryType.CAR_SEAT_COVER)) {
            if (type.equals(Type.SELECT_TYPE)) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST select a Type.\n", "Invalid search", JOptionPane.INFORMATION_MESSAGE, icon);
                return;
            }
        }
        if (accessoryType.equals(AccessoryType.STEERING_WHEEL_COVER)) {
            if (size.equals(STWDiameter.SELECT_SIZE)) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST select a Size.\n", "Invalid search", JOptionPane.INFORMATION_MESSAGE, icon);
                return;
            }
        }

        specs.put(Filter.ACCESSORY_TYPE, accessoryType);
        if (theme != null) specs.put(Filter.THEME, theme);
        if (material != null && !material.equals(Material.SELECT_MATERIAL)) specs.put(Filter.MATERIAL, material);
        if (type != null && !type.equals(Type.SELECT_TYPE)) specs.put(Filter.TYPE, type);
        if (size != null && !size.equals(STWDiameter.SELECT_SIZE)) specs.put(Filter.DIAMETER, size);

        CarAccessorySpecs carAccessorySpecs = new CarAccessorySpecs(specs, minPrice, maxPrice);
        List<CarAccessory> compatibleAccessories = inventory.findMatch(carAccessorySpecs);

        showResults(compatibleAccessories);
    }

    /**
     * Displays the search results in the application window.
     *
     * @param compatibleAccessories the list of compatible car accessories
     */
    public static void showResults(List<CarAccessory> compatibleAccessories) {
        if (compatibleAccessories.size() > 0) {
            Map<String, CarAccessory> options = new LinkedHashMap<>();
            options.put("Select Accessory", null);
            JPanel accessoryDescriptions = new JPanel();
            accessoryDescriptions.setBorder(BorderFactory.createTitledBorder("Matches found!! The following accessories meet your criteria: "));
            accessoryDescriptions.setLayout(new BoxLayout(accessoryDescriptions, BoxLayout.Y_AXIS));
            accessoryDescriptions.add(Box.createRigidArea(new Dimension(0, 10)));

            for (CarAccessory compatibleAccessory : compatibleAccessories) {
                accessoryDescriptions.add(describeIndividualAccessory(compatibleAccessory));
                options.put(compatibleAccessory.getProductName() + " (" + compatibleAccessory.getProductCode() + ")", compatibleAccessory);
            }

            JScrollPane verticalScrollBar = new JScrollPane(accessoryDescriptions);
            verticalScrollBar.setPreferredSize(new Dimension(300, 450));
            verticalScrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            SwingUtilities.invokeLater(() -> verticalScrollBar.getViewport().setViewPosition(new Point(0, 0)));

            JComboBox<String> optionsCombo = new JComboBox<>(options.keySet().toArray(new String[0]));
            ActionListener actionListener = e -> checkUserAccessorySelection(options, optionsCombo);
            optionsCombo.addActionListener(actionListener);

            JButton goBackToSearch = new JButton("Back to search");
            goBackToSearch.addActionListener(e -> {
                mainWindow.setContentPane(searchView);
                mainWindow.revalidate();
            });

            JButton sendMessage = new JButton("Send message");
            sendMessage.addActionListener(e -> userSendMessage());

            JPanel selectionPanel = new JPanel();
            selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.LINE_AXIS));
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            selectionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            selectionPanel.add(optionsCombo);
            selectionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            selectionPanel.add(goBackToSearch);
            selectionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            selectionPanel.add(sendMessage);
            selectionPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            selectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            JPanel results = new JPanel();
            results.setLayout(new BorderLayout());
            results.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
            results.add(verticalScrollBar, BorderLayout.CENTER);
            results.add(selectionPanel, BorderLayout.SOUTH);
            mainWindow.setContentPane(results);
            mainWindow.revalidate();
        } else {
            JOptionPane.showMessageDialog(searchView, "Unfortunately none of our accessories meet your criteria.\n", "No Matching Accessories", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    /**
     * Creates a text area description for an individual car accessory.
     *
     * @param carAccessory the car accessory
     * @return the text area description
     */
    public static JTextArea describeIndividualAccessory(CarAccessory carAccessory) {
        JTextArea accessoryDescription = new JTextArea(carAccessory.getInformation(Filter.values()));
        accessoryDescription.setEditable(false);
        accessoryDescription.setLineWrap(true);
        accessoryDescription.setWrapStyleWord(true);
        return accessoryDescription;
    }

    /**
     * Checks the user's selection from the accessory options combo box and takes appropriate action.
     *
     * @param options      the available accessory options
     * @param optionsCombo the accessory options combo box
     */
    public static void checkUserAccessorySelection(Map<String, CarAccessory> options, JComboBox<String> optionsCombo) {
        String accessoryName = (String) optionsCombo.getSelectedItem();
        if (options.get(accessoryName) != null) {
            CarAccessory chosenAccessory = options.get(accessoryName);
            placeOrderRequest(chosenAccessory);
        }
    }

    /**
     * Displays the user information form for sending a message.
     */
    public static void userSendMessage() {
        JPanel contactInfo = criteriaEntry.contactForm();
        JButton submit = new JButton("Send Message");
        ActionListener actionListener = e -> {
            String lineToWrite = "Name: " + criteriaEntry.getName() + " \nEmail: " + criteriaEntry.getEmail() + "\nMessage: " + criteriaEntry.getMessage();
            if (criteriaEntry.getName().length() == 0) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST enter your name.\n", "Invalid Order", JOptionPane.INFORMATION_MESSAGE, icon);
            } else if (criteriaEntry.getEmail().length() == 0) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST enter your email.\n", "Invalid Order", JOptionPane.INFORMATION_MESSAGE, icon);
            } else if (criteriaEntry.getMessage().length() == 0) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST enter your message.\n", "Invalid Order", JOptionPane.INFORMATION_MESSAGE, icon);
            }else writeMessageToFile(lineToWrite);
        };
        submit.addActionListener(actionListener);

        JPanel jPanelForm = new JPanel();
        contactInfo.setAlignmentX(0);
        jPanelForm.add(contactInfo);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        messagePanel.add(jPanelForm, BorderLayout.CENTER);
        messagePanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        messagePanel.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
        messagePanel.add(submit, BorderLayout.SOUTH);

        userInformationView = messagePanel;
        mainWindow.setContentPane(userInformationView);
        mainWindow.revalidate();
    }

    /**
     * Writes the user's message to a file.
     *
     * @param lineToWrite the message to write
     */
    public static void writeMessageToFile(String lineToWrite) {
        String folderPath = "Order";
        String fileName = criteriaEntry.getName().replace(" ", "_") + "_order.txt";
        Path folder = Paths.get(folderPath);
        Path filePath = folder.resolve(fileName);

        try {
            if (!Files.exists(folder)) {
                Files.createDirectory(folder);
            }
            Files.writeString(filePath, lineToWrite);
            JOptionPane.showMessageDialog(mainWindow, "Thank you for your message. \nOne of our friendly staff will be in touch shortly. \nClose this dialog to begin from the beginning", "Message Sent", JOptionPane.INFORMATION_MESSAGE);
            mainWindow.dispose();
            main(null);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(mainWindow, "Error: Message could not be sent! Please try again!", null, JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the place order request form for a chosen car accessory.
     *
     * @param chosenAccessory the chosen car accessory
     */
    public static void placeOrderRequest(CarAccessory chosenAccessory) {
        JLabel accessoryMessage = new JLabel("To place an order request for " + chosenAccessory.getProductName() + " fill in the form below");
        accessoryMessage.setAlignmentX(0);
        JScrollPane jScrollPane = new JScrollPane(describeIndividualAccessory(chosenAccessory));
        jScrollPane.getViewport().setPreferredSize(new Dimension(300, 150));
        jScrollPane.setAlignmentX(0);
        JPanel accessoryDescriptionPanel = new JPanel();
        accessoryDescriptionPanel.setLayout(new BorderLayout());
        accessoryDescriptionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        accessoryDescriptionPanel.add(accessoryMessage, BorderLayout.NORTH);
        accessoryDescriptionPanel.add(jScrollPane, BorderLayout.CENTER);
        JPanel userInputPanel = criteriaEntry.contactForm();
        userInputPanel.setAlignmentX(0);
        JButton submit = new JButton("Submit");
        ActionListener actionListener = e -> {
            String lineToWrite = "Name: " + criteriaEntry.getName() + " \nEmail: " + criteriaEntry.getEmail() + "\nMessage: " + criteriaEntry.getMessage() + "\n" + criteriaEntry.getName() + " has ordered " + chosenAccessory.getProductName() + " (" + chosenAccessory.getProductCode() + ")";
            if (criteriaEntry.getName().length() == 0) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST enter your name for the order.\n", "Invalid Order", JOptionPane.INFORMATION_MESSAGE, icon);
            } else if (criteriaEntry.getEmail().length() == 0) {
                JOptionPane.showMessageDialog(mainWindow, "You MUST enter your email for the order.\n", "Invalid Order", JOptionPane.INFORMATION_MESSAGE, icon);
            } else writeMessageToFile(lineToWrite);
        };
        submit.addActionListener(actionListener);

        JPanel mainFramePanel = new JPanel();
        mainFramePanel.setLayout(new BorderLayout());
        mainFramePanel.add(accessoryDescriptionPanel, BorderLayout.NORTH);
        mainFramePanel.add(userInputPanel, BorderLayout.CENTER);
        mainFramePanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        mainFramePanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        mainFramePanel.add(submit, BorderLayout.SOUTH);

        userInformationView = mainFramePanel;
        mainWindow.setContentPane(userInformationView);
        mainWindow.revalidate();
    }

    /**
     * Loads the car accessory inventory from a file.
     *
     * @param filePath the path of the inventory file
     * @return the loaded inventory
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
            String materialsRaw = info[1].replace("]", "");
            String typesRaw = info[2].replace("]", "");
            String description = info[3].replace("]", "");

            String name = singularInfo[0];

            AccessoryType accessoryType;
            if (name.toUpperCase().contains("STW")) accessoryType = AccessoryType.STEERING_WHEEL_COVER;
            else accessoryType = AccessoryType.CAR_SEAT_COVER;

            long productCode = 0;
            try {
                productCode = Long.parseLong(singularInfo[1]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Product code could not be parsed for accessory on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            double price = 0;
            try {
                price = Double.parseDouble(singularInfo[2]);
            } catch (NumberFormatException n) {
                System.out.println("Error in file. Price could not be parsed for accessory on line " + (i + 1) + ". Terminating. \nError message: " + n.getMessage());
                System.exit(0);
            }

            String theme = singularInfo[3];

            STWDiameter diameter = null;
            if (!singularInfo[4].equals("NA")) {
                try {
                    diameter = STWDiameter.valueOf("_" + singularInfo[4]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in file. Diameter could not be parsed for steering wheel on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                    System.exit(0);
                }
            }
            Set<Material> materials = new HashSet<>();
            for (String s : materialsRaw.split(",")) {
                Material material = Material.POLYESTER;
                try {
                    material = Material.valueOf(s.strip().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error in file. Material data could not be parsed for accessory on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                    System.exit(0);
                }
                materials.add(material);
            }

            Set<Type> types = new HashSet<>();
            for (String s : typesRaw.split(",")) {
                Type type = null;
                if (!s.equalsIgnoreCase("NA")) {
                    try {
                        type = Type.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error in file. Type data could not be parsed for seat cover on line " + (i + 1) + ". Terminating. \nError message: " + e.getMessage());
                        System.exit(0);
                    }
                    types.add(type);
                }
            }

            Map<Filter, Object> filterMap = new LinkedHashMap<>();
            filterMap.put(Filter.ACCESSORY_TYPE, accessoryType);
            filterMap.put(Filter.THEME, theme);
            filterMap.put(Filter.MATERIAL, materials);
            if (diameter != null) filterMap.put(Filter.DIAMETER, diameter);
            if (types.size() != 0) filterMap.put(Filter.TYPE, types);

            CarAccessorySpecs carAccessorySpecs = new CarAccessorySpecs(filterMap);
            CarAccessory carAccessory = new CarAccessory(name, productCode, price, description, carAccessorySpecs);
            inventory.addItem(carAccessory);
        }
        return inventory;
    }
}
