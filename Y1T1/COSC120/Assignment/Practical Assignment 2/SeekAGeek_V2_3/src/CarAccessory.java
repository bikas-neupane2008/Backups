import java.text.DecimalFormat;

/**
 * The CarAccessory class represents a car seat cover product that includes the product name, product code, price,
 * description, and carAccessorySpecs. It also provides a method to retrieve the car accessory information as per user format.
 */
public class CarAccessory {
    private final String productName;
    private final long productCode;
    private final double price;
    private final String description;
    private final CarAccessorySpecs carAccessorySpecs;

    /**
     * Constructs a CarAccessory object with the specified parameters.
     *
     * @param productName       the name of the car seat cover product
     * @param productCode       the unique code assigned to the product
     * @param price             the price of the product
     * @param description       the description of the car seat cover
     * @param carAccessorySpecs the object of CarSeatCoverSpecs
     */
    public CarAccessory(String productName, long productCode, double price, String description, CarAccessorySpecs carAccessorySpecs) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.description = description;
        this.carAccessorySpecs = carAccessorySpecs;
    }

    /**
     * Returns the name of the car accessory product.
     *
     * @return the name of the car accessory product
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns the unique code assigned to the car accessory product.
     *
     * @return the product code of the car accessory product
     */
    public long getProductCode() {
        return productCode;
    }

    /**
     * Returns the price of the car accessory product.
     *
     * @return the price of the car accessory product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the description of the car accessory product.
     *
     * @return the description of the car accessory product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the specifications of the car accessory product.
     *
     * @return the specifications of the car accessory product
     */
    public CarAccessorySpecs getCarAccessorySpecs() {
        return carAccessorySpecs;
    }

    /**
     * Returns a string containing the information of the car accessory including the name,
     * product code, theme and materials from carAccessorySpecs, price, and description.
     *
     * @return a string containing the information of the car accessory
     */
    public String getCarAccessoryInformation() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: " + this.getProductName() + "\nCaption: " + this.getDescription() + "\n\tProduct code: " + this.getProductCode() + getCarAccessorySpecs().getCarAccessorySpecsDescription() + "\n\tPrice: $" + df.format(this.getPrice()) + "\n";
    }
}