import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.HashSet;
import java.util.Set;

// Class to handle user input for search criteria
public class CriteriaEntry {
    // CardLayout to switch between different panels based on accessory type
    private final CardLayout cardLayout = new CardLayout();
    // Panel names for the CardLayout
    private final String TYPE_PANEL = "Type";
    private final String SIZE_PANEL = "Size";
    private final String IMAGE_PANEL = "Image";
    // Labels to provide feedback on minimum and maximum price inputs
    private final JLabel feedbackMin = new JLabel(" ");
    private final JLabel feedbackMax = new JLabel(" ");
    // Panels and fields for user input
    private JPanel accessoryTypeSpecificCriteriaPanel;
    private AccessoryType accessoryType;
    private Material material;
    private Type type;
    private STWDiameter size;
    private Set<String> themes;
    private int maximumPrice;
    private int minimumPrice;
    private double minPrice;
    private double maxPrice;
    private JTextField name;
    private JTextField email;
    private JTextArea message;

    /**
     * Generates the search view panel containing user input fields for search criteria.
     *
     * @return the search view panel
     */
    public JPanel generateSearchView() {
        JPanel criteria = new JPanel();
        criteria.setLayout(new BoxLayout(criteria, BoxLayout.Y_AXIS));

        JPanel type = this.getUserInputAccessoryType();
        type.setAlignmentX(0);
        criteria.add(type);
        JPanel generic = this.userInputGenericCriteria();
        generic.setAlignmentX(0);
        criteria.add(generic);

        accessoryTypeSpecificCriteriaPanel = new JPanel();
        accessoryTypeSpecificCriteriaPanel.setAlignmentX(0);
        accessoryTypeSpecificCriteriaPanel.setLayout(cardLayout);
        accessoryTypeSpecificCriteriaPanel.add(this.generateImagePanel(), IMAGE_PANEL);
        accessoryTypeSpecificCriteriaPanel.add(this.getUserInputType(), TYPE_PANEL);
        accessoryTypeSpecificCriteriaPanel.add(this.getUserInputSize(), SIZE_PANEL);

        criteria.add(accessoryTypeSpecificCriteriaPanel);
        return criteria;
    }

    /**
     * Generates the panel for user input of generic criteria (themes, material, price range).
     *
     * @return the panel for generic criteria input
     */
    public JPanel userInputGenericCriteria() {
        JPanel genericCriteria = new JPanel();
        genericCriteria.setLayout(new BoxLayout(genericCriteria, BoxLayout.Y_AXIS));
        genericCriteria.add(getUserInputThemes());
        genericCriteria.add(getUserInputMaterial());
        genericCriteria.add(getUserInputPriceRange());
        return genericCriteria;
    }

