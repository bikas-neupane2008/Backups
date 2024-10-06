// Part 3 of the Assignment
import java.util.Set;

public class CarSeatCover {
    // 1. Fields to represent the produce name and code, price, theme, description and collection of available types.
    // fields
    private final String name;
    private final String productCode;
    private final double price;
    private final String theme;
    private final Set<Type> types;
    private final String description;

    // In a block comment above the fields identified in Step 2,
    // explain how allowing the user to search based on price range (rather than price value) improves functionality
    /*
     * Allowing users to search by price range instead of a specific price value
     * improves functionality in Java by offering more specific
     * searches, increasing accuracy, enhancing user experience, and simplifying the search process.
     */

    // Fields to be used for the user's search, i.e. min and max price, as well as the user's chosen type.
    private double minPrice;
    private double maxPrice;

    /**
     * constructor to create a CarSeatCover object
     *
     * @param name        the car seat cover
     * @param productCode the car seat cover productCode as String
     * @param price       the car seat price
     * @param theme       the car seat theme
     * @param types       the car seat types
     * @param description the car seat description
     */
    // At least ONE constructor used to initialise fields.
    public CarSeatCover(String name, String productCode, double price, String theme, Set<Type> types, String description) {
        this.name = name;
        this.productCode = productCode;
        this.price = price;
        this.theme = theme;
        this.types = types;
        this.description = description;
    }
    // Getters and setters as appropriate.

    /**
     * @return the car seat cover name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the car seat cover product code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * @return the car seat cover price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return the car seat cover theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @return the car seat cover types
     */
    public Set<Type> getTypes() {
        return types;
    }

    /**
     * @return the car seat cover description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return a user required car seat cover minimum price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minimum price a user is willing to order
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return a user required car seat cover maximum price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maximum price a user is willing to order
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    // A method that returns a description of the car seat cover in the format required by the Greek Geek
    /**
     * @return the item in inventory that meet their search criteria in the user specified format
     */
    public String productDescription(){
        return "Item Name : " + getName() + "\nProduct Description : " + getDescription() + "\nProduct Code : " + getProductCode() + "\nTheme : " + getTheme() + "\nPrice : $" + getPrice();
    }
}