    /**
     * Generates the panel for user input of accessory type.
     *
     * @return the panel for accessory type input
     */
    public JPanel getUserInputAccessoryType() {
        JComboBox<AccessoryType> accessoryTypeJComboBox = new JComboBox<>(AccessoryType.values());
        accessoryTypeJComboBox.requestFocusInWindow();
        accessoryTypeJComboBox.setSelectedItem(AccessoryType.SELECT_ACCESSORY_TYPE);
        accessoryType = (AccessoryType) accessoryTypeJComboBox.getSelectedItem();
        accessoryTypeJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) ifTypeSelected(accessoryTypeJComboBox);
        });

        JPanel accessorySelectionPanel = new JPanel();
        accessorySelectionPanel.setLayout(new BoxLayout(accessorySelectionPanel, BoxLayout.Y_AXIS));
        accessorySelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel instruction = new JLabel("Please select your preferred Accessory Type");
        instruction.setAlignmentX(0);
        accessorySelectionPanel.add(accessoryTypeJComboBox);
        accessorySelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        return accessorySelectionPanel;
    }

    /**
     * Handles the selection of accessory type and switches the panel accordingly.
     *
     * @param accessoryTypeJComboBox the JComboBox for accessory type selection
     */
    public void ifTypeSelected(JComboBox<AccessoryType> accessoryTypeJComboBox) {
        accessoryType = (AccessoryType) accessoryTypeJComboBox.getSelectedItem();
        assert accessoryType != null;
        if (accessoryType.equals(AccessoryType.SELECT_ACCESSORY_TYPE))
            cardLayout.show(accessoryTypeSpecificCriteriaPanel, IMAGE_PANEL);
        else if (accessoryType.equals(AccessoryType.CAR_SEAT_COVER))
            cardLayout.show(accessoryTypeSpecificCriteriaPanel, TYPE_PANEL);
        else if (accessoryType.equals(AccessoryType.STEERING_WHEEL_COVER))
            cardLayout.show(accessoryTypeSpecificCriteriaPanel, SIZE_PANEL);
    }

    /**
     * Generates the panel for image display.
     *
     * @return the image panel
     */
    public JPanel generateImagePanel() {
        int width = 100;
        int height = 100;
        JLabel stw_star_wars = new JLabel(new ImageIcon(new ImageIcon("images/stw_star_wars.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        JLabel csc_star_wars = new JLabel(new ImageIcon(new ImageIcon("images/csc_star_wars.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        JLabel csc_hello_world = new JLabel(new ImageIcon(new ImageIcon("images/csc_hello_world.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        JLabel csc_spider_man = new JLabel(new ImageIcon(new ImageIcon("images/csc_spider_man.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        JLabel stw_coding_crocodile = new JLabel(new ImageIcon(new ImageIcon("images/stw_coding_crocodile.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));

        JPanel imagePanel = new JPanel();
        imagePanel.add(stw_star_wars);
        imagePanel.add(csc_star_wars);
        imagePanel.add(csc_hello_world);
        imagePanel.add(csc_spider_man);
        imagePanel.add(stw_coding_crocodile);

        return imagePanel;
    }

    /**
     * Generates the panel for user input of type.
     *
     * @return the panel for type input
     */
    public JPanel getUserInputType() {
        JComboBox<Type> typeJComboBox = new JComboBox<>(Type.values());
        typeJComboBox.setAlignmentX(0);
        typeJComboBox.setSelectedItem(Type.SELECT_TYPE);
        type = (Type) typeJComboBox.getSelectedItem();
        typeJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                type = (Type) typeJComboBox.getSelectedItem();
            }
        });

        JPanel typePanel = new JPanel(new GridLayout(5, 1));
        JLabel instruction = new JLabel("Please select your preferred Type");
        typePanel.add(instruction);
        typePanel.add(typeJComboBox);
        return typePanel;
    }

    /**
     * Generates the panel for user input of size.
     *
     * @return the panel for size input
     */
    public JPanel getUserInputSize() {
        JComboBox<STWDiameter> sizeJComboBox = new JComboBox<>(STWDiameter.values());
        sizeJComboBox.setAlignmentX(0);
        sizeJComboBox.setSelectedItem(STWDiameter.SELECT_SIZE);
        size = (STWDiameter) sizeJComboBox.getSelectedItem();
        sizeJComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                size = (STWDiameter) sizeJComboBox.getSelectedItem();
            }
        });

        JPanel sizePanel = new JPanel(new GridLayout(5, 1));
        JLabel instruction = new JLabel("Please select your preferred Size");
        sizePanel.add(instruction);
        sizePanel.add(sizeJComboBox);
        return sizePanel;
    }

    /**
     * Generates the panel for user input of themes.
     *
     * @return the panel for themes input
     */
    public JPanel getUserInputThemes() {
        JList<String> selectThemes = new JList<>(Inventory.getAllThemes().toArray(new String[0]));
        selectThemes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(selectThemes);
        selectThemes.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setPreferredSize(new Dimension(100, 120));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));

        ListSelectionListener listSelectionListener = e -> themes = new HashSet<>(selectThemes.getSelectedValuesList());
        selectThemes.addListSelectionListener(listSelectionListener);

        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel instruction = new JLabel("Please select your preferred theme/s (Optional)");
        instruction.setAlignmentX(0);
        themePanel.add(instruction);
        JLabel clarification = new JLabel("(To multi-select, hold Ctrl)");
        clarification.setAlignmentX(0);
        clarification.setFont(new Font("", Font.ITALIC, 12));
        themePanel.add(clarification);
        scrollPane.setAlignmentX(0);
        themePanel.add(scrollPane);
        themePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        return themePanel;
    }

    /**
     * Generates the panel for user input of material.
     *
     * @return the panel for material input
     */
    public JPanel getUserInputMaterial() {
        ButtonGroup materialButtonGroup = new ButtonGroup();
        JRadioButton polyurethane = new JRadioButton(Material.POLYURETHANE.toString());
        JRadioButton leather = new JRadioButton(Material.LEATHER.toString());
        JRadioButton polyester = new JRadioButton(Material.POLYESTER.toString());
        JRadioButton cotton = new JRadioButton(Material.COTTON.toString());
        JRadioButton elastane = new JRadioButton(Material.ELASTANE.toString());
        JRadioButton sheepskin = new JRadioButton(Material.SHEEPSKIN.toString());
        polyurethane.requestFocusInWindow();
        materialButtonGroup.add(polyurethane);
        materialButtonGroup.add(leather);
        materialButtonGroup.add(polyester);
        materialButtonGroup.add(cotton);
        materialButtonGroup.add(elastane);
        materialButtonGroup.add(sheepskin);
        polyurethane.setActionCommand(Material.POLYURETHANE.name());
        leather.setActionCommand(Material.LEATHER.name());
        polyester.setActionCommand(Material.POLYESTER.name());
        cotton.setActionCommand(Material.COTTON.name());
        elastane.setActionCommand(Material.ELASTANE.name());
        sheepskin.setActionCommand(Material.SHEEPSKIN.name());
        ActionListener actionListener = e -> material = Material.valueOf(materialButtonGroup.getSelection().getActionCommand().toUpperCase());
        polyurethane.addActionListener(actionListener);
        leather.addActionListener(actionListener);
        polyester.addActionListener(actionListener);
        cotton.addActionListener(actionListener);
        elastane.addActionListener(actionListener);
        sheepskin.addActionListener(actionListener);

        JPanel materialPanel = new JPanel();
        materialPanel.setAlignmentX(0);
        materialPanel.setBorder(BorderFactory.createTitledBorder("Which material do you prefer? (Optional)"));
        materialPanel.add(polyurethane);
        materialPanel.add(leather);
        materialPanel.add(polyester);
        materialPanel.add(cotton);
        materialPanel.add(elastane);
        materialPanel.add(sheepskin);
        return materialPanel;
    }

    /**
     * Generates the panel for user input of price range.
     *
     * @return the panel for price range input
     */
    public JPanel getUserInputPriceRange() {
        maximumPrice = (int) Inventory.maximumPrice() + 1;
        minimumPrice = (int) Inventory.minimumPrice();
        minPrice = minimumPrice;
        maxPrice = maximumPrice;
        JLabel minLabel = new JLabel("Min. price (Optional)");
        JLabel maxLabel = new JLabel("Max. price (Optional)");
        JTextField min = new JTextField(6);
        JTextField max = new JTextField(6);
        min.setText(String.valueOf(minPrice));
        max.setText(String.valueOf(maxPrice));

        feedbackMin.setFont(new Font("", Font.ITALIC, 12));
        feedbackMin.setForeground(Color.RED);
        feedbackMax.setFont(new Font("", Font.ITALIC, 12));
        feedbackMax.setForeground(Color.RED);

        min.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMin(min)) min.requestFocus();
                checkMax(max);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMin(min)) min.requestFocus();
                checkMax(max);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        max.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!checkMax(max)) max.requestFocusInWindow();
                checkMin(min);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!checkMax(max)) max.requestFocusInWindow();
                checkMin(min);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        JPanel ageRangePanel = new JPanel();
        ageRangePanel.add(minLabel);
        ageRangePanel.add(min);
        ageRangePanel.add(maxLabel);
        ageRangePanel.add(max);

        JPanel finalPanel = new JPanel();
        finalPanel.setBorder(BorderFactory.createTitledBorder("Enter desired Price range"));
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
        finalPanel.setAlignmentX(0);
        finalPanel.add(ageRangePanel);
        feedbackMin.setAlignmentX(0);
        feedbackMax.setAlignmentX(0);
        finalPanel.add(feedbackMin);
        finalPanel.add(feedbackMax);
        return finalPanel;
    }

    /**
     * Validates the minimum price input.
     *
     * @param minEntry the text field for minimum price input
     * @return true if the minimum price is valid, false otherwise
     */
    private boolean checkMin(JTextField minEntry) {
        feedbackMin.setText("");
        try {
            double tempMin = Double.parseDouble(minEntry.getText());
            if (tempMin < minimumPrice || tempMin > maximumPrice) {
                feedbackMin.setText("Minimum price must be in between " + minimumPrice + " and " + maximumPrice + ". Default is " + minimumPrice + " to " + maximumPrice + ".");
                minEntry.selectAll();
                return false;
            } else {
                minPrice = tempMin;
                feedbackMin.setText("");
                return true;
            }
        } catch (NumberFormatException n) {
            feedbackMin.setText("Please enter a valid number for minimum price. Default is " + minimumPrice + " to " + maximumPrice + ".");
            minEntry.selectAll();
            return false;
        }
    }

    /**
     * Validates the maximum price input.
     *
     * @param maxEntry the text field for maximum price input
     * @return true if the maximum price is valid, false otherwise
     */
    private boolean checkMax(JTextField maxEntry) {
        feedbackMax.setText("");
        try {
            double tempMax = Double.parseDouble(maxEntry.getText());
            if (tempMax < minPrice) {
                feedbackMax.setText("Maximum price must be >= " + minPrice + ". Default is " + minimumPrice + " to " + maximumPrice + ".");
                maxEntry.selectAll();
                return false;
            } else {
                maxPrice = tempMax;
                feedbackMax.setText("");
                return true;
            }
        } catch (NumberFormatException n) {
            feedbackMax.setText("Please enter a valid number for maximum price. Default is " + minimumPrice + " to " + maximumPrice + ".");
            maxEntry.selectAll();
            return false;
        }
    }

    /**
     * Generates the panel for user input of contact form.
     *
     * @return the panel for contact form input
     */
    public JPanel contactForm() {
        JLabel enterName = new JLabel("Full name");
        name = new JTextField(12);
        JLabel enterEmail = new JLabel("Email address");
        email = new JTextField(12);
        JLabel enterMessage = new JLabel("Type your message below");
        message = new JTextArea(6, 12);

        JScrollPane jScrollPane = new JScrollPane(message);
        jScrollPane.getViewport().setPreferredSize(new Dimension(250, 100));

        JPanel userInputPanel = new JPanel();
        userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userInputPanel.setAlignmentX(0);
        enterName.setAlignmentX(0);
        name.setAlignmentX(0);
        userInputPanel.add(enterName);
        userInputPanel.add(name);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        enterEmail.setAlignmentX(0);
        email.setAlignmentX(0);
        userInputPanel.add(enterEmail);
        userInputPanel.add(email);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        enterMessage.setAlignmentX(0);
        message.setAlignmentX(0);
        userInputPanel.add(enterMessage);
        jScrollPane.setAlignmentX(0);
        userInputPanel.add(jScrollPane);
        userInputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        return userInputPanel;
    }

    /**
     * Returns the selected accessory type.
     *
     * @return the accessory type
     */
    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    /**
     * Returns the selected material.
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Returns the selected type.
     *
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the selected size.
     *
     * @return the size
     */
    public STWDiameter getSize() {
        return size;
    }

    /**
     * Returns the selected themes.
     *
     * @return the set of themes
     */
    public Set<String> getThemes() {
        return themes;
    }

    /**
     * Returns the minimum price.
     *
     * @return the minimum price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price.
     *
     * @return the maximum price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Returns the entered name from the contact form.
     *
     * @return the name
     */
    public String getName() {
        return name.getText();
    }

    /**
     * Returns the entered email from the contact form.
     *
     * @return the email
     */
    public String getEmail() {
        return email.getText();
    }

    /**
     * Returns the entered message from the contact form.
     *
     * @return the message
     */
    public String getMessage() {
        return message.getText();
    }
}
